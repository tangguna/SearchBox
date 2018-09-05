package com.tangguna.searchbox.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tangguna.searchbox.library.R;
import com.tangguna.searchbox.library.adapter.HistoryDataAdapter;
import com.tangguna.searchbox.library.callback.onSearchCallBackListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 搜索框
 */
public class SearchLayout extends LinearLayout {

    private String msearch_hint;
    private int msearch_baground;
    private Context context;
    private ImageView ib_searchtext_delete;
    private EditText et_searchtext_search;
    private LinearLayout searchview;
    private Button bt_text_search_back;
    private TextView tvclearolddata;
    //历史搜索
    private SelfSearchGridView gridViewData;
    private HistoryDataAdapter historyDataAdapter;
    private ArrayList<String> historyList = new ArrayList<>();
    //热门搜索
    FlowLayout hotflowLayout;

    private String backtitle="取消",searchtitle="搜索";
    private OnClickListener TextViewItemListener;
    private int countOldDataSize=99;//默认搜索记录的条数， 正确的是传入进来的条数

    public SearchLayout(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public SearchLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.searchmlist);
        msearch_hint = array.getString(R.styleable.searchmlist_search_hint);
        msearch_baground = array.getResourceId(R.styleable.searchmlist_search_baground,R.drawable.search_baground_shap);
        array.recycle();
        initView();
    }

    public SearchLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    private void initView() {

        backtitle=getResources().getString(R.string.search_cancel);
        searchtitle=getResources().getString(R.string.search_verify);


        searchview =(LinearLayout) LayoutInflater.from(context).inflate(R.layout.msearchlayout, null);
        //把获得的view加载到这个控件中
        addView(searchview);
        //把两个按钮从布局文件中找到
        ib_searchtext_delete = (ImageView) searchview.findViewById(R.id.ib_searchtext_delete);
        et_searchtext_search = (EditText) searchview.findViewById(R.id.et_searchtext_search);
        et_searchtext_search.setBackgroundResource(msearch_baground);
        et_searchtext_search.setHint(msearch_hint);
        //搜索返回时一个按钮
        bt_text_search_back = (Button) searchview.findViewById(R.id.buttonback);
        //清除历史记录
        tvclearolddata = (TextView) searchview.findViewById(R.id.tvclearolddata);

        gridViewData=  (SelfSearchGridView)searchview.findViewById(R.id.gridviewolddata);
        gridViewData.setSelector(new ColorDrawable(Color.TRANSPARENT));//去除背景点击效果

        hotflowLayout =  (FlowLayout)searchview.findViewById(R.id.id_flowlayouthot);
        setLinstener();
    }

    //文本观察者
    private class MyTextWatcher implements TextWatcher {

        @Override
        public void afterTextChanged(Editable s) {

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            et_searchtext_search.setCursorVisible(true);
            et_searchtext_search.setSelection(et_searchtext_search.getText().toString().length());
        }
        //当文本改变时候的操作
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //如果编辑框中文本的长度大于0就显示删除按钮否则不显示
            if(s.length() > 0){
                ib_searchtext_delete.setVisibility(View.VISIBLE);
                bt_text_search_back.setText(searchtitle);
            }else{
                ib_searchtext_delete.setVisibility(View.GONE);
                bt_text_search_back.setText(backtitle);
            }
        }

    }

    private void setLinstener() {
        //给删除按钮添加点击事件
        ib_searchtext_delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                et_searchtext_search.setText("");
            }
        });
        //给编辑框添加文本改变事件
        et_searchtext_search.addTextChangedListener(new MyTextWatcher());

        et_searchtext_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {

                    String searchtext = et_searchtext_search.getText().toString().trim();
                    //dosoming
//                    Toast.makeText(context, "搜索" +searchtext, Toast.LENGTH_SHORT).show();
                    executeSearch_and_NotifyDataSetChanged(searchtext);
                    return true;
                }
                return false;
            }
        });



        bt_text_search_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchtext = et_searchtext_search.getText().toString().trim();
                if (bt_text_search_back.getText().toString().equals(searchtitle)) {
//                    Toast.makeText(context, "点击button搜索" + searchtext, Toast.LENGTH_SHORT).show();
                    executeSearch_and_NotifyDataSetChanged(searchtext);
                } else {
//                    Toast.makeText(context, "点击button  返回", Toast.LENGTH_SHORT).show();
                    if (sCBlistener != null)
                        sCBlistener.Back();
                }
            }
        });



        tvclearolddata.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sCBlistener!=null) {
                    historyList.clear();
                    historyDataAdapter.notifyDataSetChanged();
                    sCBlistener.ClearOldData();
                }
            }
        });


        TextViewItemListener = new OnClickListener(){
            @Override
            public void onClick(View v) {
                String string = ((TextView)v).getText().toString();
//              Toast.makeText(context, "Item点击"+string, Toast.LENGTH_SHORT).show();
                executeSearch_and_NotifyDataSetChanged(string);

            }
        };

        gridViewData.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(sCBlistener!=null){
                    sCBlistener.Search(historyList.get(position).trim());
                    et_searchtext_search.setText(historyList.get(position).trim());
                    et_searchtext_search.setCursorVisible(false);
                    et_searchtext_search.setSelection(et_searchtext_search.getText().toString().length());
                    //点击项移动到首部位
                    swap(historyList,position,0);
                    historyDataAdapter.notifyDataSetChanged();
                }
            }
        });

    }


    /**
     *
     * @param olddatalist  历史搜索数据集合
     * @param hotdata  热门搜索数据集合
     * @param sCb  事件处理监听
     * @param styleId 热门搜索样式(值在1到5之间) 可以在drawable下修改、添加 sousuo_bg_gray_X等样式背景
     */
    public void initData(@Nullable List<String> olddatalist,@Nullable List<String> hotdata, onSearchCallBackListener sCb,int styleId){

        SetCallBackListener(sCb);

        hotflowLayout.removeAllViews();
        historyList.clear();
        if(olddatalist!=null)
            historyList.addAll(olddatalist);
        historyDataAdapter = new HistoryDataAdapter(context,historyList);
        gridViewData.setAdapter(historyDataAdapter);


        LayoutInflater mInflater = LayoutInflater.from(context);
        if (hotdata != null){
            for (int i = 0; i < hotdata.size(); i++)
            {
                TextView tv = (TextView) mInflater.inflate(R.layout.suosou_item,hotflowLayout, false);
                tv.setText(hotdata.get(i));
                tv.setOnClickListener(TextViewItemListener);
                tv.getBackground().setLevel(MyRandom(1,styleId));
                hotflowLayout.addView(tv);
            }
        }else {
            return;
        }
    }



    private void executeSearch_and_NotifyDataSetChanged(String str){
        if(sCBlistener!=null&&(!str.equals(""))){
            if (historyList.size() > 0 && historyList.get(0).equals(str)) {
            }
            else
            {
                if (historyList.size() == countOldDataSize && historyList.size()>0)
                    historyList.remove(historyList.size() - 1);
                //判断字符串是否在历史记录中 如果在就不添加，反之则添加
                if (historyList != null && !historyList.contains(str))
                {
                    historyList.add(0, str);//把最新的添加到前面
                    historyDataAdapter.notifyDataSetChanged();
                    sCBlistener.SaveOldData(historyList);
                }else {
                    historyList.remove(str);
                    historyList.add(0, str);//把最新的添加到前面
                    historyDataAdapter.notifyDataSetChanged();
                    sCBlistener.SaveOldData(historyList);
                }
            }
            et_searchtext_search.setText(str);
            sCBlistener.Search(str);
            et_searchtext_search.setCursorVisible(false);
            et_searchtext_search.setSelection(et_searchtext_search.getText().toString().length());
        }
    }




    /**
     * 生成随机数
     * @param max  最大值
     * @param min   最小值
     * @return
     */
    public int MyRandom(int min,int max){
        Random random = new Random();
        int s = random.nextInt(max)%(max-min+1) + min;
        return s;
    }

    private onSearchCallBackListener sCBlistener;
    /**
     * 设置接口回调
     * @param onSearchCallBackListener   setCallBackListener接口
     */
    private void SetCallBackListener(onSearchCallBackListener onSearchCallBackListener){
        sCBlistener=onSearchCallBackListener;
    }


    /**
     * 调换集合中两个指定位置的元素, 若两个元素位置中间还有其他元素，需要实现中间元素的前移或后移的操作。
     * @param list 集合
     * @param oldPosition 需要调换的元素
     * @param newPosition 被调换的元素
     * @param <T>
     */
    public static <T> void swap(List<T> list, int oldPosition, int newPosition){
        if(null == list){
            throw new IllegalStateException("集合不能为空");
        }
        T tempElement = list.get(oldPosition);

        // 向前移动，前面的元素需要向后移动
        if(oldPosition < newPosition){
            for(int i = oldPosition; i < newPosition; i++){
                list.set(i, list.get(i + 1));
            }
            list.set(newPosition, tempElement);
        }
        // 向后移动，后面的元素需要向前移动
        if(oldPosition > newPosition){
            for(int i = oldPosition; i > newPosition; i--){
                list.set(i, list.get(i - 1));
            }
            list.set(newPosition, tempElement);
        }
    }
}
