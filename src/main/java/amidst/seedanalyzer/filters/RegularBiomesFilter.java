package amidst.seedanalyzer.filters;

import java.util.ArrayList;

import amidst.mojangapi.world.biome.Biome;
import amidst.seedanalyzer.FilterResults;
import amidst.seedanalyzer.NamedBiomeList;

public class RegularBiomesFilter extends Filter
{
	public RegularBiomesFilter(NamedBiomeList namedBiomes)
	{
		super(getBiomesInFilter(namedBiomes));
	
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
	public FilterResults getResults(int[] biomesSum, double[] biomesAreaPercentage, int allBiomesCount)
	{
		int biomesCount = countBiomes(biomesSum);
		int numberOfBiomesInFilter = getNumberOfBiomesInFilter();
		
		FilterResults results = new FilterResults();
		
		results.FilterId = getId();
		results.Value = biomesCount;
		results.CriteriaMet = biomesCount == numberOfBiomesInFilter;

		if (results.CriteriaMet)
		{
			results.Value = allBiomesCount - biomesCount; 
		}
		else
		{
			results.Value = biomesCount - numberOfBiomesInFilter;
		}
		
		return results;
	}
}