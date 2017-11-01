package com.example.teerasaksathu.productionjongtalad;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MarketListActivity extends AppCompatActivity{

    String[] marketList = {
            "ตลาด A",
            "ตลาด B",
            "ตลาด C",
    };

    ListView lvMarketList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_list);

        initInstances();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MarketListActivity.this, android.R.layout.simple_list_item_1,marketList);
        lvMarketList.setAdapter(adapter);
        lvMarketList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MarketListActivity.this, LockReservation.class);
                startActivity(intent);
            }
        });

    }

    private void initInstances() {
        lvMarketList = (ListView) findViewById(R.id.lvMarketList);
    }
}
