package com.example.jessie.android59.model;

import android.util.Log;

import java.util.Arrays;

public class Undo {

	private static final String TAGU = "undoLastMoveTag";

	//copy of board X
	public static void copyBoard(ChessSquare[][] X) {

		for(int i = 0; i < 8; i++) {

			for(int j = 0; j < 8; j++) {

				ChessBoard.board[i][j] = new ChessSquare(X[i][j]);
			}
		}
	}

	public static void undoLastMove(Record record) {
		/*
		Log.i(TAGU, "Start of undoLastMove the size is "+record.boardStates.size());
		Log.i(TAGU, "Start of undoLastMove the boardStates are: ");
		//displaying the boardStates
		for(int n=0; n<record.boardStates.size(); n++)
		{
			ChessSquare[][] temp = record.boardStates.get(n);
			Log.i(TAGU, "This is boardState #"+n);
			//displaying the board
			for(int i=7; i>=0; i--)
			{
				Log.i(TAGU, temp[i][0]+" "+temp[i][1]+" "+temp[i][2]+" "+temp[i][3]+" "+temp[i][4]+" "+temp[i][5]+" "+temp[i][6]+" "+temp[i][7]+" "+(i+1));
			}
			Log.i(TAGU, " a  b  c  d  e  f  g  h");
		}
		Log.i(TAGU, "Start of undoLastMove the board is: ");

		//displaying the board
		for(int i=7; i>=0; i--)
		{
				Log.i(TAGU, ChessBoard.board[i][0]+" "+ChessBoard.board[i][1]+" "+ChessBoard.board[i][2]+" "+ChessBoard.board[i][3]+" "+ChessBoard.board[i][4]+" "+ChessBoard.board[i][5]+" "+ChessBoard.board[i][6]+" "+ChessBoard.board[i][7]+" "+(i+1));
		}
		Log.i(TAGU, " a  b  c  d  e  f  g  h");
		*/

		if(record.boardStates.size() == 1) {
			//Log.i(TAGU, "Entered the start board case");
			ChessBoard.startBoard();
		}
		else {
			//Log.i(TAGU, "Before the clone the address of board is "+ChessBoard.board);
			//Log.i(TAGU, "Entered the size-2 case");
			copyBoard(record.boardStates.get(record.boardStates.size()-2));
			//Log.i(TAGU, "After the clone the address of board is "+ChessBoard.board);
		}

		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if(!ChessBoard.board[i][j].isEmptySquare && ChessBoard.board[i][j].piece.enPassantCounter == 0) {
					ChessBoard.board[i][j].piece.enPassantCounter = 1;
					ChessBoard.board[i][j].piece.enPassantRight = true;
					ChessBoard.enPassantActive = 1;
				}
				if(!ChessBoard.board[i][j].isEmptySquare && ChessBoard.board[i][j].piece.undoCounter == 0) {
					ChessBoard.board[i][j].piece.setHasMoved(false);
				}
				if(!ChessBoard.board[i][j].isEmptySquare && ChessBoard.board[i][j].piece.undoCounter2 == 0) {
					ChessBoard.board[i][j].piece.setHasMoved(false);
				}
			}
		}

		record.boardStates.remove(record.boardStates.size()-1);

		/*
		Log.i(TAGU, "End of undoLastMove the size is "+record.boardStates.size());
		Log.i(TAGU, "End of undoLastMove the boardStates are: ");
		//displaying the boardStates
		for(int n=0; n<record.boardStates.size(); n++)
		{
			ChessSquare[][] temp = record.boardStates.get(n);
			Log.i(TAGU, "This is boardState #"+n);
			//displaying the board
			for(int i=7; i>=0; i--)
			{
				Log.i(TAGU, temp[i][0]+" "+temp[i][1]+" "+temp[i][2]+" "+temp[i][3]+" "+temp[i][4]+" "+temp[i][5]+" "+temp[i][6]+" "+temp[i][7]+" "+(i+1));
			}
			Log.i(TAGU, " a  b  c  d  e  f  g  h");
		}
		Log.i(TAGU, "End of undoLastMove the board is: ");

		//displaying the board
		for(int i=7; i>=0; i--)
		{
			Log.i(TAGU, ChessBoard.board[i][0]+" "+ChessBoard.board[i][1]+" "+ChessBoard.board[i][2]+" "+ChessBoard.board[i][3]+" "+ChessBoard.board[i][4]+" "+ChessBoard.board[i][5]+" "+ChessBoard.board[i][6]+" "+ChessBoard.board[i][7]+" "+(i+1));
		}
		Log.i(TAGU, " a  b  c  d  e  f  g  h");
		*/
	}
}
