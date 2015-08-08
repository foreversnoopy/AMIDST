package amidst.seedanalyzer;

import amidst.logging.AmidstLogger;
import amidst.mojangapi.minecraftinterface.MinecraftInterface;

public class RunnableDistributedSeedAnalyzer extends DistributedSeedAnalyzer implements ThreadedSeedAnalyzer
{
	public RunnableDistributedSeedAnalyzer(String path, String serverAddress, MinecraftInterface minecraftInterface)
	{
		super(path, serverAddress, minecraftInterface);
	}

	@Override
	public void run()
	{
		try
		{
			analyzeSeeds();
		}
		catch (Exception e)
		{
			AmidstLogger.crash("Seed analyzer error. \n" + e.toString());
		}
	}
}