package amidst.seedanalyzer.filters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import amidst.minecraft.Biome;
import amidst.seedanalyzer.BiomeGroup;
import amidst.seedanalyzer.FilterResults;

public class AllBiomeGroupsWithRareBiomes extends AllBiomeGroupsFilter
{
	private List<Biome> extraBiomes;

	public AllBiomeGroupsWithRareBiomes()
	{
		this.extraBiomes = new ArrayList<Biome>();
		
		this.extraBiomes.add(Biome.icePlainsSpikes);
		this.extraBiomes.add(Biome.mesaBryce);
		this.extraBiomes.add(Biome.savannaPlateauM);
	}

	@Override
	public int getId()
	{
		return 5;
	}

	@Override
	public FilterResults getResults(int[] biomesSum, double[] biomesAreaPercentage, int allBiomesCount)
	{
		int missingExtraBiomes = countMissingBiomes(biomesSum, extraBiomes);
		int missingBiomeGroups = AllBiomeGroupsFilter.countMissingBiomeGroups(biomesSum, BiomeGroup.biomeGroups);
		
		FilterResults results = new FilterResults();
		
		results.FilterId = getId();
		results.Value = (missingBiomeGroups + missingExtraBiomes) * -1;
		results.CriteriaMet = results.Value == 0;
		
		return results;
	}

}
