package amidst.seedanalyzer.filters;

import java.util.Collection;

import amidst.mojangapi.world.biome.Biome;
import amidst.seedanalyzer.FilterResults;

public class BiomeAreaFilter extends Filter
{
	private int id;
	private double minimumArea;
	
	public BiomeAreaFilter(int id, Collection<Biome> biomesInFilter, double minimumArea)
	{
		super(biomesInFilter);
		
		this.id = id;
		this.minimumArea = minimumArea;
	}

	@Override
	public int getId()
	{
		return this.id;
	}

	@Override
	public FilterResults getResults(int[] biomesSum, double[] biomeAreaPercentage, int biomeCount)
	{
		double area = getTotalAreaPercentage(biomeAreaPercentage);
		
		FilterResults results = new FilterResults();
		
		results.FilterId = getId();
		results.Value = area;
		results.CriteriaMet = area > this.minimumArea;
		
		return results;
	}
}