package amidst.seedanalyzer.filters;

import java.util.ArrayList;
import java.util.Collection;

import amidst.mojangapi.world.biome.Biome;
import amidst.seedanalyzer.FilterResults;
import amidst.seedanalyzer.NamedBiomeList;

public class RegularBiomesFilter extends Filter
{
	private Collection<Biome> allBiomes;

	public RegularBiomesFilter(NamedBiomeList namedBiomes)
	{
		super(getBiomesInFilter(namedBiomes));
		
		this.allBiomes = new AllBiomeGroupsFilter(namedBiomes).getBiomes();
	}
	
	private static ArrayList<Biome> getBiomesInFilter(NamedBiomeList namedBiomes)
	{
		ArrayList<Biome> biomes = new ArrayList<Biome>();
		
		biomes.add(namedBiomes.mesa);
		biomes.add(namedBiomes.savanna);
		biomes.add(namedBiomes.jungle);
		biomes.add(namedBiomes.megaTaiga);
		biomes.add(namedBiomes.forest);
		biomes.add(namedBiomes.birchForest);
		biomes.add(namedBiomes.roofedForest);
		biomes.add(namedBiomes.extremeHills);
		biomes.add(namedBiomes.taiga);
		biomes.add(namedBiomes.coldTaiga);
		biomes.add(namedBiomes.icePlains);
		biomes.add(namedBiomes.plains);
		biomes.add(namedBiomes.desert);
		biomes.add(namedBiomes.swampland);
		biomes.add(namedBiomes.ocean);
		biomes.add(namedBiomes.deepOcean);
		biomes.add(namedBiomes.beach);
		biomes.add(namedBiomes.river);
		biomes.add(namedBiomes.mushroomIsland);
		
		return biomes;
	}

	@Override
	public int getId()
	{
		return 1;
	}

	@Override
	public FilterResults getResults(int[] biomesSum)
	{
		int missingBiomes = countMissingBiomes(biomesSum);
		
		int biomesCount = countBiomes(biomesSum, this.allBiomes);
		
		FilterResults results = new FilterResults();
		
		results.FilterId = getId();
		results.Value = biomesCount;
		results.CriteriaMet = missingBiomes == 0;
		
		return results;
	}
}