package amidst.seedanalyzer.filters;

import java.util.ArrayList;
import java.util.List;

import amidst.mojangapi.world.biome.Biome;
import amidst.seedanalyzer.BiomeGroup;
import amidst.seedanalyzer.FilterResults;
import amidst.seedanalyzer.NamedBiomeList;

public class AllBiomeGroupsWithRareBiomes extends AllBiomeGroupsFilter
{
	private List<Biome> extraBiomes;

	public AllBiomeGroupsWithRareBiomes(NamedBiomeList namedBiomes)
	{
		super(namedBiomes);
		
		this.extraBiomes = new ArrayList<Biome>();
		
		this.extraBiomes.add(namedBiomes.icePlainsSpikes);
		this.extraBiomes.add(namedBiomes.mesaBryce);
		this.extraBiomes.add(namedBiomes.savannaPlateauM);
	}

	@Override
	public int getId()
	{
		return 6;
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
