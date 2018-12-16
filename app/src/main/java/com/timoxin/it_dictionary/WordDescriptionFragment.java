package com.timoxin.it_dictionary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.timoxin.it_dictionary.model.WordCard;

public class WordDescriptionFragment extends Fragment {
    WordCard wordCard;
    private static final String WORD_NAME = "GET_CARD_WORD";
    private boolean statusNetwork;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        wordCard = (WordCard) getArguments().getSerializable(WORD_NAME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word_description, container, false);
        TextView nameWord = (TextView) view.findViewById(R.id.nameWord);
        TextView descWord = (TextView) view.findViewById(R.id.descWord);
        ImageView imageWord = (ImageView) view.findViewById(R.id.imageWord);

        nameWord.setText(wordCard.getName_word());
        descWord.setText(wordCard.getDesc_word());

        if(wordCard.getImgURL_word() != null) {
            Log.d("TAG", "set image");
            statusNetwork = ((MainActivity)this.getActivity()).hasConnection(getContext());
            if(statusNetwork == true) {
                Picasso.get().load(wordCard.getImgURL_word())
                        .error(R.drawable.error)
                        .into(imageWord);
            } else {
                Picasso.get().load(R.drawable.no_connection).into(imageWord);
            }
        }

        return view;
    }
}
