package model;
import java.util.*;

/**
 * @author Jessie George
 * @author Karl Xu
 * 
 * class for check/checkmate methods and helper methods
 */
public class Check {

	/**
	 * Check if king is in check
	 * @param oppColor the opponents color
	 * @param kingRank the rank of the king
	 * @param kingFile the file of the king
	 * @return true if the king is in check
	 */
	public static boolean kingInCheck(String oppColor, int kingRank, int kingFile) {
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				
				if(!ChessBoard.board[i][j].isEmptySquare && ChessBoard.board[i][j].piece.getColor().equals(oppColor)) {
					if(ChessBoard.board[i][j].piece.isValidMove(i, j, kingRank, kingFile)) {
						return true;
					}
				}
			}
		}
		
		return false;
	}

	/**
	 * Check if white king is in check
	 * @return true if white king is in check
	 */
	public static boolean whiteInCheck() {
		
		ChessSquare whiteKingSquare = null;
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				
				if(!ChessBoard.board[i][j].isEmptySquare && ChessBoard.board[i][j].piece.toString().equals("wK")) {
					whiteKingSquare = ChessBoard.board[i][j];
				}
			}
		}
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				
				if(!ChessBoard.board[i][j].isEmptySquare && ChessBoard.board[i][j].piece.getColor().equals("b")) {
					if(ChessBoard.board[i][j].piece.isValidMove(i, j, whiteKingSquare.rank, whiteKingSquare.file)) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Check if black king is in check
	 * @return true if black king is in check
	 */
	public static boolean blackInCheck() {
		
		ChessSquare blackKingSquare = null;
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				
				if(!ChessBoard.board[i][j].isEmptySquare && ChessBoard.board[i][j].piece.toString().equals("bK")) {
					blackKingSquare = ChessBoard.board[i][j];
				}
			}
		}
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				
				if(!ChessBoard.board[i][j].isEmptySquare && ChessBoard.board[i][j].piece.getColor().equals("w")) {
					if(ChessBoard.board[i][j].piece.isValidMove(i, j, blackKingSquare.rank, blackKingSquare.file)) {
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Check if king can escape check by moving to another square
	 * @param rank the rank of the other square
	 * @param file the file of the other square
	 * @param myColor the current player's color
	 * @param oppColor the opponents color
	 * @return true if king can escape check by moving to another square
	 */
	public static boolean escapeCheck(int rank, int file, String myColor, String oppColor) {
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				
				if(i == rank && j == file) {
					continue;
				}
				
				if(!ChessBoard.board[i][j].isEmptySquare && ChessBoard.board[i][j].piece.getColor().equals(oppColor)) {
					
					ChessSquare temp = new ChessSquare(ChessBoard.board[rank][file]);
					ChessBoard.board[rank][file].piece = new King();
					ChessBoard.board[rank][file].piece.color = myColor;
					ChessBoard.board[rank][file].isEmptySquare = false;
					
					if(ChessBoard.board[i][j].piece.isValidMove(i, j, rank, file)) {
						
						ChessBoard.board[rank][file] = new ChessSquare(temp);
						return false;
					}
					
					ChessBoard.board[rank][file] = new ChessSquare(temp);
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Get the chess squares along the path of a piece threatening a king in check
	 * @param threat the chess square containing the threatening piece
	 * @param kingSquare the chess square containing the king being threatened
	 * @return array list of chess squares along the threatening path
	 */
	public static ArrayList<ChessSquare> getThreateningPath(ChessSquare threat, ChessSquare kingSquare)
	{
		ArrayList<ChessSquare> threateningPath = new ArrayList<ChessSquare>();
		
		int fromRow = threat.rank;
		int fromCol = threat.file;
		int toRow = kingSquare.rank;
		int toCol = kingSquare.file;
		
		if(RelativePosition.sameRow(fromRow, toRow))
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
				threateningPath.add(ChessBoard.board[fromRow][i]);
			}
			
		}
	
		else if(RelativePosition.sameColumn(fromCol, toCol))
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
				threateningPath.add(ChessBoard.board[i][fromCol]);
			}
			
		}
	
		else if(RelativePosition.sameDiagonal(fromRow, fromCol, toRow, toCol))
		{
			boolean goRight = fromCol < toCol;
			boolean goUp = fromRow < toRow;
			
			if(goRight) //right
			{
				if(goUp) //right upper diagonal
				{
					//System.out.println("RIGHT UPPER DIAGONAL"); //REMOVE
					for(int i = fromRow+1, j = fromCol+1; i<toRow && j<toCol; i++, j++)
					{
						//System.out.format("ChessBoard.board[%d][%d]=%s \n",i,j,ChessBoard.board[i][j]); //REMOVE
						threateningPath.add(ChessBoard.board[i][j]);
						
					}
				}
				
				else //right lower diagonal
				{
					//System.out.println("RIGHT LOWER DIAGONAL"); //REMOVE
					for(int i = fromRow-1, j = fromCol+1; i>toRow && j<toCol; i--, j++)
					{
						//System.out.format("ChessBoard.board[%d][%d]=%s \n",i,j,ChessBoard.board[i][j]); //REMOVE
						threateningPath.add(ChessBoard.board[i][j]);
					}
				}
				
			}
			
			else //left
			{
				if(goUp) //left upper diagonal
				{
					//System.out.println("LEFT UPPER DIAGONAL"); //REMOVE
					for(int i = fromRow+1, j = fromCol-1; i<toRow && j>toCol; i++, j--)
					{
						//System.out.format("ChessBoard.board[%d][%d]=%s \n",i,j,ChessBoard.board[i][j]); //REMOVE
						threateningPath.add(ChessBoard.board[i][j]);
					}
				}
				
				else //left lower diagonal
				{
					//System.out.println("LEFT LOWER DIAGONAL"); //REMOVE
					for(int i = fromRow-1, j = fromCol-1; i>toRow && j>toCol; i--, j--)
					{
						//System.out.format("ChessBoard.board[%d][%d]=%s \n",i,j,ChessBoard.board[i][j]); //REMOVE
						threateningPath.add(ChessBoard.board[i][j]);
					}
				}
				
			}
				
		}
		
		return threateningPath;
	}
	
	/**
	 * Checks if white king is in checkmate
	 * @return true if white king is in checkmate
	 */
	public static boolean whiteInCheckmate() {
		
		if(!whiteInCheck()) {
			return false;
		}
		
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
						if(escapeCheck(escapeRank, escapeFile, "w", "b")) {
							return false;
						}
					}
				}
			}
		}
		
		//get all threatening pieces
		ArrayList<ChessSquare> threats = new ArrayList<ChessSquare>();
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				
				if(!ChessBoard.board[i][j].isEmptySquare && ChessBoard.board[i][j].piece.getColor().equals("b")) {
					if(ChessBoard.board[i][j].piece.isValidMove(i, j, whiteKingSquare.rank, whiteKingSquare.file)) {
						threats.add(ChessBoard.board[i][j]);
					}
				}
			}
		}
		
		//if there is more than one threatening piece, you cannot capture/block all of them
		if(threats.size() > 1) {
			return true;
		}
		
		//if there is only one threatening piece, see if it can be captured/blocked
		ArrayList<ChessSquare> threateningPath = getThreateningPath(threats.get(0), whiteKingSquare);
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				
				if(!ChessBoard.board[i][j].isEmptySquare && ChessBoard.board[i][j].piece.getColor().equals("w")
				&& !ChessBoard.board[i][j].piece.toString().equals("wK")) {
					
					//can be captured
					if(ChessBoard.board[i][j].piece.isValidMove(i, j, threats.get(0).rank, threats.get(0).file)) {
						return false;
					}
					
					//can be blocked
					if(!threats.get(0).piece.toString().equals("bN") && threateningPath.size() > 0) {
						for(int k = 0; k < threateningPath.size(); k++) {
							if(ChessBoard.board[i][j].piece.isValidMove(i, j, threateningPath.get(k).rank, threateningPath.get(k).file)) {
								return false;
							}
						}
						
					}
					
				}
			}
		}
		
		return true;
	}
	
	/**
	 * Checks if black king is in checkmate
	 * @return true if black king is in checkmate
	 */
	public static boolean blackInCheckmate() {
		
		if(!blackInCheck()) {
			return false;
		}
		
		ChessSquare blackKingSquare = null;
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				
				if(!ChessBoard.board[i][j].isEmptySquare && ChessBoard.board[i][j].piece.toString().equals("bK")) {
					blackKingSquare = ChessBoard.board[i][j];
				}
			}
		}
		
		//see if black king can escape check by moving to another square
		for(int escapeRank = blackKingSquare.rank - 1; escapeRank < blackKingSquare.rank + 2; escapeRank++) {
			for(int escapeFile = blackKingSquare.file - 1; escapeFile < blackKingSquare.file + 2; escapeFile++) {
				
				if(escapeRank == blackKingSquare.rank && escapeFile == blackKingSquare.file) {
					continue;
				}
				
				if(escapeRank >= 0 && escapeRank <= 7 && escapeFile >= 0 && escapeFile <= 7) {
					if(ChessBoard.board[escapeRank][escapeFile].isEmptySquare || ChessBoard.board[escapeRank][escapeFile].piece.getColor().equals("w")) {
						if(escapeCheck(escapeRank, escapeFile, "b", "w")) {
							return false;
						}
					}
				}
			}
		}
		
		//get all threatening pieces
		ArrayList<ChessSquare> threats = new ArrayList<ChessSquare>();
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				
				if(!ChessBoard.board[i][j].isEmptySquare && ChessBoard.board[i][j].piece.getColor().equals("w")) {
					if(ChessBoard.board[i][j].piece.isValidMove(i, j, blackKingSquare.rank, blackKingSquare.file)) {
						threats.add(ChessBoard.board[i][j]);
					}
				}
			}
		}
		
		//if there is more than one threatening piece, you cannot capture/block all of them
		if(threats.size() > 1) {
			return true;
		}
		
		//if there is only one threatening piece, see if it can be captured/blocked
		ArrayList<ChessSquare> threateningPath = getThreateningPath(threats.get(0), blackKingSquare);
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				
				if(!ChessBoard.board[i][j].isEmptySquare && ChessBoard.board[i][j].piece.getColor().equals("b")
				&& !ChessBoard.board[i][j].piece.toString().equals("bK")) {
					
					//can be captured
					if(ChessBoard.board[i][j].piece.isValidMove(i, j, threats.get(0).rank, threats.get(0).file)) {
						return false;
					}
					
					//can be blocked
					if(!threats.get(0).piece.toString().equals("wN") && threateningPath.size() > 0) {
						for(int k = 0; k < threateningPath.size(); k++) {
							if(ChessBoard.board[i][j].piece.isValidMove(i, j, threateningPath.get(k).rank, threateningPath.get(k).file)) {
								return false;
							}
						}
						
					}
					
				}
			}
		}
		
		return true;
	}
}








