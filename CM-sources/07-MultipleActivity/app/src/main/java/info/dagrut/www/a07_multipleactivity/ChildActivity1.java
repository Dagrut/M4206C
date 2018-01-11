package info.dagrut.www.a07_multipleactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ChildActivity1 extends AppCompatActivity {
    Button mBackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child1);
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        Toast.makeText(this, "Message reçu de MainActivity : " + message, Toast.LENGTH_LONG).show();

        mBackBtn = findViewById(R.id.go_back);

        mBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChildActivity1.this.finish();
            }
        });
    }

}
