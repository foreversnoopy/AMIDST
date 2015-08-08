package amidst.seedanalyzer;

import amidst.mojangapi.minecraftinterface.*;
import amidst.mojangapi.world.*;
import amidst.mojangapi.world.biome.*;
import amidst.mojangapi.world.versionfeatures.*;

import java.util.EnumSet;
import java.util.Set;
import java.util.function.Function;

public class NamedBiomeList {
    public Biome mesa;
    public Biome savanna;
    public Biome jungle;
    public Biome megaTaiga;
    public Biome forest;
    public Biome birchForest;
    public Biome roofedForest;
    public Biome extremeHills;
    public Biome taiga;
    public Biome coldTaiga;
    public Biome icePlains;
    public Biome plains;
    public Biome desert;
    public Biome swampland;
    public Biome ocean;
    public Biome deepOcean;
    public Biome beach;
    public Biome river;
    public Biome mushroomIsland;

    public BiomeList biomes;

    public NamedBiomeList() throws UnknownBiomeIdException {
        biomes = DefaultBiomes.DEFAULT_BIOMES.getValue(RecognisedVersion._1_8, createVersionFeaturesBuilder().create(RecognisedVersion._1_8));

        this.mesa = biomes.getById(DefaultBiomes.mesa);
		this.savanna = biomes.getById(DefaultBiomes.savanna);
		this.jungle = biomes.getById(DefaultBiomes.jungle);
		this.megaTaiga = biomes.getById(DefaultBiomes.megaTaiga);
		this.forest = biomes.getById(DefaultBiomes.forest);
		this.birchForest = biomes.getById(DefaultBiomes.birchForest);
		this.roofedForest = biomes.getById(DefaultBiomes.roofedForest);
		this.extremeHills = biomes.getById(DefaultBiomes.extremeHills);
		this.taiga = biomes.getById(DefaultBiomes.taiga);
		this.coldTaiga = biomes.getById(DefaultBiomes.coldTaiga);
		this.icePlains = biomes.getById(DefaultBiomes.icePlains);
		this.plains = biomes.getById(DefaultBiomes.plains);
		this.desert = biomes.getById(DefaultBiomes.desert);
		this.swampland = biomes.getById(DefaultBiomes.swampland);
		this.ocean = biomes.getById(DefaultBiomes.ocean);
		this.deepOcean = biomes.getById(DefaultBiomes.deepOcean);
		this.beach = biomes.getById(DefaultBiomes.beach);
		this.river = biomes.getById(DefaultBiomes.river);
		this.mushroomIsland = biomes.getById(DefaultBiomes.mushroomIsland);
    }

    public VersionFeatures.Builder createVersionFeaturesBuilder()
	{
		final WorldOptions worldOptions = new WorldOptions(WorldSeed.fromSaveGame(0), WorldType.DEFAULT);

		final MinecraftInterface.World minecraftWorld = new MockMinecraftWorld();

		return DefaultVersionFeatures.builder(worldOptions, minecraftWorld);
	}

	public static class MockMinecraftWorld implements MinecraftInterface.World
	{
		@Override
		public<T> T getBiomeData(final Dimension dimension, final int x, final int y, final int width, final int height, final boolean useQuarterResolution, final Function<int[], T> biomeDataMapper) {
			throw new UnsupportedOperationException();
		}

		@Override
		public Set<Dimension> supportedDimensions() {
			return EnumSet.allOf(Dimension.class);
		}
	}
}