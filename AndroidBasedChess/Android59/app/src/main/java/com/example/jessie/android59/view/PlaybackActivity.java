package com.example.jessie.android59.view;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.jessie.android59.R;
import com.example.jessie.android59.model.ChessBoard;
import com.example.jessie.android59.model.ChessSquare;
import com.example.jessie.android59.model.Record;
import com.example.jessie.android59.model.RecordList;

public class PlaybackActivity extends AppCompatActivity {

    private static final String TAG4 = "PlaybackActivityTag"; //REMOVE

    /**
     * gameOver is true if the game is over
     */
    public static boolean gameOver = false;

    /**
     * whitesMove is true if it is white's turn to play. it is false if it is black's turn to play.
     */
    public static boolean whitesMove = true;

    public static Object selectedListItem;

    private Handler handler = new Handler();

    public static Record record;
    public static int i;
    public static int numBoardStates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playback);

        initializeBoard();

        selectedListItem = RecordedActivity.getSelectedListItem();
        String selectedListItemStr = selectedListItem.toString();
        Log.i(TAG4, "selectedListItemStr = "+selectedListItemStr); //REMOVE
        TextView playbackMessage = (TextView)findViewById(R.id.playbackMessage);
        playbackMessage.setText("Playback for "+selectedListItemStr);

        TextView move = (TextView)findViewById(R.id.move);

        int position = RecordList.displayList.indexOf(selectedListItemStr);
        record = RecordList.recordList.get(position);
        Log.i(TAG4, "position = "+position); //REMOVE
        Log.i(TAG4, "record.title = "+record.title+" and record.date = "+record.getDate()); //REMOVE

        i = 0;
        numBoardStates = record.boardStates.size();

        Button nextButton = (Button)findViewById(R.id.nextButton);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(whitesMove)
                    setMoveTextView("White's move");
                else
                    setMoveTextView("Black's move");

                if(i>=numBoardStates)
                {
                    if(record.endsInDraw)
                    {
                        drawPopup();
                    }
                    else if(record.endsInWhiteResign)
                    {
                        resignPopup("White", "Black");
                    }
                    else if(record.endsInBlackResign)
                    {
                        resignPopup("Black", "White");
                    }
                    else
                    {
                        winPopup(record.winner);
                    }
                }
                else
                {
                    displayBoard(record.boardStates.get(i));
                    i++;
                    whitesMove = !whitesMove;
                }
            }
        });
    }

    protected void initializeBoard()
    {
        int imgID = 0;

        //set black pieces
        for(int rank=7; rank>=6; rank--)
        {
            for(int file=0; file<8; file++)
            {
                String s = "imageButton"+rank+file;
                int resID = getResources().getIdentifier(s, "id", getPackageName());
                ImageButton ib = (ImageButton)findViewById(resID);

                if(rank==6)
                    imgID = R.drawable.black_pawn;

                else if(file==0||file==7)
                    imgID = R.drawable.black_rook;
                else if(file==1||file==6)
                    imgID = R.drawable.black_knight;
                else if(file==2||file==5)
                    imgID = R.drawable.black_bishop;
                else if(file==3)
                    imgID = R.drawable.black_queen;
                else
                    imgID = R.drawable.black_king;

                ib.setImageResource(imgID);
                ib.setTag(imgID);
            }
        }

        //set white pieces
        for(int rank=1; rank>=0; rank--)
        {
            for(int file=0; file<8; file++)
            {
                String s = "imageButton"+rank+file;
                int resID = getResources().getIdentifier(s, "id", getPackageName());
                ImageButton ib = (ImageButton)findViewById(resID);

                if(rank==1)
                    imgID = R.drawable.white_pawn;
                else if(file==0||file==7)
                    imgID = R.drawable.white_rook;
                else if(file==1||file==6)
                    imgID = R.drawable.white_knight;
                else if(file==2||file==5)
                    imgID = R.drawable.white_bishop;
                else if(file==3)
                    imgID = R.drawable.white_queen;
                else
                    imgID = R.drawable.white_king;

                ib.setImageResource(imgID);
                ib.setTag(imgID);
            }
        }

    }

    /* not needed?
    public void checkmatePopup()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Checkmate");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void checkPopup()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Check");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    */

    public void winPopup(String winner)
    {
        if(!whitesMove)
            whitesMove = true; //reinitialize for next game

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(winner+" wins!");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void resignPopup(String player, String winner)
    {
        if(!whitesMove)
            whitesMove = true; //reinitialize for next game

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(player+" resigned. "+winner+" wins!");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void drawPopup()
    {
        if(!whitesMove)
            whitesMove = true; //reinitialize for next game

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("It's a draw.");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void setMoveTextView(String s)
    {
        TextView move = (TextView)findViewById(R.id.move);
        move.setText("Currently showing "+s);
    }


    public void displayBoard(ChessSquare[][] board)
    {
        for(int rank=7; rank>=0; rank--)
        {
            for(int file=0; file<=7; file++)
            {
                String s = "imageButton"+rank+file;
                int resID = getResources().getIdentifier(s, "id", getPackageName());
                ImageButton ib = (ImageButton)findViewById(resID);

                Log.i(TAG4, "rank = "+rank+" and file = "+file+" and s = "+s+" and board[rank][file] = "+board[rank][file]); //REMOVE
                if(!board[rank][file].isEmptySquare)
                {
                    Log.i(TAG4, "Entered the case for NOT empty square"); //REMOVE
                    String imgName = "";
                    if(board[rank][file].piece.pieceIsWhite())
                        imgName = "white_";
                    else
                        imgName = "black_";

                    char ch = board[rank][file].piece.toString().charAt(1);
                    Log.i(TAG4, "ch = "+ch);
                    switch (ch)
                    {
                        case 'p':
                            imgName += "pawn";
                            break;

                        case 'R':
                            imgName += "rook";
                            break;

                        case 'N':
                            imgName += "knight";
                            break;

                        case 'B':
                            imgName += "bishop";
                            break;

                        case 'Q':
                            imgName += "queen";
                            break;

                        case 'K':
                            imgName += "king";
                            break;
                    }

                    //imgName +=".png";
                    Log.i(TAG4, "imgName = "+imgName);
                    int imgID = getResources().getIdentifier(imgName, "drawable", getPackageName());
                    Log.i(TAG4, "imgID = "+imgID);
                    ib.setImageResource(imgID);
                }

                else
                {
                    Log.i(TAG4, "Entered the case for empty square"); //REMOVE
                    ib.setImageResource(android.R.color.transparent); //empty square
                }
            }
        }
    }
}
