package com.myapp.kr_pc.crickettracker.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.myapp.kr_pc.crickettracker.R;
import com.myapp.kr_pc.crickettracker.model.Fixture;

import java.util.List;

/**
 * Created by KR-PC on 20-12-2017.
 */

public class FixtureAdapter extends ArrayAdapter<Fixture> {
    private List<Fixture> fixtureList;
    private Context context;

    public FixtureAdapter(List<Fixture> fixtureList, Context context) {
        super(context, R.layout.fixture_items, fixtureList);
        this.fixtureList = fixtureList;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //getting the layoutinflater
        LayoutInflater inflater = LayoutInflater.from(context);

        //creating a view with our xml layout
        View fixtureViewItem = inflater.inflate(R.layout.fixture_items, parent, false);

        //getting text views
        TextView textViewDate = fixtureViewItem.findViewById(R.id.match_date);
        TextView textViewMatch = fixtureViewItem.findViewById(R.id.match);

        //Getting the fixture for the specified position
        Fixture fixture = fixtureList.get(position);

        //setting fixture values to textviews
        textViewDate.setText(fixture.getDate());
        textViewMatch.setText(fixture.getName());

        //returning the listitem
        return fixtureViewItem;
    }
}