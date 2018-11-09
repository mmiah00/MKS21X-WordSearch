public class WordSearch{
    private char[][]data;

    /**Initialize the grid to the size specified
     *and fill all of the positions with '_'
     *@param row is the starting height of the WordSearch
     *@param col is the starting width of the WordSearch
     */
    public WordSearch(int rows,int cols){
      data = new char[rows][cols];
      clear ();
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
          if (y != data[x].length - 1) {
            ans += data[x][y] + " ";
          }
          else {
            ans += data [x][y] + "\n";
          }
        }
      }
      return ans;
    }


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
 }

 /*[rowIncrement,colIncrement] examples:

  *[-1,1] would add up to the right because (row -1 each time, col + 1 each time)

  *[1,0] would add to the right because (row+1), with no col change
  */
}
