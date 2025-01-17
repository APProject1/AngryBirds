Setup and Run Instructions
- Prerequisites-
1. Java Development Kit (JDK): JDK 22 is used in this project.
2. LibGDX Project Setup: The project should be structured according to LibGDX conventions and requires a Gradle build system.

Run Configuration- 
- Main class is set as the primary ApplicationAdapter.
- Main class: io.github.badlogic.AngryBirds.Main
- The program should start running from: AngryBirds.lwjgl3.main
- Launcher: io.github.badlogic.AngryBirds.lwjgl3.Lwjgl3Launcher

Assumptions-
- The game is designed to fit aspect ratio of 900x600 in windowed mode at the moment. After scaling the window or in Fullscreen, the game will stretch to fit the screen but functionality may be affected.
- The class state is used for making different screens and a game state manager implementing  stack is used to keep track of instances of different states.
- In the SavedGameState, click on a saved game leads to a dummy state called LoadSavedGameState which is yet to be implemented.
- Instead of using class Button for creating buttons, inputs have been handled manually using Rectangle and Textures.


Working-
- The game has a class Main that implements Class ApplicationAdaptor which in turn implements lwjgl3 module to launch and run the game.
- A package states is created for all the states/screens used in the game. The class state is used for making different screens and a game state manager implementing stack is used to keep track of instances of different states, which is used to jump between different states/screens.
- Several states are created MenuState for home/main menu, pause state, Level select state, win state, lose state.
- There is a LevelSelectState state that holds a list of levels and has methods that are used to setup those levels in the list in the constructor, and GUI input is used to play a certain level. Currently only 3 levels are implemented with no movement of elements implemented yet.
  
