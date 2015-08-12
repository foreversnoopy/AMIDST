package amidst.seedanalyzer.filters;

import amidst.seedanalyzer.FilterResults;
import amidst.seedanalyzer.NamedBiomeList;

public class NumberOfBiomeGroupsFilter extends AllBiomeGroupsFilter
{
	public NumberOfBiomeGroupsFilter(NamedBiomeList namedBiomes)
	{
		super(namedBiomes);
	}

	@Override
	public int getId()
	{
		return 4;
	}

	@Override
	public FilterResults getResults(int[] biomesSum, double[] biomesAreaPercentage, int allBiomesCount)
	{
		FilterResults results = super.getResults(biomesSum, biomesAreaPercentage, allBiomesCount);
		
		results.Value = allBiomesCount;
		
		return results;
	}
}