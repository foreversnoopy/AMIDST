package amidst.seedanalyzer.filters;

import amidst.seedanalyzer.BiomeGroup;
import amidst.seedanalyzer.FilterResults;
import amidst.seedanalyzer.NamedBiomeList;

public class AllBiomeGroupsFilter extends Filter
{
	public AllBiomeGroupsFilter(NamedBiomeList namedBiomes)
	{
		super(namedBiomes.biomesColl);
	}

	@Override
	public int getId()
	{
		return 3;
	}

	@Override
	public FilterResults getResults(int[] biomesSum, double[] biomesAreaPercentage, int allBiomesCount)
	{
		int missingGroups = 0;
		
		for (BiomeGroup biomeGroup : BiomeGroup.biomeGroups)
		{
			int area = getTotalArea(biomesSum, biomeGroup.getBiomes());
			
			if (area < 2) // v1, v2 and v3 : 2
			{
				missingGroups++;
			}
		}
		
		FilterResults results = new FilterResults();
		
		results.FilterId = getId();
		results.Value = missingGroups * -1;
		results.CriteriaMet = missingGroups == 0;
		
		return results;
	}
}