package amidst.seedanalyzer.filters;

import java.util.ArrayList;
import java.util.Collection;

import amidst.mojangapi.world.biome.Biome;
import amidst.seedanalyzer.*;

public class SpecialBiomesFilter extends Filter
{
	private ArrayList<Biome> biomes;
	private Collection<Biome> allBiomes;
	
	public SpecialBiomesFilter(NamedBiomeList namedBiomes)
	{
		super(getBiomesInFilter(namedBiomes));
		
		this.allBiomes = new AllBiomeGroupsFilter(namedBiomes).getBiomes();
	}
	
	private static ArrayList<Biome> getBiomesInFilter(NamedBiomeList namedBiomes)
	{
		ArrayList<Biome> biomes = new ArrayList<Biome>();
		
		Collection<Biome> regularBiomes = new RegularBiomesFilter(namedBiomes).getBiomes();
		
		for(Biome biome : namedBiomes.biomes.iterable())
		{
			if (biome != null)
			{
				String biomeName = biome.getName();

                                if (!biomeName.contains("Frozen Ocean") &&
				    !regularBiomes.contains(biome))
				{
					biomes.add(biome);
				}
			}
		}
		
		return biomes;
	}
	
	@Override
	public int getId()
	{
		return 2;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<Biome> getBiomes()
	{
		return (Collection<Biome>)this.biomes.clone();
	}

	@Override
	public FilterResults getResults(int[] biomesSum)
	{
		int missingBiomes = countMissingBiomes(biomesSum);
		
		int biomesCount = countBiomes(biomesSum, this.allBiomes);
		
		FilterResults results = new FilterResults();
		
		results.FilterId = getId();
		results.Value = biomesCount;
		results.CriteriaMet = missingBiomes <= 20;
		
		return results;
	}
}
