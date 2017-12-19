package com.example.jessie.android59.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jessie.android59.R;
import com.example.jessie.android59.model.*;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    private static final String TAG = "GameActivityTag"; //REMOVE

    /**
     * promotionLetter is the letter which specifies what type pawn is being promoted into
     */
    public static String promotionLetter = "";

    /**
     * gameOver is true if the game is over
     */
    public static boolean gameOver = false;

    /**
     * whitesMove is true if it is white's turn to play. it is false if it is black's turn to play.
     */
    public static boolean whitesMove = true;

    /**
     * madeLegalMove is true if player made a legal chess move
     */
    public static boolean madeLegalMove = false;

    public static int fromRow = -1;
    public static int fromCol = -1;
    public static int toRow = -1;
    public static int toCol = -1;

    public static ImageButton fromButton; //when user clicks first chess square
    //public static ImageButton toButton;
    public static ColorDrawable fromButtonColor;
    //public static ColorDrawable toButtonColor;
    public static int fromButtonColorID;
    //public static int toButtonColorID;

    public static boolean justClickedUndo = false;

    public static ImageButton fromib; //move from this chess square
    public static ImageButton toib; //move to this chess square
    public static Integer oldImageID; //the image that was eaten by move, will be used for undo

    public Record myRecord;

    public static GameActivity gmactSpecial; //this game activity object will be used for special moves in android gui

    public static ImageButton eatenPawnButton; //image button for the pawn that is eaten during enpassant move
    public static Integer oldImageIDForEnpassant; //image ID for the pawn that was eaten during enpassant move. This will be used if user clicks undo after enpassant.
    public static boolean justDidEnpassant = false; //just did the enpassant move, this will be used if user clicks undo after enpassant.
    public static int numMovesAfterEnpassant = -1; //number of moves after enpassant and before undo

    public static ImageButton promotedButton; //image button for the pawn that is promoted
    public static Integer oldImageIDForPromotion; //image ID for the pawn that was promoted. This will be used if user clicks undo after promotion
    public static boolean justBeganPromotion = false; //beginning promotion is an asynchronous task so we want the others to wait
    public static boolean justDidPromotion = false; //just did the promotion move, this will be used if user clicks undo after promotion
    public static int numMovesAfterPromotion = -1; //number of moves after promotion and before undo

    public static ImageButton oldRookButton; //button for rook before castling
    public static Integer oldRookImageID; //image ID for rook before castling
    public static ImageButton newRookButton; //button for rook after castling
    public static boolean justDidCastling = false; //just did the castling move, this will be used if user clicks undo after castling
    public static int numMovesAfterCastling = -1; //number of moves after castling and before undo

    //create a copy of the chessboard
    public static ChessSquare[][] copyBoard() {

        ChessSquare[][] myCopy = new ChessSquare[8][8];

        for(int i = 0; i < 8; i++) {

            for(int j = 0; j < 8; j++) {

                myCopy[i][j] = new ChessSquare(ChessBoard.board[i][j]);
            }
        }

        /*
        //displaying myCopy
        Log.i(TAG, "myCopy is: ");
        for(int i=7; i>=0; i--)
        {
            Log.i(TAG, myCopy[i][0]+" "+myCopy[i][1]+" "+myCopy[i][2]+" "+myCopy[i][3]+" "+myCopy[i][4]+" "+myCopy[i][5]+" "+myCopy[i][6]+" "+myCopy[i][7]+" "+(i+1));
        }
        Log.i(TAG, " a  b  c  d  e  f  g  h");
        */

        return myCopy;
    }

    public static void setClickedLocations(int rank, int file)
    {
        if(fromRow == -1 && fromCol == -1)
        {
            fromRow = rank;
            fromCol = file;
            return;
        }

        if(toRow == -1 && toCol == -1)
        {
            toRow = rank;
            toCol = file;
            return;
        }
    }

    /**
     *
     * @param row row for the pawn being eaten by enpassant move
     * @param col column for the pawn being eaten by enpassant move
     */
    public void androidGuiForEnpassant(int row, int col)
    {
        String s = "imageButton"+row+col;
        int resID = getResources().getIdentifier(s, "id", getPackageName());

        //the image button for the pawn that is eaten by enpassant move
        eatenPawnButton = (ImageButton)findViewById(resID);
        oldImageIDForEnpassant = (Integer)eatenPawnButton.getTag(); //we will use this if the user clicks undo

        //eat the pawn
        eatenPawnButton.setImageResource(android.R.color.transparent);
        eatenPawnButton.setTag(android.R.color.transparent);

        justDidEnpassant = true; //we will use this if the user clicks undo
        numMovesAfterEnpassant = 0;
    }

    /**
     *
     * @param row the row for the pawn you are promoting
     * @param col the col for the pawn you are promoting
     * @param promotionImgName the name of the image you want to put there (i.e. what do you want to promote it to)
     */
    public void androidGuiForPromotion(int row, int col, String promotionImgName)
    {
        String s = "imageButton"+row+col;
        int resID = getResources().getIdentifier(s, "id", getPackageName());

        //the image button for the pawn that is promoted
        promotedButton = (ImageButton)findViewById(resID);
        oldImageIDForPromotion = (Integer)promotedButton.getTag(); //we will use this if the user clicks undo

        //make the image the thing you want to promote it to
        int promotedImgID = getResources().getIdentifier(promotionImgName, "drawable", getPackageName());
        promotedButton.setImageResource(promotedImgID);
        promotedButton.setTag(promotedImgID);

        //justDidPromotion = true; //we will use this if the user clicks undo
    }

    public static void setGameActivitySpecialObject(GameActivity g)
    {
        gmactSpecial = g;
    }

    public static GameActivity getGameActivitySpecialObject()
    {
        return  gmactSpecial;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        initializeBoard();
        ChessBoard.startBoard();
		myRecord = new Record();
        setGameActivitySpecialObject(this);

        //UNDO
        Button undoButton = (Button)findViewById(R.id.undoButton);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Clicked undo"); //REMOVE
                performUndo();
            }
        });

        //AI
        Button aiButton = (Button)findViewById(R.id.aiButton);
        aiButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "Clicked AI"); //REMOVE
                //CHANGE THIS
                Move m;
                if(whitesMove)
                    m =  AI.getMoveAI("w");
                else
                    m = AI.getMoveAI("b");

                String s1 = "imageButton"+m.fromRow+m.fromCol;
                int resID1 = getResources().getIdentifier(s1, "id", getPackageName());
                fromib = (ImageButton)findViewById(resID1);
                fromib.performClick();

                String s2 = "imageButton"+m.toRow+m.toCol;
                int resID2 = getResources().getIdentifier(s2, "id", getPackageName());
                toib = (ImageButton)findViewById(resID2);
                toib.performClick();

            }
        });

        //DRAW
        Button drawButton = (Button)findViewById(R.id.drawButton);
        drawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawConfirmationPopup();
            }
        });

        //RESIGN
        Button resignButton = (Button)findViewById(R.id.resignButton);
        resignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resignConfirmationPopup();
            }
        });
    }

    /**
     *
     * @param oldRow the row where the rook was before castling
     * @param oldCol the row where the rook was before castling
     * //@param rookColor the color of the rook, "w" or "b"
     * @param newRow the new row where the rook is after castling (note: this should always be the same as oldRow 'cause castling happens in the same row)
     * @param newCol the new col where the rook is after castling
     */
    public void setRookInfoForCastling(int oldRow, int oldCol, int newRow, int newCol)
    {
        String s = "imageButton"+oldRow+oldCol;
        int oldRookID = getResources().getIdentifier(s, "id", getPackageName());
        oldRookButton = (ImageButton)findViewById(oldRookID);
        oldRookImageID = (Integer)oldRookButton.getTag();

        /*
        String imageName;
        if(rookColor.equals("w"))
            imageName = "white_rook";
        else
            imageName = "black_rook";

        oldRookImageID =
        */

        String s1 = "imageButton"+newRow+newCol;
        int newRookID = getResources().getIdentifier(s1, "id", getPackageName());
        newRookButton = (ImageButton)findViewById(newRookID);
    }

    protected void performUndo()
    {
        if(justClickedUndo)
        {
            undoTwiceErrorPopup();
            return;
        }

        if(!madeLegalMove)
        {
            undoErrorPopup();
            return;
        }

        if(justDidCastling && numMovesAfterCastling==0)
        {
            //move the rook back to where it was before castling
            oldRookButton.setImageResource(oldRookImageID);
            oldRookButton.setTag(oldRookImageID);

            //restore blankness
            newRookButton.setImageResource(android.R.color.transparent);
            newRookButton.setTag(android.R.color.transparent);
        }

        if(justDidPromotion && numMovesAfterPromotion==0)
        {
            //make the promoted thing a pawn again
            promotedButton.setImageResource(oldImageIDForPromotion);
            promotedButton.setTag(oldImageIDForPromotion);
        }

        //move the piece back
        Integer toImgID = (Integer)toib.getTag();
        fromib.setImageResource(toImgID);
        fromib.setTag(toImgID);

        //restore whatever was on toib
        if(oldImageID != null) //toib had a piece which you ate on the move, restore that piece during undo. (or transparency, if you did undo then manually redo and then undo again)
        {
            Log.i(TAG,"Entered the case where you ate a piece on toib with oldImageID = "+oldImageID); //REMOVE
            toib.setImageResource(oldImageID);
            toib.setTag(oldImageID);
        }
        else //toib was blank before the move, restore blankness during undo
        {
            Log.i(TAG, "Entered the case where toib was blank before the move"); //REMOVE
            toib.setImageResource(android.R.color.transparent);
            toib.setTag(android.R.color.transparent);
        }

        //if you click undo after enpassant, then gui should restore the eaten pawn
        if(justDidEnpassant && numMovesAfterPromotion==0)
        {
            eatenPawnButton.setImageResource(oldImageIDForEnpassant);
            eatenPawnButton.setTag(oldImageIDForEnpassant);
        }

        justClickedUndo = true;
        //you only got one shot to undo the special move! so if you didn't undo it right after, then you lost your chance.
        justDidCastling = false; //reinitialize
        justDidPromotion = false; //reinitialize
        justDidEnpassant = false; //reinitialize
        numMovesAfterCastling = -1; //reinitialize
        numMovesAfterPromotion = -1; //reinitialize
        numMovesAfterEnpassant = -1; //reinitialize
        Undo.undoLastMove(myRecord); //make backend reinitializeconsistent with gui

        /*
        Log.i(TAG, "After undoLastMove the size is "+myRecord.boardStates.size());
        Log.i(TAG, "After undoLastMove the boardStates are:");
        //displaying the boardStates
        for(int n=0; n<myRecord.boardStates.size(); n++)
        {
            ChessSquare[][] temp = myRecord.boardStates.get(n);
            Log.i(TAG, "This is boardState #"+n);
            //displaying the board
            for(int i=7; i>=0; i--)
            {
                Log.i(TAG, temp[i][0]+" "+temp[i][1]+" "+temp[i][2]+" "+temp[i][3]+" "+temp[i][4]+" "+temp[i][5]+" "+temp[i][6]+" "+temp[i][7]+" "+(i+1));
            }
            Log.i(TAG, " a  b  c  d  e  f  g  h");
        }
        */

        //undo consumes this players turn
        whitesMove = !whitesMove;
        if(whitesMove)
            setMoveTextView("WHITE'S MOVE");
        else
            setMoveTextView("BLACK'S MOVE");
    }

    protected void undoTwiceErrorPopup()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You cannot undo twice");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    protected void undoErrorPopup()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("You cannot undo because you haven't made a legal move yet.");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    protected  void drawConfirmationPopup()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to draw?");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                drawPopup();
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked CANCEL button
                //do nothing
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    protected  void drawPopup()
    {
        //REINITIALIZE EVERYTHING FOR NEXT GAME
        promotionLetter = "";
        gameOver = false;
        whitesMove = true;
        madeLegalMove = false;
        fromRow = -1;
        fromCol = -1;
        toRow = -1;
        toCol = -1;
        justClickedUndo = false;
        justDidEnpassant = false;
        justBeganPromotion = false;
        justDidPromotion = false;
        justDidCastling = false;
        numMovesAfterEnpassant = -1;
        numMovesAfterPromotion = -1;
        numMovesAfterCastling = -1;


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("It's a draw. \nDo you want to store this game?");
        myRecord.setEndsInDraw(true);

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                Log.i(TAG, "user wants to store the game. make a popup to get title."); //REMOVE
                gameTitlePopup();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked NO button
                Log.i(TAG, "user doesn't want to store the game. go to home page."); //REMOVE
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    protected  void resignConfirmationPopup()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to resign?");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                if(whitesMove) //white resigned
                {
                    myRecord.setEndsInWhiteResign(true);
                    winPopup("Black");
                }
                else
                {
                    myRecord.setEndsInBlackResign(true);
                    winPopup("White");
                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked CANCEL button
                //do nothing
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    protected void gameTitlePopup()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enter game title");

        final EditText input = new EditText(this);
        builder.setView(input);

        builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                Log.i(TAG, "user entered game title. put it in the list"); //REMOVE
                String gameTitle = input.getText().toString();
                if (gameTitle.isEmpty())
                {
                    emptyGameTitleErrorPopup();
                }

                else
                {
                    Log.i(TAG, "gameTitle = "+gameTitle); //REMOVE

                    myRecord.setTitle(gameTitle);
                    RecordList.addToRecordList(myRecord);

                    finish();
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    protected void emptyGameTitleErrorPopup()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ERROR");
        builder.setMessage("You cannot have an empty game title. Please enter a game title.");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                gameTitlePopup();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
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

    void promptForResult(final PromptRunnable postrun) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setMessage("What do you want to promote this pawn into? \nEnter 'q' for queen. \nOr enter 'r' for rook. \nOr enter 'n' for knight. \nOr enter 'b' for bishop. \nNote:If you enter an invalid letter we will set the default to queen.");
        // Create textbox to put into the dialog
        final EditText input = new EditText(this);
        // put the textbox into the dialog
        alert.setView(input);
        // procedure for when the ok button is clicked.
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();
                dialog.dismiss();
                // set value from the dialog inside our runnable implementation
                postrun.setValue(value);
                // ** HERE IS WHERE THE MAGIC HAPPENS! **
                // now that we have stored the value, lets run our Runnable
                postrun.run();
                return;
            }
        });

        alert.show();
    }

    public void requestPromotionChoice()
    {

        promptForResult(new PromptRunnable(){
            // put whatever code you want to run after user enters a result
            public void run() {
                // get the value we stored from the dialog
                String value = this.getValue();

                promotionLetter = value;
                Log.i(TAG, "Inside promptForResult with promotionLetter = "+promotionLetter);
                Promotion.promote(toRow, toCol, ChessBoard.board[toRow][toCol].piece.toString().substring(0,1), promotionLetter);

                //DO EVERYTHING YOU WOULD'VE DONE IN THE MAKEMOVE METHOD INSIDE CHESSBOARD
                if(ChessBoard.enPassantActive == 1) {

                    for(int i = 0; i < 8; i++) {
                        for(int j = 0; j < 8; j++) {

                            if(!ChessBoard.board[i][j].isEmptySquare && ChessBoard.board[i][j].piece.enPassantRight == true) {
                                ChessBoard.board[i][j].piece.enPassantRight = false;
                            }
                        }
                    }
                }

                ChessBoard.enPassantActive--;

                for(int i = 0; i < 8; i++) {
                    for(int j = 0; j < 8; j++) {

                        if(!ChessBoard.board[i][j].isEmptySquare) {
                            ChessBoard.board[i][j].piece.enPassantCounter--;
                        }
                    }
                }

                if(Check.whiteInCheck() || Check.blackInCheck()) {

                    if(Check.whiteInCheckmate()) {
                        checkmatePopup();
                        winPopup("Black");
                        GameActivity.gameOver = true;
                    }

                    else if(Check.blackInCheckmate()) {
                        checkmatePopup();
                        winPopup("White");
                        GameActivity.gameOver = true;
                    }
                    else {
                        checkPopup();
                    }
                }

                //DO EVERYTHING YOU WOULD'VE DONE INSIDE THE CLICKEDCHESSSQUARE METHOD OF GAMEACTIVITY
                if(madeLegalMove)
                {
                    //record move
                    ChessSquare[][] boardCopy = copyBoard();
                    myRecord.boardStates.add(boardCopy);

                    whitesMove = !whitesMove;

                    justClickedUndo = false;
                }

                //reinitialize for next clicks
                fromRow = -1;
                fromCol = -1;
                toRow = -1;
                toCol = -1;

                fromButton.setBackgroundColor(fromButtonColorID); //change the color back now that it's been deselected

                if(whitesMove)
                    setMoveTextView("WHITE'S MOVE");
                else
                    setMoveTextView("BLACK'S MOVE");


                justBeganPromotion = false;
                justDidPromotion = true; //we'll use this if the user clicks undo after promotion
                numMovesAfterPromotion = 0;
            }
        });
    }

    public void clickedChessSquare(View view)
    {
        madeLegalMove = false; //initialize so that undo can't happen if the move isn't complete

        int resID = view.getId();

        //The selected chess square becomes cyan
        if(fromRow==-1 && fromCol==-1)
        {
            fromButton = (ImageButton)view;
            fromButtonColor = (ColorDrawable)fromButton.getBackground();
            fromButtonColorID = fromButtonColor.getColor();
            fromButton.setBackgroundColor(Color.CYAN);
        }

        String buttonName = getResources().getResourceEntryName(resID);
        String nums = buttonName.substring(buttonName.length()-2);
        int rank = Character.getNumericValue(nums.charAt(0));
        int file = Character.getNumericValue(nums.charAt(1));
        setClickedLocations(rank, file);

        Log.i(TAG, "rank = "+rank+" file = "+file+" fromRow = "+fromRow+" fromCol = "+fromCol+" toRow = "+toRow+" toCol = "+toCol); //REMOVE

        if(fromRow!=-1 && fromCol!=-1 && toRow!=-1 && toCol!=-1) //you clicked twice
        {
            if(justDidCastling)
                numMovesAfterCastling++;
            if(justDidPromotion)
                numMovesAfterPromotion++;
            if(justDidEnpassant)
                numMovesAfterEnpassant++;

            madeLegalMove = ChessBoard.makeMove(this, fromRow, fromCol, toRow, toCol);

            if(!justBeganPromotion)
            {
                if(madeLegalMove)
                {
                    //record move
                    ChessSquare[][] boardCopy = copyBoard();
                    myRecord.boardStates.add(boardCopy);

                    whitesMove = !whitesMove;

                    justClickedUndo = false;
                }

                //reinitialize for next clicks
                fromRow = -1;
                fromCol = -1;
                toRow = -1;
                toCol = -1;

                fromButton.setBackgroundColor(fromButtonColorID); //change the color back now that it's been deselected

                if(whitesMove)
                    setMoveTextView("WHITE'S MOVE");
                else
                    setMoveTextView("BLACK'S MOVE");
            }

        }
    }

    public void makeAndroidMove(int fr, int fc, int tr, int tc)
    {
        Log.i(TAG, "fr = "+fr+" fc = "+fc+" tr = "+tr+" tc = "+tc); //REMOVE

        String s1 = "imageButton"+fr+fc;
        int resID1 = getResources().getIdentifier(s1, "id", getPackageName());
        fromib = (ImageButton)findViewById(resID1);

        String s2 = "imageButton"+tr+tc;
        int resID2 = getResources().getIdentifier(s2, "id", getPackageName());
        toib = (ImageButton)findViewById(resID2);
        oldImageID = (Integer)toib.getTag(); //we will use this if the user clicks undo

        Integer fromImgID = (Integer)fromib.getTag();
        toib.setImageResource(fromImgID);
        toib.setTag(fromImgID);

        fromib.setImageResource(android.R.color.transparent);
        fromib.setTag(android.R.color.transparent);
    }

    public void illegalMoveError()
    {
        Log.i(TAG, "Entered illegal move error"); //REMOVE

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Illegal move, try again");
        builder.setTitle("Error");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

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

    public void winPopup(String winner)
    {
        //REINITIALIZE EVERYTHING FOR NEXT GAME
        promotionLetter = "";
        gameOver = false;
        whitesMove = true;
        madeLegalMove = false;
        fromRow = -1;
        fromCol = -1;
        toRow = -1;
        toCol = -1;
        justClickedUndo = false;
        justDidEnpassant = false;
        justBeganPromotion = false;
        justDidPromotion = false;
        justDidCastling = false;
        numMovesAfterEnpassant = -1;
        numMovesAfterPromotion = -1;
        numMovesAfterCastling = -1;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(winner+" wins! \nDo you want to store this game?");
        myRecord.setWinner(winner);

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
                Log.i(TAG, "user wants to store the game. make a popup to get title."); //REMOVE
                gameTitlePopup();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked CANCEL button
                Log.i(TAG, "user doesn't want to store the game. go to home page."); //REMOVE
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void setMoveTextView(String s)
    {
        TextView move = (TextView)findViewById(R.id.move);
        move.setText(s);
    }
}
