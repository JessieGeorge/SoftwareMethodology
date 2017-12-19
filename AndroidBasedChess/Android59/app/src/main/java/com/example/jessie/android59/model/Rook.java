package com.example.jessie.android59.model;

//import com.example.jessie.android59.view.GameActivity;

/**
 * @author Jessie George
 * @author Karl Xu
 * 
 * class for Rook
 */
public class Rook extends ChessPiece {

	/**
	 * toString makes the string value of color and piece
	 * @return the string value of color and piece
	 */
	public String toString()
	{
		return super.getColor()+Constants.ROOK;
	}
	
	
	/**
	 * Check if the move meets the conditions for a valid move for this chess piece
	 * @param fromRow row you are moving from
	 * @param fromCol column you are moving from
	 * @param toRow row you are moving to
	 * @param toCol column you are moving to
	 * @return true if it is a valid move
	 */
	public boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol)
	{

		//check if move would expose king to check
		boolean inCheck = Check.exposeKingToCheck(fromRow, fromCol, toRow, toCol, super.getColor());
		
		if(inCheck) {
			return false;
		}

		
		//same row or same column and no obstruction
		return (RelativePosition.sameRow(fromRow, toRow) || RelativePosition.sameColumn(fromCol, toCol)) && RelativePosition.noObstruction(fromRow, fromCol, toRow, toCol);
	}

	public boolean isThreatenMove(int fromRow, int fromCol, int toRow, int toCol)
	{

		//same row or same column and no obstruction
		return (RelativePosition.sameRow(fromRow, toRow) || RelativePosition.sameColumn(fromCol, toCol)) && RelativePosition.noObstruction(fromRow, fromCol, toRow, toCol);
	}

	public Move getLegalMove(int row, int col) {

		for(int i = row + 1, j = col; i < 8; i++) {

			if(!ChessBoard.board[i][j].isEmptySquare && (ChessBoard.board[i][j].piece.pieceIsWhite() == ChessBoard.board[row][col].piece.pieceIsWhite())) {
				continue;
			}

			if(isValidMove(row, col, i, j)) {
				return new Move(row, col, i, j);
			}
		}

		for(int i = row, j = col + 1; j < 8; j++) {

			if(!ChessBoard.board[i][j].isEmptySquare && (ChessBoard.board[i][j].piece.pieceIsWhite() == ChessBoard.board[row][col].piece.pieceIsWhite())) {
				continue;
			}

			if(isValidMove(row, col, i, j)) {
				return new Move(row, col, i, j);
			}
		}

		for(int i = row - 1, j = col; i >= 0; i--) {

			if(!ChessBoard.board[i][j].isEmptySquare && (ChessBoard.board[i][j].piece.pieceIsWhite() == ChessBoard.board[row][col].piece.pieceIsWhite())) {
				continue;
			}

			if(isValidMove(row, col, i, j)) {
				return new Move(row, col, i, j);
			}
		}

		for(int i = row, j = col - 1; j >= 0; j--) {

			if(!ChessBoard.board[i][j].isEmptySquare && (ChessBoard.board[i][j].piece.pieceIsWhite() == ChessBoard.board[row][col].piece.pieceIsWhite())) {
				continue;
			}

			if(isValidMove(row, col, i, j)) {
				return new Move(row, col, i, j);
			}
		}

		//there are no legal moves
		return new Move(10, 10, 10, 10);
	}
}
