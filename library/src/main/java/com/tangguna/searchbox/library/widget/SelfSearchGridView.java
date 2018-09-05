package com.tangguna.searchbox.library.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.GridView;

public class SelfSearchGridView extends GridView {

    public SelfSearchGridView(Context context) {
        super(context);
    }

    public SelfSearchGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public SelfSearchGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int heightSpec;
        if(getLayoutParams().height== ViewGroup.LayoutParams.WRAP_CONTENT)
        {
            heightSpec = MeasureSpec.makeMeasureSpec(
                    Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
            //Integer.MAX_VALUE >> 2 == 2的31次方-1 表示的int的最大值
        }
        else {
            // Any other height should be respected as is.
            heightSpec = heightMeasureSpec;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
