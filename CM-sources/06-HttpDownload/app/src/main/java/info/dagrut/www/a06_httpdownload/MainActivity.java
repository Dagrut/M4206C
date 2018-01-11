package info.dagrut.www.a06_httpdownload;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView mHtmlView;

    private class ReceiveData extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            /* Exécuté dans le thread secondaire */
            HttpURLConnection urlConnection = null;
            String result = "";

            try {
                URL url = new URL(strings[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                int code = urlConnection.getResponseCode();

                if(code==200){
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    if (in != null) {
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                        String line = "";

                        while ((line = bufferedReader.readLine()) != null)
                            result += line;
                    }
                    in.close();
                }

                return result;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            finally {
                urlConnection.disconnect();
            }
            return result;
        }

        @Override
        protected void onPreExecute() {
            /* Exécuté sur le thread principal */
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            /* Exécuté dans le thread principal */
            Toast.makeText(MainActivity.this, "Téléchargement terminé", Toast.LENGTH_LONG).show();
            mHtmlView.setText(s);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHtmlView = findViewById(R.id.html_view);

        ReceiveData receiver = new ReceiveData();

        receiver.execute("https://www.google.com/");
    }
}
