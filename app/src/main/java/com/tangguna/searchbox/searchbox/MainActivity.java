package com.tangguna.searchbox.searchbox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.tangguna.searchbox.library.cache.HistoryCache;
import com.tangguna.searchbox.library.callback.onSearchCallBackListener;
import com.tangguna.searchbox.library.widget.SearchLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SearchLayout msearchLy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        msearchLy = (SearchLayout)findViewById(R.id.msearchlayout);
        initData();
    }

    private void initData() {

        List<String> skills = HistoryCache.toArray(getApplicationContext());

        String shareHotData ="C++,C,PHP,React";
        List<String> skillHots = Arrays.asList(shareHotData.split(","));

        msearchLy.initData(skills, skillHots, new onSearchCallBackListener() {
            @Override
            public void Search(String str) {
                //进行或联网搜索
                Log.e("点击",str.toString());
                Bundle bundle = new Bundle();
                bundle.putString("data",str);
                startActivity(SearchActivity.class,bundle);
            }
            @Override
            public void Back() {
                finish();
            }

            @Override
            public void ClearOldData() {
                //清除历史搜索记录  更新记录原始数据
                HistoryCache.clear(getApplicationContext());
                Log.e("点击","清除数据");
            }
            @Override
            public void SaveOldData(ArrayList<String> AlloldDataList) {
                //保存所有的搜索记录
                HistoryCache.saveHistory(getApplicationContext(),HistoryCache.toJsonArray(AlloldDataList));
                Log.e("点击","保存数据");
            }
        },5);
    }



    public void startActivity(Class<?> openClass, Bundle bundle) {
        Intent intent = new Intent(this,openClass);
        if (null != bundle)
            intent.putExtras(bundle);
        startActivity(intent);
    }

}
