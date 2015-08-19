package amidst.seedanalyzer;

public interface ThreadedSeedAnalyzer extends Runnable
{
	public void stop();
	
	public boolean isStopped();
}
