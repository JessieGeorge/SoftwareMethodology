package com.example.jessie.android59.model;

//import com.example.jessie.android59.view.GameActivity;

import com.example.jessie.android59.view.GameActivity;

/**
 * @author Jessie George
 * @author Karl Xu
 * 
 * class for Pawn
 */
public class Pawn extends ChessPiece {
	
	/**
	 * toString makes the string value of color and piece
	 * @return the string value of color and piece
	 */
	public String toString()
	{
		return super.getColor()+Constants.PAWN;
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


		//EN PASSANT CASE
		if(enPassantRight == true) {
			
			if(ChessBoard.board[toRow][toCol].isEmptySquare
			&& RelativePosition.sameDiagonal(fromRow, fromCol, toRow, toCol)
			&& RelativePosition.movesForward(fromRow, toRow, super.pieceIsWhite())
			&& (RelativePosition.numberOfSteps(fromRow, fromCol, toRow, toCol)==1)) {
				
				if(super.pieceIsWhite()) {
					ChessBoard.board[toRow-1][toCol].piece = null;
					ChessBoard.board[toRow-1][toCol].isEmptySquare = true;

					//ANDROID GUI FOR ENPASSANT
					GameActivity gmactSpecial = GameActivity.getGameActivitySpecialObject();
					gmactSpecial.androidGuiForEnpassant(toRow-1, toCol);
				}
				else {
					ChessBoard.board[toRow+1][toCol].piece = null;
					ChessBoard.board[toRow+1][toCol].isEmptySquare = true;

					//ANDROID GUI FOR ENPASSANT
					GameActivity gmactSpecial = GameActivity.getGameActivitySpecialObject();
					gmactSpecial.androidGuiForEnpassant(toRow+1, toCol);
				}
				
				return true;
			}
		}
		
		//CAPTURE CASE
		//the two pieces are of opposite color, so it's a capture
		if(!ChessBoard.board[toRow][toCol].isEmptySquare
		&& RelativePosition.sameDiagonal(fromRow, fromCol, toRow, toCol)
		&& RelativePosition.movesForward(fromRow, toRow, super.pieceIsWhite())
		&& (RelativePosition.numberOfSteps(fromRow, fromCol, toRow, toCol)==1)) {

			if(super.pieceIsWhite() && fromRow == 1) {
				undoCounter = 1;
			}
			if(!super.pieceIsWhite() && fromRow == 6) {
				undoCounter = 1;
			}
			return true;
		}
		
		//TWO STEP CASE
		//if it's first move, number of steps can be two or one .. if it's two steps you should check that there's no obstruction..if it's one step it should just go to the last line i.e. the regular case
		if(!super.getHasMoved() //it has not moved before i.e. this is the first move
		&& (RelativePosition.numberOfSteps(fromRow, fromCol, toRow, toCol)==2)
		&& RelativePosition.noObstruction(fromRow, fromCol, toRow, toCol)
		&& RelativePosition.sameColumn(fromCol, toCol)
		&& (RelativePosition.movesForward(fromRow, toRow, super.pieceIsWhite()))
		&& (ChessBoard.board[toRow][toCol].isEmptySquare)) {
			
			String oppColor;
			if(super.getColor().equals("w")) {
				oppColor = "b";
			}
			else {
				oppColor = "w";
			}
			
			if(toCol > 0 && !ChessBoard.board[toRow][toCol-1].isEmptySquare && ChessBoard.board[toRow][toCol-1].piece.toString().equals(oppColor + "p")) {
				ChessBoard.board[toRow][toCol-1].piece.enPassantRight = true;
				ChessBoard.board[toRow][toCol-1].piece.enPassantCounter = 2;
				ChessBoard.enPassantActive = 2;
			}
			if(toCol < 7 && !ChessBoard.board[toRow][toCol+1].isEmptySquare && ChessBoard.board[toRow][toCol+1].piece.toString().equals(oppColor + "p")) {
				ChessBoard.board[toRow][toCol+1].piece.enPassantRight = true;
				ChessBoard.board[toRow][toCol+1].piece.enPassantCounter = 2;
				ChessBoard.enPassantActive = 2;
			}

			undoCounter = 1;
			return true;
		}

		//REGULAR CASE
		//same column and 1 step and forward and to an unoccupied space NOTE: noObstruction can't be called here because it's just one step. 
		boolean result = RelativePosition.sameColumn(fromCol, toCol) && (RelativePosition.numberOfSteps(fromRow, fromCol, toRow, toCol)==1) && (RelativePosition.movesForward(fromRow, toRow, super.pieceIsWhite())) && (ChessBoard.board[toRow][toCol].isEmptySquare);

		if(result) {
			if(super.pieceIsWhite() && fromRow == 1) {
				undoCounter = 1;
			}
			if(!super.pieceIsWhite() && fromRow == 6) {
				undoCounter = 1;
			}
		}

		return result;
	}

	public boolean isThreatenMove(int fromRow, int fromCol, int toRow, int toCol)
	{

		//CAPTURE CASE
		//the two pieces are of opposite color, so it's a capture
		if(!ChessBoard.board[toRow][toCol].isEmptySquare
				&& RelativePosition.sameDiagonal(fromRow, fromCol, toRow, toCol)
				&& RelativePosition.movesForward(fromRow, toRow, super.pieceIsWhite())
				&& (RelativePosition.numberOfSteps(fromRow, fromCol, toRow, toCol)==1)) {

			return true;
		}

		return false;
	}

	public Move getLegalMove(int row, int col) {

		int i = row + 2;
		int j = col;

		if(i >= 0 && i < 8 && j >= 0 && j < 8) {
			if(ChessBoard.board[i][j].isEmptySquare || (ChessBoard.board[i][j].piece.pieceIsWhite() != ChessBoard.board[row][col].piece.pieceIsWhite())) {
				if(isValidMove(row, col, i, j)) {
					return new Move(row, col, i, j);
				}
			}
		}

		for(i = row + 1, j = col - 1; j <= col + 1; j++) {

			if(i < 0 || i > 7 || j < 0 || j > 7) {
				continue;
			}

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
