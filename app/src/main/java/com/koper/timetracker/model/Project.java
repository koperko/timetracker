package com.koper.timetracker.model;

import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by koper on 05.03.16.
 */
@Table(name = "Project")
public class Project extends Model {
    @Column(name = "name", index = true)
    private String fName;
    @Column(name = "icon")
    private int fIcon;
    @Column(name = "color")
    private int fColor;

    public Project() {
        super();
    }

    public Project(String aName, @DrawableRes int aIcon, int aColor) {
        fName = aName;
        fIcon = aIcon;
        fColor = aColor;
    }

    public String getName() {
        return fName;
    }

    public void setName(String aName) {
        fName = aName;
    }

    public int getIcon() {
        return fIcon;
    }

    public void setIcon(int aIcon) {
        fIcon = aIcon;
    }

    public int getColor() {
        return fColor;
    }

    public void setColor(int aColor) {
        fColor = aColor;
    }
}
