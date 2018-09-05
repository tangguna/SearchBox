package com.tangguna.searchbox.library.cache;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * 历史缓存工具类
 */
public class HistoryCache {

    /**
     * 清除所有数据
     * @param context
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences("record", Context.MODE_PRIVATE);
        sp.edit().clear().commit();
    }

    /**
     * 保存历史记录到SharedPreferences中
     *
     * @param result
     */
    public static void saveHistory(Context context, String result) {
        SharedPreferences sp = context.getSharedPreferences("record", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("record_key", result);
        editor.commit();
    }

    /**
     * 获取保存在SharedPreferences中的历史记录
     *
     * @return
     */
    public static String getHistory(Context context) {
        SharedPreferences sp = context.getSharedPreferences("record", Context.MODE_PRIVATE);
        String result = sp.getString("record_key", null);
        return result;
    }

    /**
     * 把json数据转换成list
     * @param context
     * @return
     */
    public static List<String> toArray(Context context) {

        String history = getHistory(context);
        Gson gson = new Gson();
        List<String> retList = gson.fromJson(history, new TypeToken<List<String>>() {
        }.getType());
        return retList;
    }

    /**
     * ArrayList 转换成JsonArray
     * @param historyList
     * @return
     */
    public static String toJsonArray(List<String> historyList) {

        Gson gson = new Gson();
        return gson.toJson(historyList); // 将JSONArray转换得到String
    }
}
