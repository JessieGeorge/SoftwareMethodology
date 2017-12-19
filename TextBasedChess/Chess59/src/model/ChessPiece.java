package model;


/**
 * @author Jessie George
 * @author Karl Xu
 *
 * class for the chess piece
 */
public abstract class ChessPiece {

	/**
	 * color is the color of the piece (w or b)
	 */
	public String color;
	
	/**
	 * hasMoved is true if the piece has moved.
	 */
	public boolean hasMoved = false;
	
	/**
	 * enPassantRight is true if a piece currently has the right to do the enPassant move
	 */
	public boolean enPassantRight = false;
	
	/**
	 * method to assign color
	 * @param color the color of the piece
	 */
	public void setColor(String color)
	{
		this.color = color;
	}
	
	/**
	 * method to get color
	 * @return color of the piece
	 */
	public String getColor()
	{
		return this.color;
	}
	
	/**
	 * method to get the opponent's color
	 * @return the opponent's color
	 */
	public String getOppColor() {
		
		if(this.color.equals("w")) {
			return "b";
		}
		return "w";
	}
	
	/**
	 * method to check if the piece is white
	 * this method is used to make boolean logic shorter syntactically
	 * NOTE: you have to make sure that it's not an empty square before you use this method, depending on what context you're using it in. If you're calling it as a super function then you know piece exists so you don't need to check if square is empty. If you're calling it not as a super function like ChessSquare.piece.pieceIsWhite() then you need to check if the square is an empty tile or if it actually has a piece.
	 * @return true if the piece is white
	 */
	public boolean pieceIsWhite() //return true if piece is white, return false if piece is black
	{
		return color.equals(Constants.WHITE);
	}
	
	/**
	 * assign value to hasMoved
	 * @param b the boolean to assign to hasMoved
	 */
	public void setHasMoved(boolean b)
	{
		this.hasMoved = b;
	}
	
	/**
	 * get hasMoved
	 * @return hasMoved
	 */
	public boolean getHasMoved()
	{
		return this.hasMoved;
	}
	
	/**
	 * abstract method to check if the move is valid for a piece
	 * @param fromRow row you are moving from
	 * @param fromCol column you are moving from
	 * @param toRow row you are moving to
	 * @param toCol column you are moving to
	 * @return true if it is a valid move
	 */
	public abstract boolean isValidMove(int fromRow, int fromCol, int toRow, int toCol);
	
}
