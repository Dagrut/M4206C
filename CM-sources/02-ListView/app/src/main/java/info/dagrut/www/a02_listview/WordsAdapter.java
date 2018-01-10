package info.dagrut.www.a02_listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dagrut on 10/01/18.
 */

class WordsAdapter extends ArrayAdapter<Word> {
    public WordsAdapter(Context context, List<Word> wordsList) {
        super(context, 0, wordsList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.word_list_item,parent, false);
        }

        WordsViewHolder viewHolder = (WordsViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new WordsViewHolder();
            viewHolder.word = (TextView) convertView.findViewById(R.id.word_text);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Word word = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.word.setText(word.getMot());

        return convertView;
    }

    private class WordsViewHolder {
        public TextView word;
    }
}
