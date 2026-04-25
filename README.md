[![Open in Codespaces](https://classroom.github.com/assets/launch-codespace-2972f46106e565e64193e422d61a12cf1da4916b45550586e14ef0a7c637dd04.svg)](https://classroom.github.com/open-in-codespaces?assignment_repo_id=23603060)
# cpsc39-finalProjects

## Minesweeper
### Description
Minesweeper is a game, where the goal is to open every square on a given grid that doesn't have a mine. Opening a single square with a mine is an instant loss.
### How to Run
Download the src and main folder. Do not worry about the temp folder. Then, run Main.java. At some point in the program, it will create a new save file, savedata.csv.
### Features
This program features a text-based version of the class Minesweeper that runs in terminal. It features a save system that stores the user's wins, and allows the user to sort the leaderboard and search for specific usernames tied to games. The game itself has several difficulties, and allows the player to use their own custom difficulty.
### Data Structures Used
#### 1. Arrays
Arrays are used to mainly store the individual squares on the game's grid. Arrays were chosen because they are easy and simply to implement, and make the code more cleaner.

#### 2. Array Lists
Array Lists were chosen to store many things, mainly the entries in the save file. They were chosen because they have the ability to change size, which happens frequently when new entries are added to the save file.

#### 3. Queues
A queue is used in tandem with the game's flood fill algorithm. Normally, the flood fill algorithm is recursive. However, because the game's board can be large, using a recursive flood fill algorithm would cause a stack overflow and crash the program.

Instead, a queue is used, so that when the flood fill algorithm is called, squares that need to be filled in are instead placed in a queue, which is then looped over, thus preventing a stack overflow.

#### 4. HashMaps
A HashMap is used to store the games difficulties. It is mainly used when sorting the leaderboard by difficulty. It was chosen bause it allows the program to easily sort the leaderboard by difficulty.

#### 5. Strings
Strings are used everywhere in the program. They handle user inputs, save entries, and text graphics. They were chosen because they are text, which play the most important role in this program.

### Algorithms
#### 1. Flood Fill
Likely the most important algorithm in this program is the flood fill algorithm. During a game of minesweepr, it is common that a player open square, where there are no mines surrounding the square. The program instantly opens all the neighbors to make the game less tedious.

Although usually a recursive algorithm, to prevent a stack overflow, the algorithm in this program makes use of a queue.

First, the program creates a new queue, and adds the user's coordinates as the first element. Then, in a while loop that ends when the queue is empty, the program checks every adjacent tile. If the adjacent tile meets the game's requirements for being opened, it is placed in the queue. Once all the neighbors have been checked, the original coordinates are removed, and the next set of coordinates are checked. It repeats until the queue has been exhausted.

Big O: O(N)
#### 2. QuickSort
QuickSort is used to sort the leaderboards.

The algorithm, taking a list of entries as an input, continuously splits it in half, sorts the smaller halves, then combines them back together. It repeatedly does this until the entire list is sorted.

First, a pivot is chosen (in this program, the last element is the pivot).

Second, partitions are created around the pivot. Two points are created: a point i at index -1, and a point  jat index 0. If the value at j is smaller than the pivot, increment i, swap the elements at i and j, then increment j. If an element is larger than the pivot, then only j is incremented. Once j has reached the last element (the pivot), the elements at i + 1 and the pivot are swapped, and the new pivot is chosen as i + 1. Now, everything to the left of the new pivot is less than the pivot, and everything to the right of the new pivot is greater than the pivot. Either side of the pivot is a new partition.

Finally, the algorithm recursively calls on itself. Specifically, the new partitions created from the partition algorithm are passed as inputs. The partition algorithm is ran again on the partitions, creating smaller partitions, until eventually the newer partitions are one element long. 

Once all the elements are in their correct positions, the list is sorted.

This algorithm has a time complexity of:

Average: O(nlog n)

Worst Case: O(n^2)

#### 3. Mines Placement
Before the game starts, the game needs to scatter mines around the grid, and make sure that all non-mine squares show how many mines are adjacent to them.

First, the game asks the users for starting coordinates. The game will then scatter mines around the grid while avoiding placing mines directly adjacent to or on the user's coordinates. It does this by using and ArrayList that stores places of where it is acceptable to places mines. The game uses a regular array that stores 9 coordinates, which are the user's coordinates and the 8 coordinates adjacent to it. When the game iterates over every coordinate in the grid, if the coordinate is not part of that regular array, it is then added to the ArrayList. If it is, it's skipped.

Second, with an array list full of acceptable locations, the game uses a for loop, set to end when i equals the number of intended bombs. The game uses a randomized integer to pick a random index in the ArrayList, which corresponds to a random coordinate, places a mine there, then removes the coordinate. Once the number of mines on the grid equals the number of intended mines (depends on the difficulty), the algorithm ends.

If, for whatever reason, the user chooses to put so many mines such that it is physically impossible to put more mines down (e.x. on a 5 by 5 grid, the user wants 17 mines, which means that 16 mines go around the perimeter, but one MUST be adjacent to the target), it skips checking for if the coordinate is adjacent to the user's coordiantes. It will then only prevent mines from being placed on the coordinate itself.

Since the number of maximum mines allowed is one less than the area, if the user asks the games to place the maxmimum mines allowed, mines are placed everywhere except for the user's coordiantes, thus granting the player an automatic win.

Big O: O(xSize * ySize + n^2)

#### 4. Mine Counting
One of the most important clues that the player gets for where the mines are located are the numbers that the squares show. They tell the player how many bombs are adjacent to itself. If it is 0, then the square is blank.

Despite being so important, it is the most simplest algorithm. It simply iterates over every square in the grid, skipping squares with mines. It then gets an array with the squares neighbors (the same array used during neighbor checking in the mine placement algorithm), then checks how many of those neighbors are squares.

Big O: O(xSize * ySize)

### Development Reflection
1. One problem I encountered was getting the save system to work. I had trouble getting the program to create a save file, read it, write to it, and gernerally be able to access it. I solved this problem by using online guides and tutorials that helped me streamline the process.
2. One design improvement I discovered was making each entry in the save list a java object. Before I made the Entry.java class, everytime the program needed to use data from the save file, each function and class had to individually parse the string text. After I made the Entry.java class, it automatically did the parsing and allowed the other functions and classes to access the data using simply getter methods.
3. In Version 2, I would completely switch to a GUI version of the game, rather than a terminal based one. While the terminal works, having to type each coordinate individually gets tedious. However, if I were to implement a GUI, a user can simply click on the desired square.