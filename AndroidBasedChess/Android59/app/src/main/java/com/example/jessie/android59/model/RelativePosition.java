package com.example.jessie.android59.model;

/**
 * @author Jessie George
 * @author Karl Xu
 * 
 * class to find the relative position of the two spots in a move i.e. the spot you are moving from and the spot you are moving to
 */

public class RelativePosition {
	
	/**
	 * Check if you are moving in the same row
	 * @param fromRow row you are moving from
	 * @param toRow row you are moving to
	 * @return true if both rows are equal
	 */
	
	public static boolean sameRow(int fromRow, int toRow)
	{
		return fromRow == toRow;
	}

	/**
	 * Check if you are moving in the same column
	 * @param fromCol column you are moving from
	 * @param toCol column you are moving to
	 * @return true if both columns are equal
	 */
	public static boolean sameColumn(int fromCol, int toCol)
	{
		return fromCol == toCol;
	}
	
	/**
	 * Check if you moving in the same diagonal
	 * @param fromRow row you are moving from
	 * @param fromCol column you are moving from
	 * @param toRow row you are moving to
	 * @param toCol column you are moving to
	 * @return true if you are moving in the same diagonal
	 */
	public static boolean sameDiagonal(int fromRow, int fromCol, int toRow, int toCol)
	{
		int baseOfTriangle = Math.abs(toCol - fromCol);
		int heightOfTriangle = Math.abs(toRow - fromRow);
		
		return baseOfTriangle == heightOfTriangle;
	}
	
	/**
	 * Check if there is no obstruction on the path of movement. i.e no piece exists between the from position and the to position
	 * @param fromRow row you are moving from
	 * @param fromCol column you are moving from
	 * @param toRow row you are moving to
	 * @param toCol column you are moving to
	 * @return true if there is no obstruction on the path of movement
	 */
	public static boolean noObstruction(int fromRow, int fromCol, int toRow, int toCol)
	{
		if(sameRow(fromRow, toRow))
		{
			//check the columns between the two pieces
			
			int smallerCol;
			int biggerCol;
			
			if(fromCol <= toCol)
			{
				smallerCol =  fromCol;
				biggerCol = toCol;
			}
			
			else
			{
				smallerCol =  toCol;
				biggerCol = fromCol;
			}
			
			for(int i=smallerCol+1; i<biggerCol; i++)
			{
				if(!ChessBoard.board[fromRow][i].isEmptySquare) //obstruction
					return false;
			}
			
			return true; //no obstruction between two pieces in the same row
		}
	
		else if(sameColumn(fromCol, toCol))
		{
			//check the rows between the two pieces
		
			int smallerRow;
			int biggerRow;
		
			if(fromRow <= toRow)
			{
				smallerRow =  fromRow;
				biggerRow = toRow;
			}
		
			else
			{
				smallerRow =  toRow;
				biggerRow = fromRow;
			}
		
			for(int i=smallerRow+1; i<biggerRow; i++)
			{
				if(!ChessBoard.board[i][fromCol].isEmptySquare) //obstruction
					return false;
			}
		
			return true; //no obstruction between two pieces in the same column
		}
	
		else if(sameDiagonal(fromRow, fromCol, toRow, toCol))
		{
			boolean goRight = fromCol < toCol;
			boolean goUp = fromRow < toRow;
			
			if(goRight) //right
			{
				if(goUp) //right upper diagonal
				{
					for(int i = fromRow+1, j = fromCol+1; i<toRow && j<toCol; i++, j++)
					{
						if(!ChessBoard.board[i][j].isEmptySquare) //obstruction
							return false;
					}
				}
				
				else //right lower diagonal
				{
					for(int i = fromRow-1, j = fromCol+1; i>toRow && j<toCol; i--, j++)
					{
						if(!ChessBoard.board[i][j].isEmptySquare) //obstruction
							return false;
					}
				}
				
			}
			
			else //left
			{
				if(goUp) //left upper diagonal
				{
					for(int i = fromRow+1, j = fromCol-1; i<toRow && j>toCol; i++, j--)
					{
						if(!ChessBoard.board[i][j].isEmptySquare) //obstruction
							return false;
					}
				}
				
				else //left lower diagonal
				{
					for(int i = fromRow-1, j = fromCol-1; i>toRow && j>toCol; i--, j--)
					{
						if(!ChessBoard.board[i][j].isEmptySquare) //obstruction
							return false;
					}
				}
				
			}
			
			return true; //no obstruction between two pieces on the same diagonal
		}
		
		//not same row or column or diagonal ... If your code works well, it should never reach here. This is just a safety measure.
		else
		{
			return false;
		}
	}
	
	/**
	 * Find the number of steps you take when you move
	 * @param fromRow row you are moving from
	 * @param fromCol column you are moving from
	 * @param toRow row you are moving to
	 * @param toCol column you are moving to
	 * @return number of steps
	 */
	public static int numberOfSteps(int fromRow, int fromCol, int toRow, int toCol)
	{
		if(sameRow(fromRow, toRow))
		{
			//number of steps is the column difference
			return Math.abs(toCol - fromCol);
		}
		
		else if(sameColumn(fromCol, toCol))
		{
			//number of steps is the row difference
			return Math.abs(toRow - fromRow);
		}
		
		else if(sameDiagonal(fromRow, fromCol, toRow, toCol))
		{
			//NOTE: for diagonal, the difference of rows is equal to the difference of columns, so you could return either one. here we chose to return difference of rows.
			return Math.abs(toRow - fromRow); 
		}
		
		else{
			return 999; //Bogus value. You're code should never reach here
		}
	}
	
	/**
	 * Checks if the piece is moving in the forward direction (for white, forward means go up the board. for black, forward means go down the board.)
	 * @param fromRow row you are moving from
	 * @param toRow row you are moving to
	 * @param pieceIsWhite true if the color of piece is white
	 * @return true if the piece moves in the forward direction
	 */
	public static boolean movesForward(int fromRow, int toRow, boolean pieceIsWhite)
	{
		if(pieceIsWhite)
		{
			return toRow > fromRow;
		}
		
		else //piece is black
		{
			return toRow < fromRow;
		}
	}
}
