package com.example.sulin.lab1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WeatherForecast extends AppCompatActivity {

    class ForecastQuery extends AsyncTask<String, Integer, String> {
        protected String minTemp;
        protected String maxTemp;
        protected String curTemp;
        protected String bmpFileName;

        @Override
        protected String doInBackground(String ...args) {
            try {
                URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=d99666875e0e51521f0040a3d97d0f6a&mode=xml&units=metric");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                InputStream in =  conn.getInputStream();

                XmlPullParser parser = Xml.newPullParser();
                parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                parser.setInput(in, null);

               // String inputStreamString = new Scanner(in,"UTF-8").useDelimiter("\\A").next();        /*check xml content*/
                final int sleepTime = 500;
                String iconName = "";
                try {
                    int next = parser.next();
                    do {
                        if (next == XmlPullParser.START_TAG) {
                            String strTag = parser.getName();
                            if (strTag.equals("temperature")) {
                                curTemp = "Current Temperature:\n" + parser.getAttributeValue(null, "value");
                                publishProgress(25);
                                Thread.sleep(sleepTime);
                                minTemp = "\nMinimum Temperature:\n" + parser.getAttributeValue(null, "min");
                                publishProgress(50);
                                Thread.sleep(sleepTime);
                                maxTemp = "\nMaximum Temperature:\n" + parser.getAttributeValue(null, "max");
                                publishProgress(75);
                                Thread.sleep(sleepTime);
                            } else if (strTag.equals("weather")) {
                                iconName = parser.getAttributeValue(null, "icon");
                            }
                        }
                        next = parser.next();
                    } while (next != XmlPullParser.END_DOCUMENT);
                }
                finally {
                    if (in != null) {
                        in.close();
                    }
                }

                bmpFileName = iconName + ".png";
                File file = getBaseContext().getFileStreamPath(bmpFileName);
                if (!file.exists()) {   //file not exists
                    Log.i("WeatherForecast", "Downloading bmp file " + bmpFileName);

                    //download bitmap
                    String urlBitmap = "http://openweathermap.org/img/w/" + bmpFileName;
                   // Bitmap image  = HTTPUtils.getImage(urlBitmap));
                    URL urlFile = new URL(urlBitmap);
                    Bitmap image = BitmapFactory.decodeStream(urlFile.openConnection().getInputStream());
                    FileOutputStream outputStream = openFileOutput( bmpFileName, Context.MODE_PRIVATE);
                    image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                    outputStream.flush();
                    outputStream.close();
                }
                else {
                    Log.i("WeatherForecast", "Find bmp file " + bmpFileName + " on local disk.");
                }
                publishProgress(100);
                Thread.sleep(sleepTime);
            }
            catch (Exception e) {
                return e.getMessage();
            }
            return "done";
        }

        @Override
        protected void onProgressUpdate(Integer ...values) {
            ProgressBar pBar = (ProgressBar)findViewById(R.id.progressBar);
            pBar.setVisibility(View.VISIBLE);
            pBar.setProgress(values[0]);

            TextView tvCur = (TextView)findViewById(R.id.tvCurrentTemp);
            TextView tvMin = (TextView)findViewById(R.id.tvMinTemp);
            TextView tvMax = (TextView)findViewById(R.id.tvMaxTemp);
            tvCur.setText(curTemp);
            tvMin.setText(minTemp);
            tvMax.setText(maxTemp);
        }

        @Override
        protected void onPostExecute(String result) {

            //set imageview
            FileInputStream fis = null;
            try {
                fis = openFileInput(bmpFileName);
            }
            catch (FileNotFoundException e) {    e.printStackTrace();  }

            if (fis != null) {
                Bitmap bm = BitmapFactory.decodeStream(fis);

                ImageView imageView = (ImageView)findViewById(R.id.ivCurrentWeather);
                imageView.setImageBitmap(bm);
            }

            //set progress bar invisible
            ProgressBar pBar = (ProgressBar)findViewById(R.id.progressBar);
            pBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        ProgressBar pBar = (ProgressBar)findViewById(R.id.progressBar);
        pBar.setVisibility(View.VISIBLE);

        ForecastQuery fcQuery = new ForecastQuery();
        fcQuery.execute();
    }
}
