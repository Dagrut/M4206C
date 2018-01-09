package info.dagrut.www.a01_menus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_title:
                Log.e("01", "Titre du menu sélectionné");
                break;
            case R.id.m_entry1:
                Log.e("01", "1er sous-menu sélectionné");
                break;
            case R.id.m_entry2:
                Log.e("01", "2eme sous-menu sélectionné");
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
