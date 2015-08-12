package amidst.seedanalyzer;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

import amidst.logging.AmidstLogger;
import amidst.mojangapi.minecraftinterface.MinecraftInterface;
import amidst.mojangapi.minecraftinterface.MinecraftInterfaceException;
import amidst.mojangapi.world.*;
import amidst.mojangapi.world.biome.*;
import amidst.mojangapi.world.coordinates.CoordinatesInWorld;
import amidst.mojangapi.world.versionfeatures.*;
import amidst.seedanalyzer.filters.AllBiomeGroupsFilter;
import amidst.seedanalyzer.filters.BiomeAreaFilter;
import amidst.seedanalyzer.filters.Filter;
import amidst.seedanalyzer.filters.FilterStatistics;
import amidst.seedanalyzer.filters.NumberOfBiomeGroupsFilter;
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
	private VersionFeatures versionFeatures;

	private HashMap<Integer, Filter> filters;
	private HashMap<Integer, String> filterSavePaths = new HashMap<Integer, String>();

	private int radius;
	private boolean saveResults;

	private boolean stop;

	public SeedAnalyzer(String path, int radius, MinecraftInterface minecraftInterface) throws UnknownBiomeIdException {
		this.versionFeatures = createVersionFeaturesBuilder().create(minecraftInterface.getRecognisedVersion());
		this.namedBiomes = new NamedBiomeList(minecraftInterface.getRecognisedVersion(), versionFeatures);
		this.minecraftInterface = minecraftInterface;
		this.path = path + File.separator + "radius" + File.separator;
		this.saveResults = (path != null && path != "");
		this.radius = radius;

		createFilters();

		if (this.saveResults)
		{
			for(Filter filter : filters.values())
			{
				File d = new File(path + File.separator + radius + File.separator + filter.getId() + File.separator);

				if (!d.exists())
				{
					d.mkdirs();
				}

				this.filterSavePaths.put(filter.getId(), d.getAbsolutePath());
			}
		}
	}

	public VersionFeatures.Builder createVersionFeaturesBuilder()
	{
		return createVersionFeaturesBuilder(new MockMinecraftWorld());
	}

	public VersionFeatures.Builder createVersionFeaturesBuilder(MinecraftInterface.World world)
	{
		final WorldOptions worldOptions = new WorldOptions(WorldSeed.fromSaveGame(0), WorldType.DEFAULT);

		final MinecraftInterface.World minecraftWorld = world;

		return DefaultVersionFeatures.builder(worldOptions, minecraftWorld);
	}

	public static class MockMinecraftWorld implements MinecraftInterface.World
	{
		@Override
		public<T> T getBiomeData(final Dimension dimension, final int x, final int y, final int width, final int height, final boolean useQuarterResolution, final Function<int[], T> biomeDataMapper) {
			throw new UnsupportedOperationException();
		}

		@Override
		public Set<Dimension> supportedDimensions() {
			return EnumSet.allOf(Dimension.class);
		}
	}

	private void createFilters() {
		this.filters = new HashMap<Integer, Filter>();

		Filter filter = new RegularBiomesFilter(namedBiomes);

		filters.put(filter.getId(), filter);
		
		filter = new SpecialBiomesFilter(-24, namedBiomes); // v1 and v2 : -20, v3 : -25 -> -24
		filters.put(filter.getId(), filter);

		filter = new AllBiomeGroupsFilter(namedBiomes);
		filters.put(filter.getId(), filter);
		
		//Added in v3, id #4
		filter = new NumberOfBiomeGroupsFilter(namedBiomes);
		filters.put(filter.getId(), filter);
		
		ArrayList<Biome> biomesAllForestTypes = new ArrayList<Biome>();

		biomesAllForestTypes.addAll(namedBiomes.biomesBirchForest.getBiomes());
		biomesAllForestTypes.addAll(namedBiomes.biomesForest.getBiomes());
		biomesAllForestTypes.addAll(namedBiomes.biomesRoofedForest.getBiomes());
		biomesAllForestTypes.addAll(namedBiomes.biomesTaiga.getBiomes());
		biomesAllForestTypes.addAll(namedBiomes.biomesMegaTaiga.getBiomes());

		filter = new BiomeAreaFilter(100, biomesAllForestTypes, 80); // v1 and v2 : 60, v3 : 70 -> 80
		filters.put(filter.getId(), filter);

		ArrayList<Biome> biomesArid = new ArrayList<Biome>();

		biomesArid.addAll(namedBiomes.biomesBeach.getBiomes());
		biomesArid.addAll(namedBiomes.biomesDesert.getBiomes());
		biomesArid.addAll(namedBiomes.biomesMesa.getBiomes());

		filter = new BiomeAreaFilter(110, biomesArid, 80); // v1 : 50, v2 : 55, v3 : 65 -> 80
		filters.put(filter.getId(), filter);

		ArrayList<Biome> biomesHills = new ArrayList<Biome>();

		biomesHills.addAll(namedBiomes.biomesColl.stream().filter(b -> b.getName().contains("Hills")).collect(Collectors.toList()));

		filter = new BiomeAreaFilter(120, biomesHills, 52); // v1 : 30, v2 : 40, v3 : 43 -> 52
		filters.put(filter.getId(), filter);
		
		
		filter = new BiomeAreaFilter(1000, namedBiomes.biomesBeach.getBiomes(), 9.75); // v1 : 10, v2 : 8, v3 : 9 -> 9.75
		filters.put(filter.getId(), filter);
		
		filter = new BiomeAreaFilter(2000, namedBiomes.biomesBirchForest.getBiomes(), 32); // v1 : 15, v2 : 20, v3 : 29 -> 32
		filters.put(filter.getId(), filter);
		
		filter = new BiomeAreaFilter(3000, namedBiomes.biomesDesert.getBiomes(), 68); // v1 : 40, v2 : 50, v3 : 58 -> 68
		filters.put(filter.getId(), filter);
		
		filter = new BiomeAreaFilter(4000, namedBiomes.biomesExtremeHills.getBiomes(), 45); // v1 : 25, v2 : 30, v3 : 37 -> 45
		filters.put(filter.getId(), filter);
		
		filter = new BiomeAreaFilter(5000, namedBiomes.biomesForest.getBiomes(), 50); // v1 : 40, v2 : 35, v3 : 42 -> 50
		filters.put(filter.getId(), filter);
		
		filter = new BiomeAreaFilter(6000, namedBiomes.biomesIce.getBiomes(), 82); // v1 : 40, v2 : 60, v3 : 73 -> 82
		filters.put(filter.getId(), filter);
		
		filter = new BiomeAreaFilter(7000, namedBiomes.biomesJungle.getBiomes(), 70); // v1 : 20, v2 : 35, v3 : 54 -> 70
		filters.put(filter.getId(), filter);
		
		filter = new BiomeAreaFilter(8000, namedBiomes.biomesMegaTaiga.getBiomes(), 60); // v1 : 20, v2 : 30, v3 : 49 -> 60
		filters.put(filter.getId(), filter);
		
		filter = new BiomeAreaFilter(9000, namedBiomes.biomesMesa.getBiomes(), 68); // v1 : 20, v2 : 35, v3 : 49 -> 68
		filters.put(filter.getId(), filter);
		
		filter = new BiomeAreaFilter(10000, namedBiomes.biomesMushroomIsland.getBiomes(), 6); // v1 and v2 : 2, v3 : 4 -> 6
		filters.put(filter.getId(), filter);
		
		filter = new BiomeAreaFilter(11000, namedBiomes.biomesOcean.getBiomes(), 93); // v1 : 80, v2 : 85, v3 : 92 -> 93
		filters.put(filter.getId(), filter);
		
		filter = new BiomeAreaFilter(12000, namedBiomes.biomesPlains.getBiomes(), 36); // v1 : 25, v2 : 25, v3 : 30 -> 36
		filters.put(filter.getId(), filter);
		
		filter = new BiomeAreaFilter(13000, namedBiomes.biomesRiver.getBiomes(), 6.5); // v1 : 10, v2 : 5.7, v3 : 6 -> 6.5
		filters.put(filter.getId(), filter);
		
		filter = new BiomeAreaFilter(14000, namedBiomes.biomesRoofedForest.getBiomes(), 23); // v1 : 15, v2 : 14, v3 : 19 -> 23
		filters.put(filter.getId(), filter);
		
		filter = new BiomeAreaFilter(15000, namedBiomes.biomesSavanna.getBiomes(), 49); // v1 : 25, v2 : 34, v3 : 45 -> 49
		filters.put(filter.getId(), filter);
		
		filter = new BiomeAreaFilter(16000, namedBiomes.biomesSwampland.getBiomes(), 33); // v1 : 15, v2 : 20, v3 : 29 -> 33
		filters.put(filter.getId(), filter);
		
		filter = new BiomeAreaFilter(17000, namedBiomes.biomesTaiga.getBiomes(), 45); // v1 : 20, v2 : 30, v3 : 35 -> 45
		filters.put(filter.getId(), filter);
	}

	public SeedAnalysisResults analyzeSeeds(long startSeed, long endSeed) throws IOException, MinecraftInterfaceException, UnknownBiomeIdException {
		HashMap<Integer, ArrayList<FilterResults>> allResults = new HashMap<Integer, ArrayList<FilterResults>>();

		AmidstLogger.info("New work item: " + startSeed + " to " + endSeed +".");

		Stopwatch stopwatch = new Stopwatch();

		long analyzedCount = 0;

		long seed = startSeed;

		while (!stop && seed <= endSeed)
		{
			Collection<FilterResults> results = analyzeSeed(seed, this.radius, filters.values());

			addResults(results, allResults, filters);

			analyzedCount++;

			reportProgress(seed);

			seed++;
		}

		Collection<FilterStatistics> statistics = compileStatistics(allResults);

		Collection<FilterResults> filteredResults = getFilteredResults(allResults, filters);

		reportRunCompleted(analyzedCount, stopwatch);

		SeedAnalysisResults seedAnalysisResults = new SeedAnalysisResults();
		seedAnalysisResults.FilteredSeeds = filteredResults;
		seedAnalysisResults.Statistics = statistics;

		return seedAnalysisResults;
	}

	private Collection<FilterStatistics> compileStatistics(HashMap<Integer, ArrayList<FilterResults>> allResults)
	{
		HashMap<Integer, FilterStatistics> statisticsByFilter = new HashMap<Integer, FilterStatistics>();

		for(Filter filter : filters.values())
		{
			Collection<FilterResults> resultsInFilter = allResults.get(filter.getId());

			FilterStatistics stats = new FilterStatistics();
			stats.FilterId = filter.getId();

			double min = 999, max = -999, total = 0;

			for(FilterResults result : resultsInFilter)
			{
				min = Math.min(min, result.Value);

				if (result.Value > max)
				{
					max = result.Value;
					stats.MaxValue = result.Value;
					stats.MaxSeed = result.SeedId;
				}

				total += result.Value;
			}

			stats.MinValue = min;
			stats.AvgValue = total / (double)resultsInFilter.size();

			statisticsByFilter.put(filter.getId(), stats);
		}

		return statisticsByFilter.values();
	}

	public Collection<FilterResults> analyzeSeed(long seed, int radius, Collection<Filter> filters)	throws FileNotFoundException, UnsupportedEncodingException, IOException, MinecraftInterfaceException, UnknownBiomeIdException
	{
		ArrayList<FilterResults> results = new ArrayList<FilterResults>();

		MinecraftInterface.World world = minecraftInterface.createWorld(seed, WorldType.DEFAULT, "");

		CoordinatesInWorld spawn = createVersionFeaturesBuilder(world).create(minecraftInterface.getRecognisedVersion()).get(FeatureKey.WORLD_SPAWN_ORACLE).get();
		
		if (spawn == null) {
			spawn = CoordinatesInWorld.origin();
		}

		// Quarter resolution, centered on (0, 0)
		int[] biomeData = world.getBiomeData(Dimension.OVERWORLD, radius / 4, radius / 4, radius / 2, radius / 2, true, data -> {
			return data.clone();
		});
		
		int[] biomesSum = getBiomesSum(biomeData);
		
		double[] biomesAreaPercentage = getBiomesAreaPercentage(biomesSum, biomeData.length);
		
		int allBiomesCount = Filter.countBiomes(biomesSum, namedBiomes.biomesColl);
		
		for(Filter filter : filters)
		{
			FilterResults result = filter.getResults(biomesSum, biomesAreaPercentage, allBiomesCount);
			result.SeedId = seed;
			
			if (result.CriteriaMet)
			{
				AmidstLogger.info("Seed found matching filterId=" + filter.getId() + " : " + seed);

				if (this.saveResults)
				{
					saveBiomeAnalysis(seed, filter.getId(), biomesAreaPercentage);
					
					saveScreenshot(seed, filter.getId(), biomeData, radius);
				}
			}
			
			results.add(result);
		}
		
		return results;
	}

	private int[] getBiomesSum(int[] biomeData)
	{
		int[] biomesSum = new int[1 + namedBiomes.biomesColl.stream().map(b -> b.getId()).max((id1, id2) -> Integer.compare(id1, id2)).get()];
		
		for (int i = 0; i < biomeData.length; i++)
		{
			biomesSum[biomeData[i]]++;
		}

		return biomesSum;
	}
	
	private double[] getBiomesAreaPercentage(int[] biomesSum, int totalArea)
	{
		double[] biomesAreaPercentage = new double[biomesSum.length];
		
		for (int i = 0; i < biomesSum.length; i++)
		{
			biomesAreaPercentage[i] = ((double)biomesSum[i] / (double)totalArea) * 100d;
		}
		
		return biomesAreaPercentage;
	}

	private void saveBiomeAnalysis(long seed, int filterId, double[] biomesAreaPercentage) throws IOException
	{
		File file = new File(this.filterSavePaths.get(filterId) + File.separator + seed + ".txt");

		PrintWriter writer = new PrintWriter(file, "UTF-8");
		
		for(Biome b : namedBiomes.biomesColl)
		{
			if (b != null)
			{
				writer.printf("%30s\t\t%3.2f%n", b.getName(), (float)biomesAreaPercentage[b.getId()]);
			}
		}
		
		writer.close();
	}

	private void saveScreenshot(long seed, int filterId, int[] biomeData, int radius) throws FileNotFoundException, IOException, UnknownBiomeIdException
	{
		FileOutputStream outputStream = new FileOutputStream(this.filterSavePaths.get(filterId) + File.separator + seed + ".png");
		
		MinecraftMapRgbImage image = new MinecraftMapRgbImage(biomeData, radius);
		
		ImageInfo imageInfo = new ImageInfo(radius / 2, radius / 2, 8, false); // 8 bits per channel, no alpha
		
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
	
	private Collection<FilterResults> getFilteredResults(HashMap<Integer, ArrayList<FilterResults>> allResults,
			HashMap<Integer, Filter> filters)
	{
		ArrayList<FilterResults> bestResults = new ArrayList<FilterResults>();
		
		for(Entry<Integer, ArrayList<FilterResults>> resultsByFilter : allResults.entrySet())
		{
			ArrayList<FilterResults> resultsInFilter = resultsByFilter.getValue();
			
			bestResults.addAll(resultsInFilter.stream().filter(r -> r.CriteriaMet).collect(Collectors.toList()));
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
	
	private void reportRunCompleted(long analyzedCount, Stopwatch stopwatch)
	{
		double elapsedTime = Math.round(stopwatch.elapsedTime() * 10d) / 10d;

		long numberOfSeeds = Math.abs(analyzedCount);
		
		double rate = numberOfSeeds / elapsedTime;
		
		rate = Math.round(rate * 10d) / 10d;

		AmidstLogger.info("Run completed : " + numberOfSeeds + " seeds in " + elapsedTime + " seconds (" + rate + " seeds per second)");
	}

	public void stop()
	{
		this.stop = true;
	}
}
