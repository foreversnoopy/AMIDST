package amidst.seedanalyzer.filters;

import java.util.Comparator;

import amidst.mojangapi.world.biome.Biome;
import amidst.seedanalyzer.FilterResults;

public abstract class Filter implements Comparator<FilterResults>
{
	public abstract int getId();
	
	public abstract FilterResults getResults(int[] biomesSum);
	
	public int countMissingBiomes(int[] biomesSum, Biome[] biomesInFilter)
	{
		int missingBiomes = 0;
		
		int i = 0;
		
		Biome biome = biomesInFilter[i];
		
		while (biome != null && i < biomesInFilter.length)
		{
			biome = biomesInFilter[i];
			
			if (biomesSum[biome.getId()] <= 0)
			{
				missingBiomes++;
			}
			
			i++;
		}
		
		return missingBiomes;
	}
}
