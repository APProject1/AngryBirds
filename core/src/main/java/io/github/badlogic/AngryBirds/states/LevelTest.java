package io.github.badlogic.AngryBirds.states;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import io.github.badlogic.AngryBirds.Elements.*;
import org.junit.jupiter.api.*;
import java.io.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class LevelTest{
    public String TEST_FILE ="../saves/testbirdPositions.txt";
    public String TEST1_FILE ="../saves/testblockPositions.txt";
    public String TEST2_FILE ="../saves/testpigPositions.txt";
    public Level level;
    public GameStateManager gsm;
    public World world;

    @BeforeEach
    public void setUp(){
        Level level=new Level(gsm);
        level.birds=new ArrayList<>();
        level.blocks=new ArrayList<>();
        level.pigs=new ArrayList<>();
        level.level=1;
        world=new World(new Vector2(0, -20), true);
        RedBird bird = new RedBird(world,100,200,0,0,true);
        level.birds.add(bird);
        StoneBlock block= new StoneBlock(world,50,40,70,70,1,1,true);
        KingPig pig=new KingPig(world,300,300,0,0,2,true);
        level.blocks.add(block);
        level.pigs.add(pig);
        level.saveBirdPositions(TEST_FILE, "2024-11-29 12:00:00");
        level.saveBlockPositions(TEST1_FILE, "2024-11-29 12:00:00");
        level.saveBirdPositions(TEST2_FILE, "2024-11-29 12:00:00");
    }

    @Test
    public void testBirdFileExists() {
        File file = new File(TEST_FILE);
        assertTrue(file.exists(), "The file should exist at the specified path.");
    }

    @Test
    public void testBlockFileExists() {
        File file = new File(TEST1_FILE);
        assertTrue(file.exists(), "The file should exist at the specified path.");
    }

    @Test
    public void testPigFileExists() {
        File file = new File(TEST2_FILE);
        assertTrue(file.exists(), "The file should exist at the specified path.");
    }

    @Test
    public void testBirdSaveFunctionality() {
        try(BufferedReader reader=new BufferedReader(new FileReader(TEST_FILE))){
            String line=reader.readLine();
            assertNotNull(line, "The file should contain at least one line.");
        } catch (IOException e) {
            fail("IOException occurred while reading the test file: " + e.getMessage());
        }
    }

    @Test
    public void testBlockSaveFunctionality(){
        try(BufferedReader reader=new BufferedReader(new FileReader(TEST1_FILE))){
            String line=reader.readLine();
            assertNotNull(line, "The file should contain at least one line.");
        } catch (IOException e) {
            fail("IOException occurred while reading the test file: " + e.getMessage());
        }
    }

    @Test
    public void testPigSaveFunctionality(){
        try(BufferedReader reader=new BufferedReader(new FileReader(TEST2_FILE))){
            String line=reader.readLine();
            assertNotNull(line, "The file should contain at least one line.");
        } catch (IOException e) {
            fail("IOException occurred while reading the test file: " + e.getMessage());
        }
    }
    @Test
    public void testBirdData(){
        String timestamp = "2024-11-29 12:00:00";
        boolean dataMatches = checkFileFormat("../saves/testbirdPositions.txt",timestamp);
        assertTrue(dataMatches,"The saved data should match the expected data for the given timestamp.");
    }
    @Test
    public void testBlockData(){
        String timestamp = "2024-11-29 12:00:00";
        boolean dataMatches = checkFileFormat("../saves/testblockPositions.txt",timestamp);
        assertTrue(dataMatches,"The saved data should match the expected data for the given timestamp.");
    }
    @Test
    public void testPigData(){
        String timestamp = "2024-11-29 12:00:00";
        boolean dataMatches = checkFileFormat("../saves/testpigPositions.txt",timestamp);
        assertTrue(dataMatches,"The saved data should match the expected data for the given timestamp.");
    }

    private boolean checkFileFormat(String fileName, String timestamp) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean isTimestampFound = false;

            while ((line = reader.readLine()) != null) {
                if (line.contains(timestamp)) {
                    isTimestampFound = true;
                    String[] parts = line.split(", ");
                    if (parts.length != 3) {
                        return false;
                    }
                    line = reader.readLine();
                    if (line == null) {
                        return false;
                    }
                    String[] details = line.split(", ");
                    if(details.length != 5 && details.length != 6){
                        return false;
                    }
                    return true;
                }
            }
            return isTimestampFound;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @AfterEach
    public void tearDown() {
        deleteFile(TEST_FILE);
        deleteFile(TEST1_FILE);
        deleteFile(TEST2_FILE);
    }
    private void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Deleted test file: " + filePath);
            } else {
                System.err.println("Failed to delete test file: " + filePath);
            }
        }
    }
}

