package com.timoxin.it_dictionary.view;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.timoxin.it_dictionary.MainActivity;
import com.timoxin.it_dictionary.R;
import com.timoxin.it_dictionary.model.WordCard;

import java.io.Serializable;
import java.util.ArrayList;

public class MainWordsFragment extends Fragment {

    private ListView mainWordsListView;
    private ArrayAdapter<String> listAdapter;
    private Cursor dataWord;
    private ArrayList<String> wordArray;
    private static final String WORD_NAME = "GET_CARD_WORD";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        wordArray= new ArrayList<>();
        dataWord = ((MainActivity)this.getActivity()).getDataBaseHelperObject().getListWords();
        listAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, wordArray);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_words, container, false);
        EditText filter = (EditText) view.findViewById(R.id.editFilter);
        mainWordsListView = (ListView) view.findViewById(R.id.main_words_list_view);

        if(dataWord.getCount() == 0){
            Toast.makeText(this.getContext(),"Нет слов в базе :(",Toast.LENGTH_LONG).show();
        } else {
            while(dataWord.moveToNext()){
                wordArray.add(dataWord.getString(1));
                mainWordsListView.setAdapter(listAdapter);
            }
        }

        mainWordsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String nameWord = mainWordsListView.getItemAtPosition(position).toString();
                WordCard wordCard = ((MainActivity) getActivity()).getDataBaseHelperObject().getInfoMainWord(nameWord);
                Bundle bundle= new Bundle();
                bundle.putSerializable(WORD_NAME, (Serializable) wordCard);
                Fragment fragmentDescription = new WordDescriptionFragment();
                fragmentDescription.setArguments(bundle);
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.content_frame, fragmentDescription, "wordDescriptionFragment").addToBackStack("mainWordsFragment").commit();
            }
        });

        filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                listAdapter.getFilter().filter(charSequence);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        return view;
    }
}