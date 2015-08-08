package amidst.seedanalyzer.filters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import amidst.mojangapi.world.biome.Biome;
import amidst.seedanalyzer.FilterResults;
import amidst.seedanalyzer.NamedBiomeList;

public class AllBiomeGroupsFilter extends Filter
{
	private static ArrayList<List<Biome>> biomeGroups;

	public AllBiomeGroupsFilter(NamedBiomeList namedBiomes)
	{
		super(getBiomesInFilter(namedBiomes));
	}

	private static synchronized ArrayList<Biome> getBiomesInFilter(NamedBiomeList namedBiomes)
	{
		if (biomeGroups == null)
		{
			Collection<Biome> biomes = new ArrayList<Biome>();
			namedBiomes.biomes.iterable().forEach(b -> biomes.add(b));
			
			List<Biome> biomesIce = biomes.stream().filter(b -> b.getName().contains("Ice") || b.getName().contains("Frozen River")).collect(Collectors.toList());
			List<Biome> biomesBeach = biomes.stream().filter(b -> b.getName().contains("Beach")).collect(Collectors.toList());
			List<Biome> biomesTaiga = biomes.stream().filter(b -> b.getName().contains("Taiga") && !b.getName().contains("Mega")).collect(Collectors.toList());
			List<Biome> biomesExtremeHills = biomes.stream().filter(b -> b.getName().contains("Extreme Hills") && !b.getName().contains("Extreme Hills Edge")).collect(Collectors.toList());
			List<Biome> biomesMegaTaiga = biomes.stream().filter(b -> b.getName().contains("Mega") && b.getName().contains("Taiga")).collect(Collectors.toList());
			List<Biome> biomesPlains = biomes.stream().filter(b -> b.getName().contains("Plains")).collect(Collectors.toList());
			List<Biome> biomesForest = biomes.stream().filter(b -> b.getName().contains("Forest") && !b.getName().contains("Roofed") && !b.getName().contains("Birched")).collect(Collectors.toList());
			List<Biome> biomesSwampland = biomes.stream().filter(b -> b.getName().contains("Swampland")).collect(Collectors.toList());
			List<Biome> biomesRiver = biomes.stream().filter(b -> b.getName().contains("River") && !b.getName().contains("Frozen River")).collect(Collectors.toList());
			List<Biome> biomesJungle = biomes.stream().filter(b -> b.getName().contains("Jungle")).collect(Collectors.toList());
			List<Biome> biomesBirchedForest = biomes.stream().filter(b -> b.getName().contains("Birched Forest")).collect(Collectors.toList());
			List<Biome> biomesRoofedForest = biomes.stream().filter(b -> b.getName().contains("Roofed Forest")).collect(Collectors.toList());
			List<Biome> biomesMushroomIsland = biomes.stream().filter(b -> b.getName().contains("Mushroom Island")).collect(Collectors.toList());
			List<Biome> biomesDesert = biomes.stream().filter(b -> b.getName().contains("Desert")).collect(Collectors.toList());
			List<Biome> biomesSavanna = biomes.stream().filter(b -> b.getName().contains("Savanna")).collect(Collectors.toList());
			List<Biome> biomesMesa = biomes.stream().filter(b -> b.getName().contains("Mesa")).collect(Collectors.toList());
			List<Biome> biomesOcean = biomes.stream().filter(b -> b.getName().contains("Ocean") && !b.getName().contains("Frozen Ocean")).collect(Collectors.toList());
			
			biomeGroups = new ArrayList<List<Biome>>();
			
			biomeGroups.add(biomesIce);
			biomeGroups.add(biomesBeach);
			biomeGroups.add(biomesTaiga);
			biomeGroups.add(biomesExtremeHills);
			biomeGroups.add(biomesMegaTaiga);
			biomeGroups.add(biomesPlains);
			biomeGroups.add(biomesForest);
			biomeGroups.add(biomesSwampland);
			biomeGroups.add(biomesRiver);
			biomeGroups.add(biomesJungle);
			biomeGroups.add(biomesBirchedForest);
			biomeGroups.add(biomesRoofedForest);
			biomeGroups.add(biomesMushroomIsland);
			biomeGroups.add(biomesDesert);
			biomeGroups.add(biomesSavanna);
			biomeGroups.add(biomesMesa);
			biomeGroups.add(biomesOcean);
		}
		
		ArrayList<Biome> biomesInFilter = new ArrayList<Biome>();
		
		for (List<Biome> biomeList : biomeGroups)
		{
			biomesInFilter.addAll(biomeList);
		}
		
		System.out.print("AllBiome filters exclusions : ");
		
		int index = 0;
		for(Biome biome : namedBiomes.biomes.iterable())
		{
			if (!biomesInFilter.contains(biome))
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
		
		return biomesInFilter;
	}

	@Override
	public int getId()
	{
		return 3;
	}

	@Override
	public FilterResults getResults(int[] biomesSum)
	{
		int missingGroups = 0;
		
		for (List<Biome> biomeList : biomeGroups)
		{
			double area = getTotalArea(biomesSum, biomeList);
			
			if (area <= 0)
			{
				missingGroups++;
			}
		}
		
		int biomesCount = countBiomes(biomesSum);
		
		FilterResults results = new FilterResults();
		
		results.FilterId = getId();
		results.Value = biomesCount;
		results.CriteriaMet = missingGroups == 0;
		
		return results;
	}
}