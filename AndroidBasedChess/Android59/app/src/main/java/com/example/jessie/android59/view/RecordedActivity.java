package com.example.jessie.android59.view;
import com.example.jessie.android59.model.RecordList;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.jessie.android59.R;

public class RecordedActivity extends AppCompatActivity {

    private static final String TAG3 = "RecordedActivityTag"; //REMOVE

    public static boolean clickedOneItem = false;
    public static View oldView;
    public static Object selectedListItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recorded);

        //clickedOneItem = false;

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, RecordList.displayList);

        //LISTVIEW
        final ListView recordedGamesListView = (ListView)findViewById(R.id.recordedGamesListView);
        recordedGamesListView.setAdapter(adapter);
        recordedGamesListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        recordedGamesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(clickedOneItem)
                {
                    oldView.setBackgroundColor(Color.WHITE);
                }

                selectedListItem = recordedGamesListView.getItemAtPosition(position);

                // Set the item as checked to be highlighted
                recordedGamesListView.setItemChecked(position, true);
                view.setBackgroundColor(Color.CYAN);
                oldView = view;
                adapter.notifyDataSetChanged();

                clickedOneItem = true;
            }
        });

        //BACK
        Button backButton = (Button)findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //SORT BY DATE
        Button sortByDateButton = (Button)findViewById(R.id.sortByDateButton);
        sortByDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG3, "sort by date"); //REMOVE

                if(oldView!=null)
                    oldView.setBackgroundColor(Color.WHITE);

                clickedOneItem = false;
                recordedGamesListView.clearChoices();
                recordedGamesListView.requestLayout();

                if(RecordList.displayList.size()==0)
                    nothingToSortErrorPopup();
                else
                {
                    //SORT BY DATE CODE
                    RecordList.sortByDate();
                    adapter.notifyDataSetChanged();
                }
            }
        });

        //SORT BY TITLE
        Button sortByTitleButton = (Button)findViewById(R.id.sortByTitleButton);
        sortByTitleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG3, "sort by title"); //REMOVE

                if(oldView!=null)
                    oldView.setBackgroundColor(Color.WHITE);

                clickedOneItem = false;
                recordedGamesListView.clearChoices();
                recordedGamesListView.requestLayout();

                if(RecordList.displayList.size()==0)
                    nothingToSortErrorPopup();
                else {
                    //SORT BY TITLE CODE
                    RecordList.sortByTitle();
                    adapter.notifyDataSetChanged();
                }
            }
        });

        //PLAYBACK SELECTED GAME
        Button playbackGameButton = (Button)findViewById(R.id.playbackGameButton);
        playbackGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG3, "playback game"); //REMOVE

                goToPlayback();

                if(oldView!=null)
                    oldView.setBackgroundColor(Color.WHITE);

                clickedOneItem = false;
                recordedGamesListView.clearChoices();
                recordedGamesListView.requestLayout();
            }
        });
    }

    public void goToPlayback()
    {
        if(clickedOneItem == false)
        {
            nothingSelectedErrorPopup();
        }
        else
        {
            String selectedListItemStr = selectedListItem.toString();
            Log.i(TAG3, "selectedListItemStr = "+selectedListItemStr); //REMOVE

            Intent intent = new Intent(this, PlaybackActivity.class);
            startActivity(intent);

            //selectedListItem = null; //reinitialize not needed?
        }
    }

    public static Object getSelectedListItem()
    {
        return selectedListItem;
    }

    public void nothingSelectedErrorPopup()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ERROR");
        builder.setMessage("Nothing selected. You cannot playback.");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void nothingToSortErrorPopup()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ERROR");
        builder.setMessage("Nothing to sort.");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
