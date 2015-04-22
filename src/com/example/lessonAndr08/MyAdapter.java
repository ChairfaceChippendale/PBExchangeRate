package com.example.lessonAndr08;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.List;

public class MyAdapter extends ArrayAdapter<ExchangeRateByDate>
{

    private final LayoutInflater inflater;

    public MyAdapter(Context context, List<ExchangeRateByDate> objects) {
        super(context, 0, objects);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.my_list_item, parent, false);
        }

        ExchangeRateByDate item = getItem(position);

        String currency = item.stringExchangeRateHashMap.get(Const.DEFAULT_CURRENCY).currency;
        double saleRate = item.stringExchangeRateHashMap.get(Const.DEFAULT_CURRENCY).saleRate;
        String saleRateString = String.valueOf(roundUp(saleRate, Const.SIGNS_AFTER_DOT));

        String data = item.date + "       1 " + currency + " = " + saleRateString + " UAH";

        TextView text = (TextView) convertView.findViewById(R.id.itemView);
        text.setText(data);

        return convertView;
    }

    private BigDecimal roundUp(double value, int digits){
        return new BigDecimal(""+value).setScale(digits, BigDecimal.ROUND_HALF_UP);
    }

    @Override
    public ExchangeRateByDate getItem(int position) {
        return super.getItem(position);
    }
}
