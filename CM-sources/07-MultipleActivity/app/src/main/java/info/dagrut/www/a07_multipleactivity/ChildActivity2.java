package info.dagrut.www.a07_multipleactivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ChildActivity2 extends AppCompatActivity {
    Button mBackBtn;
    public static final int RESPONSE_CODE_OK = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child2);

        mBackBtn = findViewById(R.id.go_back);

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /* The intent parameter is optional and can be used to send data */
                Intent intent = new Intent();
                ChildActivity2.this.setResult(RESPONSE_CODE_OK, intent);
                ChildActivity2.this.finish();
            }
        });
    }
}
