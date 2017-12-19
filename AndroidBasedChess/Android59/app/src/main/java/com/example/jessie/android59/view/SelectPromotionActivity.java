package com.example.jessie.android59.view;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.jessie.android59.R;
import com.example.jessie.android59.model.RecordList;

import java.util.ArrayList;
import java.util.List;

public class SelectPromotionActivity extends AppCompatActivity {

    private static final String TAGSPA = "SPA"; //REMOVE
    public static boolean clickedOneItem = false;
    public static View oldView;
    public static Object selectedListItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_promotion);

        Log.i(TAGSPA, "Inside select promotion activity"); //REMOVE

        final List<String> promotionOptionsList = new ArrayList<String>();
        promotionOptionsList.add("Queen");
        promotionOptionsList.add("Rook");
        promotionOptionsList.add("Bishop");
        promotionOptionsList.add("Knight");
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, promotionOptionsList);

        final ListView promotionOptionsListView = (ListView)findViewById(R.id.promotionOptionsListView);
        promotionOptionsListView.setAdapter(adapter);
        promotionOptionsListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        promotionOptionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(clickedOneItem)
                {
                    oldView.setBackgroundColor(Color.WHITE);
                }

                selectedListItem = promotionOptionsListView.getItemAtPosition(position);

                // Set the item as checked to be highlighted
                promotionOptionsListView.setItemChecked(position, true);
                view.setBackgroundColor(Color.CYAN);
                oldView = view;
                adapter.notifyDataSetChanged();

                clickedOneItem = true;
            }
        });

        Button doneButton = (Button)findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAGSPA, "clicked done button and string is "+selectedListItem.toString()); //REMOVE
                //GameActivity.setPromotionLetter(selectedListItem.toString()); //UNCOMMENT THIS LINE IF YOU'RE USING THIS ACTIVITY AND INCLUDE THAT METHOD IN GAME ACTIVITY IF YOU'VE DELETED IT
                finish();
            }
        });
    }
}
