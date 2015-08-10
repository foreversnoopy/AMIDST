package amidst.seedanalyzer;

import java.util.ArrayList;

import amidst.mojangapi.world.biome.Biome;

public class SpecialBiomesFilter extends Filter
{
	private Biome[] biomes;
	
	public SpecialBiomesFilter(NamedBiomeList namedBiomes)
	{
		ArrayList<Biome> biomesList = new ArrayList<Biome>();
		
		ArrayList<Biome> regularBiomes = new RegularBiomesFilter(namedBiomes).getBiomes();
		
		for(Biome biome : namedBiomes.biomesColl)
		{
			if (biome != null)
			{
				String biomeName = biome.getName();

				if (!biomeName.contains("Sky") &&
					!biomeName.contains("Hell") &&
					!biomeName.contains("Edge") &&
					!biomeName.contains("Frozen Ocean") &&
					!regularBiomes.contains(biome))
				{
					biomesList.add(biome);
				}
			}
		}
		
		this.biomes = new Biome[biomesList.size()];
		
		biomesList.toArray(biomes);
	}
	
	@Override
	public int getId()
	{
		return 2;
	}

	@Override
	public FilterResults getResults(int[] biomesSum)
	{
		int missingBiomes = countMissingBiomes(biomesSum, this.biomes);
		
		FilterResults results = new FilterResults();
		
		results.FilterId = getId();
		results.Value = missingBiomes;
		results.CriteriaMet = missingBiomes <= 20;
		
		return results;
	}

	@Override
	public int compare(FilterResults resultA, FilterResults resultB)
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
