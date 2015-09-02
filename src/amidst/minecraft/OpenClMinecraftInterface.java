package amidst.minecraft;

import amidst.version.VersionInfo;
import genlayers.GenLayer;
import genlayers.IntCache;
import genlayers.opencl.GenLayerChainOpenCL;

public class OpenClMinecraftInterface implements IMinecraftInterface
{
	private GenLayer biomeGen;
	
	@Override
	public int[] getBiomeData(int x, int y, int width, int height, boolean useQuarterResolutionMap)
	{
		return biomeGen.getInts(x, y, width, height);
	}

	@Override
	public void createWorld(long seed, String type, String generatorOptions)
	{
		IntCache.resetIntCache();
		
		biomeGen = GenLayerChainOpenCL.createGenLayerChainHybrid(seed);
	}

	@Override
	public VersionInfo getVersion()
	{
		return null;
	}
}
