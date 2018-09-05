# SearchBox
android 自定义选择框
##### gridView布局
![图片加载失败](https://github.com/tangguna/SearchBox/blob/master/img/Screenshot_1536113915.png)
##### ListView布局
![图片加载失败](https://github.com/tangguna/SearchBox/blob/master/img/Screenshot_1536126402.png)
### 功能
    1.包含历史搜索，热门搜索；
    2.可以从服务器或者本地加载数据；
    3.历史记录缓存，动态添加记录
    4 listView，gridView两种显示方式
### 配置
#### 在根build.gradle中添加
     	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
#### 添加依赖
     dependencies {
	        implementation 'com.github.tangguna:SearchBox:1.0.1'
	}

###使用方式
#### 实现接口onSearchCallBackListener
     public abstract void Search(String str)：搜索关键字
     public abstract void Back()：返回
     public abstract void ClearOldData()：清空历史记录
     public abstract void SaveOldData(ArrayList<String> AlloldDataList)：保存搜索记录
#### 调用 GridView
     在XML中添加布局
     <com.tangguna.searchbox.library.widget.SearchLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/msearchlayout"
        app:search_hint="输入搜索内容"
        app:search_baground="@drawable/search_baground_shap">
    </com.tangguna.searchbox.library.widget.SearchLayout>
#### 实现initData(@Nullable List<String> olddatalist,@Nullable List<String> hotdata, onSearchCallBackListener sCb,int styleId)
     /**
     *
     * @param olddatalist  历史搜索数据集合
     * @param hotdata  热门搜索数据集合
     * @param sCb  事件处理监听
     * @param styleId 热门搜索样式(值在1到5之间) 可以在drawable下修改、添加 sousuo_bg_gray_X等样式背景
     */
    public void initData(@Nullable List<String> olddatalist,@Nullable List<String> hotdata, onSearchCallBackListener sCb,int styleId){
    }
#### 在activity中添加：
     List<String> skills = HistoryCache.toArray(getApplicationContext());
     String shareHotData ="C++,C,PHP,React";
     List<String> skillHots = Arrays.asList(shareHotData.split(","));
     searchLayout.initData(skills, skillHots, new onSearchCallBackListener() {
        @Override
        public void Search(String str) {
            //进行或联网搜索
            Log.e("点击",str.toString());
            Bundle bundle = new Bundle();
            bundle.putString("data",str);
            startActivity(SearchActivity.class,bundle);//此方法为跳转传值，详情见代码
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
    },1);
    
#### HistoryCache
     HistoryCache为历史记录缓存类，包含方法如下：
     public static void clear(Context context)：清除所有数据
     public static void saveHistory(Context context, String result)：保存记录
     public static String getHistory(Context context)：获取保存记录
     public static List<String> toArray(Context context):把json数据转换成list
     public static String toJsonArray(List<String> historyList):ArrayList 转换成JsonArray
