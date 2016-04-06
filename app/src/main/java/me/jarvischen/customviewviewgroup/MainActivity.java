package me.jarvischen.customviewviewgroup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import me.jarvischen.customviewviewgroup.taglayout.TagLayoutActivity;

public class MainActivity extends AppCompatActivity {

    private static final int TAGLAYOUT = 0;
    private String[] indexs = {"TagLayout"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1, indexs);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch (position) {
                    case TAGLAYOUT:
                        intent = new Intent(MainActivity.this, TagLayoutActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }
}
