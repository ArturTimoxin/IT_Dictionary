package com.timoxin.it_dictionary;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.timoxin.it_dictionary.model.WordCard;

import java.io.Serializable;
import java.util.ArrayList;

public class MyWordsFragment extends Fragment{

    private ArrayList<String> myWordArray;
    private ArrayAdapter myListAdapter;
    private Cursor myDataWord;
    TextView textListIsEmpty;
    EditText filterMyWords;
    ListView myWordsListView;
    private static final String WORD_NAME = "GET_CARD_WORD";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        myWordArray= new ArrayList<>();
        myListAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, myWordArray);
        myDataWord = ((MainActivity)this.getActivity()).getDataBaseHelperObject().getListMyWords();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View viewMyWords = inflater.inflate(R.layout.fragment_my_words, container, false);
        textListIsEmpty = (TextView) viewMyWords.findViewById(R.id.textListIsEmpty);
        filterMyWords = (EditText) viewMyWords.findViewById(R.id.editFilterMyWords);
        myWordsListView = (ListView) viewMyWords.findViewById(R.id.my_words_list_view);

        if(myDataWord.getCount() == 0){  // if table is empty
            myWordsListView.setVisibility(viewMyWords.GONE);
            filterMyWords.setVisibility(viewMyWords.GONE);
            textListIsEmpty.setVisibility(viewMyWords.VISIBLE);
            Toast.makeText(this.getContext(),"Вы пока не добавили слов",Toast.LENGTH_SHORT).show();
            return viewMyWords;
        } else {
            while(myDataWord.moveToNext()){
                myWordArray.add(myDataWord.getString(1)); // get data from column "word" table "words"
                myWordsListView.setAdapter(myListAdapter);
            }
        }

        myWordsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String nameWord = myWordsListView.getItemAtPosition(position).toString();
                WordCard wordCard = ((MainActivity) getActivity()).getDataBaseHelperObject().getInfoMyWord(nameWord);
                Bundle bundle= new Bundle();
                bundle.putSerializable(WORD_NAME, (Serializable) wordCard);
                Fragment fragmentDescription = new WordDescriptionFragment();
                fragmentDescription.setArguments(bundle);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragmentDescription, "wordDescriptionFragment").addToBackStack("myWordsFragment").commit();
            }
        });

        filterMyWords.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                myListAdapter.getFilter().filter(charSequence);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        return viewMyWords;
    }

}
