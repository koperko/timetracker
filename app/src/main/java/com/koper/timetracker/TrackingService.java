package com.koper.timetracker;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import com.koper.timetracker.model.Project;
import com.koper.timetracker.model.TimeRecord;

import java.util.Date;

public class TrackingService extends Service {

    public static final String PARAM_DESCRIPTION = "com.koper.timetracker.extra.description";
    public static final String PARAM_PROJECT_ID = "com.koper.timetracker.extra.projectId";
    private static final String PARAM_START_TIME = "com.koper.timetracker.extra.startTime";

    private String fDescription;
    private long fProjectId;
    private long fCreated;

    private boolean fIsRunning = false;

    private IBinder fBinder = new LocalBinder();
    private Project fProject;
    private long fStartTime;

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    public static void startTracking(Context context, long aProjectId, String aDescription) {
        Intent intent = new Intent(context, TrackingService.class);
        intent.putExtra(PARAM_DESCRIPTION, aDescription);
        intent.putExtra(PARAM_PROJECT_ID, aProjectId);
        intent.putExtra(PARAM_START_TIME, new Date().getTime());
        context.startService(intent);
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            fDescription = intent.getStringExtra(PARAM_DESCRIPTION);
            fProjectId = intent.getLongExtra(PARAM_PROJECT_ID, 0);
            fStartTime = intent.getLongExtra(PARAM_START_TIME, 0);
            fCreated = SystemClock.elapsedRealtime();
            fIsRunning = true;
        }
        return START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return fBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return true;
    }

    @Override
    public void onDestroy() {
        if(isRunning()){
            new TimeRecord(fStartTime, new Date().getTime(), fDescription, Project.load(Project.class, fProjectId)).save();
        }
        super.onDestroy();
    }

    public class LocalBinder extends Binder {
        public TrackingService getInstance() {
            return TrackingService.this;
        }
    }

    public String getDescription() {
        return fDescription;
    }

    public void setDescription(String aDescription) {
        fDescription = aDescription;
    }

    public long getProjectId() {
        return fProjectId;
    }

    public void setProjectId(long aProjectId) {
        fProjectId = aProjectId;
        fProject = Project.load(Project.class, fProjectId);
    }

    public long getCreated() {
        return fCreated;
    }

    public void setCreated(long aCreated) {
        fCreated = aCreated;
    }

    public boolean isRunning() {
        return fIsRunning;
    }

    public Project getProject(){
        if(fProject == null){
            fProject = Project.load(Project.class, fProjectId);
        }
        return fProject;
    }

}
