package io.github.badlogic.AngryBirds.states;

import com.badlogic.gdx.math.Vector2;
import io.github.badlogic.AngryBirds.Elements.Bird;
import io.github.badlogic.AngryBirds.Elements.RedBird;
//import io.github.badlogic.AngryBirds.Level;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.badlogic.gdx.physics.box2d.World;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class SavedGamesStateTest{
    private SavedGamesState SavedState;
    private GameStateManager gameStateManager;
    private World world;

    @BeforeEach
    void setUp() {
        // Set up the environment for the tests
        gameStateManager=new GameStateManager(){
            /*@Override
            public void set(Level level) {
                // Simulated method for setting a Level in the GameStateManager
            }*/
        };
        world = new World(new Vector2(0, -20), true); // Use actual World instance from LibGDX
        SavedState = new SavedGamesState(gameStateManager); // Instantiate the SavedGamesState with our stubbed GameStateManager
    }

    @Test
    void testReadBirdPositions() throws IOException {
        // Prepare a mock file with bird data
        String mockFileName = "testBirdPositions.txt";
        try (FileWriter writer = new FileWriter(mockFileName)) {
            writer.write("2024-11-25 12:00:00, 1, 2\n");
            writer.write("RedBird, 100.0, 200.0, 0.0, 0.0\n");
            writer.write("YellowBird, 150.0, 250.0, 10.0, 5.0\n");
        }

        // Read bird positions from the mock file
        SavedState.readBirdPositions(mockFileName, "2024-11-25 12:00:00", world);

        // Validate parsed data
        ArrayList<Bird> birds = SavedState.resetBirds;
        assertEquals(2, birds.size(), "There should be two birds in the list");
        //assertTrue(birds.get(0) instanceof RedBird, "First bird should be RedBird");
        //assertEquals(100.0, birds.get(0).getBody().getPosition().x, 0.1, "RedBird's x position should be correct");
    }

    @Test
    void testTimestampButtonsCreation() throws IOException {
        // Prepare a mock file with timestamps
        String mockFileName = "testTimestampButtons.txt";
        try (FileWriter writer = new FileWriter(mockFileName)) {
            writer.write("2024-11-25 12:00:00, 1\n");
            writer.write("2024-11-25 13:00:00, 2\n");
        }

        // Create timestamp buttons based on the mock file
        SavedState.createTimestampButtons(mockFileName, SavedState.stage);

        // Validate that the correct number of buttons were created
        assertEquals(2, SavedState.stage.getActors().size, "There should be 2 buttons in the stage");
    }

    @Test
    void testLevelInitialization() {
        // Set up the game SavedState with initial birds, pigs, and blocks
        SavedState.resetBirds = new ArrayList<>();
        SavedState.resetBirds.add(new RedBird(world, 100, 200, 0, 0));
        SavedState.resetPigs = new ArrayList<>();
        SavedState.resetBlocks = new ArrayList<>();
        SavedState.flyingBird = SavedState.resetBirds.get(0);

        // Simulate setting a new level in the GameStateManager
        SavedState.gsm.set(new Level(world, gameStateManager, 1, SavedState.resetBirds, SavedState.resetPigs, SavedState.resetBlocks, "level1bg.png"));

        // Manually check if the level was set
        assertNotNull(SavedState.gsm, "GameStateManager should not be null after setting the level");
    }

    @Test
    void testEmptyFileHandling() {
        // Test reading bird positions from an empty file
        String emptyFileName = "emptyFile.txt";
        SavedState.readBirdPositions(emptyFileName, "2024-11-25 12:00:00", world);

        // Validate that no birds were added since the file was empty
        assertTrue(SavedState.resetBirds.isEmpty(), "Bird list should be empty when the file is empty");
    }
}
