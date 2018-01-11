package info.dagrut.www.a07_multipleactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button mBtnRun1;
    Button mBtnRun2;
    public static final String EXTRA_MESSAGE = "info.dagrut.www.a07_multipleactivity.MainActivity.MSG";
    public static final int ACT3_REQUEST_CODE = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnRun1 = findViewById(R.id.launch_activity1);
        mBtnRun2 = findViewById(R.id.launch_activity2);

        mBtnRun1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChildActivity1.class);
                intent.putExtra(EXTRA_MESSAGE, "Message test");
                startActivity(intent);
            }
        });

        mBtnRun2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* This method allows the activity to return a response */
                Intent intent = new Intent(MainActivity.this, ChildActivity2.class);
                startActivityForResult(intent, ACT3_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ACT3_REQUEST_CODE) {
            if(resultCode == ChildActivity2.RESPONSE_CODE_OK) {
                Toast.makeText(this, "Activity 2 returned OK code", Toast.LENGTH_LONG).show();
            }
        }
    }
}
