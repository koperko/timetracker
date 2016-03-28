package com.koper.timetracker.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "TimeRecord")
public class TimeRecord extends Model{

    @Column(name = "start_time", index = true)
    private long fStartTime;
    @Column(name = "stop_time", index = true)
    private long fStopTime;
    @Column(name = "description")
    private String fDescription;
    @Column(name = "project", index = true, onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
    private Project fAssignedProject;

    public TimeRecord() {
        super();
    }

    public TimeRecord(long aStartTime, long aStopTime, String aDescription, Project aAssignedProject) {
        super();
        fStartTime = aStartTime;
        fStopTime = aStopTime;
        fDescription = aDescription;
        fAssignedProject = aAssignedProject;
    }

    public long getStartTime() {
        return fStartTime;
    }

    public void setStartTime(long aStartTime) {
        fStartTime = aStartTime;
    }

    public long getStopTime() {
        return fStopTime;
    }

    public void setStopTime(long aStopTime) {
        fStopTime = aStopTime;
    }

    public String getDescription() {
        return fDescription;
    }

    public void setDescription(String aDescription) {
        fDescription = aDescription;
    }

    public Project getAssignedProject() {
        return fAssignedProject;
    }

    public void setAssignedProject(Project aAssignedProject) {
        fAssignedProject = aAssignedProject;
    }
}
