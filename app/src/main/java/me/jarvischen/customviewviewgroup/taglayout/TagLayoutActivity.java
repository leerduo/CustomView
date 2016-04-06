package me.jarvischen.customviewviewgroup.taglayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.TextView;

import me.jarvischen.customviewviewgroup.R;

public class TagLayoutActivity extends AppCompatActivity {

    private String[] vals = new String[]{"php","css","html","Android",
            "Java","ios","math",
            "sql","c++","python",
            "edify","shell","linux"};
    private TagLayout tagLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag_layout);
        tagLayout = (TagLayout) findViewById(R.id.tagLayout);
        initData();
    }

    public void initData(){
        LayoutInflater inflater = LayoutInflater.from(this);
        for (int i = 0; i < vals.length; i++) {
           /* Button button = new Button(this);
            ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                    ViewGroup.MarginLayoutParams.WRAP_CONTENT,
                    ViewGroup.MarginLayoutParams.WRAP_CONTENT);
            button.setText(vals[i]);
            tagLayout.addView(button,lp);*/
            TextView textView = (TextView) inflater.inflate(R.layout.taglayout_tv, tagLayout, false);
            textView.setText(vals[i]);
            tagLayout.addView(textView);
        }
    }

}
