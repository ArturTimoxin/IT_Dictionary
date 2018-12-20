package com.timoxin.it_dictionary.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.timoxin.it_dictionary.MainActivity;
import com.timoxin.it_dictionary.R;
import com.timoxin.it_dictionary.model.WordCard;

public class WordDescriptionFragment extends Fragment {
    private WordCard wordCard;
    private static final String WORD_NAME = "GET_CARD_WORD";
    private boolean statusNetwork;
    private boolean showActionBarForMyWord = false;
    private boolean isExistsWord;

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

        if(wordCard.getIsItMyWordList()){
            showActionBarForMyWord = true;
        }

        setHasOptionsMenu(true);

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if(showActionBarForMyWord) {
            inflater.inflate(R.menu.menu_desc_my_word, menu);
        } else {
            inflater.inflate(R.menu.menu_desc_main_word, menu);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.addToFavorite:
                isExistsWord = ((MainActivity)this.getActivity()).getDataBaseHelperObject().isExistsWordInMyWords(wordCard.getID_word());
                Log.d("TAG", "" + isExistsWord);
                if(isExistsWord == true) {
                    Toast.makeText(this.getContext(), "Слово есть уже в вашем списке!", Toast.LENGTH_SHORT).show();
                } else if(isExistsWord == false) {
                    ((MainActivity) this.getActivity()).getDataBaseHelperObject().addWord(wordCard.getID_word(),wordCard.getName_word(), wordCard.getDesc_word());
                    Toast.makeText(this.getContext(), "Слово добавлено в ваш список.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.deleteFromMyList:
                ((MainActivity)this.getActivity()).getDataBaseHelperObject().deleteWord(wordCard.getID_word());
                Toast.makeText(this.getContext(),"Cлово удалено из вашего списка.",Toast.LENGTH_SHORT).show();
                ((MainActivity)this.getActivity()).onBackPressed();
                break;
            case R.id.back:
                ((MainActivity)this.getActivity()).onBackPressed();
                break;
        }
        return true;
    }
}
