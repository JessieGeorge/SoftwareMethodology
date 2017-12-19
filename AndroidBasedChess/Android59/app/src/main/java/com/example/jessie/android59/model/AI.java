package com.example.jessie.android59.model;

public class AI {
	
	public static Move getMoveAI(String color) {

		for (int i = 0; i < 8; i++) {

			for (int j = 0; j < 8; j++) {

				if (!ChessBoard.board[i][j].isEmptySquare && ChessBoard.board[i][j].piece.getColor().equals(color)) {

					Move move = ChessBoard.board[i][j].piece.getLegalMove(i, j);

					if(move.fromRow == 10 && move.fromCol == 10 && move.toRow == 10 && move.toCol == 10) {
						continue;
					}

					return move;
				}
			}
		}

		//there are no legal moves
		return new Move(10, 10, 10, 10);
	}
}