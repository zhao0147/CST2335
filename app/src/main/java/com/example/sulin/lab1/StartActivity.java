package com.example.sulin.lab1;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "StartActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button btn = (Button)findViewById(R.id.button);
        btn.setOnClickListener( new View.OnClickListener() {
                public void onClick(View v) {

                    //open listItems activity
                    Intent intent = new Intent(StartActivity.this, ListItemsActivity.class);
                    startActivityForResult(intent, 5);
                }
        });

        //start chat button callback handler
        Button btnStartChat = (Button)findViewById(R.id.btnStartChat);
        btnStartChat.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "User clicked Start Chat");

                Intent intent = new Intent(StartActivity.this, ChatWindow.class);
                startActivityForResult(intent, 0);
            }});

        Button btnWeatherFct = (Button)findViewById(R.id.btnWeatherForcast);
        btnWeatherFct.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {

                //open listItems activity
                Intent intent = new Intent(StartActivity.this, WeatherForecast.class);
                startActivityForResult(intent, 1);
            }
        });

        Log.i(ACTIVITY_NAME, "In onCreate()");
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent data) {
        if (requestCode == 5 && responseCode == Activity.RESULT_OK) {
            CharSequence text = "ListItemsActivity passed: " + data.getStringExtra("Response");
            Toast toast = Toast.makeText(this , text, Toast.LENGTH_LONG);
            toast.show();

            Log.i(ACTIVITY_NAME, "Returned to StartActivity.onActivityResult");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Log.i(ACTIVITY_NAME, "In onRestart()");
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }
}
