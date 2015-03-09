package edu.auburn.eng.csse.comp3710.cdd0021.midterm;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by Carlos on 3/3/2015.
 */
public class MainFragment extends Fragment {

    private static final String TAG_HAIKU_STRING = "midterm.mainFragment.haiku";
    private static final String TAG_HAIKU_OBJECT = "midterm.haiku.object";
    private static final String TAG_HAIKU_STACK = "midterm.haiku.stack";
    private static final String TAG_HAIKU_ADAPTER = "midterm.haiku.adapter";

    private ArrayAdapter<CharSequence> adapterAdjectives;
    private ArrayAdapter<CharSequence> adapterNouns;
    private ArrayAdapter<CharSequence> adapterVerbs;
    private ArrayAdapter<CharSequence> adapterOther;
    private RadioGroup radioGroup;
    private Spinner wordsSpinner;
    private Activity activity;
    private TextView textViewListWords1;
    private TextView textViewListWords2;
    private TextView textViewListWords3;
    private Stack<Haiku> haikuStack;
    private Button startOver;
    private Button deleteTheLastWord;
    private Button addWord;
    private Button display;
    private Haiku haiku;
    private ArrayList<Word> wordList;
    private Adapter currentAdapter;
    private String sentence;


    public MainFragment(Activity activity){
        // Set adapters with their content
        this.activity = activity;
        adapterAdjectives = ArrayAdapter.createFromResource(activity , R.array.adjectives, android.R.layout.simple_spinner_item );
        adapterNouns = ArrayAdapter.createFromResource(activity , R.array.nouns, android.R.layout.simple_spinner_item );
        adapterVerbs = ArrayAdapter.createFromResource(activity , R.array.verbs, android.R.layout.simple_spinner_item );
        adapterOther = ArrayAdapter.createFromResource(activity , R.array.other, android.R.layout.simple_spinner_item );
        haikuStack = new Stack<Haiku>();
    }

    public MainFragment(){}

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(TAG_HAIKU_STRING, getSentence());
        savedInstanceState.putSerializable(TAG_HAIKU_OBJECT, haiku);
        savedInstanceState.putSerializable(TAG_HAIKU_STACK, haikuStack);
        savedInstanceState.putInt(TAG_HAIKU_ADAPTER, radioGroup.getCheckedRadioButtonId());
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence() {
        String string = "";
        for(Line l : haiku.getLines()){
            string += l.toString() + "\n";
        }
        sentence = string;
    }

    //Listener for the RadioGroup
    RadioGroup.OnCheckedChangeListener radioGroupAction = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            int availableSyze = haiku.getAvailableSpace();
            switch (checkedId){
                case R.id.radioButton1:
                    wordsSpinner.setAdapter(adapterAdjectives);
                    currentAdapter = adapterAdjectives;
                    processWordSpinner(adapterAdjectives, haiku.getAvailableSpace());
                    break;
                case R.id.radioButton2:
                    wordsSpinner.setAdapter(adapterNouns);
                    currentAdapter = adapterNouns;
                    processWordSpinner(adapterNouns, haiku.getAvailableSpace());
                    break;
                case R.id.radioButton3:
                    wordsSpinner.setAdapter(adapterVerbs);
                    currentAdapter = adapterVerbs;
                    processWordSpinner(adapterVerbs, haiku.getAvailableSpace());
                    break;
                case R.id.radioButton4:
                    wordsSpinner.setAdapter(adapterOther);
                    currentAdapter = adapterOther;
                    processWordSpinner(adapterOther, haiku.getAvailableSpace());
                    break;

            }
            showItems();
        }
    };

    Spinner.OnItemSelectedListener spinnerListenerSelectedWord = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            CharSequence buttonContent = getString(R.string.add) + "\n '" + parent.getSelectedItem().toString().toUpperCase() + "'\n" + getString(R.string.toTheHaiku);
            addWord.setText(buttonContent);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    // Process the words - Take off numbers from strings and just add strings with determined size
    public void processWordSpinner(Adapter adapter, int size){
        wordList = new ArrayList<Word>();

        int contSyllables;

        for (int i=0; i < adapter.getCount(); i++){
            contSyllables = Integer.parseInt(String.valueOf(adapter.getItem(i).toString().charAt(0)));
            if(contSyllables <= size) {
                Word wd = new Word(adapter.getItem(i).toString().substring(1),contSyllables);
                wordList.add(wd);
            }
        }
        Adapter newAdapter = new ArrayAdapter(activity, android.R.layout.simple_spinner_dropdown_item ,wordList);
        wordsSpinner.setAdapter((android.widget.SpinnerAdapter) newAdapter);
        testAvailableSpace();

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.i("onCreate", "onCreateFragment");
    }

    public void testAvailableSpace(){
        if (haiku.getAvailableSpace() == 0){
            addWord.setVisibility(View.INVISIBLE);
            wordsSpinner.setVisibility(View.INVISIBLE);
        }
    }


    Button.OnClickListener buttonListenerAddWord = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            deleteTheLastWord.setVisibility(View.VISIBLE);

            Word word = wordList.get(wordsSpinner.getSelectedItemPosition());
            haiku.addWord(word);

            haikuStack.push(haiku.clone());

            processWordSpinner(currentAdapter, haiku.getAvailableSpace());
            changeWordsView(haiku);
            if (haiku.getAvailableSpace()==0){
                wordsSpinner.setVisibility(View.INVISIBLE);
                addWord.setVisibility(View.INVISIBLE);
            }


        }
    };

    Button.OnClickListener buttonListenerDeleteLastWord = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(haikuStack.size() > 1) {
                haikuStack.pop();
                haiku = haikuStack.peek().clone();
                changeWordsView(haiku);
                processWordSpinner(currentAdapter, haiku.getAvailableSpace());
                showItems();
            }
            if (haikuStack.size() == 1)
                deleteTheLastWord.setVisibility(View.INVISIBLE);
        }

    };

    Button.OnClickListener buttonListenerStartOver = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            textViewListWords1.setText("1) ");
            textViewListWords2.setText("2) ");
            textViewListWords3.setText("3) ");


            radioGroup.clearCheck();
            initHaiku();

            hideItems();

        }
    };

    Button.OnClickListener buttonListenerDisplay = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            setSentence();
            ((operations) activity).changeToDisplay(sentence);
        }
    };




    public interface operations {
        public void changeToDisplay(String string);
    }

    public void changeWordsView(Haiku haiku){
        Line l;
        for(int i=0; i < haiku.getLines().size(); i++){
            l = haiku.getLines().get(i);
                switch (i) {
                    case 0:
                        textViewListWords1.setText("1) " + l.toString());
                        break;
                    case 1:
                        textViewListWords2.setText("2) " + l.toString());
                        break;
                    case 2:
                        textViewListWords3.setText("3) " + l.toString());
                        break;
                }
        }

    }


    public void hideItems(){
        addWord.setVisibility(View.INVISIBLE);
        wordsSpinner.setVisibility(View.INVISIBLE);
        startOver.setVisibility(View.INVISIBLE);
        display.setVisibility(View.INVISIBLE);
        deleteTheLastWord.setVisibility(View.INVISIBLE);
    }

    public void showItems(){
        addWord.setVisibility(View.VISIBLE);
        wordsSpinner.setVisibility(View.VISIBLE);
        startOver.setVisibility(View.VISIBLE);
        display.setVisibility(View.VISIBLE);
    }


    public void initHaiku(){
        haiku = new Haiku();
        haikuStack.clear();
        haikuStack.push(haiku.clone());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        Log.i("onCreateView", "onCreateViewFragment");
        // Fragment view
        View v = inflater.inflate(R.layout.fragment_main, parent, false);

        // Elements in the view
        radioGroup = (RadioGroup) v.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(radioGroupAction);
        wordsSpinner = (Spinner) v.findViewById(R.id.spinnerWords);

        textViewListWords1 = (TextView) v.findViewById(R.id.textViewListWords1);
        textViewListWords2 = (TextView) v.findViewById(R.id.textViewListWords2);
        textViewListWords3 = (TextView) v.findViewById(R.id.textViewListWords3);

        startOver = (Button) v.findViewById(R.id.buttonStartOver);
        deleteTheLastWord = (Button) v.findViewById(R.id.buttonDeleteLastWord);
        addWord = (Button) v.findViewById(R.id.buttonAddWord);
        display = (Button) v.findViewById(R.id.buttonDisplay);

        addWord.setOnClickListener(buttonListenerAddWord);
        wordsSpinner.setOnItemSelectedListener(spinnerListenerSelectedWord);
        deleteTheLastWord.setOnClickListener(buttonListenerDeleteLastWord);
        startOver.setOnClickListener(buttonListenerStartOver);
        display.setOnClickListener(buttonListenerDisplay);
        hideItems();


        if (savedInstanceState != null) {
            int currentButton = savedInstanceState.getInt(TAG_HAIKU_ADAPTER);
            switch (currentButton){
                case R.id.radioButton1:
                    currentAdapter = adapterAdjectives;
                    processWordSpinner(adapterAdjectives, haiku.getAvailableSpace());
                    break;
                case R.id.radioButton2:
                    currentAdapter = adapterNouns;
                    processWordSpinner(adapterNouns, haiku.getAvailableSpace());
                    break;
                case R.id.radioButton3:
                    currentAdapter = adapterVerbs;
                    processWordSpinner(adapterVerbs, haiku.getAvailableSpace());
                    break;
                case R.id.radioButton4:
                    currentAdapter = adapterOther;
                    processWordSpinner(adapterOther, haiku.getAvailableSpace());
                    break;

            }
            haikuStack = (Stack<Haiku>) savedInstanceState.getSerializable((TAG_HAIKU_STACK));
            haiku = (Haiku) savedInstanceState.getSerializable(TAG_HAIKU_OBJECT);
        }
        else if (!haikuStack.isEmpty()) {
            changeWordsView(haiku);
        } else {
            initHaiku();
        }

        if (haiku.getAvailableSpace()==0){
            addWord.setVisibility(View.INVISIBLE);
            wordsSpinner.setVisibility(View.INVISIBLE);
            deleteTheLastWord.setVisibility(View.VISIBLE);

        }

        return v;
    }


}
