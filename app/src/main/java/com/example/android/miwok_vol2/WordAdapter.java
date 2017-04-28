package com.example.android.miwok_vol2;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Tomi on 12.4.2017..
 */

// this class extend from array adapter and takes input object Word
public class WordAdapter extends ArrayAdapter<Word> {

    private int mColorResourceId;

    // this is class constructor with inputs context and array of objects named words
    public WordAdapter(Activity context, ArrayList < Word > words, int colorResourceId) {


        // here we are calling superclass's constructor with 3 parameters
        // the second parameters is 0 because we are manually creating list item view in getView method
        super(context, 0, words);
        mColorResourceId = colorResourceId;
    }

    @NonNull
    @Override
    // this method is called when the list view is trying to display a list of items at given position
    // the third parameter parent represents parent view where the child view named listitemview will be added
    // the parent view in this case is listView itself
    // the purpose of this method is to get listitemview and return in to the listview
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // we are changing the name of convertview so we know with what we are working with exactly
        View listItemView = convertView;

        // if list item view is null we are creating new list item view from root view list item.xml
        // we inflate list item view meaning that we create new list item layout from the XMl resource list_item
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    // the third parameter is false because we dont want to add view to the layout yet
                    R.layout.list_item, parent, false);
        }

        // this line gets position in object
        Word currentWord = getItem(position);

        TextView miwokTextView = (TextView)listItemView.findViewById(R.id.miwok_view_2);

        // here we are adding text from object currentWord to the text view miwoktextview
        miwokTextView.setText(currentWord.getmMiwokTranslation());

        TextView defaultTextView = (TextView)listItemView.findViewById(R.id.default_view_1);

        defaultTextView.setText(currentWord.getmDefaultTranslation());

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image_view_1);

        // Check if an image is provided for this word or not
        if(currentWord.hasImage()) {
            // If an image is available, display the provided image based on the resource ID
            imageView.setImageResource(currentWord.getmImageResourceId());
            // Make sure the view is visible
            imageView.setVisibility(View.VISIBLE);
        }
            else {
            // Otherwise hide the ImageView (set visibility to GONE)
                imageView.setVisibility(View.GONE);
            }

        // Set the theme color for the list item view
        View textContainer = listItemView.findViewById(R.id.text_container);
        // Find the color that the resource ID maps to
        int color = ContextCompat.getColor(getContext(), mColorResourceId);
        // Set the background color of the text container View
        textContainer.setBackgroundColor(color);

        // here we are returning the updated listitemview (updates are coming from currentWord object=
        return listItemView;
    }
}
