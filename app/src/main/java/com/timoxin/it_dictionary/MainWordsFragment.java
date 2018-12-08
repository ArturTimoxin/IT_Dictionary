package com.timoxin.it_dictionary;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainWordsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("TAG", "Start onCreateView MainWordsFragment");

        View view = inflater.inflate(R.layout.fragment_main_words, container, false);

        ListView mainWordsListView = (ListView) view.findViewById(R.id.main_words_list_view);
        ArrayList<String> wordArray= new ArrayList<>();
        Cursor dataWord = ((MainActivity)this.getActivity()).getDataBaseHelperObject().getListWords();
        if(dataWord.getCount() == 0){
            Toast.makeText(this.getContext(),"Нет слов в базе :(",Toast.LENGTH_LONG).show();
        } else {
            while(dataWord.moveToNext()){
                wordArray.add(dataWord.getString(1)); // get data from column "word" table "words"
                ListAdapter listAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, wordArray);
                mainWordsListView.setAdapter(listAdapter);
                Log.d("TAG", "addword");
            }
        }

        return view;
    }
}
