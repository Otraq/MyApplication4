package com.example.amin.criminalintent;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Amin on 6/20/2018.
 */

public class Crime {
    private UUID mId;
    private String mTitle;
    private Date mDate;
    private String mSuspect;
    private boolean mSolved;

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        this.mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        this.mSolved = solved;
    }

    public String getSuspect() {
        return mSuspect;
    }

    public void setSuspect(String suspect) {
        this.mSuspect = suspect;
    }

    public String getPhotoFileName() {
        return  "IMG_" + getId().toString() + ".jpg";
    }

    public Crime(UUID id) {
        mId = id;
        mDate = new Date();
    }

    public Crime() {
        this(UUID.randomUUID());
    }
}
