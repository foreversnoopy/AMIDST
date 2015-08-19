package amidst.seedanalyzer;

import amidst.mojangapi.minecraftinterface.*;
import amidst.mojangapi.world.biome.*;
import amidst.mojangapi.world.versionfeatures.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

public class NamedBiomeList {
	public final Biome mesa;
	public final Biome mesaBryce;
	public final Biome savanna;
	public final Biome savannaPlateauM;
    public final Biome jungle;
	public final Biome megaTaiga;
	public final Biome megaSpruceTaigaHills;
	public final Biome forest;
	public final Biome flowerForest;
    public final Biome birchForest;
	public final Biome roofedForest;
	public final Biome roofedForestM;
    public final Biome extremeHills;
    public final Biome taiga;
    public final Biome coldTaiga;
	public final Biome icePlains;
	public final Biome icePlainsSpikes;
	public final Biome plains;
	public final Biome sunflowerPlains;
    public final Biome desert;
    public final Biome swampland;
    public final Biome ocean;
    public final Biome deepOcean;
    public final Biome beach;
    public final Biome river;
    public final Biome mushroomIsland;

	public final BiomeList biomesMap;
	public final Collection<Biome> biomesColl;
	
	public final BiomeGroup biomesIce;
	public final BiomeGroup biomesBeach;
	public final BiomeGroup biomesTaiga;
	public final BiomeGroup biomesExtremeHills;
	public final BiomeGroup biomesMegaTaiga;
	public final BiomeGroup biomesPlains;
	public final BiomeGroup biomesForest;
	public final BiomeGroup biomesSwampland;
	public final BiomeGroup biomesRiver;
	public final BiomeGroup biomesJungle;
	public final BiomeGroup biomesBirchForest;
	public final BiomeGroup biomesRoofedForest;
	public final BiomeGroup biomesMushroomIsland;
	public final BiomeGroup biomesDesert;
	public final BiomeGroup biomesSavanna;
	public final BiomeGroup biomesMesa;
	public final BiomeGroup biomesOcean;

	public final Collection<BiomeGroup> biomeGroups = new ArrayList<BiomeGroup>();

    public NamedBiomeList(RecognisedVersion recognisedVersion, VersionFeatures versionFeatures) throws UnknownBiomeIdException {
        biomesMap = DefaultBiomes.DEFAULT_BIOMES.getValue(recognisedVersion, versionFeatures);

		this.mesa = biomesMap.getById(DefaultBiomes.mesa);
		this.mesaBryce = biomesMap.getById(DefaultBiomes.mesaBryce);
		this.savanna = biomesMap.getById(DefaultBiomes.savanna);
		this.savannaPlateauM = biomesMap.getById(DefaultBiomes.savannaPlateauM);
		this.jungle = biomesMap.getById(DefaultBiomes.jungle);
		this.megaTaiga = biomesMap.getById(DefaultBiomes.megaTaiga);
		this.megaSpruceTaigaHills = biomesMap.getById(DefaultBiomes.megaSpruceTaigaHills);
		this.forest = biomesMap.getById(DefaultBiomes.forest);
		this.flowerForest = biomesMap.getById(DefaultBiomes.flowerForest);
		this.birchForest = biomesMap.getById(DefaultBiomes.birchForest);
		this.roofedForest = biomesMap.getById(DefaultBiomes.roofedForest);
		this.roofedForestM = biomesMap.getById(DefaultBiomes.roofedForestM);
		this.extremeHills = biomesMap.getById(DefaultBiomes.extremeHills);
		this.taiga = biomesMap.getById(DefaultBiomes.taiga);
		this.coldTaiga = biomesMap.getById(DefaultBiomes.coldTaiga);
		this.icePlains = biomesMap.getById(DefaultBiomes.icePlains);
		this.icePlainsSpikes = biomesMap.getById(DefaultBiomes.icePlainsSpikes);
		this.plains = biomesMap.getById(DefaultBiomes.plains);
		this.sunflowerPlains = biomesMap.getById(DefaultBiomes.sunflowerPlains);
		this.desert = biomesMap.getById(DefaultBiomes.desert);
		this.swampland = biomesMap.getById(DefaultBiomes.swampland);
		this.ocean = biomesMap.getById(DefaultBiomes.ocean);
		this.deepOcean = biomesMap.getById(DefaultBiomes.deepOcean);
		this.beach = biomesMap.getById(DefaultBiomes.beach);
		this.river = biomesMap.getById(DefaultBiomes.river);
		this.mushroomIsland = biomesMap.getById(DefaultBiomes.mushroomIsland);

		biomesColl = new ArrayList<Biome>();
		biomesMap.iterable().forEach(b -> biomesColl.add(b));

		this.biomesIce 				= new BiomeGroup("Ice", 			biomesColl.stream().filter(b -> b.getName().contains("Ice") || b.getName().contains("Frozen River")).collect(Collectors.toList()));
		this.biomesBeach 			= new BiomeGroup("Beach", 			biomesColl.stream().filter(b -> b.getName().contains("Beach")).collect(Collectors.toList()));
		this.biomesTaiga 			= new BiomeGroup("Taiga", 			biomesColl.stream().filter(b -> b.getName().contains("Taiga") && !b.getName().contains("Mega")).collect(Collectors.toList()));
		this.biomesExtremeHills 	= new BiomeGroup("Extreme Hills", 	biomesColl.stream().filter(b -> b.getName().contains("Extreme Hills") && !b.getName().contains("Extreme Hills Edge")).collect(Collectors.toList()));
		this.biomesMegaTaiga 		= new BiomeGroup("Mega Taiga", 		biomesColl.stream().filter(b -> b.getName().contains("Mega") && b.getName().contains("Taiga")).collect(Collectors.toList()));
		this.biomesPlains 			= new BiomeGroup("Plains", 			biomesColl.stream().filter(b -> b.getName().contains("Plains") && !b.getName().contains("Ice")).collect(Collectors.toList()));
		this.biomesForest 			= new BiomeGroup("Forest", 			biomesColl.stream().filter(b -> b.getName().contains("Forest") && !b.getName().contains("Roofed") && !b.getName().contains("Birch")).collect(Collectors.toList()));
		this.biomesSwampland 		= new BiomeGroup("Swampland", 		biomesColl.stream().filter(b -> b.getName().contains("Swampland")).collect(Collectors.toList()));
		this.biomesRiver 			= new BiomeGroup("River", 			biomesColl.stream().filter(b -> b.getName().contains("River") && !b.getName().contains("Frozen River")).collect(Collectors.toList()));
		this.biomesJungle 			= new BiomeGroup("Jungle", 			biomesColl.stream().filter(b -> b.getName().contains("Jungle")).collect(Collectors.toList()));
		this.biomesBirchForest 		= new BiomeGroup("Birch Forest", 	biomesColl.stream().filter(b -> b.getName().contains("Birch Forest")).collect(Collectors.toList()));
		this.biomesRoofedForest 	= new BiomeGroup("Roofed Forest", 	biomesColl.stream().filter(b -> b.getName().contains("Roofed Forest")).collect(Collectors.toList()));
		this.biomesMushroomIsland 	= new BiomeGroup("Mushroom Island", biomesColl.stream().filter(b -> b.getName().contains("Mushroom Island")).collect(Collectors.toList()));
		this.biomesDesert 			= new BiomeGroup("Desert", 			biomesColl.stream().filter(b -> b.getName().contains("Desert")).collect(Collectors.toList()));
		this.biomesSavanna 			= new BiomeGroup("Savanna", 		biomesColl.stream().filter(b -> b.getName().contains("Savanna")).collect(Collectors.toList()));
		this.biomesMesa 			= new BiomeGroup("Mesa", 			biomesColl.stream().filter(b -> b.getName().contains("Mesa")).collect(Collectors.toList()));
		this.biomesOcean 			= new BiomeGroup("Ocean", 			biomesColl.stream().filter(b -> b.getName().contains("Ocean") && !b.getName().contains("Frozen Ocean")).collect(Collectors.toList()));

		BiomeGroup.showExcludedBiomesInBiomeGroups(this);
    }
}