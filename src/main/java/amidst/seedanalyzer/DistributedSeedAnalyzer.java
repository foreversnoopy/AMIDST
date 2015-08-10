package amidst.seedanalyzer;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.async.Callback;
import com.mashape.unirest.http.exceptions.UnirestException;

import amidst.logging.AmidstLogger;
import amidst.mojangapi.minecraftinterface.MinecraftInterface;
import amidst.mojangapi.world.biome.UnknownBiomeIdException;
import amidst.seedanalyzer.filters.FilterStatistics;

public class DistributedSeedAnalyzer {
	private static final int CLIENT_ID = 2;

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
		SeedAnalyzer seedAnalyser = new SeedAnalyzer(this.path, 1536, this.minecraftInterface);
		
		String urlNewWorkItems = "http://" + this.serverAddress + "/api/workitems/getnew";
		
		
		while (!stop)
		{
			try
			{
				HttpResponse<WorkItem> workItemResponse = Unirest.get(urlNewWorkItems).asObject(WorkItem.class);
				
				WorkItem workItem = workItemResponse.getBody();
				
				SeedAnalysisResults seedAnalysisResults = seedAnalyser.analyzeSeeds(workItem.StartSeed, workItem.EndSeed);
				
				//SeedAnalysisResults seedAnalysisResults = seedAnalyser.analyzeSeeds(-9223372036854773094l, -9223372036854773094l);
				
				assignClientId(seedAnalysisResults);
				
				WorkItemResults workItemResults = new WorkItemResults();
				workItemResults.WorkItem = workItem;
				workItemResults.FilteredSeeds = seedAnalysisResults.FilteredSeeds;
				
				postResultsAndStatistics(workItemResults, seedAnalysisResults);
			}
			catch (Exception e)
			{
				AmidstLogger.warn(e, "Error in distributed seed analyzer client. Retrying in 5 seconds.");
				Thread.sleep(5000);
			}
		}
		
		close();
	}

	private void postResultsAndStatistics(WorkItemResults workItemResults, SeedAnalysisResults seedAnalysisResults) throws UnirestException, InterruptedException, ExecutionException
	{
		String urlPostWorkItems = "http://" + this.serverAddress + "/api/workitems/post";
		String urlPostStatistics = "http://" + this.serverAddress + "/api/statistics/poststatistics";
		
		if (workItemResults.FilteredSeeds.size() > 0)
		{
			AmidstLogger.info("Posting " + workItemResults.FilteredSeeds.size() + " new results.");
		}
		
		/*
		Future<HttpResponse<JsonNode>> futureResponse = Unirest.post(urlPostWorkItems)
		   .header("accept", "application/json")
		   .header("Content-Type", "application/json")
		   .body(workItemResults)
		   .asJsonAsync(new Callback<JsonNode>()
		   {
			@Override
			public void failed(UnirestException arg0)
			{
				Log.w("HTTP POST error : " + arg0.getMessage());
			}
			
			@Override
			public void completed(HttpResponse<JsonNode> arg0)
			{
				Log.i("HTTP POST completed (work item)");
			}
			
			@Override
			public void cancelled()
			{
				Log.w("HTTP POST canceled (work item)");
			}
		});
		
		HttpResponse<JsonNode> response = futureResponse.get();*/
		
		HttpResponse<JsonNode> response = Unirest.post(urlPostWorkItems)
				   .header("accept", "application/json")
				   .header("Content-Type", "application/json")
				   .body(workItemResults)
				   .asJson();
		
		if (response.getStatus() != 204)
		{
			AmidstLogger.warn("HTTP POST error : " + response.getStatus() + " " + response.getStatusText());
		}
		
		/*
		futureResponse = Unirest.post(urlPostStatistics)
		   .header("accept", "application/json")
		   .header("Content-Type", "application/json")
		   .body(seedAnalysisResults.Statistics)
		   .asJsonAsync(new Callback<JsonNode>()
		   {
			@Override
			public void failed(UnirestException arg0)
			{
				Log.w("HTTP POST error : " + arg0.getMessage());
			}
			
			@Override
			public void completed(HttpResponse<JsonNode> arg0)
			{
				Log.i("HTTP POST completed (statistiques)");
			}
			
			@Override
			public void cancelled()
			{
				Log.w("HTTP POST canceled (statistics)");
			}
		});
		
		response = futureResponse.get();*/
		
		response = Unirest.post(urlPostStatistics)
				   .header("accept", "application/json")
				   .header("Content-Type", "application/json")
				   .body(seedAnalysisResults.Statistics)
				   .asJson();
		
		if (response.getStatus() != 204)
		{
			AmidstLogger.warn("HTTP POST error : " + response.getStatus() + " " + response.getStatusText());
		}
	}

	private void assignClientId(SeedAnalysisResults seedAnalysisResults)
	{
		for(FilterResults results : seedAnalysisResults.FilteredSeeds)
		{
			results.ClientId = CLIENT_ID;
		}
		
		for(FilterStatistics stats : seedAnalysisResults.Statistics)
		{
			stats.ClientId = CLIENT_ID;
		}
	}
	
	public void stop()
	{
		this.stop = true;
	}

	private void close()
	{
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