package chess;
import model.*;
import java.io.*;
import java.util.StringTokenizer;

/**
 * @author Jessie George
 * @author Karl Xu
 *
 * class for Chess. this contains main.
 */
public class Chess {
	
	/**
	 * filePath is the the file path of the input
	 */
	private static String filePath = "testInput/test5.txt";
	
	/**
	 * promotionLetter is the letter which specifies what type pawn is being promoted into
	 */
	public static String promotionLetter = ""; 
	
	/**
	 * gameOver is true if the game is over
	 */
	public static boolean gameOver = false;
	
	/**
	 * whitesMove is true if it is white's turn to play. it is false if it is black's turn to play.
	 */
	public static boolean whitesMove = true;
	
	/**
	 * madeLegalMove is true if player made a legal chess move
	 */
	public static boolean madeLegalMove = false;
	
	/**
	 * method to play the game of chess
	 * @throws IOException input output exception
	 */
	public static void game() throws IOException
	{
		ChessBoard.startBoard();
		BufferedReader br = new BufferedReader(new FileReader(filePath));
		
		String specialWord = ""; //special word is resign or draw
		String moveFrom = ""; //first input
		String moveTo = ""; //second input
		
		while(!gameOver)
		{
			ChessBoard.displayBoard();
			
			do
			{
				System.out.println(); //white line before message
				
				if(Stalemate.whiteProducesStalemate())
				{
					System.out.println("Stalemate");
					System.out.println();
					System.out.println("draw");
					System.out.println();
					Chess.gameOver = true;
					madeLegalMove = true;
					return;
				}
				
				if(Stalemate.blackProducesStalemate())
				{
					System.out.println("Stalemate");
					System.out.println();
					System.out.println("draw");
					System.out.println();
					Chess.gameOver = true;
					madeLegalMove = true;
					return;
				}
				
				if(whitesMove)
					System.out.print("White's move: ");
				else
					System.out.print("Black's move: ");
				
				String input = br.readLine();
				System.out.println(input); //making the input from text viewable in output
				StringTokenizer st = new StringTokenizer(input);
				int countTok = st.countTokens();
				
				System.out.println(); //white line after message
				
				/*
				 * ASSUMING CORRECT INPUT FORMAT AS THE INSTRUCTIONS SPECIFY:
				 * Q: Do we need to worry about bad input, like a player entering "draw" without being asked by the other player, or just entering things that don't make sense?
				 * A: No
				 */
				
				if(countTok==1) //player entered "resign" or "draw"
				{
					specialWord = st.nextToken();
					gameOver = true;
					madeLegalMove = true; //consider resign or draw as a legal way to end the game
				}
				
				if(countTok==2) //player entered move
				{
					moveFrom = st.nextToken();
					moveTo = st.nextToken();
					madeLegalMove = ChessBoard.makeMove(moveFrom, moveTo);
					promotionLetter = "";
				}
				
				if(countTok==3) //player entered move and promotion letter OR player entered move and "draw?" request
				{
					moveFrom = st.nextToken();
					moveTo = st.nextToken();
					String temp = st.nextToken();
					if(temp.equals("draw?")) //player entered draw request //NOTE: we're assuming when a player gives draw request, the next player MUST accept draw request, as specified in lecture.
					{
						specialWord = temp;
					}
					else //player entered promotion letter
					{
						promotionLetter = temp;
					}
					
					madeLegalMove = ChessBoard.makeMove(moveFrom, moveTo);
				}
				
				if(madeLegalMove)
					whitesMove = !whitesMove;
				
			}while(!madeLegalMove);
			
		} //game over
		
		if(specialWord.equalsIgnoreCase("draw"))
			System.out.println("draw"); //is this last draw needed? you've asked grader
		
		if(specialWord.equalsIgnoreCase("resign")) //make this else if once you have the other conditions
		{
			if(whitesMove) //means black just resigned so white wins
				System.out.println("White wins");
			
			else //means white just resigned so black wins
				System.out.println("Black wins");
		}
			
		br.close();
	}
	
	/**
	 * main method, which calls the game method
	 * @param args arguments
	 * @throws IOException input output exception
	 */
	public static void main(String args[]) throws IOException
	{
		game();		
	}
}
