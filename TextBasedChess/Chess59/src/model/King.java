package model;

/**
 * @author Jessie George
 * @author Karl Xu
 * 
 * class for King
 */
public class King extends ChessPiece {

	/**
	 * toString makes the string value of color and piece
	 * @return the string value of color and piece
	 */
	public String toString()
	{
		return super.getColor()+Constants.KING;
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
		
		//CASTLING CASE
		if(RelativePosition.numberOfSteps(fromRow, fromCol, toRow, toCol) == 2
		&& RelativePosition.sameRow(fromRow, toRow)
		&& !super.getHasMoved()){
			
			if(toCol > fromCol
			&& !ChessBoard.board[toRow][7].piece.getHasMoved()
			&& RelativePosition.noObstruction(fromRow, fromCol, toRow, 7)
			&& !Check.kingInCheck(super.getOppColor(), fromRow, fromCol)
			&& !Check.kingInCheck(super.getOppColor(), fromRow, fromCol + 1)
			&& !Check.kingInCheck(super.getOppColor(), fromRow, fromCol + 2)) {
				
				ChessBoard.board[toRow][toCol-1].piece = ChessBoard.board[toRow][7].piece;
				ChessBoard.board[toRow][toCol-1].isEmptySquare = ChessBoard.board[toRow][7].isEmptySquare;
				
				ChessBoard.board[toRow][7].makeSquareEmpty(); //make rook's old spot empty
				
				return true;
			}
			else if(toCol < fromCol
			&& !ChessBoard.board[toRow][0].piece.getHasMoved()
			&& RelativePosition.noObstruction(fromRow, fromCol, toRow, 0)
			&& !Check.kingInCheck(super.getOppColor(), fromRow, fromCol)
			&& !Check.kingInCheck(super.getOppColor(), fromRow, fromCol - 1)
			&& !Check.kingInCheck(super.getOppColor(), fromRow, fromCol - 2)) {
				
				ChessBoard.board[toRow][toCol+1].piece = ChessBoard.board[toRow][0].piece;
				ChessBoard.board[toRow][toCol+1].isEmptySquare = ChessBoard.board[toRow][0].isEmptySquare;
				
				ChessBoard.board[toRow][0].makeSquareEmpty(); //make rook's old spot empty
				
				return true;
			}
			
			
		}
		
		//REGULAR CASE
		//same row or column or diagonal and one step. NOTE: noObstruction can't be called here because it's just one step. 
		return (RelativePosition.sameRow(fromRow, toRow) || RelativePosition.sameColumn(fromCol, toCol) || RelativePosition.sameDiagonal(fromRow, fromCol, toRow, toCol)) && (RelativePosition.numberOfSteps(fromRow, fromCol, toRow, toCol) == 1);
	}
}
