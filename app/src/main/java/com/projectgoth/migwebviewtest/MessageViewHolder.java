package com.projectgoth.migwebviewtest;

import android.graphics.Color;
import android.view.View;
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
            container.addView(cachedWebView, webView.getLayoutParams());

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
