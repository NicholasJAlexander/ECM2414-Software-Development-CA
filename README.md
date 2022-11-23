# ECM2414-Software-Development-CA

### ECM2414 Software Development pair programming coursework

This is a card game that is played automatically on the command line
<br>

### Task specification

The game has n players, each numbered 1 to n, with n being a positive integer, and n decks of cards again numbers 1 to n.
Each player will hold a hand of 4 cards. Both of these hands and decks will be drawn from a pack which contains 8n cards.
Each card has a face value (denomination) of a non-negative integer.

The decks and players will form a ring topology. As seen below

![img.png](doc/topological%20relationship.png)

To win the game, a player needs four cards of the same value in their hand. 
If a player is given four cards which are all the same value at the start of the gane, they should immediately declare this
and that players should then notify the other player threads.

If the game is not won immediately, then the game progresses as follows:
- Each player picks a card from the top of the deck to their left
- Discards a card from their deck to the bottom of the deck to their right

This process will continue until a player declares that they have four cards of the same value.

### Developing a solution

The program will need to take two inputs. The first being the number of players in playing the game and the second being the text file that contains the card data.
The pack file must be valid and contain enough cards for the game to start.

The actions that the player takes should be outputted to a text file, for example: 
```
player 1 draws a 4 from deck 1
player 1 discards a 3 to deck 2
player 1 current hand is 1 1 2 4
```

# Running the program

To run the game you will need to execute the compiled jar file `ECM2414-Software-Development-CA.jar`, this can be done by following these steps:

1. Open a command line interface such as powershell
2. Type `java -jar .\cards.jar`
3. You will then be prompted to input the number of players (e.g. 4)
4. You will then be asked for the filename of the pack, you will need to provide the full path of the pack file
5. The program will then run and the outputs will be in a directory call `Logs` which will be in the directory that you ran the jar file in

### Running JUnit tests
Testing for this program has been developed in JUnit 4.13.1. These tests can be run using the jar file:

1. Open a command line interface such as powershell
2. Type `java -cp .\cards.jar RunTests`
3. The output of the tests will then be printed to the console window



# Authors
- Nicholas Alexander
- James Calnan

# License
MIT License
