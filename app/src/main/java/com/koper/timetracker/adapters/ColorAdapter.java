package com.koper.timetracker.adapters;

import android.app.Application;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;

import com.koper.timetracker.R;
import com.koper.timetracker.TrackingApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by koper on 05.03.16.
 */
public class ColorAdapter extends ArrayAdapter {

    private static int[] fColorResIds = {R.color.blue_light, R.color.green_light, R.color.orange_light, R.color.red_light, R.color.yellow_light};

    private static ArrayList<Integer> fColorInts = getColors();

    private int fSelectedPosition = 0;

    public ColorAdapter(Context context) {
        super(context, R.layout.card_color_option, fColorInts);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.card_color_option, parent, false);
        }
        ((CardView) convertView).setCardBackgroundColor((Integer) getItem(position));

        if(position == fSelectedPosition){
            ((CardView) convertView).setCardElevation(30);
        } else {
            ((CardView) convertView).setCardElevation(5);
        }
        return convertView;
    }

    public void setSelectedPosition(int aSelectedPosition) {
        fSelectedPosition = aSelectedPosition;
    }

    private static ArrayList<Integer> getColors() {
        ArrayList<Integer> mColors = new ArrayList<>();
        for (int o : fColorResIds) {
            mColors.add(TrackingApplication.getAppContext().getResources().getColor(o));
        }
        return mColors;
    }

    public static int getColor(int aPosition){
        return fColorInts.get(aPosition);
    }
}
