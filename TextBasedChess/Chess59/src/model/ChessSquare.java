package model;
/**
 * @author Jessie George
 * @author Karl Xu
 * 
 * class for ChessSquare i.e a square on the chess board
 */
public class ChessSquare {

	/**
	 * rank is row number
	 */
	int rank;
	
	/**
	 * file is column number
	 */
	int file; 
	
	/**
	 * piece is p R N B Q K in w or b
	 */
	ChessPiece piece;
	
	/**
	 * isEmptySquare square doesn't contain a piece
	 */
	boolean isEmptySquare;
	
	/**
	 * empty squares are light or dark tiles
	 */
	String tile;
	
	/**
	 * Constructor for chess square
	 * @param rank the rank of the chess square
	 * @param file the file of the chess square
	 */
	public ChessSquare(int rank, int file)
	{
		this.rank = rank;
		this.file = file;
	}
	
	/**
	 * Constructor to set the contents of one ChessSquare to equal the contents of another ChessSquare
	 * This special constructor is used for Checkmate functionality
	 * @param copyMyContents the chess square whose contents you want to copy
	 */
	public ChessSquare(ChessSquare copyMyContents)
	{
		this.rank = copyMyContents.rank;
		this.file = copyMyContents.file;
		this.piece = copyMyContents.piece;
		this.isEmptySquare = copyMyContents.isEmptySquare;
		this.tile = copyMyContents.tile;
	}
	
	/**
	 * Assign piece
	 * @param piece the piece assignment value
	 */
	public void setPiece(ChessPiece piece)
	{
		this.piece = piece;
	}
	
	/**
	 * method to make a square empty i.e. remove the piece and make the square a light or dark tile
	 */
	public void makeSquareEmpty() //no piece on square, just a light or dark tile
	{
		this.piece = null;
		this.isEmptySquare = true;
		
		if(rank%2==0)
		{
			if(file%2==0)
				this.tile = Constants.DARK_TILE;
			else
				this.tile = Constants.LIGHT_TILE;
		}
			
		else 
		{
			if(file%2==0)
				this.tile = Constants.LIGHT_TILE;
			else
				this.tile = Constants.DARK_TILE;
		}
	}
	
	/**
	 * toString makes the string value of the chess square (which could be tile or piece)
	 * @return the string value of the chess square
	 */
	public String toString()
	{
		if(this.isEmptySquare)
			return this.tile;
		
		else
			return this.piece.toString();
	}
}
