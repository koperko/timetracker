package com.koper.timetracker.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.koper.timetracker.R;
import com.koper.timetracker.fragments.TrackerFragment;
import com.koper.timetracker.model.Project;

import java.util.ArrayList;
import java.util.List;

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.ViewHolder> {

    private List<Project> fAllProjects = new Select().all().from(Project.class).execute();
    private List<Project> fProjects = new ArrayList<>();
    private TrackerFragment fTrackerFragment;


    public ProjectsAdapter(TrackerFragment aTrackerFragment) {
        fTrackerFragment = aTrackerFragment;
            if(fAllProjects.size() > 0) {
                fProjects.addAll(fAllProjects.subList(0, fAllProjects.size()));
            }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup aParent, int aViewType) {
        CardView mCard = (CardView) LayoutInflater.from(aParent.getContext()).inflate(R.layout.card_project, aParent, false);
        return new ViewHolder(mCard);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.getCard().findViewById(R.id.card_edit_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: open new fragment for project modification
            }
        });

        holder.setProject(position);

//                if (!fRawIconBitmaps.containsKey(R.drawable.ic_android)) {
//                    fRawIconBitmaps.put(R.drawable.ic_android, ImageUtils.convertDrawableToBitmap(holder.getCard().getContext().getResources().getDrawable(R.drawable.ic_android)));
//                }
//                ImageView mIcon = (ImageView) holder.getCard().findViewById(R.id.card_icon);
//                Bitmap mFinalBitmap = ImageUtils.changeImageColor(fRawIconBitmaps.get(R.drawable.ic_android), holder.getCard().getContext().getResources().getColor(R.color.blue));
//                mIcon.setImageBitmap(mFinalBitmap);
    }

    @Override
    public int getItemCount() {
        return fProjects.size();
    }

    public void refreshDataSet(){
        fAllProjects = new Select().all().from(Project.class).execute();
        fProjects.clear();
        fProjects.addAll(fAllProjects);
    }

    public void filterDataSet(String aProjectName) {
        List<Project> mFoundProjects = new ArrayList<>();
        for (Project o : fAllProjects) {
            if(o.getName().contains(aProjectName)) {
                mFoundProjects.add(o);
            }
        }
        fProjects.clear();
        fProjects.addAll(mFoundProjects);
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CardView fCard;
        private int fPosition;


        public ViewHolder(CardView aItem) {
            super(aItem);
            fCard = aItem;
            fCard.setOnClickListener(this);
        }

        public CardView getCard() {
            return fCard;
        }

        public void setProject(int aProjectPosition){
            fPosition = aProjectPosition;
            ((TextView)fCard.findViewById(R.id.card_name)).setText(fProjects.get(aProjectPosition).getName());
            fCard.setCardBackgroundColor(fProjects.get(aProjectPosition).getColor());
        }

        public void onClick(View aView){
            fTrackerFragment.setProjectId(fProjects.get(fPosition).getId());
            fTrackerFragment.getFragmentManager().popBackStack();
        }

    }


}
