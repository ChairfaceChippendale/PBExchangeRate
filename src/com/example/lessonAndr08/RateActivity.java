package com.example.lessonAndr08;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class RateActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rate);

        Intent intent = getIntent();
        ExchangeRateByDate rate = (ExchangeRateByDate) intent.getSerializableExtra("rate");

        final LinearLayout contentPole = (LinearLayout) findViewById(R.id.content_pole);

        TextView dateOnRate = (TextView) findViewById(R.id.date_on_rate);
        dateOnRate.setText(rate.date);

        for (String key: Const.CURRENCIES)
        {
            if (rate.stringExchangeRateHashMap.containsKey(key)) {

                if (rate.stringExchangeRateHashMap.get(key).saleRate > 0) {

                    TextView text = new TextView(RateActivity.this);
                    //text.setLayoutParams(dateOnRate.getLayoutParams());

                    text.setTextSize(25);
                    text.setGravity(Gravity.CENTER);
                    text.setPadding(0, 10, 0, 10);

                    String currency = rate.stringExchangeRateHashMap.get(key).currency;
                    double saleRate = rate.stringExchangeRateHashMap.get(key).saleRate;
                    String data = "1 " + currency + " = " + saleRate + " UAH";

                    text.setText(data);
                    contentPole.addView(text);
                }

            }
        }


    }



}