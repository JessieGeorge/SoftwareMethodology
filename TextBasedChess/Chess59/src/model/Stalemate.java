package model;
import chess.Chess;


/**
 * @author Jessie George
 * @author Karl Xu
 *
 * class for Stalemate condition
 */
public class Stalemate {
	
	/**
	 * method to check if white king causes a stalemate
	 * @return true if white king caused a stalemate
	 */
	public static boolean whiteProducesStalemate()
	{
		if(!Check.whiteInCheck()) //white is not in check
		{
			if(!Chess.whitesMove)
				return false;
			
			ChessSquare whiteKingSquare = null;
			
			for(int i = 0; i < 8; i++) {
				for(int j = 0; j < 8; j++) {
					
					if(!ChessBoard.board[i][j].isEmptySquare && ChessBoard.board[i][j].piece.toString().equals("wK")) {
						whiteKingSquare = ChessBoard.board[i][j];
					}
				}
			}
			
			//see if white king can escape check by moving to another square
			for(int escapeRank = whiteKingSquare.rank - 1; escapeRank < whiteKingSquare.rank + 2; escapeRank++) {
				for(int escapeFile = whiteKingSquare.file - 1; escapeFile < whiteKingSquare.file + 2; escapeFile++) {
							
					if(escapeRank == whiteKingSquare.rank && escapeFile == whiteKingSquare.file) {
							continue;
					}
							
					if(escapeRank >= 0 && escapeRank <= 7 && escapeFile >= 0 && escapeFile <= 7) {
						if(ChessBoard.board[escapeRank][escapeFile].isEmptySquare || ChessBoard.board[escapeRank][escapeFile].piece.getColor().equals("b")) {
							if(Check.escapeCheck(escapeRank, escapeFile, "w", "b")) {
								return false; //king has a valid move that doesn't put him in check
							}
						}
					}
				}
			}
			
			for(int i = 0; i < 8; i++) {
				for(int j = 0; j < 8; j++) {
					
					if(!ChessBoard.board[i][j].isEmptySquare && ChessBoard.board[i][j].piece.pieceIsWhite() && !ChessBoard.board[i][j].piece.toString().equals("wK")) {
						for(int k=0; k<8; k++){
							for(int l=0; l<8; l++){
								
								if(!ChessBoard.board[k][l].isEmptySquare && (ChessBoard.board[k][l].piece.pieceIsWhite() == ChessBoard.board[k][l].piece.pieceIsWhite())){
									continue;
								}
								
								if(ChessBoard.board[i][j].piece.isValidMove(i, j, k, l))
									return false; //a piece has a valid move
							}
						}
					}
				}
			}
			
			return true; //white has produced a stalemate
		}
		
		return false; //white king is in check
	}
	
	/**
	 * method to check if black king causes a stalemate
	 * @return true if black king caused a stalemate
	 */
	public static boolean blackProducesStalemate()
	{
		if(!Check.blackInCheck()) //black is not in check
		{
			if(Chess.whitesMove)
				return false;
			
			
			ChessSquare blackKingSquare = null;
			
			for(int i = 0; i < 8; i++) {
				for(int j = 0; j < 8; j++) {
					
					if(!ChessBoard.board[i][j].isEmptySquare && ChessBoard.board[i][j].piece.toString().equals("bK")) {
						blackKingSquare = ChessBoard.board[i][j];
					}
				}
			}
			
			for(int escapeRank = blackKingSquare.rank - 1; escapeRank < blackKingSquare.rank + 2; escapeRank++) {
				for(int escapeFile = blackKingSquare.file - 1; escapeFile < blackKingSquare.file + 2; escapeFile++) {
					
					if(escapeRank == blackKingSquare.rank && escapeFile == blackKingSquare.file) {
						continue;
					}
					
					if(escapeRank >= 0 && escapeRank <= 7 && escapeFile >= 0 && escapeFile <= 7) {
						if(ChessBoard.board[escapeRank][escapeFile].isEmptySquare || ChessBoard.board[escapeRank][escapeFile].piece.getColor().equals("w")) {
							if(Check.escapeCheck(escapeRank, escapeFile, "b", "w")) {
								return false; //king has a valid move that doesn't put him in check
							}
						}
					}
				}
			}
			
			for(int i = 0; i < 8; i++) {
				for(int j = 0; j < 8; j++) {
					
					if(!ChessBoard.board[i][j].isEmptySquare && !ChessBoard.board[i][j].piece.pieceIsWhite() && !ChessBoard.board[i][j].piece.toString().equals("bK")) {
						for(int k=0; k<8; k++){
							for(int l=0; l<8; l++){
								
								if(!ChessBoard.board[k][l].isEmptySquare && (ChessBoard.board[k][l].piece.pieceIsWhite() == ChessBoard.board[k][l].piece.pieceIsWhite())){
									continue;
								}
								
								if(ChessBoard.board[i][j].piece.isValidMove(i, j, k, l))
									return false; //a piece has a valid move
							}
						}
					}
				}
			}
			
			return true; //black has produced a stalemate
		}
		
		return false; //black king is in check
	}
}
