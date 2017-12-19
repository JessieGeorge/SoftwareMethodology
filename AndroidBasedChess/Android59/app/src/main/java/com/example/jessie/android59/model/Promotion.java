package com.example.jessie.android59.model;
import android.util.Log;

import com.example.jessie.android59.view.*;

/**
 * @author Jessie George
 * @author Karl Xu
 * 
 * class for promotion method
 */
public class Promotion {

	private static final String TAGP = "PromotionTag"; //REMOVE
	/**
	 * Promotes a pawn to another piece
	 * @param rank the rank of the location of the promoted piece
	 * @param file the file of the location of the promoted piece
	 * @param color the color of the promoted piece
	 */
	public static void promote(int rank, int file, String color, String promotionLetter) {

		GameActivity gmactSpecial = GameActivity.getGameActivitySpecialObject();

		Log.i(TAGP, "Start of promotion and promotionLetter = "+promotionLetter);

		if(promotionLetter.equalsIgnoreCase("R")||promotionLetter.equalsIgnoreCase("Rook")) {
			Log.i(TAGP,"Entered back end for promotion to rook"); //REMOVE
			ChessBoard.board[rank][file].piece = new Rook();
			ChessBoard.board[rank][file].piece.setColor(color);

			//android gui
			if(color.equals("w"))
				gmactSpecial.androidGuiForPromotion(rank, file, "white_rook");
			else
				gmactSpecial.androidGuiForPromotion(rank, file, "black_rook");
		}
		else if(promotionLetter.equalsIgnoreCase("B")||promotionLetter.equalsIgnoreCase("Bishop")) {
			Log.i(TAGP,"Entered back end for promotion to bishop"); //REMOVE
			ChessBoard.board[rank][file].piece = new Bishop();
			ChessBoard.board[rank][file].piece.setColor(color);

			//android gui
			if(color.equals("w"))
				gmactSpecial.androidGuiForPromotion(rank, file, "white_bishop");
			else
				gmactSpecial.androidGuiForPromotion(rank, file, "black_bishop");
		}
		else if(promotionLetter.equalsIgnoreCase("N")||promotionLetter.equalsIgnoreCase("Knight")) {
			Log.i(TAGP,"Entered back end for promotion to knight"); //REMOVE
			ChessBoard.board[rank][file].piece = new Knight();
			ChessBoard.board[rank][file].piece.setColor(color);

			//android gui
			if(color.equals("w"))
				gmactSpecial.androidGuiForPromotion(rank, file, "white_knight");
			else
				gmactSpecial.androidGuiForPromotion(rank, file, "black_knight");
		}
		else {
			//DEFAULT CASE IS QUEEN

			Log.i(TAGP,"Entered back end for promotion to queen"); //REMOVE
			ChessBoard.board[rank][file].piece = new Queen();
			ChessBoard.board[rank][file].piece.setColor(color);

			//android gui
			if(color.equals("w"))
				gmactSpecial.androidGuiForPromotion(rank, file, "white_queen");
			else
				gmactSpecial.androidGuiForPromotion(rank, file, "black_queen");
		}
	}
}