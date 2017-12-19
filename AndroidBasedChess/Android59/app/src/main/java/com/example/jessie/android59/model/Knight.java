package com.example.jessie.android59.model;

//import com.example.jessie.android59.view.GameActivity;

/**
 * @author Jessie George
 * @author Karl Xu
 * 
 * class for Knight
 */
public class Knight extends ChessPiece {

	/**
	 * toString makes the string value of color and piece
	 * @return the string value of color and piece
	 */
	public String toString()
	{
		return super.getColor()+Constants.KNIGHT;
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


		//NOTE: The knight can jump over pieces so we don't call noObstruction
		
		if(fromRow < toRow) //need to go up
		{
			if(fromCol < toCol) //need to go right
			{
				//L shape is two up and one right
				if(((toRow - fromRow) == 2) && ((toCol - fromCol)==1))
					return true;
				
				//L shape is two right and one up
				if(((toCol - fromCol)==2) && ((toRow - fromRow) == 1))
					return true;
			}
			
			else //need to go left
			{
				//L shape is two up and one left
				if(((toRow - fromRow) == 2) && ((fromCol - toCol)==1))
					return true;
				
				//L shape is two left and one up
				if(((fromCol - toCol)==2) && ((toRow - fromRow) == 1))
					return true;
			}	
			
			return false; //you didn't satisfy any of the four L shapes that go up.
		}
		
		else //need to go down
		{
			if(fromCol < toCol) //need to go right
			{
				//L shape is two down and one right
				if(((fromRow - toRow) == 2) && ((toCol - fromCol)==1))
					return true;
				
				//L shape is two right and one down
				if(((toCol - fromCol)==2) && ((fromRow - toRow) == 1))
					return true;
			}
			
			else //need to go left
			{
				//L shape is two down and one left
				if(((fromRow - toRow) == 2) && ((fromCol - toCol)==1))
					return true;
				
				//L shape is two left and one down
				if(((fromCol - toCol)==2) && ((fromRow - toRow) == 1))
					return true;
			}
			
			return false; //you didn't satisfy any of the four L shapes that go down.
		}
	}

	public boolean isThreatenMove(int fromRow, int fromCol, int toRow, int toCol)
	{

		//NOTE: The knight can jump over pieces so we don't call noObstruction

		if(fromRow < toRow) //need to go up
		{
			if(fromCol < toCol) //need to go right
			{
				//L shape is two up and one right
				if(((toRow - fromRow) == 2) && ((toCol - fromCol)==1))
					return true;

				//L shape is two right and one up
				if(((toCol - fromCol)==2) && ((toRow - fromRow) == 1))
					return true;
			}

			else //need to go left
			{
				//L shape is two up and one left
				if(((toRow - fromRow) == 2) && ((fromCol - toCol)==1))
					return true;

				//L shape is two left and one up
				if(((fromCol - toCol)==2) && ((toRow - fromRow) == 1))
					return true;
			}

			return false; //you didn't satisfy any of the four L shapes that go up.
		}

		else //need to go down
		{
			if(fromCol < toCol) //need to go right
			{
				//L shape is two down and one right
				if(((fromRow - toRow) == 2) && ((toCol - fromCol)==1))
					return true;

				//L shape is two right and one down
				if(((toCol - fromCol)==2) && ((fromRow - toRow) == 1))
					return true;
			}

			else //need to go left
			{
				//L shape is two down and one left
				if(((fromRow - toRow) == 2) && ((fromCol - toCol)==1))
					return true;

				//L shape is two left and one down
				if(((fromCol - toCol)==2) && ((fromRow - toRow) == 1))
					return true;
			}

			return false; //you didn't satisfy any of the four L shapes that go down.
		}
	}

	public Move getLegalMove(int row, int col) {

		int i = row - 1;
		int j = col - 2;

		if(i >= 0 && i < 8 && j >= 0 && j < 8) {
			if(ChessBoard.board[i][j].isEmptySquare || (ChessBoard.board[i][j].piece.pieceIsWhite() != ChessBoard.board[row][col].piece.pieceIsWhite())) {
				if(isValidMove(row, col, i, j)) {
					return new Move(row, col, i, j);
				}
			}
		}

		i = row + 1;
		j = col - 2;

		if(i >= 0 && i < 8 && j >= 0 && j < 8) {
			if(ChessBoard.board[i][j].isEmptySquare || (ChessBoard.board[i][j].piece.pieceIsWhite() != ChessBoard.board[row][col].piece.pieceIsWhite())) {
				if(isValidMove(row, col, i, j)) {
					return new Move(row, col, i, j);
				}
			}
		}

		i = row - 1;
		j = col + 2;

		if(i >= 0 && i < 8 && j >= 0 && j < 8) {
			if(ChessBoard.board[i][j].isEmptySquare || (ChessBoard.board[i][j].piece.pieceIsWhite() != ChessBoard.board[row][col].piece.pieceIsWhite())) {
				if(isValidMove(row, col, i, j)) {
					return new Move(row, col, i, j);
				}
			}
		}

		i = row + 1;
		j = col + 2;

		if(i >= 0 && i < 8 && j >= 0 && j < 8) {
			if(ChessBoard.board[i][j].isEmptySquare || (ChessBoard.board[i][j].piece.pieceIsWhite() != ChessBoard.board[row][col].piece.pieceIsWhite())) {
				if(isValidMove(row, col, i, j)) {
					return new Move(row, col, i, j);
				}
			}
		}

		i = row + 2;
		j = col - 1;

		if(i >= 0 && i < 8 && j >= 0 && j < 8) {
			if(ChessBoard.board[i][j].isEmptySquare || (ChessBoard.board[i][j].piece.pieceIsWhite() != ChessBoard.board[row][col].piece.pieceIsWhite())) {
				if(isValidMove(row, col, i, j)) {
					return new Move(row, col, i, j);
				}
			}
		}

		i = row + 2;
		j = col + 1;

		if(i >= 0 && i < 8 && j >= 0 && j < 8) {
			if(ChessBoard.board[i][j].isEmptySquare || (ChessBoard.board[i][j].piece.pieceIsWhite() != ChessBoard.board[row][col].piece.pieceIsWhite())) {
				if(isValidMove(row, col, i, j)) {
					return new Move(row, col, i, j);
				}
			}
		}

		i = row - 2;
		j = col - 1;

		if(i >= 0 && i < 8 && j >= 0 && j < 8) {
			if(ChessBoard.board[i][j].isEmptySquare || (ChessBoard.board[i][j].piece.pieceIsWhite() != ChessBoard.board[row][col].piece.pieceIsWhite())) {
				if(isValidMove(row, col, i, j)) {
					return new Move(row, col, i, j);
				}
			}
		}

		i = row - 2;
		j = col + 1;

		if(i >= 0 && i < 8 && j >= 0 && j < 8) {
			if(ChessBoard.board[i][j].isEmptySquare || (ChessBoard.board[i][j].piece.pieceIsWhite() != ChessBoard.board[row][col].piece.pieceIsWhite())) {
				if(isValidMove(row, col, i, j)) {
					return new Move(row, col, i, j);
				}
			}
		}

		//there are no legal moves
		return new Move(10, 10, 10, 10);
	}
}
