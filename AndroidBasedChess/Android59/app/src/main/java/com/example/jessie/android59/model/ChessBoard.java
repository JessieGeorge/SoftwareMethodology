package com.example.jessie.android59.model;
import android.util.Log;

import com.example.jessie.android59.view.*;

/**
 * @author Jessie George
 * @author Karl Xu
 *
 *class for the chess board
 */
public class ChessBoard {

	private static final String TAG2 = "ChessBoardTag"; //REMOVE

	/**
	 * board is the chess board of 64 chess squares
	 */
	public static ChessSquare[][] board = new ChessSquare[8][8];
	
	/**
	 * enPassantActive is a counter to see how long a piece can make an en Passant move
	 */
	public static int enPassantActive = 0;

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

	public static boolean makeMove(GameActivity ga, int fromRow, int fromCol, int toRow, int toCol)
	{
		//if moveFrom points to an empty square, it's an illegal move.
		if(board[fromRow][fromCol].isEmptySquare)
		{
			ga.illegalMoveError();
			return false;
		}

		//you can't bump into a piece that is the same color as you
		if(!board[toRow][toCol].isEmptySquare && (board[toRow][toCol].piece.pieceIsWhite() == board[fromRow][fromCol].piece.pieceIsWhite()))
		{
			ga.illegalMoveError();
			return false;
		}

		else
		{
			if(board[fromRow][fromCol].piece.isValidMove(fromRow, fromCol, toRow, toCol))
			{
				Log.i(TAG2, "Done with isValidMove");
				board[fromRow][fromCol].piece.setHasMoved(true);

				//make the move
				board[toRow][toCol].piece = board[fromRow][fromCol].piece;
				board[toRow][toCol].isEmptySquare = board[fromRow][fromCol].isEmptySquare;

				board[fromRow][fromCol].makeSquareEmpty(); //make old spot empty

				//make the move for the android gui
				ga.makeAndroidMove(fromRow, fromCol, toRow, toCol);

				if((toRow == 0 || toRow == 7)
						&& (board[toRow][toCol].piece.toString().equals("wp") || board[toRow][toCol].piece.toString().equals("bp"))) {

					ga.requestPromotionChoice();
					ga.justBeganPromotion = true; //asynchronous task so we want the others to wait
					return true;
				}

				if(enPassantActive == 1) {

					for(int i = 0; i < 8; i++) {
						for(int j = 0; j < 8; j++) {

							if(!board[i][j].isEmptySquare && board[i][j].piece.enPassantRight == true) {
								board[i][j].piece.enPassantRight = false;
							}
						}
					}
				}

				enPassantActive--;

				for(int i = 0; i < 8; i++) {
					for(int j = 0; j < 8; j++) {

						if(!board[i][j].isEmptySquare) {
							board[i][j].piece.enPassantCounter--;
							board[i][j].piece.undoCounter--;
							board[i][j].piece.undoCounter2--;
						}
					}
				}

				if(Check.whiteInCheck() || Check.blackInCheck()) {

					if(Check.whiteInCheckmate()) {
						ga.checkmatePopup();
						ga.winPopup("Black");
						GameActivity.gameOver = true;
					}

					else if(Check.blackInCheckmate()) {
						ga.checkmatePopup();
						ga.winPopup("White");
						GameActivity.gameOver = true;
					}
					else {
						ga.checkPopup();
					}
				}

				return true;
			}

			else
			{
				ga.illegalMoveError();
				return false;
			}
		}
	}
}
