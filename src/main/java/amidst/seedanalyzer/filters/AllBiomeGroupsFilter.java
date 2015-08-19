package amidst.seedanalyzer.filters;

import java.util.Collection;

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
		int missingGroups = countMissingBiomeGroups(biomesSum, BiomeGroup.biomeGroups);

		FilterResults results = new FilterResults();
		
		results.FilterId = getId();
		results.Value = missingGroups * -1;
		results.CriteriaMet = missingGroups == 0;
		
		return results;
	}

	public static int countMissingBiomeGroups(int[] biomesSum, Collection<BiomeGroup> biomeGroups)
	{
		int missingGroups = 0;
		
		for (BiomeGroup biomeGroup : biomeGroups)
		{
			int area = getTotalArea(biomesSum, biomeGroup.getBiomes());
			
			if (area < 2) // v1, v2, v3 and v4 : 2
			{
				missingGroups++;
			}
		}
		return missingGroups;
	}
}