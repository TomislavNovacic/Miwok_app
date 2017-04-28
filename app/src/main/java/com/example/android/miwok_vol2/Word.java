package com.example.android.miwok_vol2;

/**
 * Created by Tomi on 11.4.2017..
 */

public class Word {

    // this are properties of a class
    private String mDefaultTranslation;

    private String mMiwokTranslation;

    private int mImageResourceId = NO_IMAGE_PROVIDED;

    private int mAudioResourceId;

    /** Constant value that represents no image was provided for this word */
    private static final int NO_IMAGE_PROVIDED = -1;

    // this is class constructor with two inputs
    public Word (String defaultTranslation, String miwokTranslation, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudioResourceId = audioResourceId;
    }

    // this is second class constructor with three inputs that has also imageid
    // we have added new constructor because our forth activity phrases need to use first constructor so we can't modify it
    public Word (String defaultTranslation, String miwokTranslation, int imageResourceId, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResourceId = imageResourceId;
        mAudioResourceId = audioResourceId;
    }

    // this are methods so we can comunicate with outside world
    public String getmDefaultTranslation(){

        return mDefaultTranslation;
    }

    public String getmMiwokTranslation(){

        return mMiwokTranslation;
    }

    public int getmImageResourceId(){
        return mImageResourceId;
    }

    /**
         * Returns whether or not there is an image for this word.
         */
     public boolean hasImage() {
         return mImageResourceId != NO_IMAGE_PROVIDED;
     }


     public int getmAudioResourceId() {
        return  mAudioResourceId;
    }

    //this is for debugging purposes
    @Override
    public String toString() {
        return "Word{" +
                "mDefaultTranslation='" + mDefaultTranslation + '\'' +
                ", mMiwokTranslation='" + mMiwokTranslation + '\'' +
                ", mImageResourceId=" + mImageResourceId +
                ", mAudioResourceId=" + mAudioResourceId +
                '}';
    }
}



