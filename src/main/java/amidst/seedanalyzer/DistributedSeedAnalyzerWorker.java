package amidst.seedanalyzer;

import amidst.logging.AmidstLogger;
import amidst.mojangapi.minecraftinterface.MinecraftInterface;

public class DistributedSeedAnalyzerWorker extends DistributedSeedAnalyzer implements ThreadedSeedAnalyzer
{
	private boolean isStopped;

	public DistributedSeedAnalyzerWorker(String path, String serverAddress, MinecraftInterface minecraftInterface)
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