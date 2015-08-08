package amidst.seedanalyzer;

import amidst.mojangapi.world.biome.BiomeColor;
import amidst.mojangapi.world.biome.UnknownBiomeIdException;
import amidst.settings.biomeprofile.BiomeProfile;
import amidst.settings.biomeprofile.BiomeProfileSelection;

public class MinecraftMapRgbImage {
	private BiomeProfileSelection biomeProfileSelection = new BiomeProfileSelection(BiomeProfile.getDefaultProfile());
	private int[] biomeData;
	private int radius;

	public MinecraftMapRgbImage(int[] biomeData, int radius) {
		this.biomeData = biomeData;
		this.radius = radius;
	}

	// @Override
	public int getRgb888Pixel(int x, int y) throws UnknownBiomeIdException {
		int biomeId = biomeData[y * this.radius / 2 + x];

		BiomeColor color = biomeProfileSelection.getBiomeColor(biomeId);
		
		return color.getRGB();
	}
}