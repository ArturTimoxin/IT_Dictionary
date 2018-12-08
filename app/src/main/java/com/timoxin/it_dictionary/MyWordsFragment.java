package com.timoxin.it_dictionary;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.timoxin.it_dictionary.data.DatabaseHelper;

import java.util.ArrayList;

public class MyWordsFragment extends Fragment{

    ListView myWordsListView;

    /*
    Объект LayoutInflater используется для установки ресурса разметки для создания интерфейса
    Параметр ViewGroup container устанавливает контейнер интерфейса
    Параметр Bundle savedInstanceState передает ранее сохраненное состояние
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //myWordsListView = (ListView) getView().findViewById(R.id.myWordsListView);
        //ArrayList<String> myWordArray = new ArrayList<>();
        // how to get obj db from mainActivity?
        //Cursor dataMyWord =

        return inflater.inflate(R.layout.fragment_my_words, container, false);
    }

}
