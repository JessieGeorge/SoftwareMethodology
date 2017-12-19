package model;
import chess.*;

/**
 * @author Jessie George
 * @author Karl Xu
 * 
 * class for promotion method
 */
public class Promotion {

	/**
	 * Promotes a pawn to another piece
	 * @param rank the rank of the location of the promoted piece
	 * @param file the file of the location of the promoted piece
	 * @param color the color of the promoted piece
	 */
	public static void promote(int rank, int file, String color) {
		
		if(Chess.promotionLetter.equals("Q") || Chess.promotionLetter.equals("")) {
			
			ChessBoard.board[rank][file].piece = new Queen();
			ChessBoard.board[rank][file].piece.setColor(color);
		}
		else if(Chess.promotionLetter.equals("R")) {
			
			ChessBoard.board[rank][file].piece = new Rook();
			ChessBoard.board[rank][file].piece.setColor(color);
		}
		else if(Chess.promotionLetter.equals("B")) {
			
			ChessBoard.board[rank][file].piece = new Bishop();
			ChessBoard.board[rank][file].piece.setColor(color);
		}
		else if(Chess.promotionLetter.equals("N")) {
			
			ChessBoard.board[rank][file].piece = new Knight();
			ChessBoard.board[rank][file].piece.setColor(color);
		}
	}
}