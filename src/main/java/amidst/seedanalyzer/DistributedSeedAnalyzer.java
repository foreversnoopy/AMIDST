package amidst.seedanalyzer;

import java.io.IOException;
import java.util.Collection;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import amidst.logging.AmidstLogger;
import amidst.mojangapi.minecraftinterface.MinecraftInterface;
import amidst.mojangapi.world.biome.UnknownBiomeIdException;

public class DistributedSeedAnalyzer {
	private static final int CLIENT_ID = 1;

	private String path;
	private String serverAddress;
	
	private boolean stop;

	private MinecraftInterface minecraftInterface;

	public DistributedSeedAnalyzer(String path, String serverAddress, MinecraftInterface minecraftInterface) {
		this.path = path;
		this.serverAddress = serverAddress;
		this.minecraftInterface = minecraftInterface;

		// Only one time
		Unirest.setObjectMapper(new JsonDateObjectMapper());
	}

	public void analyzeSeeds() throws UnirestException, IOException, InterruptedException, UnknownBiomeIdException
	{
		SeedAnalyzer seedAnalyser = new SeedAnalyzer(this.path, this.minecraftInterface);
		
		String urlPostWorkItems = "http://" + this.serverAddress + "/api/workitems/";
		String urlNewWorkItems = "http://" + this.serverAddress + "/api/workitems/new";
		
		while (!stop)
		{
			try
			{
				HttpResponse<WorkItem> bookResponse = Unirest.get(urlNewWorkItems).asObject(WorkItem.class);
				
				WorkItem workItem = bookResponse.getBody();
				
				Collection<FilterResults> bestSeeds = seedAnalyser.analyzeSeeds(workItem.StartSeed, workItem.EndSeed);
				
				for(FilterResults results : bestSeeds)
				{
					results.ClientId = CLIENT_ID;
				}
				
				WorkItemResults workItemResults = new WorkItemResults();
				workItemResults.WorkItem = workItem;
				workItemResults.BestSeeds = bestSeeds;
				
				Unirest.post(urlPostWorkItems)
			       .header("accept", "application/json")
			       .header("Content-Type", "application/json")
			       .body(workItemResults)
			       .asJson();
			}
			catch (Exception e)
			{
				AmidstLogger.warn(e, "Error in distributed seed analyzer client. Retrying in 5 seconds.");
				Thread.sleep(5000);
			}
		}
	}
	
	public void stop()
	{
		this.stop = true;
		
		try
		{
			Unirest.shutdown();
		}
		catch (IOException e)
		{
			AmidstLogger.warn(e);
		}
	}
}