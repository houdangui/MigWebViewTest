package com.projectgoth.migwebviewtest;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by houdangui on 23/6/15.
 */
public class MessageViewHolder {

    private RelativeLayout container;
    private TextView webViewTitle;
    private WebView  webView;

    private Message message;

    public MessageViewHolder(View view) {
        container = (RelativeLayout)view.findViewById(R.id.container);
        webViewTitle = (TextView)view.findViewById(R.id.text_embeded_webview);
        webView = (WebView)view.findViewById(R.id.embeded_webview);
    }

    private void setupWebView() {
        webViewTitle.setText("msg index:" + message.getMsgIndex() + " webview type:" + message.getWebViewIndex());

        String key = message.getWebViewkey();
        WebView cachedWebView = WebViewCache.getWebView(key);

        Log.d("WebinList", "----- setupWebView msg index:" + message.getMsgIndex() + " webview type:" + message.getWebViewIndex());

        if (cachedWebView == null) {
            Log.d("WebinList", "----- web view load");
            //create layout params
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.BELOW, R.id.text_embeded_webview);
            if (webView.getParent() != container) {
                RelativeLayout parentView = (RelativeLayout)webView.getParent();
                if (parentView != null) {
                    parentView.removeView(webView);
                }
                container.addView(webView, layoutParams);
            }

            webView.getSettings().setDomStorageEnabled(true);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadDataWithBaseURL(WebViewCache.baseURl,
                    key,
                    "text/html", "UTF-8", null);
            webView.setBackgroundColor(Color.YELLOW);

        } else {
            Log.d("WebinList", "----- web view add");
            container.removeView(webView);
            //create layout params
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.BELOW, R.id.text_embeded_webview);
            //remove parent view of cached webview
            RelativeLayout parentView = (RelativeLayout)cachedWebView.getParent();
            if (parentView != null) {
                parentView.removeView(cachedWebView);
            }
            //add cached webview
            container.addView(cachedWebView, layoutParams);
            String logChildrenViews = "";
            for (int i = 0 ; i < container.getChildCount(); i++) {
                View childView = container.getChildAt(i);
                logChildrenViews += childView.getClass().getCanonicalName();
                if (i != container.getChildCount() - 1) {
                    logChildrenViews += ";";
                }
            }
            Log.d("WebinList", "----- after adding " + logChildrenViews);

            WebViewCache.removeWebView(key);
        }

        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               RelativeLayout c = container;
                c.requestLayout();
            }
        });
    }

    public void setData(Message msg) {
        message = msg;

        setupWebView();
    }

    public String getKey() {
        return message.getWebViewkey();
    }

    public WebView getWebView() {
        return webView;
    }

    public Message getMessage() {
        return message;
    }

}
