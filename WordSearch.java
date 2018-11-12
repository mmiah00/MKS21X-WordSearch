import java.util.*; //random, scanner, arraylist
import java.io.*; //file, filenotfoundexception
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class WordSearch{
    private char[][]data;       // write word.txt with a list of words
    private int seed;           //the random seed used to produce this WordSearch
    private Random randgen;     //a random Object to unify your random calls
    private ArrayList<String>wordsToAdd; //all words from a text file get added to wordsToAdd, indicating that they have not yet been added
    private ArrayList<String>wordsAdded; //all words that were successfully added get moved into wordsAdded.

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
      //addAllWords ();
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
      //addAllWords ();
    }
    /**Set all values in the WordSearch to underscores'_'*/
    private void clear(){
      for (int x = 0; x < data.length; x ++) {
        for (int y = 0; y < data[x].length; y ++) {
          data[x][y] = '_';
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
          if (y != data[x].length - 1) {
            ans += data[x][y] + " ";
          }
          else {
            ans += data [x][y] + "| \n";
          }
        }
      }
      // ^^ makes the WordGrid

      ans += "Words: ";
      for (int x = 0; x < wordsAdded.size (); x ++) {
        if (x == wordsAdded.size () - 1) {
          ans += wordsAdded.get (x) + "(seed: " + seed + ")";
        }
        ans += wordsAdded.get (x) + " ";
      }
      return ans;
    }

    private boolean fits (String word, int r, int c, int rowIncrement, int colIncrement) {
       if ( (rowIncrement & colIncrement) == 0) return false;
       if (r + rowIncrement * word.length () > data.length - 1)  return false;
       if (c + colIncrement * word.length () > data[r].length - 1)  return false;
       int x = r;
       int y = c;
       for (int i = 0; i < word.length (); i ++) {
         if (data[x][y] != word.charAt (i) && data[x][y] != '_') {
           return false;
         }
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
      int randRow = Math.abs (randgen.nextInt () % data[0].length);
      int randCol = Math.abs (randgen.nextInt () % data.length);
      int rowInc = (randgen.nextInt (2)) - 1;
      int colInc = (randgen.nextInt (2)) - 1;
      int successes = 0;
      int fails = 0;
      while (fails < 1000) { 
        for (int x = 0; x < wordsToAdd.size (); x ++ ) {
          String now = wordsToAdd.get (x);
          if (! (addWord (now, randRow, randCol, rowInc, colInc))) {
            fails += 1;
          }
          else {
            addWord (now,randRow, randCol, rowInc, colInc);
            wordsAdded.add (now);
          }
        }
      }
    }







    //***********************************************************************************************************\\`

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

    /*
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
    */

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

    /*
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
    */

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

  /*
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
   */

   //**********************************************************************************************************************\\

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
}
