package amidst.seedanalyzer.filters;

import java.util.ArrayList;

import amidst.mojangapi.world.biome.Biome;
import amidst.seedanalyzer.FilterResults;

public class BiomeGroupFilter extends Filter
{
	private int id;
	private double minimumArea;
	
	public BiomeGroupFilter(int id, ArrayList<Biome> biomesInFilter, double minimumArea)
	{
		super(biomesInFilter);
		
		this.id = id;
		this.minimumArea = minimumArea;
	}

	@Override
	public int compare(FilterResults resultA, FilterResults resultB)
	{
		if (resultA.FilterId == resultB.FilterId && resultA.FilterId == getId())
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
		else
		{
			return 1;
		}
	}

	@Override
	public int getId()
	{
		return this.id;
	}

	@Override
	public FilterResults getResults(int[] biomesSum)
	{
		double area = getTotalArea(biomesSum);
		
		FilterResults results = new FilterResults();
		
		results.FilterId = getId();
		results.Value = area;
		results.CriteriaMet = area > this.minimumArea;
		
		return results;
	}
}