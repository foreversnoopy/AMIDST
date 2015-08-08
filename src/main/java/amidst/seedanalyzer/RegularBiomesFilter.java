package amidst.seedanalyzer;

import amidst.mojangapi.world.biome.*;

import java.util.ArrayList;

public class RegularBiomesFilter extends Filter
{
	private final ArrayList<Biome> biomesList;
	
	private final Biome[] biomes;
	
	public RegularBiomesFilter(NamedBiomeList namedBiomes)
	{
		this.biomesList = new ArrayList<Biome>();
		
		biomesList.add(namedBiomes.mesa);
		biomesList.add(namedBiomes.savanna);
		biomesList.add(namedBiomes.jungle);
		biomesList.add(namedBiomes.megaTaiga);
		biomesList.add(namedBiomes.forest);
		biomesList.add(namedBiomes.birchForest);
		biomesList.add(namedBiomes.roofedForest);
		biomesList.add(namedBiomes.extremeHills);
		biomesList.add(namedBiomes.taiga);
		biomesList.add(namedBiomes.coldTaiga);
		biomesList.add(namedBiomes.icePlains);
		biomesList.add(namedBiomes.plains);
		biomesList.add(namedBiomes.desert);
		biomesList.add(namedBiomes.swampland);
		biomesList.add(namedBiomes.ocean);
		biomesList.add(namedBiomes.deepOcean);
		biomesList.add(namedBiomes.beach);
		biomesList.add(namedBiomes.river);
		biomesList.add(namedBiomes.mushroomIsland);
		
		this.biomes = new Biome[biomesList.size()];
		
		biomesList.toArray(biomes);
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<Biome> getBiomes()
	{
		return (ArrayList<Biome>)this.biomesList.clone();
	}
	
	@Override
	public int getId()
	{
		return 1;
	}

	@Override
	public FilterResults getResults(final int[] biomesSum)
	{
		final int missingBiomes = countMissingBiomes(biomesSum, this.biomes);
		
		final FilterResults results = new FilterResults();
		
		results.FilterId = getId();
		results.Value = missingBiomes;
		results.CriteriaMet = missingBiomes == 0;
		
		return results;
	}

	@Override
	public int compare(final FilterResults resultA, final FilterResults resultB)
	{
		if (resultA.FilterId == resultB.FilterId && resultA.FilterId == getId())
		{
			if (resultA.Value < resultB.Value)
			{
				return -1;
			}
			else if (resultA.Value > resultB.Value)
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
}