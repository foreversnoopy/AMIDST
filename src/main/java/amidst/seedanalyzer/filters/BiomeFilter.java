package amidst.seedanalyzer.filters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import amidst.mojangapi.world.biome.Biome;
import amidst.seedanalyzer.FilterResults;

public class BiomeFilter extends Filter
{
	private int id;
	private double minimumArea;

	public BiomeFilter(int id, Biome biome, double minimumArea)
	{
		super(createBiome(biome));
		
		this.id = id;
		this.minimumArea = minimumArea;
	}

	private static Collection<Biome> createBiome(Biome biome)
	{
		List<Biome> biomes = new ArrayList<Biome>();
		biomes.add(biome);
		return biomes;
	}

	@Override
	public int getId() 
	{
		return id;
	}

	@Override
	public FilterResults getResults(int[] biomesSum, double[] biomesAreaPercentage, int allBiomesCount)
	{
		double area = getTotalAreaPercentage(biomesAreaPercentage);
		
		FilterResults results = new FilterResults();
		
		results.FilterId = getId();
		results.Value = area;
		results.CriteriaMet = area > this.minimumArea;
		
		return results;
	}

}
