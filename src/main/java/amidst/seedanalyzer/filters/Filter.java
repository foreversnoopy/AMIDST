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
	
	protected Filter(Collection<Biome> biomesInFilter)
	{
		this.biomesInFilter = new ArrayList<Biome>(biomesInFilter);
	}
	
	public abstract int getId();
	
	public abstract FilterResults getResults(int[] biomesSum, double[] biomesAreaPercentage, int allBiomesCount);

	public ArrayList<Biome> getBiomes()
	{
		return new ArrayList<Biome>(biomesInFilter);
	}
	
	public int getNumberOfBiomesInFilter()
	{
		return this.biomesInFilter.size();
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
		return biomesInFilter.size() - countBiomes(biomesSum, biomesInFilter);
	}

	public int getTotalArea(int[] biomesSum)
	{
		return getTotalArea(biomesSum, this.biomesInFilter);
	}
	
	public static int getTotalArea(int[] biomesSum, Collection<Biome> biomesInFilter)
	{
		int sum = 0;
		
		for(Biome biome : biomesInFilter)
		{
			sum += biomesSum[biome.getId()];
		}
		
		return sum;
	}
	
	public double getTotalAreaPercentage(double[] biomesAreaPercentage)
	{
		return getTotalAreaPercentage(biomesAreaPercentage, this.biomesInFilter);
	}
	
	public static double getTotalAreaPercentage(double[] biomesAreaPercentage, Collection<Biome> biomesInFilter)
	{
		int sum = 0;
		
		for(Biome biome : biomesInFilter)
		{
			sum += biomesAreaPercentage[biome.getId()];
		}
		
		return sum;
	}
	
	@Override
	public int compare(FilterResults resultA, FilterResults resultB)
	{
		if (resultA.FilterId == resultB.FilterId && resultA.FilterId == getId())
		{
			if (resultA.CriteriaMet && resultB.CriteriaMet)
			{
				return 0;
			}
			else if (resultA.CriteriaMet)
			{
				return -1;
			}
			else if (resultB.CriteriaMet)
			{
				return 1;
			}
			else
			{
				if (resultA.Value > resultB.Value)
				{
					return -1;
				}
				else if (resultA.Value < resultB.Value)
				{
					return 1;
				}
				else
				{
					return 0;
				}
			}
		}
		else
		{
			return 1;
		}
	}
}