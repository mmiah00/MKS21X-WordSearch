import java.util.*; //random, scanner, arraylist
import java.io.*; //file, filenotfoundexception
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Driver{
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println ("Please add more paramters. Format:\n	~java Driver rows cols fileName [randomSeed] [answers]");
			System.out.println ("~rows and dimensions of the puzzle");
			System.out.println ("~fileName is the file that contains words");
			System.out.println ("~randomSeed is optional. Forces the same puzzle to occur");
			System.out.println ("~write [answers] to trigger the answer key (minus brakets)");
		}
	}
}
