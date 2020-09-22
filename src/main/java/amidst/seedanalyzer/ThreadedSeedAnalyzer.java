package amidst.seedanalyzer;

import amidst.threading.worker.Worker;

public interface ThreadedSeedAnalyzer extends Runnable, Worker
{
	public void stop();
	
	public boolean isStopped();
}
