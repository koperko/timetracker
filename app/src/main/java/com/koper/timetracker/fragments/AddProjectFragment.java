package com.koper.timetracker.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.koper.timetracker.R;
import com.koper.timetracker.adapters.ColorAdapter;
import com.koper.timetracker.model.Project;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddProjectFragment extends Fragment {

    @Bind(R.id.add_project_name)
    EditText fAddProjectName;
    @Bind(R.id.add_project_color)
    GridView fAddProjectColor;
    @Bind(R.id.add_project_save_btn)
    Button fAddProjectSaveBtn;
    private int fColorInt;
    private int fColorPosition;

    public AddProjectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        LinearLayout mRootView = (LinearLayout) inflater.inflate(R.layout.fragment_add_project, container, false);
        ButterKnife.bind(this, mRootView);

        fAddProjectColor.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                fColorPosition = position;
                fColorInt = ColorAdapter.getColor(position);
                ((ColorAdapter) fAddProjectColor.getAdapter()).setSelectedPosition(position);
                ((ColorAdapter) fAddProjectColor.getAdapter()).notifyDataSetChanged();
            }
        });
        fAddProjectColor.setAdapter(new ColorAdapter(getContext()));


        return mRootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.add_project_save_btn)
    public void saveProject() {
        new Project(fAddProjectName.getText().toString(), R.drawable.ic_android, fColorInt).save();
        getFragmentManager().popBackStack();
    }
}
