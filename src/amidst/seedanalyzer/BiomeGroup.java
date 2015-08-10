package amidst.seedanalyzer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import amidst.minecraft.Biome;

public class BiomeGroup
{
	public static final ArrayList<BiomeGroup> biomeGroups = new ArrayList<BiomeGroup>();
	
	public static final BiomeGroup biomesIce 			= new BiomeGroup("Ice", 			Biome.biomeMap.values().stream().filter(b -> b.name.contains("Ice") || b.name.contains("Frozen River")).collect(Collectors.toList()));
	public static final BiomeGroup biomesBeach 			= new BiomeGroup("Beach", 			Biome.biomeMap.values().stream().filter(b -> b.name.contains("Beach")).collect(Collectors.toList()));
	public static final BiomeGroup biomesTaiga 			= new BiomeGroup("Taiga", 			Biome.biomeMap.values().stream().filter(b -> b.name.contains("Taiga") && !b.name.contains("Mega")).collect(Collectors.toList()));
	public static final BiomeGroup biomesExtremeHills 	= new BiomeGroup("Extreme Hills", 	Biome.biomeMap.values().stream().filter(b -> b.name.contains("Extreme Hills") && !b.name.contains("Extreme Hills Edge")).collect(Collectors.toList()));
	public static final BiomeGroup biomesMegaTaiga 		= new BiomeGroup("Mega Taiga", 		Biome.biomeMap.values().stream().filter(b -> b.name.contains("Mega") && b.name.contains("Taiga")).collect(Collectors.toList()));
	public static final BiomeGroup biomesPlains 		= new BiomeGroup("Plains", 			Biome.biomeMap.values().stream().filter(b -> b.name.contains("Plains") && !b.name.contains("Ice")).collect(Collectors.toList()));
	public static final BiomeGroup biomesForest 		= new BiomeGroup("Forest", 			Biome.biomeMap.values().stream().filter(b -> b.name.contains("Forest") && !b.name.contains("Roofed") && !b.name.contains("Birch")).collect(Collectors.toList()));
	public static final BiomeGroup biomesSwampland 		= new BiomeGroup("Swampland", 		Biome.biomeMap.values().stream().filter(b -> b.name.contains("Swampland")).collect(Collectors.toList()));
	public static final BiomeGroup biomesRiver 			= new BiomeGroup("River", 			Biome.biomeMap.values().stream().filter(b -> b.name.contains("River") && !b.name.contains("Frozen River")).collect(Collectors.toList()));
	public static final BiomeGroup biomesJungle 		= new BiomeGroup("Jungle", 			Biome.biomeMap.values().stream().filter(b -> b.name.contains("Jungle")).collect(Collectors.toList()));
	public static final BiomeGroup biomesBirchForest 	= new BiomeGroup("Birch Forest", 	Biome.biomeMap.values().stream().filter(b -> b.name.contains("Birch Forest")).collect(Collectors.toList()));
	public static final BiomeGroup biomesRoofedForest 	= new BiomeGroup("Roofed Forest", 	Biome.biomeMap.values().stream().filter(b -> b.name.contains("Roofed Forest")).collect(Collectors.toList()));
	public static final BiomeGroup biomesMushroomIsland = new BiomeGroup("Mushroom Island", Biome.biomeMap.values().stream().filter(b -> b.name.contains("Mushroom Island")).collect(Collectors.toList()));
	public static final BiomeGroup biomesDesert 		= new BiomeGroup("Desert", 			Biome.biomeMap.values().stream().filter(b -> b.name.contains("Desert")).collect(Collectors.toList()));
	public static final BiomeGroup biomesSavanna 		= new BiomeGroup("Savanna", 		Biome.biomeMap.values().stream().filter(b -> b.name.contains("Savanna")).collect(Collectors.toList()));
	public static final BiomeGroup biomesMesa 			= new BiomeGroup("Mesa", 			Biome.biomeMap.values().stream().filter(b -> b.name.contains("Mesa")).collect(Collectors.toList()));
	public static final BiomeGroup biomesOcean 			= new BiomeGroup("Ocean", 			Biome.biomeMap.values().stream().filter(b -> b.name.contains("Ocean") && !b.name.contains("Frozen Ocean")).collect(Collectors.toList()));
	
	public static final List<Biome> allBiomes = getAllBiomes();
	
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
	
	private static ArrayList<Biome> getAllBiomes()
	{
		ArrayList<Biome> allBiomes = new ArrayList<>();
		
		for(BiomeGroup biomeGroup : biomeGroups)
		{
			allBiomes.addAll(biomeGroup.getBiomes());
		}
		
		System.out.print("AllBiome filters exclusions : ");
		
		int index = 0;
		for(Biome biome : Biome.biomeMap.values())
		{
			if (!allBiomes.contains(biome))
			{
				if (index > 0)
				{
					System.out.print(", ");
				}
				
				System.out.print(biome.name);
			}
			
			index++;
		}
		
		System.out.println();
		
		return allBiomes;
	}
}