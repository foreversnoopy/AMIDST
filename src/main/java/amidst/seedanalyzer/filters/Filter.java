package amidst.seedanalyzer.filters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

import amidst.mojangapi.world.biome.Biome;
import amidst.seedanalyzer.FilterResults;

public abstract class Filter implements Comparator<FilterResults>
{
	private ArrayList<Biome> biomesInFilter;
	
	@SuppressWarnings("unchecked")
	protected Filter(ArrayList<Biome> biomesInFilter)
	{
		this.biomesInFilter = (ArrayList<Biome>)biomesInFilter.clone();
	}
	
	public abstract int getId();
	
	public abstract FilterResults getResults(int[] biomesSum);
	
	@SuppressWarnings("unchecked")
	public Collection<Biome> getBiomes()
	{
		return (Collection<Biome>)this.biomesInFilter.clone();
	}
	
	public int countBiomes(int[] biomesSum)
	{
		return countBiomes(biomesSum, this.biomesInFilter);
	}
	
	public static int countBiomes(int[] biomesSum, Collection<Biome> biomesInFilter)
	{
		int biomesCount = 0;
		
		int i = 0;
		
		Iterator<Biome> iterator = biomesInFilter.iterator();
		
		while (iterator.hasNext() && i < biomesInFilter.size())
		{
			Biome biome = iterator.next();
			
			if (biomesSum[biome.getId()] > 0)
			{
				biomesCount++;
			}
			
			i++;
		}
		
		return biomesCount;
	}
	
	public int countMissingBiomes(int[] biomesSum)
	{
		return countMissingBiomes(biomesSum, this.biomesInFilter);
	}
	
	public static int countMissingBiomes(int[] biomesSum, Collection<Biome> biomesInFilter)
	{
		int missingBiomes = 0;
		
		int i = 0;
		
		Iterator<Biome> iterator = biomesInFilter.iterator();
		
		while (iterator.hasNext() && i < biomesInFilter.size())
		{
			Biome biome = iterator.next();
			
			if (biomesSum[biome.getId()] <= 0)
			{
				missingBiomes++;
			}
			
			i++;
		}
		
		return missingBiomes;
	}
	
	public int getTotalArea(int[] biomesSum)
	{
		return countMissingBiomes(biomesSum, this.biomesInFilter);
	}
	
	public static double getTotalArea(int[] biomesSum, Collection<Biome> biomesInFilter)
	{
		double sum = 0;
		
		for(Biome biome : biomesInFilter)
		{
			sum += biomesSum[biome.getId()];
		}
		
		return sum;
	}
	
	@Override
	public int compare(FilterResults resultA, FilterResults resultB)
	{
		if (resultA.FilterId == resultB.FilterId && resultA.FilterId == getId())
		{
			if (resultA.Value < resultB.Value)
			{
				return -1;
			}
			else if (resultA.Value > resultB.Value)
			{
				return 1;
			}
			else
			{
				return 0;
			}
		}
		else
		{
			return 1;
		}
	}
}