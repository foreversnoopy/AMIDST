package amidst.seedanalyzer;

import amidst.logging.AmidstLogger;
import amidst.mojangapi.minecraftinterface.MinecraftInterface;
import amidst.mojangapi.world.biome.UnknownBiomeIdException;

public class RunnableSeedAnalyzer extends SeedAnalyzer implements ThreadedSeedAnalyzer {
	private long startSeed = Long.MIN_VALUE;
	private long endSeed = Long.MAX_VALUE;

	public RunnableSeedAnalyzer(String path, MinecraftInterface minecraftInterface) throws UnknownBiomeIdException
	{
		super(path, minecraftInterface);
	}
	
	public RunnableSeedAnalyzer(String path, long startSeed, long endSeed, MinecraftInterface minecraftInterface)
			throws UnknownBiomeIdException
	{
		super(path, minecraftInterface);
		
		this.startSeed = startSeed;
		this.endSeed = endSeed;
	}
	
	@Override
	public void run()
	{
		try
		{
			analyzeSeeds(this.startSeed, this.endSeed);
		}
		catch (Exception e)
		{
			AmidstLogger.warn("Seed analyzer error. \n" + e.toString());
		}
	};
}