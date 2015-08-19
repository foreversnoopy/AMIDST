package amidst.seedanalyzer;

import amidst.logging.AmidstLogger;
import amidst.mojangapi.minecraftinterface.MinecraftInterface;

public class RunnableDistributedSeedAnalyzer extends DistributedSeedAnalyzer implements ThreadedSeedAnalyzer
{
	private boolean isStopped;

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
		
		this.isStopped = true;
	}

	@Override
	public boolean isStopped()
	{
		return this.isStopped;
	}
}