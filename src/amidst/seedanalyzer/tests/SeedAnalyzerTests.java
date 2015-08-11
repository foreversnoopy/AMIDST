package amidst.seedanalyzer.tests;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import amidst.Options;
import amidst.Util;
import amidst.minecraft.Minecraft;
import amidst.minecraft.MinecraftUtil;
import amidst.seedanalyzer.FilterResults;
import amidst.seedanalyzer.SeedAnalyzer;

public class SeedAnalyzerTests
{
	private static SeedAnalyzer seedAnalyzer;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception
	{
		Options.instance.minecraftPath = "C:\\Users\\Max\\AppData\\Roaming\\.minecraft";
		
		Util.setMinecraftDirectory();
		Util.setMinecraftLibraries();
		Util.setProfileDirectory(Options.instance.minecraftPath);
		
		File f = new File(Options.instance.minecraftPath + "\\versions\\1.8.8\\1.8.8.jar");
		
		if (f.exists())
		{
			MinecraftUtil.setBiomeInterface(new Minecraft(f).createInterface());
			
			seedAnalyzer = new SeedAnalyzer(null, 2048);
		}
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception
	{
	}

	@Before
	public void setUp() throws Exception
	{
	}

	@After
	public void tearDown() throws Exception
	{
	}

	@Test
	public void testAnalyzeSeeds() throws IOException
	{
		Collection<FilterResults> results = seedAnalyzer.analyzeSeeds(-9223372036854774212l, -9223372036854774212l).FilteredSeeds;
		
		Assert.assertTrue("Should have passed filter 1", results.stream().anyMatch(r -> r.FilterId == 1));
		Assert.assertTrue("Should have passed filter 2", results.stream().anyMatch(r -> r.FilterId == 2));
		Assert.assertTrue("Should have passed filter 3", results.stream().anyMatch(r -> r.FilterId == 3));
		
		results = seedAnalyzer.analyzeSeeds(-9223372036854766762l, -9223372036854766762l).FilteredSeeds;
		Assert.assertTrue("Should have passed filter 1", results.stream().anyMatch(r -> r.FilterId == 1));
		Assert.assertTrue("Should have passed filter 2", results.stream().anyMatch(r -> r.FilterId == 2));
		Assert.assertTrue("Should have passed filter 3", results.stream().anyMatch(r -> r.FilterId == 3));
		
		results = seedAnalyzer.analyzeSeeds(-9223372036854771713l, -9223372036854771713l).FilteredSeeds;
		Assert.assertTrue("Should have passed filter 1", results.stream().anyMatch(r -> r.FilterId == 1));
		Assert.assertTrue("Should have passed filter 2", results.stream().anyMatch(r -> r.FilterId == 2));
		Assert.assertTrue("Should have passed filter 3", results.stream().anyMatch(r -> r.FilterId == 3));
		
		
		
		results = seedAnalyzer.analyzeSeeds(-988340l, -988340l).FilteredSeeds;
		Assert.assertTrue("Should have passed filter 1", results.stream().anyMatch(r -> r.FilterId == 1));
		Assert.assertTrue("Should have passed filter 2", results.stream().anyMatch(r -> r.FilterId == 2));
		
		results = seedAnalyzer.analyzeSeeds(2001160l, 2001160l).FilteredSeeds;
		Assert.assertTrue("Should have passed filter 1", results.stream().anyMatch(r -> r.FilterId == 1));
		Assert.assertTrue("Should have passed filter 2", results.stream().anyMatch(r -> r.FilterId == 2));
		
		results = seedAnalyzer.analyzeSeeds(3000173l, 3000173l).FilteredSeeds;
		Assert.assertTrue("Should have passed filter 1", results.stream().anyMatch(r -> r.FilterId == 1));
		Assert.assertTrue("Should have passed filter 2", results.stream().anyMatch(r -> r.FilterId == 2));
		
		results = seedAnalyzer.analyzeSeeds(3003318l, 3003318l).FilteredSeeds;
		Assert.assertTrue("Should have passed filter 1", results.stream().anyMatch(r -> r.FilterId == 1));
		Assert.assertTrue("Should have passed filter 2", results.stream().anyMatch(r -> r.FilterId == 2));
		
		results = seedAnalyzer.analyzeSeeds(3014502l, 3014502l).FilteredSeeds;
		Assert.assertTrue("Should have passed filter 1", results.stream().anyMatch(r -> r.FilterId == 1));
		Assert.assertTrue("Should have passed filter 2", results.stream().anyMatch(r -> r.FilterId == 2));
		
		results = seedAnalyzer.analyzeSeeds(3021397l, 3021397l).FilteredSeeds;
		Assert.assertTrue("Should have passed filter 1", results.stream().anyMatch(r -> r.FilterId == 1));
		Assert.assertTrue("Should have passed filter 2", results.stream().anyMatch(r -> r.FilterId == 2));
		
		results = seedAnalyzer.analyzeSeeds(-9223372036854506246l, -9223372036854506246l).FilteredSeeds;
		Assert.assertTrue("Should have passed filter 1", results.stream().anyMatch(r -> r.FilterId == 1));
		Assert.assertTrue("Should have passed filter 2", results.stream().anyMatch(r -> r.FilterId == 2));
		
		results = seedAnalyzer.analyzeSeeds(-9223372036854512550l, -9223372036854512550l).FilteredSeeds;
		Assert.assertTrue("Should have passed filter 1", results.stream().anyMatch(r -> r.FilterId == 1));
		Assert.assertTrue("Should have passed filter 2", results.stream().anyMatch(r -> r.FilterId == 2));
		
		results = seedAnalyzer.analyzeSeeds(-9223372036854529884l, -9223372036854529884l).FilteredSeeds;
		Assert.assertTrue("Should have passed filter 1", results.stream().anyMatch(r -> r.FilterId == 1));
		Assert.assertTrue("Should have passed filter 2", results.stream().anyMatch(r -> r.FilterId == 2));
		
		results = seedAnalyzer.analyzeSeeds(-9223372036854534099l, -9223372036854534099l).FilteredSeeds;
		Assert.assertTrue("Should have passed filter 1", results.stream().anyMatch(r -> r.FilterId == 1));
		Assert.assertTrue("Should have passed filter 2", results.stream().anyMatch(r -> r.FilterId == 2));
		
		results = seedAnalyzer.analyzeSeeds(-9223372036854535965l, -9223372036854535965l).FilteredSeeds;
		Assert.assertTrue("Should have passed filter 1", results.stream().anyMatch(r -> r.FilterId == 1));
		Assert.assertTrue("Should have passed filter 2", results.stream().anyMatch(r -> r.FilterId == 2));
		
		results = seedAnalyzer.analyzeSeeds(-9223372036854541093l, -9223372036854541093l).FilteredSeeds;
		Assert.assertTrue("Should have passed filter 1", results.stream().anyMatch(r -> r.FilterId == 1));
		Assert.assertTrue("Should have passed filter 2", results.stream().anyMatch(r -> r.FilterId == 2));
		
		results = seedAnalyzer.analyzeSeeds(-9223372036854541885l, -9223372036854541885l).FilteredSeeds;
		Assert.assertTrue("Should have passed filter 1", results.stream().anyMatch(r -> r.FilterId == 1));
		Assert.assertTrue("Should have passed filter 2", results.stream().anyMatch(r -> r.FilterId == 2));
		
		results = seedAnalyzer.analyzeSeeds(-9223372036854751003l, -9223372036854751003l).FilteredSeeds;
		Assert.assertTrue("Should have passed filter 1", results.stream().anyMatch(r -> r.FilterId == 1));
		Assert.assertTrue("Should have passed filter 2", results.stream().anyMatch(r -> r.FilterId == 2));
		
		
		
		results = seedAnalyzer.analyzeSeeds(-9223372036854773094l, -9223372036854773094l).FilteredSeeds;
		Assert.assertTrue("Should have passed filter 1", results.stream().anyMatch(r -> r.FilterId == 1));
		Assert.assertFalse("Should not have passed filter 2", results.stream().anyMatch(r -> r.FilterId == 2));
		Assert.assertTrue("Should have passed filter 3", results.stream().anyMatch(r -> r.FilterId == 3));
		
		results = seedAnalyzer.analyzeSeeds(-9223372036854773143l, -9223372036854773143l).FilteredSeeds;
		Assert.assertTrue("Should have passed filter 1", results.stream().anyMatch(r -> r.FilterId == 1));
		Assert.assertFalse("Should not have passed filter 2", results.stream().anyMatch(r -> r.FilterId == 2));
		Assert.assertTrue("Should have passed filter 3", results.stream().anyMatch(r -> r.FilterId == 3));
		
		results = seedAnalyzer.analyzeSeeds(-9223372036854773459l, -9223372036854773459l).FilteredSeeds;
		Assert.assertTrue("Should have passed filter 1", results.stream().anyMatch(r -> r.FilterId == 1));
		Assert.assertFalse("Should not have passed filter 2", results.stream().anyMatch(r -> r.FilterId == 2));
		Assert.assertTrue("Should have passed filter 3", results.stream().anyMatch(r -> r.FilterId == 3));
		
		
		results = seedAnalyzer.analyzeSeeds(-9223372036854756036l, -9223372036854756036l).FilteredSeeds;
		Assert.assertTrue("Should have passed only filter 2", results.stream().allMatch(r -> r.FilterId == 2));
		
		results = seedAnalyzer.analyzeSeeds(-9223372036854756220l, -9223372036854756220l).FilteredSeeds;
		Assert.assertTrue("Should have passed only filter 2", results.stream().allMatch(r -> r.FilterId == 2));
		
		results = seedAnalyzer.analyzeSeeds(-9223372036854760702l, -9223372036854760702l).FilteredSeeds;
		Assert.assertTrue("Should have passed only filter 2", results.stream().allMatch(r -> r.FilterId == 2));
		
		
		
		results = seedAnalyzer.analyzeSeeds(-9223372036854756693l, -9223372036854756693l).FilteredSeeds;
		Assert.assertTrue("Should have passed only filter 3", results.stream().allMatch(r -> r.FilterId == 3));
		
		results = seedAnalyzer.analyzeSeeds(-9223372036854757540l, -9223372036854757540l).FilteredSeeds;
		Assert.assertTrue("Should have passed only filter 3", results.stream().allMatch(r -> r.FilterId == 3));
		
		results = seedAnalyzer.analyzeSeeds(-9223372036854761182l, -9223372036854761182l).FilteredSeeds;
		Assert.assertTrue("Should have passed only filter 3", results.stream().allMatch(r -> r.FilterId == 3));
		
		results = seedAnalyzer.analyzeSeeds(-9223372036854773918l, -9223372036854773918l).FilteredSeeds;
		Assert.assertTrue("Should have passed only filter 3", results.stream().allMatch(r -> r.FilterId == 3));
	}
}
