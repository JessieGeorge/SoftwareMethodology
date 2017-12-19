package model;

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
}
