import java.util.*; //random, scanner, arraylist
import java.io.*; //file, filenotfoundexception
import java.io.File;
import java.lang.Math.*;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class WordSearch{
    private char[][]data;       // write word.txt with a list of words
    private int seed;           //the random seed used to produce this WordSearch
    private Random randgen;     //a random Object to unify your random calls
    private ArrayList<String>wordsToAdd; //all words from a text file get added to wordsToAdd, indicating that they have not yet been added
    private ArrayList<String>wordsAdded; //all words that were successfully added get moved into wordsAdded.
    private boolean key;

    public WordSearch (int rows, int cols, String fileName, int randSeed, boolean k) {
      data = new [rows][cols];
      clear ();
      randGen = new Random (seed);
      wordsToAdd = new ArrayList<String> ();
      wordsAdded = new ArrayList<String> ();
      File f = new File (fileName);
      Scanner in = new Scanner (f);
      while (in.hasNext ()) {
        String word = in.next ();
        wordsToAdd.add (word);
      }
      addAllWords();
      if (!key) {
        addRandomLetters();
      }
    }
/*
    public WordSearch (int rows, int cols, String fileName) throws FileNotFoundException {
          data = new char [rows][cols];
          clear ();
          Random r = new Random ();
          seed = r.nextInt () % 1000;
          randgen = new Random (seed);
          wordsToAdd = new ArrayList<String> ();
          wordsAdded = new ArrayList<String> ();
          File f = new File (fileName);
          Scanner in = new Scanner (f);
          while (in.hasNext ()) {
            String word = in.next ();
            wordsToAdd.add (word);
          }
          addAllWords ();
          addRandomLetters ();
    }

    public WordSearch (int rows, int cols, String filename, int randSeed) throws FileNotFoundException {
      data = new char [rows][cols];
      clear ();
      seed = randSeed;
      randgen = new Random (seed);
      wordsToAdd = new ArrayList<String> ();
      wordsAdded = new ArrayList<String> ();
      File f = new File (filename);
      Scanner in = new Scanner (f);
      while (in.hasNext ()) {
        String word = in.next ();
        wordsToAdd.add (word);
      }
      addAllWords ();
    }
*/

    /**Set all values in the WordSearch to underscores'_'*/
    private void clear(){
      for (int x = 0; x < data.length; x ++) {
        for (int y = 0; y < data[x].length; y ++) {
          data[x][y] = '_';
        }
      }
    }

    private void addRandomLetters() {
      char [] a = new char[26];
      for (int x = 0; x < 26; x ++ ) {
        a [x] = (char) ('A' + x);
      }

      for (int x = 0; x < data.length; x++) {
        for (int y = 0; y < data[x].length; y++) {
          if (data[x][y] == '_') {
            int z = (randgen.nextInt(26));
            data[x][y] = a[z];
          }
        }
      }
      }

    /**Each row is a new line, there is a space between each letter
     *@return a String with each character separated by spaces, and rows
     *separated by newlines.
     */
    public String toString(){
      String ans = "";
      for (int x = 0; x < data.length; x ++ ) {
        for (int y = 0; y < data[x].length; y ++) {
          if (y == 0) {
            ans += "| ";
          }
          ans += data[x][y] + " ";
          if (y == data[x].length - 1) {
            ans += " | \n";
          }
        }
      }
      // ^^ makes the WordGrid

      ans += "Words: ";
      for (int x = 0; x < wordsAdded.size (); x ++) {
        if (x == wordsAdded.size () - 1) {
          ans += wordsAdded.get (x) + " (seed: " + seed + ")";
        }
        else {
          ans += wordsAdded.get (x) + ", ";
        }
      }
      return ans;
    }


    private boolean fits (String word, int r, int c, int rowIncrement, int colIncrement) {
       if ( (rowIncrement & colIncrement) == 0) return false;
       if ( r >= data.length || c >= data[0].length || r < 0 || c < 0) return false;
       if (r + rowIncrement * word.length () > data.length - 1 || r + rowIncrement * word.length () < 0 )  return false;
       if (c + colIncrement * word.length () > data[r].length - 1 || c + colIncrement * word.length () < 0)  return false;
       int x = c;
       int y = r;
       for (int i = 0; i < word.length () ; i ++ ) {
         char l = word.charAt (i);
         if (data[y][x] != l && data[y][x] != '_') {
           return false;
         }
         x += colIncrement;
         y += rowIncrement;
       }
       return true;
    }


    /**Attempts to add a given word to the specified position of the WordGrid.
     *The word is added in the direction rowIncrement,colIncrement
     *Words must have a corresponding letter to match any letters that it overlaps.
     *
     *@param word is any text to be added to the word grid.
     *@param row is the vertical locaiton of where you want the word to start.
     *@param col is the horizontal location of where you want the word to start.
     *@param rowIncrement is -1,0, or 1 and represents the displacement of each letter in the row direction
     *@param colIncrement is -1,0, or 1 and represents the displacement of each letter in the col direction
     *@return true when: the word is added successfully.
     *        false when: the word doesn't fit, OR  rowchange and colchange are both 0,
     *        OR there are overlapping letters that do not match
     */
     /*[rowIncrement,colIncrement] examples:
      *[-1,1] would add up and the right because (row -1 each time, col + 1 each time)
      *[ 1,0] would add downwards because (row+1), with no col change
      *[ 0,-1] would add towards the left because (col - 1), with no row change
      */

    private boolean addWord(String word,int row, int col, int rowIncrement, int colIncrement){
      if (fits (word, row, col, rowIncrement, colIncrement)) {
        int index = 0;
        int x = col;
        int y = row;
        while (index < word.length ()) {
          data[y][x] = word.charAt (index);
          x += colIncrement;
          y += rowIncrement;
          index += 1;
        }
        return true;
      }
      else { return false;}
    }


    private void addAllWords () {
      int fails = 0;
      int i = 0;
      while (fails < 1000000 && wordsToAdd.size () > 0) {
        String now = wordsToAdd.get (i);
        if (now.length () == 0) {
          i += 1;
        }
        int randRow = Math.abs (randgen.nextInt () % data.length);
        int randCol = Math.abs (randgen.nextInt () % data[0].length);
        int rowInc = (randgen.nextInt (3)) - 1;
        int colInc = (randgen.nextInt (3)) - 1;
        if (! (fits (now, randRow, randCol, rowInc, colInc))) {
          fails += 1;
        }
        else {
          addWord (now,randRow, randCol, rowInc, colInc);
          wordsToAdd.remove (0);
          wordsAdded.add (now);
          //i += 1;
        }
      }
    }







    //****************************************NOT USED BUT JUST KEEPING IT IN CASE ************************************************\\`

    /**Attempts to add a given word to the specified position of the WordGrid.
     *The word is added from left to right, must fit on the WordGrid, and must
     *have a corresponding letter to match any letters that it overlaps.
     *
     *@param word is any text to be added to the word grid.
     *@param row is the vertical locaiton of where you want the word to start.
     *@param col is the horizontal location of where you want the word to start.
     *@return true when the word is added successfully. When the word doesn't fit,
     * or there are overlapping letters that do not match, then false is returned
     * and the board is NOT modified.
     */

    public boolean addWordHorizontal(String word,int row, int col){
      if (col + word.length () - 1> data[row].length - 1) {
        return false;
      }
      if (data[row][col] != '_' && data[row][col] != word.charAt (0)) {
        return false;
      }
      int y = col;
      int i = 0;
      while (i < word.length ()) {
        data [row][y] = word.charAt (i);
        y += 1;
        i += 1;
      }
      return true;
    }


   /**Attempts to add a given word to the specified position of the WordGrid.
     *The word is added from top to bottom, must fit on the WordGrid, and must
     *have a corresponding letter to match any letters that it overlaps.
     *
     *@param word is any text to be added to the word grid.
     *@param row is the vertical locaiton of where you want the word to start.
     *@param col is the horizontal location of where you want the word to start.
     *@return true when the word is added successfully. When the word doesn't fit,
     *or there are overlapping letters that do not match, then false is returned.
     *and the board is NOT modified.
     */


    public boolean addWordVertical(String word,int row, int col){
      if (row + word.length () > data.length - 1) {
        return false;
      }
      if (data[row][col] != '_' && data[row][col] != word.charAt (0)) {
        return false;
      }
      int x = row;
      int i = 0;
      while (i < word.length ()) {
        data [x][col] = word.charAt (i);
        x += 1;
        i += 1;
      }
      return true;
    }


    /**Attempts to add a given word to the specified position of the WordGrid.
      *The word is added from top left to bottom right, must fit on the WordGrid,
      *and must have a corresponding letter to match any letters that it overlaps.
      *
      *@param word is any text to be added to the word grid.
      *@param row is the vertical locaiton of where you want the word to start.
      *@param col is the horizontal location of where you want the word to start.
      *@return true when the word is added successfully. When the word doesn't fit,
      *or there are overlapping letters that do not match, then false is returned.
      */


   public boolean addWordDiagonal(String word,int row, int col){
     int x = col;
     int y = row;
     if (x + word.length () - 1 > data[row].length || y + word.length () - 1 > data.length) {
       return false;
     }
     int index = 0;
     while (index < word.length ()) {
       data[y][x] = word.charAt (index);
       x += 1;
       y += 1;
       index += 1;
     }
     return true;
   }

   //****************************************NOT USED BUT JUST KEEPING IT IN CASE ************************************************\\`

   /**Attempts to add a given word to the specified position of the WordGrid.
  *The word is added in the direction rowIncrement,colIncrement

  *Words must have a corresponding letter to match any letters that it overlaps.

  *
  *@param word is any text to be added to the word grid.
  *@param row is the vertical locaiton of where you want the word to start.
  *@param col is the horizontal location of where you want the word to start.

  *@param rowIncrement is -1,0, or 1 and represents the displacement of each letter in the row direction
  *@param colIncrement is -1,0, or 1 and represents the displacement of each letter in the col direction

  *@return true when: the word is added successfully.
  *        false when: the word doesn't fit, OR  rowchange and colchange are both 0,
  *        OR there are overlapping letters that do not match
  */
  /*[rowIncrement,colIncrement] examples:

   *[-1,1] would add up to the right because (row -1 each time, col + 1 each time)

   *[1,0] would add to the right because (row+1), with no col change
   */
/*
 public boolean addWord(String word,int row, int col, int rowIncrement, int colIncrement){
   int x = col;
   int y = row;
   if (rowIncrement == 0 && colIncrement == 0) {
     return false;
   }
   if (x + word.length () - 1 > data[row].length || y + word.length () - 1 > data.length) {
     return false;
   }
   int index = 0;
   while (index < word.length ()) {
     data[y][x] = word.charAt (index);
     x += colIncrement;
     y += rowIncrement;
     index += 1;
   }
   return true;
  }
  */
  //**********************************************************************************************************************\\
  public static void instructions () {
    System.out.println ("Welcome! \n Please enter the following parameters \n \t ~Rows: The numbers of rows you want your word search to be \n\t ~Columns: The nums of columns you want your essay to be. \n\t ~Filename: The name of the plain text file that contains the words. \n\t ~RandomSeed: Forces the same puzzle to occur again (optional) \n\t ~Key?: write key for the answers to appear (optional) \n\n\t ~If you write key you must also add a seed");
  }

  public static void main(String[] args) {
    WordSearch test;
    try {
      if (args.length == 3) {
        myrandgen = new Random ();
        myseed = randgen.nextInt () % 1000;
        kee = false;
        test = new WordSearch (Integer.parseInt (args [0]), Integer.parseInt (args [1]), args [2], myseed, kee);
        System.out.println (test);
      }
      if (args.length == 4) {
        test = new WordSearch (Integer.parseInt (args [0]), Integer.parseInt (args [1]), args [2], Integer.parseInt (args [3]));
        System.out.println (test);
      }
      if (args.length < 3) {
        instructions ();
      }
    }
    catch (FileNotFoundException e) {
      System.out.println ("File " + args[2] + " not found");
    }
  }

}

//testing 0 constructors
// testing 0 methods
// checking only through command line arguments
// write one constructor
  // so if one is not provided, you can set it in the main
//data = new [][] --> clear () --> randGen = new Random (seed) --> wordsToAdd = getWord (filename) --> addAllWords(); --> if (!key) { fillRandomLetters()};
