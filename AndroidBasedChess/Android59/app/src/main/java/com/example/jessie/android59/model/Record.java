package com.example.jessie.android59.model;
import java.util.*;

public class Record {
	
	public List<ChessSquare[][]> boardStates;
	
	public boolean endsInWhiteResign;
	
	public boolean endsInBlackResign;
	
	public boolean endsInDraw;

	public String winner; //Jes comment - i need this for playback when the game ends in an ordinary win i.e no draw or resign
	
	public Calendar date;
	
	public String title;
	
	public Record() {
		
		boardStates = new ArrayList<ChessSquare[][]>();
		
		endsInWhiteResign = false;
		endsInBlackResign = false;
		endsInDraw = false;
		winner = "";
		
		date = Calendar.getInstance();
		date.set(Calendar.MILLISECOND, 0);

		title = "";
	}

	public String getDate() {

		String r1 = "" + (date.get(Calendar.MONTH) + 1);
		if(r1.length()==1) {
			r1 = "0" + r1;
		}

		String r2 = "" + date.get(Calendar.DATE);
		if(r2.length()==1) {
			r2 = "0" + r2;
		}

		String result = r1 + "/" + r2 + "/" + date.get(Calendar.YEAR)
				+ " " + date.get(Calendar.HOUR) + ":" + date.get(Calendar.MINUTE) + ":" + date.get(Calendar.SECOND);

		return result;
	}

	public void setEndsInWhiteResign(boolean b) {

		endsInWhiteResign = b;
	}
	
	public void setEndsInBlackResign(boolean b) {
		
		endsInBlackResign = b;
	}
	
	public void setEndsInDraw(boolean b) {

		endsInDraw = b;
	}

	public  void setWinner(String s){
		winner = s;
	}

	public void setTitle(String title) {

		this.title = title;
	}
}