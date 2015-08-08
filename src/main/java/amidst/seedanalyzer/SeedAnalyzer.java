package amidst.seedanalyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map.Entry;

import amidst.logging.AmidstLogger;
import amidst.mojangapi.minecraftinterface.MinecraftInterface;
import amidst.mojangapi.minecraftinterface.MinecraftInterfaceException;
import amidst.mojangapi.world.*;
import amidst.mojangapi.world.biome.*;
import amidst.seedanalyzer.filters.Filter;
import amidst.seedanalyzer.filters.RegularBiomesFilter;
import amidst.seedanalyzer.filters.SpecialBiomesFilter;
import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.ImageLineHelper;
import ar.com.hjg.pngj.ImageLineInt;
import ar.com.hjg.pngj.PngWriter;
import ar.com.hjg.pngj.chunks.PngChunkTextVar;

public class SeedAnalyzer {
	private String path;
	private NamedBiomeList namedBiomes;
	private MinecraftInterface minecraftInterface;

	public SeedAnalyzer(String path, MinecraftInterface minecraftInterface) throws UnknownBiomeIdException {
		this.path = path;
		this.namedBiomes = new NamedBiomeList();
		this.minecraftInterface = minecraftInterface;
	}

	public Collection<FilterResults> run(long startSeed, long endSeed) throws IOException, MinecraftInterfaceException,
			UnknownBiomeIdException
	{
		Stopwatch stopwatch = new Stopwatch();

		HashMap<Integer, Filter> filters = new HashMap<Integer, Filter>();
		Filter filter = new RegularBiomesFilter(namedBiomes);
		filters.put(filter.getId(), filter);
		
		filter = new SpecialBiomesFilter(namedBiomes);
		filters.put(filter.getId(), filter);
		
		HashMap<Integer, ArrayList<FilterResults>> allResults = new HashMap<Integer, ArrayList<FilterResults>>();
		
		for (long seed = startSeed; seed <= endSeed; seed++)
		{
			Collection<FilterResults> results = analyzeSeed(seed, filters.values(), 2048);
			
			addResults(results, allResults, filters);
			
			reportProgress(seed);
		}
		
		Collection<FilterResults> bestResults = getBestResults(allResults, filters);
		
		reportRunCompleted(startSeed, endSeed, stopwatch);
		
		return bestResults;
	}

	private Collection<FilterResults> analyzeSeed(long seed, Collection<Filter> filters, int radius)	throws FileNotFoundException, UnsupportedEncodingException, IOException, MinecraftInterfaceException,
			UnknownBiomeIdException
	{
		MinecraftInterface.World world = minecraftInterface.createWorld(seed, WorldType.DEFAULT, "");
		
		int[] biomeData = world.getBiomeData(Dimension.OVERWORLD, radius / -4, radius / -4, radius / 2, radius / 2, true, data -> {
			return data.clone();
		});
		
		int[] biomesSum = sumBiomes(biomeData);
		
		ArrayList<FilterResults> results = new ArrayList<FilterResults>();
		
		for(Filter filter : filters)
		{
			FilterResults result = filter.getResults(biomesSum);
			result.SeedId = seed;
			
			results.add(result);
		}
		
		boolean criteriaMet = false;
		
		for(FilterResults result : results)
		{
			criteriaMet = criteriaMet | result.CriteriaMet;
		}
		
		if (criteriaMet)
		{
			saveBiomeAnalysis(seed, biomesSum, biomeData.length);
			
			saveScreenshot(seed, biomeData, radius);
		}
		
		return results;
	}

	private int[] sumBiomes(int[] biomeData)
	{
		int[] biomesSum = new int[256];
		
		for (int i = 0; i < biomeData.length; i++)
		{
			biomesSum[biomeData[i]]++;
		}
		
		return biomesSum;
	}

	private void saveBiomeAnalysis(long seed, int[] biomesSum, int divisor)	throws FileNotFoundException, UnsupportedEncodingException
	{
		File f = new File(path + File.separator + seed + ".txt");

		PrintWriter writer = new PrintWriter(f, "UTF-8");
		
		for(Biome b : namedBiomes.biomes.iterable())
		{
			if (b != null)
			{
				writer.printf("%30s\t\t%3.2f%n", b.getName(), ((float)biomesSum[b.getId()]) / divisor * 100);
				//writer.println();
			}
		}
		
		writer.close();
	}

	private void saveScreenshot(long seed, int[] biomeData, int radius) throws FileNotFoundException, IOException,
			UnknownBiomeIdException
	{
		MinecraftMapRgbImage image = new MinecraftMapRgbImage(biomeData, radius);
	
		ImageInfo imageInfo = new ImageInfo(radius / 2, radius / 2, 8, false); // 8 bits per channel, no alpha

		FileOutputStream outputStream = new FileOutputStream(path + File.separator + seed + ".png");
		
        PngWriter png = new PngWriter(outputStream, imageInfo);
        
        // add some optional metadata (chunks)
        png.getMetadata().setDpi(100.0);
        png.getMetadata().setTimeNow(0); // 0 seconds fron now = now
        png.getMetadata().setText(PngChunkTextVar.KEY_Title, "Seed " + seed);

        for (int row = 0; row < png.imgInfo.rows; row++)
        {
        	ImageLineInt line = new ImageLineInt(imageInfo);
        	
        	for (int col = 0; col < imageInfo.cols; col++)
            {
                ImageLineHelper.setPixelRGB8(line, col, image.getRgb888Pixel(col, row));
            }
        	
            png.writeRow(line);
        }
        
        try
		{
			png.end();
		}
		finally
		{
			outputStream.close();
		}
		/*
		BmpImage bmp = new BmpImage();
		bmp.image = image;
		
		FileOutputStream outputStream = new FileOutputStream(path + "\\"  + seed + ".bmp");
		
		try
		{
			BmpWriter.write(outputStream, bmp);
		}
		finally
		{
			outputStream.close();
		}*/
	}
	
	private void addResults(Collection<FilterResults> results,
			HashMap<Integer, ArrayList<FilterResults>> allResults, HashMap<Integer, Filter> filters)
	{
		for(FilterResults result : results)
		{
			ArrayList<FilterResults> resultsInFilter;
			
			if (allResults.containsKey(result.FilterId))
			{
				resultsInFilter = allResults.get(result.FilterId);
			}
			else
			{
				resultsInFilter = new ArrayList<FilterResults>();
				
				allResults.put(result.FilterId, resultsInFilter);
			}
			
			resultsInFilter.add(result);
			
			
		}
	}
	
	private Collection<FilterResults> getBestResults(HashMap<Integer, ArrayList<FilterResults>> allResults,
			HashMap<Integer, Filter> filters)
	{
		ArrayList<FilterResults> bestResults = new ArrayList<FilterResults>();
		
		for(Entry<Integer, ArrayList<FilterResults>> resultsByFilter : allResults.entrySet())
		{
			ArrayList<FilterResults> resultsInFilter = resultsByFilter.getValue();
			
			Filter filter = filters.get(resultsByFilter.getKey());
			
			resultsInFilter.sort(filter);
			
			int i = 0;
			boolean criteriaMet = true;
			
			while (i < resultsInFilter.size() && (criteriaMet || i < 100))
			{
				FilterResults best = resultsInFilter.get(i);
				
				criteriaMet = best.CriteriaMet;
				
				bestResults.add(best);
				
				i++;
			}
		}
		
		return bestResults;
	}
	
	private void reportProgress(long seed)
	{
		if (Math.abs(seed % 100) == 0)
		{
			AmidstLogger.info(String.valueOf(seed));
		}
	}
	
	private void reportRunCompleted(long startSeed, long endSeed, Stopwatch stopwatch)
	{
		// For some reason you can't do the rounding in one instruction. It won't round properly.
		double elapsedTime = stopwatch.elapsedTime();
		
		elapsedTime = Math.round(elapsedTime * 10);
		
		elapsedTime = elapsedTime / 10;
		
		long numberOfSeeds = Math.abs(endSeed - startSeed) + 1;
		
		double rate = numberOfSeeds / elapsedTime;
		
		rate = Math.round(rate * 10);
		
		rate = rate / 10;
		
		AmidstLogger.info("Run completed : " + numberOfSeeds + " seeds in " + elapsedTime + " seconds (" + rate + " seeds per second)");
	}
}
