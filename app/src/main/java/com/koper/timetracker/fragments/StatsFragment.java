package com.koper.timetracker.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.activeandroid.query.Select;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.koper.timetracker.R;
import com.koper.timetracker.model.TimeRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatsFragment extends Fragment {

    public static final String TAG = StatsFragment.class.getSimpleName();

    @Bind(R.id.fragment_stats_pie)
    PieChart fFragmentStatsPie;
    private ArrayMap<String, Long> fProjects;
    private List<TimeRecord> fTimeRecords;
    private ArrayList<String> fProjectNames = new ArrayList<>();
    private ArrayList<Entry> mEntries = new ArrayList<>();


    public static StatsFragment newInstance() {
        StatsFragment fragment = new StatsFragment();
        Bundle args = new Bundle();
        // pass arguments for fragment creation
        fragment.setArguments(args);
        return fragment;
    }

    public StatsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        fTimeRecords = new Select().all().from(TimeRecord.class).execute();
        fProjects = new ArrayMap<>();
        for (TimeRecord o : fTimeRecords) {
            String mProject = o.getAssignedProject() != null ? o.getAssignedProject().getName() : "No project";
            Long mCurrentVal = fProjects.get(mProject) != null ? fProjects.get(mProject) : 0;
            fProjects.put(mProject, mCurrentVal + (o.getStopTime() - o.getStartTime()));
        }
        fProjectNames.addAll(fProjects.keySet());
        int mIndexCounter = 0;
        for (Map.Entry<String, Long> o : fProjects.entrySet()) {
            mEntries.add(new Entry(o.getValue(), mIndexCounter++));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mRoot = inflater.inflate(R.layout.fragment_stats, container, false);
        ButterKnife.bind(this, mRoot);
        PieDataSet mDataSet = new PieDataSet(mEntries, "");
        mDataSet.setSliceSpace(2);

        ArrayList<Integer> mColors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            mColors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            mColors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            mColors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            mColors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            mColors.add(c);

        mColors.add(ColorTemplate.getHoloBlue());
        mDataSet.setColors(mColors);
        mDataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return value + " %";
            }
        });

        fFragmentStatsPie.setData(new PieData(fProjectNames, mDataSet));
        fFragmentStatsPie.setDescription("");
        fFragmentStatsPie.setUsePercentValues(true);


        return mRoot;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
