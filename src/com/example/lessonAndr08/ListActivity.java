package com.example.lessonAndr08;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;
import java.util.List;


public class ListActivity extends Activity {

    ArrayAdapter<ExchangeRateByDate> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Intent intent = getIntent();
        List<ExchangeRateByDate> listOfExchangeRatesByDate = (ArrayList<ExchangeRateByDate>) intent.getSerializableExtra("list");

        ListView lvMain = (ListView) findViewById(R.id.lvMain);

        adapter = new MyAdapter(this, listOfExchangeRatesByDate);

        lvMain.setAdapter(adapter);

        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ExchangeRateByDate item = adapter.getItem(position);
                Intent intent = new Intent(ListActivity.this, RateActivity.class);
                intent.putExtra("rate", item);
                startActivity(intent);

            }
        });


    }





}