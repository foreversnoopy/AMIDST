package amidst.seedanalyzer.filters;

import java.util.ArrayList;
import java.util.Collection;

import amidst.mojangapi.world.biome.Biome;
import amidst.seedanalyzer.*;

public class SpecialBiomesFilter extends Filter
{
	private int minimum;

	public SpecialBiomesFilter(int minimum, NamedBiomeList namedBiomes)
	{
		super(getBiomesInFilter(namedBiomes));
        
        this.minimum = minimum;
	}
	
	private static ArrayList<Biome> getBiomesInFilter(NamedBiomeList namedBiomes)
	{
		ArrayList<Biome> biomes = new ArrayList<Biome>();
		
		Collection<Biome> regularBiomes = new RegularBiomesFilter(namedBiomes).getBiomes();
		
		for(Biome biome : namedBiomes.biomesColl)
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

	@Override
	public FilterResults getResults(int[] biomesSum, double[] biomesAreaPercentage, int allBiomesCount)
	{
		int biomesCount = countBiomes(biomesSum);
		int numberOfBiomesInFilter = getNumberOfBiomesInFilter();
		
		FilterResults results = new FilterResults();
		
		results.FilterId = getId();
		results.CriteriaMet = (biomesCount - numberOfBiomesInFilter) >= this.minimum;
		
		if (biomesCount == numberOfBiomesInFilter)
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
