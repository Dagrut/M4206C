package info.dagrut.www.a03_events;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView mTxt1;
    TextView mTxt2;
    TextView mTxt3;
    TextView mTxt4;

    private class InternalClickHdl implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Toast.makeText(MainActivity.this, "Méthode 3", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTxt1 = (TextView) findViewById(R.id.textView1);
        mTxt2 = (TextView) findViewById(R.id.textView2);
        mTxt3 = (TextView) findViewById(R.id.textView3);
        mTxt4 = (TextView) findViewById(R.id.textView4);

        mTxt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Méthode 1", Toast.LENGTH_LONG).show();
            }
        });

        mTxt2.setOnClickListener(this);

        mTxt3.setOnClickListener(new InternalClickHdl());

        mTxt4.setOnClickListener(new ExternalClickHdl(this));
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(MainActivity.this, "Méthode 2", Toast.LENGTH_LONG).show();
    }
}
