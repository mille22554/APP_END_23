package com.example.app_end_23;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button button;
    TextView mytest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mytest = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GoodTask().execute("http://atm201605.appspot.com/h");
            }
        });
    }

    class GoodTask extends AsyncTask<String, Integer, String> {
        private static final int TIME_OUT = 1000;
        String jsonString1 = "";
        protected void onPreExecute() {
        }
        @Override
        protected String doInBackground(String... countTo) {
            try{
                HttpURLConnection conn = null;
                URL url = new URL(countTo[0]);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("GET");
                conn.connect();
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        conn.getInputStream(), "UTF-8"));
                jsonString1 = reader.readLine();
                reader.close();
                if (Thread.interrupted()) {
                    throw new InterruptedException();

                }
                if (jsonString1.equals("")) {
                    Thread.sleep(1000);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
                return "網路中斷"+e;
            }
            return jsonString1;
        }

        public void onPostExecute(String result )
        { super.onPreExecute();
            mytest.setText("JSON:\r\n"+ result);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

    }
}