package com.example.lessonAndr08;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.text.SimpleDateFormat;
import java.util.*;

public class DownloadActivity extends Activity {

    MyTask downloadExchangeRate;
    TextView downloadProg;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download);

        downloadProg = (TextView) findViewById(R.id.download_progress);

        downloadExchangeRate = new MyTask();
        downloadExchangeRate.execute();

    }


    class MyTask extends AsyncTask<Void, Integer, ArrayList<ExchangeRateByDate>> {

        @Override
        protected ArrayList<ExchangeRateByDate> doInBackground(Void... params)
        {

            ArrayList<ExchangeRateByDate> tempExchRatesForPeriod = new ArrayList<ExchangeRateByDate>();

            try {

               for (int i = 0; i < Const.PERIOD; i++) {

                    Calendar date = new GregorianCalendar();
                    date.add(Calendar.DAY_OF_YEAR, (-i+Const.TWO_DAYS_AGO));
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

                    String exchangeRateURL = Const.PB_EXCHANGE_RATE_URL + dateFormat.format(date.getTime());

                    HttpClient client = new DefaultHttpClient();
                    HttpPost post = new HttpPost(exchangeRateURL);
                    HttpResponse response = client.execute(post);
                    HttpEntity entity = response.getEntity();
                    String responseString = EntityUtils.toString(entity, "UTF-8");

                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();
                    ExchangeRateByDate currentExchangeRateByDate = gson.fromJson(responseString, ExchangeRateByDate.class);

//                   Log.d("myLog", ""+currentExchangeRateByDate.exchangeRate.get(0).currency);

                   currentExchangeRateByDate.stringExchangeRateHashMap = new HashMap<String,ExchangeRate>();
                   for (ExchangeRate j : currentExchangeRateByDate.exchangeRate) {
                       currentExchangeRateByDate.stringExchangeRateHashMap.put(j.currency,j);
                   }

                   tempExchRatesForPeriod.add(currentExchangeRateByDate);

                   publishProgress(i*100/Const.PERIOD);
               }

            } catch (Exception e) {
                e.printStackTrace();
            }

            publishProgress(99);
            return tempExchRatesForPeriod;

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            downloadProg.setText(""+values[0]+"% completed");

        }

        @Override
        protected void onPostExecute(ArrayList<ExchangeRateByDate> list) {
            super.onPostExecute(list);
/*
            for (ExchangeRateByDate i: list) {
                Log.d("myLog", ""+i.date );
                Log.d("myLog", ""+i.stringExchangeRateHashMap.get("USD").currency + " "
                        + i.stringExchangeRateHashMap.get("USD").saleRate);
            }
*/
            Intent intent = new Intent(DownloadActivity.this, ListActivity.class);
            intent.putExtra("list", list);
            startActivity(intent);
            finish();

        }
    }


}
