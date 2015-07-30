package com.projectgoth.migwebviewtest;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.WebView;

/**
 * Created by houdangui on 9/7/15.
 */
public class MyWebView extends WebView {

    public long startLoadingTime;
    public long finishLoadingTime;

    public MyWebView(Context context) {
        super(context);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public long getLoadingTime() {
        return finishLoadingTime - startLoadingTime;
    }

    public long getStartLoadingTime() {
        return startLoadingTime;
    }

    public void setStartLoadingTime(long startLoadingTime) {
        this.startLoadingTime = startLoadingTime;
    }

    public long getFinishLoadingTime() {
        return finishLoadingTime;
    }

    public void setFinishLoadingTime(long finishLoadingTime) {
        this.finishLoadingTime = finishLoadingTime;
    }

    @Override
    protected void onSizeChanged(int w, int h, int ow, int oh) {
        int webViewType =  (Integer)getTag();
        Log.d("WebinList", "MyWebView type:" + webViewType + " onSizeChanged-" +
                "w:" + w + " h:" + h + " ow:" + ow + " oh:" + oh);
        super.onSizeChanged(w, h, ow, oh);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d("WebinList", "MyWebView.onLayout-" +
                "l:" + l + " t:" + t + " r:" + r + " b:" + b);
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d("WebinList", "MyWebView.onMeasure-" +
                "widthMeasureSpec:" + widthMeasureSpec + "heightMeasureSpec:" + heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public static int dpToPx(int dp)  {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    @Override
    public void requestLayout() {
        super.requestLayout();
    }

}
