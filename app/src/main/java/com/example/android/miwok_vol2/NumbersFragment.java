package com.example.android.miwok_vol2;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NumbersFragment extends Fragment {


    private MediaPlayer mMediaPlayer;

    private AudioManager mAudioManager;

    // this is our callback method to notihy us when audio focus state changes
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback and reset player to the start of the file. That way, we can
                // play the word from the beginning when we resume playback.
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mMediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };


    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Now that the sound file has finished playing, release the media player resources.
            // Toast.makeText(getApplicationContext(), "The song is done.",Toast.LENGTH_LONG).show();
            releaseMediaPlayer();
        }
    };


    public NumbersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        // Create and setup the {@link AudioManager} to request audio focus
        // here we are creating an instance of AudioManager class

        // this error occurs because fragments don't have access to the system services while acitivites does
        // we first need to call the Activity object instance, this is the activity than encloses the current fragment
        //in this case it is NumbersActivity for NumbersFragment
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        // here we are creating arraylist with input object word (contains two strings eng and miwok word)
        final ArrayList<Word> words = new ArrayList<Word>();

        // here we are creating new objects and adding it to the arraylist named words
        words.add(new Word("one","lutti",R.drawable.number_one, R.raw.number_one));
        words.add(new Word("two","otiiko",R.drawable.number_two,R.raw.number_two));
        words.add(new Word("three","tolookosu",R.drawable.number_three,R.raw.number_three));
        words.add(new Word("four","oyyisa",R.drawable.number_four,R.raw.number_four));
        words.add(new Word("five","massokka",R.drawable.number_five,R.raw.number_five));
        words.add(new Word("six","temmokka",R.drawable.number_six,R.raw.number_six));
        words.add(new Word("seven","kenekaku",R.drawable.number_seven,R.raw.number_seven));
        words.add(new Word("eight","kawinta",R.drawable.number_eight,R.raw.number_eight));
        words.add(new Word("nine","wo'e",R.drawable.number_nine,R.raw.number_nine));
        words.add(new Word("ten","na'aacha",R.drawable.number_ten,R.raw.number_ten));

        //we are creating new wordadapter named adapter with inputs context and arraylist of object words

        //There’s a problem with the arguments passed into the WordAdapter constructor because the first parameter “this”
        // refers to this class (which is the NumbersFragment), and a Fragment is not a valid Context.
        // However, the code used to work when “this” referred the NumbersActivity because an Activity is a valid Context.
        // we fix the error by passing in a reference to the Activity that encloses this Fragment as the context.
        WordAdapter adapter = new WordAdapter(getActivity(),words,R.color.category_numbers);

        // the same this just with the GridView instead of ListView
        // GridView gridView = (GridView) findViewById(R.id.gridview);
        // gridView.setAdapter(itemsAdapter);

        // we are finding listview
        // the fragments don't have findViewById method like activities
        // so we need to call the method on rootView object which should contain views such ListView
        final ListView listView = (ListView) rootView.findViewById(R.id.list);

        // here we are adding adapter to the listview and giving him input adapter object
        listView.setAdapter(adapter);

       /*
        // Calling linearView with ID rootView and creating textView variable and adding in text from zeroth index
        LinearLayout rootView = (LinearLayout) findViewById(R.id.rootView);

        for(int index = 0; index < words.size(); index++){
            // we dynamiclly created a textView in Java code
            TextView wordView = new TextView(this);
            wordView.setText(words.get(index));
            //Here we are adding childView to the parentView
            rootView.addView(wordView);
        }*/

        // Set a click listener to play the audio when the list item is clicked on
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), "lets this is it working", Toast.LENGTH_SHORT).show();
                // Get the {@link Word} object at the given position the user clicked on
                // we create a new object to get the position of clicked item view

                // Release the media player if it currently exists because we are about to
                // play a different sound file
                //  this line of code releases the mediaPlayer resources right before we start to initialize another mediaPlayer object
                // in order to player another audio file
                releaseMediaPlayer();

                Word selectedFromList = words.get(position);

                // this is where me implicitly call the toString() method in logcat we created earlier
                Log.v("NumbersActivity", "current Word " + selectedFromList );

                // Request audio focus so in order to play the audio file. The app needs to play a
                // short audio file, so we will request audio focus with a short amount of time
                // with AUDIOFOCUS_GAIN_TRANSIENT.
                // it returns audiofocus_reguest_granted of  audiofocus_reguest_failer
                // the first input is the listener which is being notified when audio focus changes
                //second parameter is streamTYpe and third parametere is durationHint
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // We have audio focus now.

                    // Create and setup the {@link MediaPlayer} for the audio resource associated with the current word
                    // we are calling the method from new object from class Word
                    mMediaPlayer = MediaPlayer.create(getActivity(), selectedFromList.getmAudioResourceId());
                    mMediaPlayer.start();

                    //Setup a listener on the media player, so that we can stop and release the
                    // media player once the sound has finished playing.
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();

        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        releaseMediaPlayer();
    }

        private void releaseMediaPlayer() {
            // If the media player is not null, then it may be currently playing a sound.
            if (mMediaPlayer != null) {
                // Regardless of the current state of the media player, release its resources
                // because we no longer need it.
                mMediaPlayer.release();

                // Set the media player back to null. For our code, we've decided that
                // setting the media player to null is an easy way to tell that the media player
                // is not configured to play an audio file at the moment.
                mMediaPlayer = null;

                // Regardless of whether or not we were granted audio focus, abandon it. This also
                // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
                mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
            }
        }

    }

