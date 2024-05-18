## Authors

Calvin Tom, Alguazil Florian, Allegre Romain, Estève Kelian

## Software Presentation

The software is based on the game Wordle. At the start of the game, a random word is selected, and the player has six attempts to guess it. For each attempt, a visual indicator informs whether each letter is correctly positioned in the word or not.

## Source Code Organization

- **Main:** Creation of the main game instance
- **Wordle:** Creation, management of the game scene, and retrieval of hints
- **GameWordle:** Management of the grid interactions
- **Dictionary:** Retrieval of the dictionary
- **Difficulty:** Definition of difficulty levels
- **Score:** Management of the score
- **Timer:** Management of the timer
- **VirtualKeyboard and KeyButton:** Management of the virtual keyboard and keys
- **TextBox:** Management of the grid cells
- **WordleGrid:** Management of the grid and grid/keyboard interaction methods

## Necessary Resources

1. **Eclipse IDE with the JavaFX library**
2. **External IDE to run the Python server and generate hints**

## Execution Procedure

1. **Run the Python program to handle the hints (if it is not running, the game will function without hints)**
2. **Execute the Wordle main in Eclipse**

## Libraries Used

1. java.fx
2. java.io
3. java.net
4. java.util
5. java.text

## Bibliographic References

1. [Wordle](https://wordle.louan.me/)
2. [JavaDoc](https://docs.oracle.com/javase/7/docs/api/)
3. Model used for hint generation: word2vec
