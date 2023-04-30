package org.bedu.aistest1.classes;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import org.bedu.aistest1.R;
import java.util.ArrayList;

public class ItemAdapter extends ArrayAdapter<Option> {

    public ItemAdapter(Activity context, ArrayList<Option> items) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item_option, parent, false);
        }

        // Get the {@link Word} object located at this position in the list
        Option currentItem = getItem(position);

        //View textContainer = listItemView.findViewById(R.id.list_item);
        //int color = ContextCompat.getColor(getContext(), mColorResourceId);
        //textContainer.setBackgroundColor(color);

        // Find the TextView in the list_item.xml layout with the ID default_text_view
        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.textView);
        // Get the default translation from the current Word object and
        // set this text on the default TextView
        defaultTextView.setText(currentItem.getOption());

        //Get the proper image if apply
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.imageView);
        imageView.setImageResource(currentItem.getImage());

        // Return the whole list item layout (containing 2 TextViews)
        // so that it can be shown in the ListView
        return listItemView;
    }
}