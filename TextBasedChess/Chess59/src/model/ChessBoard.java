package model;
import chess.*;

/**
 * @author Jessie George
 * @author Karl Xu
 *
 *class for the chess board
 */
public class ChessBoard {

	/**
	 * board is the chess board of 64 chess squares
	 */
	static ChessSquare[][] board = new ChessSquare[8][8];
	
	/**
	 * enPassantActive is a counter to see how long a piece can make an en Passant move
	 */
	static int enPassantActive = 0;
	
	/**
	 * method to initialize the value of the board when you start the game
	 */
	public static void startBoard()
	{
		for(int i=0; i<8; i++)
		{
			for(int j=0; j<8; j++)
			{
				board[i][j] = new ChessSquare(i,j);
				
				if(i>=2 && i<=5)
				{
					board[i][j].makeSquareEmpty();
				}
				
				else
				{
					board[i][j].isEmptySquare = false;
					
					ChessPiece cp;
					
					//type of piece
					if(i==1||i==6)
						cp = new Pawn();
					else if(j==0||j==7)
						cp = new Rook();
					else if(j==1||j==6)
						cp = new Knight();
					else if(j==2||j==5)
						cp = new Bishop();
					else if(j==3)
						cp = new Queen();
					else
						cp = new King();
					
					//color of piece
					if(i==0 || i==1)
						cp.setColor(Constants.WHITE);
					
					else if(i==6 || i==7)
						cp.setColor(Constants.BLACK);
					
					board[i][j].setPiece(cp);
				}	
			}
		}
	}
	
	/**
	 * method to print the board
	 */
	public static void displayBoard()
	{	
		for(int i=7; i>=0; i--)
		{
			for(int j=0; j<=7; j++)
			{
				System.out.print(board[i][j]+" ");
			}
			
			System.out.println(i+1);
		}
		
		System.out.println(" a  b  c  d  e  f  g  h");
	}
	
	/**
	 * method to make a piece move on the board
	 * @param moveFrom is the location you are moving from (i.e. user input)
	 * @param moveTo is the location you are moving to (i.e. user input)
	 * @return rue if successful move is made. return false if it was an illegal move.
	 */
	public static boolean makeMove(String moveFrom, String moveTo)
	{
		//coordinates of moveFrom
		int fromRow = Character.getNumericValue(moveFrom.charAt(1))-1; //subtract 1 because the board array starts from 0 subscript
		int fromCol = (int)Character.toLowerCase(moveFrom.charAt(0)) - (int)'a'; //subtract ascii value of a, so that a=0, b=1, c=2 and so on.
		
		//coordinates of moveTo
		int toRow = Character.getNumericValue(moveTo.charAt(1))-1; //subtract 1 because the board array starts from 0 subscript
		int toCol = (int)Character.toLowerCase(moveTo.charAt(0)) - (int)'a'; //subtract ascii value of a, so that a=0, b=1, c=2 and so on.
	
		//if moveFrom points to an empty square, it's an illegal move.
		if(board[fromRow][fromCol].isEmptySquare)
		{
			printIllegalMoveError();
			return false;
		}
		
		//you can't bump into a piece that is the same color as you
		if(!board[toRow][toCol].isEmptySquare && (board[toRow][toCol].piece.pieceIsWhite() == board[fromRow][fromCol].piece.pieceIsWhite()))
		{
			printIllegalMoveError();
			return false;
		}
		
		else
		{
			if(board[fromRow][fromCol].piece.isValidMove(fromRow, fromCol, toRow, toCol))
			{
				board[fromRow][fromCol].piece.setHasMoved(true);
				
				if(!board[toRow][toCol].isEmptySquare && board[toRow][toCol].piece.toString().equals("wK")) //you are trying to capture the white king
				{
					//make the move
					board[toRow][toCol].piece = board[fromRow][fromCol].piece;
					board[toRow][toCol].isEmptySquare = board[fromRow][fromCol].isEmptySquare;
					
					board[fromRow][fromCol].makeSquareEmpty(); //make old spot empty
					
					displayBoard();
					System.out.println();
					System.out.println("Black wins");
					System.out.println();
					
					Chess.gameOver = true; //the game should end when king is captured
					return true; //made a move
				}
				
				if(!board[toRow][toCol].isEmptySquare && board[toRow][toCol].piece.toString().equals("bK")) //you are trying to capture the black king
				{
					//make the move
					board[toRow][toCol].piece = board[fromRow][fromCol].piece;
					board[toRow][toCol].isEmptySquare = board[fromRow][fromCol].isEmptySquare;
					
					board[fromRow][fromCol].makeSquareEmpty(); //make old spot empty
					
					displayBoard();
					System.out.println();
					System.out.println("White wins");
					System.out.println();
					
					Chess.gameOver = true; //the game should end when king is captured
					return true; //made a move
					
				}
	
				//make the move
				board[toRow][toCol].piece = board[fromRow][fromCol].piece;
				board[toRow][toCol].isEmptySquare = board[fromRow][fromCol].isEmptySquare;
				
				if((toRow == 0 || toRow == 7)
				&& (board[toRow][toCol].piece.toString().equals("wp") || board[toRow][toCol].piece.toString().equals("bp"))) {
					
					Promotion.promote(toRow, toCol, board[toRow][toCol].piece.toString().substring(0,1));
				}
				
				board[fromRow][fromCol].makeSquareEmpty(); //make old spot empty
				
				if(enPassantActive == 1) {
					
					for(int i = 0; i < 8; i++) {
						for(int j = 0; j < 8; j++) {
							
							if(!board[i][j].isEmptySquare && board[i][j].piece.enPassantRight == true) {
								board[i][j].piece.enPassantRight = false;
							}
						}
					}
				}
				
				if(enPassantActive > 0) {
					
					enPassantActive--;
				}
				
				if(Check.whiteInCheck() || Check.blackInCheck()) {
					
					if(Check.whiteInCheckmate()) {
						displayBoard();
						System.out.println();
						System.out.println("Checkmate");
						System.out.println();
						System.out.println("Black wins");
						System.out.println();
						Chess.gameOver = true;
					}
					else if(Check.blackInCheckmate()) {
						displayBoard();
						System.out.println();
						System.out.println("Checkmate");
						System.out.println();
						System.out.println("White wins");
						System.out.println();
						Chess.gameOver = true;
					}
					else {
						System.out.println("Check");
						System.out.println();
					}
				}
				
				return true;
			}
			
			
			else
			{
				printIllegalMoveError();
				return false;
			}
		}
		
	}
	
	/**
	 * method to print error for illegal move
	 */
	public static void printIllegalMoveError()
	{
		System.out.println("Illegal move, try again");
	}
	
}
