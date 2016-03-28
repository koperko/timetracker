package com.koper.timetracker.fragments;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.koper.timetracker.MainActivity;
import com.koper.timetracker.R;
import com.koper.timetracker.TrackingService;
import com.koper.timetracker.model.Project;
import com.koper.timetracker.utils.CustomAnimationUtils;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class TrackerFragment extends Fragment {

    public static final String TAG = "TrackerFragment";

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final int ONGOING_NOTIFICATION_ID = 0;


    private boolean fIsStarted = false;
    private View fRootView;
    private CardView fStartButton;
    private ImageView fIcon;
    private Chronometer fTimer;
    private EditText fDescription;

    private TrackingService.LocalBinder fBinder;
    private ServiceConnection fConnection;
    private TextView fProjectName;

    private ArrayList<CharSequence> fProjectOptionsStrings;
    private ProjectSelectionFragment fProjectSelectionFragment;
    private long fProjectId = -1;
    private Project fProject;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static TrackerFragment newInstance() {
        TrackerFragment fragment = new TrackerFragment();
        Bundle args = new Bundle();
        // pass arguments for fragment creation to the bundle
        fragment.setArguments(args);
        return fragment;
    }


    public TrackerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fProjectOptionsStrings = getArguments().getCharSequenceArrayList(ARG_SECTION_NUMBER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fRootView = inflater.inflate(R.layout.fragment_tracker, container, false);
        fTimer = (Chronometer) fRootView.findViewById(R.id.timer);
        fStartButton = (CardView) fRootView.findViewById(R.id.start);
        fIcon = (ImageView) fRootView.findViewById(R.id.start_icon);
        fProjectName = (TextView) fRootView.findViewById(R.id.project_name);
        fDescription = (EditText) fRootView.findViewById(R.id.description);


        fProjectName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fProjectSelectionFragment = ProjectSelectionFragment.newInstance();
                getFragmentManager().beginTransaction()
                        .addToBackStack(null)
                        .setCustomAnimations(android.R.anim.slide_in_left, 0, 0, android.R.anim.slide_out_right)
                        .add(R.id.container, fProjectSelectionFragment, ProjectSelectionFragment.TAG)
                        .commit();
            }
        });

        fDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(fBinder != null) {
                    fBinder.getInstance().setDescription(s.toString());
                }
            }
        });
        fStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fIsStarted) {
                    stopTracking();
                } else {
                    startTracking();
                }
            }
        });
        fConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                fBinder = (TrackingService.LocalBinder) service;
                if (fBinder.getInstance().isRunning()) {
                    fIsStarted = true;
                    fRootView.setBackgroundResource(R.color.orange_light);
                    fStartButton.setCardBackgroundColor(getResources().getColor(R.color.start_orange));
                    fIcon.setImageResource(R.drawable.ic_pause_white_48dp);
                    long mTrackingStartedTimestamp = fBinder.getInstance().getCreated();
                    fTimer.setBase(mTrackingStartedTimestamp);
                    fTimer.start();
                    fProjectName.setText(fBinder.getInstance().getProject() == null ? "Pick an activity" : fBinder.getInstance().getProject().getName());
                    fDescription.setText(fBinder.getInstance().getDescription());
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        getContext().bindService(new Intent(getContext(), TrackingService.class), fConnection, 0);


        return fRootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        ((MainActivity) activity).onSectionAttached(
//                getArguments().getInt(ARG_SECTION_NUMBER));
    }

    @Override
    public void onDestroy() {
        if (fTimer != null) {
            fTimer.stop();
        }
        if (fConnection != null) {
            getContext().unbindService(fConnection);
        }
        super.onDestroy();
    }

    public void setProjectId(long aProjectId){
        fProjectId = aProjectId;
        if(fBinder != null && fBinder.getInstance().isRunning() ) {
            fBinder.getInstance().setProjectId(aProjectId);
            fProjectName.setText(fBinder.getInstance().getProject().getName());
        } else {
            fProject = Project.load(Project.class, aProjectId);
            if(fProject != null){
                fProjectName.setText(fProject.getName());
            }
        }
        if(fIsStarted){
            createOrUpdateOngoingNotification();
        }
    }

    private void startTracking() {
        fIsStarted = true;
        TrackingService.startTracking(getContext(), fProjectId, fDescription.getText().toString());

        CustomAnimationUtils.animateCardViewBackgroundToColor(fStartButton, getResources().getColor(R.color.start_green), getResources().getColor(R.color.start_orange));
        CustomAnimationUtils.animateBackgroundToColor(fRootView, getResources().getColor(android.R.color.white), getResources().getColor(R.color.orange_light));
        CustomAnimationUtils.animateToImageDrawable(fIcon, getResources().getDrawable(R.drawable.ic_pause_white_48dp));

        fTimer.setBase(SystemClock.elapsedRealtime());
        fTimer.start();

        createOrUpdateOngoingNotification();

    }

    private void stopTracking() {
        fIsStarted = false;
        CustomAnimationUtils.animateCardViewBackgroundToColor(fStartButton, getResources().getColor(R.color.start_orange), getResources().getColor(R.color.start_green));
        CustomAnimationUtils.animateBackgroundToColor(fRootView, getResources().getColor(R.color.orange_light), getResources().getColor(android.R.color.white));
        CustomAnimationUtils.animateToImageDrawable(fIcon, getResources().getDrawable(R.drawable.ic_play_arrow_white_48dp));
        getContext().stopService(new Intent(getContext(), TrackingService.class));
        fTimer.stop();
        fTimer.setBase(SystemClock.elapsedRealtime());
        fDescription.setText("");
        ((NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE)).cancel(ONGOING_NOTIFICATION_ID);

    }

    private void createOrUpdateOngoingNotification(){
        Project mProject = Project.load(Project.class, fProjectId);

        Intent mIntent = new Intent(getContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent mPendingIntent = PendingIntent.getActivity(getContext(), 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification mNotification = new Notification.Builder(getContext())
                .setOngoing(true)
                .setContentTitle("Time tracking")
                .setContentText(mProject == null ? "No project" : mProject.getName())
                .setSmallIcon(R.drawable.ic_timer_white_24dp)
                .setUsesChronometer(true)
                .setWhen(System.currentTimeMillis())
                .setContentIntent(mPendingIntent)
                .build();
        ((NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE)).notify(ONGOING_NOTIFICATION_ID, mNotification);
    }


}