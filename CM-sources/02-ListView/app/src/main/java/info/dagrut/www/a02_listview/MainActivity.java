package info.dagrut.www.a02_listview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView mListView;
    TextView mTitreCliquable;
    String mMainWordDict[] = {
            "rejoint", "encalminées", "gémissante", "basanées", "réorienta", "rabbin", "dessaisis",
            "luxurieux", "intervenant", "embauchée", "retouche", "matérialisèrent", "gageons",
            "implémenta", "pressurisant", "reposerions", "déboîtée", "extravertie", "subviendrions",
            "submergeant", "financièrement", "faussèrent", "ornèrent", "entêta", "grimacé",
            "attraction", "transformerai", "poliront", "plaisantin", "traitait"
    };
    int currentWordId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.main_words_list);

        WordsAdapter adapter = new WordsAdapter(MainActivity.this, new ArrayList<Word>());
        mListView.setAdapter(adapter);

        mTitreCliquable = (TextView) findViewById(R.id.clickable_title);
        mTitreCliquable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WordsAdapter adapter = (WordsAdapter) mListView.getAdapter();
                adapter.add(new Word(mMainWordDict[currentWordId]));
                adapter.notifyDataSetChanged();
                currentWordId++;
                if(currentWordId >= mMainWordDict.length) {
                    currentWordId = mMainWordDict.length;
                    mTitreCliquable.setText(getString(R.string.no_word_left));
                    mTitreCliquable.setOnClickListener(null);
                    return;
                }
            }
        });
    }
}
