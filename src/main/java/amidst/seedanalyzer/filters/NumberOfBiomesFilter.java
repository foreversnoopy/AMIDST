package amidst.seedanalyzer.filters;

import java.util.Collection;

import amidst.mojangapi.world.biome.Biome;
import amidst.seedanalyzer.FilterResults;

public class NumberOfBiomesFilter extends Filter
{
	private int id;
	private int minimum;

	public NumberOfBiomesFilter(int id, Collection<Biome> biomesInFilter, int minimum)
	{
		super(biomesInFilter);

		this.id = id;
		this.minimum = minimum;
	}

	@Override
	public int getId()
	{
		return this.id;
	}

	@Override
	public FilterResults getResults(int[] biomesSum, double[] biomesAreaPercentage, int allBiomesCount)
	{
		FilterResults results = new FilterResults();
		
		results.FilterId = getId();
		results.Value = allBiomesCount;
		results.CriteriaMet = allBiomesCount >= this.minimum;
		
		return results;
	}
}
