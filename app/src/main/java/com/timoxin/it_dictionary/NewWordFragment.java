package com.timoxin.it_dictionary;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewWordFragment extends Fragment {

    EditText editWordName, editWordDescription;
    Button btnAddWord;
    String tmpWordData, tmpDescriptionData;
    boolean insertData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewAddWord = inflater.inflate(R.layout.fragment_new_word, container, false);
        editWordName = (EditText) viewAddWord.findViewById(R.id.editWordName);
        editWordDescription = (EditText) viewAddWord.findViewById(R.id.editWordDescription);
        btnAddWord = (Button) viewAddWord.findViewById(R.id.addWord);

        btnAddWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tmpWordData = editWordName.getText().toString();
                tmpDescriptionData = editWordDescription.getText().toString();
                if (editWordName.length() !=0 && editWordDescription.length() != 0){
                    addData(tmpWordData,tmpDescriptionData);
                    editWordName.setText("");
                    editWordDescription.setText("");
                }else{
                    Toast.makeText(getContext(),"Введите слово и его описание!",Toast.LENGTH_SHORT).show();
                }
            }
        });

        return viewAddWord;
    }

    public void addData(String word, String description) {
        insertData = ((MainActivity)this.getActivity()).getDataBaseHelperObject().addWord(word,description);

        if(insertData == true){
            Toast.makeText(getContext(),"Слово добавлено в ваш словарь :)",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(),"Что то пошло не так :(",Toast.LENGTH_SHORT).show();
        }
    }
}
