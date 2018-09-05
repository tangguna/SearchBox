package com.tangguna.searchbox.library.callback;

import android.os.Bundle;

import java.util.ArrayList;

public interface onSearchCallBackListener {
    /**
     * @param str  搜索关键字
     */
    public abstract void Search(String str);
    /**
     * 后退
     */
    public abstract void Back();
    /**
     * 清除历史搜索记录
     */
    public abstract void ClearOldData();

    /**
     * 保存搜索记录
     */
    public abstract void SaveOldData(ArrayList<String> AlloldDataList);

}
