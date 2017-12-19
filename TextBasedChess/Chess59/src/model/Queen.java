package model;

/**
 * @author Jessie George
 * @author Karl Xu
 * 
 * class for Queen
 */
public class Queen extends ChessPiece {

	/**
	 * toString makes the string value of color and piece
	 * @return the string value of color and piece
	 */
	public String toString()
	{
		return super.getColor()+Constants.QUEEN;
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
		//same row or column or diagonal, and no obstruction
		return (RelativePosition.sameRow(fromRow, toRow) || RelativePosition.sameColumn(fromCol, toCol) || RelativePosition.sameDiagonal(fromRow, fromCol, toRow, toCol)) && RelativePosition.noObstruction(fromRow, fromCol, toRow, toCol);
	}
}
