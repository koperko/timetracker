package com.koper.timetracker.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.koper.timetracker.R;
import com.koper.timetracker.adapters.ProjectsAdapter;

import java.util.ArrayList;

/**
 * Created by Matus on 08.02.2016.
 */
public class ProjectSelectionFragment extends Fragment {

    private static final String ARG_PROJECTS = "available_projects";
    RelativeLayout fRootView;

    private EditText fSearch;
    private RecyclerView fProjects;

    private FloatingActionButton fFab;
    private TrackerFragment fTrackerFragment;

    public static ProjectSelectionFragment newInstance() {

        Bundle args = new Bundle();
        ProjectSelectionFragment fragment = new ProjectSelectionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public ProjectSelectionFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fTrackerFragment = (TrackerFragment) getFragmentManager().findFragmentByTag(TrackerFragment.TAG);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fRootView = (RelativeLayout) inflater.inflate(R.layout.fragment_project_selection, container, false);
        fSearch = (EditText) fRootView.findViewById(R.id.projects_search);
        fFab = (FloatingActionButton)fRootView.findViewById(R.id.projects_add);
        fProjects = (RecyclerView) fRootView.findViewById(R.id.projects_list);
        fProjects.setLayoutManager(new LinearLayoutManager(getContext()));
        final ProjectsAdapter mAdapter = new ProjectsAdapter(fTrackerFragment);
        fProjects.setAdapter(mAdapter);
        fSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mAdapter.filterDataSet(s.toString());
            }
        });
        fFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left, 0, 0, android.R.anim.slide_out_right).add(R.id.container, new AddProjectFragment()).addToBackStack("addProject").commit();
            }
        });

        return fRootView;
    }

}
