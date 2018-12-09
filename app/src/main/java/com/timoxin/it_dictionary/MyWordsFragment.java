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
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class MyWordsFragment extends Fragment{

    /*
    Объект LayoutInflater используется для установки ресурса разметки для создания интерфейса
    Параметр ViewGroup container устанавливает контейнер интерфейса
    Параметр Bundle savedInstanceState передает ранее сохраненное состояние
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View viewMyWords = inflater.inflate(R.layout.fragment_my_words, container, false);
        TextView textListIsEmpty = (TextView) viewMyWords.findViewById(R.id.textListIsEmpty);
        ListView myWordsListView = (ListView) viewMyWords.findViewById(R.id.my_words_list_view);
        ArrayList<String> myWordArray= new ArrayList<>();

        Cursor myDataWord = ((MainActivity)this.getActivity()).getDataBaseHelperObject().getListMyWords();
        if(myDataWord.getCount() == 0){
            myWordsListView.setVisibility(View.GONE);
            textListIsEmpty.setVisibility(View.VISIBLE);
            Toast.makeText(this.getContext(),"Вы пока не добавили слов",Toast.LENGTH_SHORT).show();
            return viewMyWords;
        } else {
            while(myDataWord.moveToNext()){
                myWordArray.add(myDataWord.getString(1)); // get data from column "word" table "words"
                ListAdapter myListAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, myWordArray);
                myWordsListView.setAdapter(myListAdapter);
                Log.d("TAG", "find myWord");
            }
        }


        return viewMyWords;
    }

}
