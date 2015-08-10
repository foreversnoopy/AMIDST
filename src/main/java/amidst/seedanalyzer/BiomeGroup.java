package amidst.seedanalyzer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import amidst.mojangapi.world.biome.Biome;

public class BiomeGroup
{
    public static final ArrayList<BiomeGroup> biomeGroups = new ArrayList<BiomeGroup>();
	
	private String name;
	private List<Biome> biomes;
	
	public BiomeGroup(String name, List<Biome> biomes)
	{
		this.name = name;
		this.biomes = biomes;
		
		biomeGroups.add(this);
	}

	public Collection<Biome> getBiomes()
	{
		return biomes;
	}
	
	public String getName()
	{
		return name;
	}
	
	public static void showExcludedBiomesInBiomeGroups(NamedBiomeList namedBiomes)
	{
		ArrayList<Biome> allBiomes = new ArrayList<>();
		
		for(BiomeGroup biomeGroup : biomeGroups)
		{
			allBiomes.addAll(biomeGroup.getBiomes());
		}
		
		System.out.print("AllBiome filters exclusions : ");
		
		int index = 0;
		for(Biome biome : namedBiomes.biomesColl)
		{
			if (!allBiomes.contains(biome))
			{
				if (index > 0)
				{
					System.out.print(", ");
				}
				
				System.out.print(biome.getName());
			}
			
			index++;
		}
		
		System.out.println();
	}
}