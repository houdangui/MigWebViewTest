package com.projectgoth.migwebviewtest;

import android.graphics.Color;
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
        webViewTitle.setText("test web view no. " + message.getWebViewIndex());

        String key = message.getWebViewkey();
        WebView cachedWebView = WebViewCache.getWebView(key);

        if (cachedWebView == null) {
            webView.getSettings().setDomStorageEnabled(true);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadDataWithBaseURL(WebViewCache.baseURl,
                    key,
                    "text/html", "UTF-8", null);
            webView.setBackgroundColor(Color.YELLOW);

        } else {
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

            WebViewCache.removeWebView(key);
        }
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

}
