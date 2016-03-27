package com.koper.timetracker.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.koper.timetracker.R;
import com.koper.timetracker.model.Project;
import com.koper.timetracker.model.TimeRecord;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;

import org.w3c.dom.Text;

import java.util.Date;
import java.util.List;

/**
 * Created by koper on 25.03.16.
 */
public class HistoryRecordsAdapter extends RecyclerView.Adapter<HistoryRecordsAdapter.ViewHolder> implements StickyRecyclerHeadersAdapter<HistoryRecordsAdapter.HeaderViewHolder> {

    private List<TimeRecord> fRecords;

    public HistoryRecordsAdapter() {
        fRecords = new Select().all().from(TimeRecord.class).orderBy("start_time DESC").execute();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout mHeader = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_records, parent, false);
        return new ViewHolder(mHeader);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int elapsedSeconds = (int) ((fRecords.get(position).getStopTime() - fRecords.get(position).getStartTime()) / 1000);
        int elapsedHours = elapsedSeconds / 3600;
        int elapsedMinutes = (elapsedSeconds % 3600) / 60;

        Project mProject = fRecords.get(position).getAssignedProject();

        holder.setRow(mProject == null ? "No project" : mProject.getName(), String.format("%02d:%02d", elapsedHours, elapsedMinutes));
    }

    @Override
    public long getHeaderId(int position) {
        return DateFormat.format("dd.MM.yyyy", new Date(fRecords.get(position).getStartTime())).hashCode();
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        LinearLayout mHeader = (LinearLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_records_header, parent, false);
        return new HeaderViewHolder(mHeader);
    }

    @Override
    public void onBindHeaderViewHolder(HeaderViewHolder holder, int position) {
        holder.setHeader(DateFormat.format("dd.MM.yyyy", new Date(fRecords.get(position).getStartTime())).toString());
    }


    @Override
    public int getItemCount() {
        return fRecords.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private LinearLayout fRow;
        private int fPosition;

        public ViewHolder(LinearLayout aRow) {
            super(aRow);
            fRow = aRow;
        }

        public LinearLayout getRow() {
            return fRow;
        }

        public void onClick(View aView){

        }

        public void setRow(String aProjectName, String aTimeSpent){
            ((TextView)fRow.findViewById(R.id.item_records_project_name)).setText(aProjectName);
            ((TextView)fRow.findViewById(R.id.item_records_time_spent)).setText(aTimeSpent);
        }

    }


    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout fHeader;


        public HeaderViewHolder(LinearLayout aItem) {
            super(aItem);
            fHeader = aItem;
        }

        public void setHeader(String aHeader){
            ((TextView) fHeader.findViewById(R.id.item_records_header)).setText(aHeader);
        }
    }

}
