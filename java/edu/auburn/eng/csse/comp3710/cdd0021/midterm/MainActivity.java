package edu.auburn.eng.csse.comp3710.cdd0021.midterm;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements MainFragment.operations {

    private static final String TAG_FRAG = "midterm.fragment";
    private static final String TAG_DISPLAY = "midterm.display";
    private static final String TAG_HAIKU_STRING = "midterm.display.haiku";
    private static final String TAG_HAIKU_OBJECT = "midterm.haiku.object";

    private String haiku;

    private int f1 = R.id.fragmentContainer;



    private MainFragment mainFragment;
    private DisplayFragment displayFragment;

    public void changeToDisplay(String string){
        setHaiku(string);

        Bundle args = new Bundle();
        args.putString(TAG_HAIKU_STRING, getHaiku());
        displayFragment.setArguments(args);
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace( R.id.fragmentContainer, displayFragment , TAG_DISPLAY).addToBackStack(TAG_DISPLAY).commit();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(TAG_HAIKU_STRING, getHaiku());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);




        if (savedInstanceState != null) {

        } else {
            mainFragment = new MainFragment(this);
            displayFragment = new DisplayFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.fragmentContainer, mainFragment, TAG_FRAG);

            transaction.commit();
        }

    }

    public String getHaiku() {
        return haiku;
    }

    public void setHaiku(String haiku) {
        this.haiku = haiku;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
