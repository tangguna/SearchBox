package com.tangguna.searchbox.searchbox;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        TextView textView =(TextView) findViewById(R.id.tv_show);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            textView.setText(bundle.getString("data"));
        }

    }
}
