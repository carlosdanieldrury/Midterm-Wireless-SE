package edu.auburn.eng.csse.comp3710.cdd0021.midterm;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Carlos on 3/3/2015.
 */
public class DisplayFragment extends Fragment {
    private static final String TAG_HAIKU_STRING = "midterm.display.haiku";
    private String haiku;

    private TextView textViewHaiku;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TAG_HAIKU_STRING, haiku);
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.i("onCreate", "onCreateDisplay");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        Log.i("onCreateView", "onCreateViewFragment");
        View v = inflater.inflate(R.layout.fragment_haiku, parent, false);
        textViewHaiku = (TextView) v.findViewById(R.id.textViewHaiku);

        //if (savedInstanceState != null){


        setTextViewHaiku(getArguments().getString(TAG_HAIKU_STRING));





        return v;
    }

    public String getHaiku() {
        return haiku;
    }

    public void setHaiku(String haiku) {
        this.haiku = haiku;
    }

    public void setTextViewHaiku(String string){
        textViewHaiku.setText(string);
    }

}
