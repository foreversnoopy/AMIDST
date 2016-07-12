package amidst.seedanalyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import amidst.mojangapi.world.biome.Biome;
import amidst.mojangapi.world.biome.UnknownBiomeIdException;
import amidst.seedanalyzer.filters.Filter;
import ar.com.hjg.pngj.ImageInfo;
import ar.com.hjg.pngj.ImageLineHelper;
import ar.com.hjg.pngj.ImageLineInt;
import ar.com.hjg.pngj.PngWriter;
import ar.com.hjg.pngj.chunks.PngChunkTextVar;

public class ResultsRepository {
	private HashMap<Integer, String> filterSavePaths = new HashMap<Integer, String>();
	private NamedBiomeList namedBiomes;

	public ResultsRepository(String savePath, HashMap<Integer, Filter> filters, NamedBiomeList namedBiomes) {
		this.namedBiomes = namedBiomes;

		for (Filter filter : filters.values()) {
			File d = new File(savePath + File.separator + filter.getId() + File.separator);

			if (!d.exists()) {
				d.mkdirs();
			}

			this.filterSavePaths.put(filter.getId(), d.getAbsolutePath());
		}
	}

	public void saveBiomeAnalysis(long seed, int filterId, double[] biomesAreaPercentage) throws IOException {
		File file = new File(this.filterSavePaths.get(filterId) + File.separator + seed + ".txt");

		PrintWriter writer = new PrintWriter(file, "UTF-8");

		for (Biome b : namedBiomes.biomesColl) {
			if (b != null) {
				writer.printf("%30s\t\t%3.2f%n", b.getName(), (float) biomesAreaPercentage[b.getId()]);
			}
		}

		writer.close();
	}

	public void saveScreenshot(long seed, int filterId, int[] biomeData, int radius)
			throws FileNotFoundException, IOException, UnknownBiomeIdException
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
}