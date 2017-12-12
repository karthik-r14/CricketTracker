package com.example.kr_pc.crickettracker.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.kr_pc.crickettracker.R;
import com.example.kr_pc.crickettracker.model.Match;

import java.util.List;

/**
 * Created by KR-PC on 04-12-2017.
 */

public class ListViewAdapter extends ArrayAdapter<Match> {

    //the hero list that will be displayed
    private List<Match> matchList;

    //the context object
    private Context mCtx;

    //here we are getting the herolist and context
    //so while creating the object of this adapter class we need to give herolist and context
    public ListViewAdapter(List<Match> matchList, Context mCtx) {
        super(mCtx, R.layout.list_items, matchList);
        this.matchList = matchList;
        this.mCtx = mCtx;
    }

    //this method will return the list item
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //getting the layoutinflater
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        //creating a view with our xml layout
        View listViewItem = inflater.inflate(R.layout.list_items, parent, false);

        //getting text views
        TextView textViewTeam1 = listViewItem.findViewById(R.id.team1);
        TextView textViewTeam2 = listViewItem.findViewById(R.id.team2);

        //Getting the match for the specified position
        Match match = matchList.get(position);

        //setting match values to textviews
        textViewTeam1.setText(match.getTeam1());
        textViewTeam2.setText(match.getTeam2());

        //returning the listitem
        return listViewItem;
    }
}