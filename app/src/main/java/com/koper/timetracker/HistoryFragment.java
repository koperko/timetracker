package com.koper.timetracker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.koper.timetracker.adapters.HistoryRecordsAdapter;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HistoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HistoryFragment extends Fragment {

    public static final String TAG = "HistoryFragment";

    @Bind(R.id.fragment_history_records)
    RecyclerView fFragmentHistoryRecords;

    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mRoot = inflater.inflate(R.layout.fragment_history, container, false);
        ButterKnife.bind(this, mRoot);

        HistoryRecordsAdapter mAdapter = new HistoryRecordsAdapter();
        fFragmentHistoryRecords.setAdapter(mAdapter);
        fFragmentHistoryRecords.setLayoutManager(new LinearLayoutManager(getContext()));
        fFragmentHistoryRecords.addItemDecoration(new StickyRecyclerHeadersDecoration(mAdapter));

        return mRoot;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
