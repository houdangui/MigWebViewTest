package com.projectgoth.migwebviewtest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by houdangui on 23/6/15.
 */
public class MessageViewHolder {

    private RelativeLayout container;
    private TextView webViewTitle;
    private MyWebView  webView;

    private Message message;
    private Activity context;

    private WebViewListener listener;

    public interface WebViewListener {
        void onPageFinished(WebView webView);
    }


    public MessageViewHolder(View view) {
        container = (RelativeLayout)view.findViewById(R.id.container);
        webViewTitle = (TextView)view.findViewById(R.id.text_embeded_webview);
    }

    public void setWebViewListener(WebViewListener listener) {
        this.listener = listener;
    }

    public void setContext(Activity context) {
        this.context = context;
    }

    private void setupWebView() {
        if (message.getWebViewkey() == null) {
            webViewTitle.setText("msg index:" + message.getMsgIndex() + " webview type: null");
            removeWebViewInChild(container);
            return;
        }

        webViewTitle.setText("msg index:" + message.getMsgIndex() + " webview type:" + message.getWebViewType());

        String key = message.getWebViewkey();
        MyWebView cachedWebView = WebViewCache.getWebView(key);

        Log.d("WebinList", "----- setupWebView msg index:" + message.getMsgIndex() + " webview type:" + message.getWebViewType());

        if (cachedWebView == null) {
            removeWebViewInChild(container);

            Log.d("WebinList", "----- new web view & load");
            webView = new MyWebView(context);
            if (Build.VERSION.SDK_INT >= 11) webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

            //Log.d("WebinList", "----- before adding " + logChildrenViews);
            container.addView(webView, getWebViewLayoutParam(MyWebView.WEB_VIEW_LOADING_HEIGHT));
            String logChildrenViews = getChildViewNames(container);
            //Log.d("WebinList", "----- after adding " + logChildrenViews);

            webView.getSettings().setDomStorageEnabled(true);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadDataWithBaseURL(WebViewCache.baseURl,
                    key,
                    "text/html", "UTF-8", null);
            webView.setBackgroundColor(Color.YELLOW);
            webView.setTag(message.getWebViewType());

            //webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
            //webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

            webView.setWebViewClient(new WebViewClient(){

                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);

                    MyWebView myWebView = (MyWebView) view;
                    myWebView.setStartLoadingTime(System.currentTimeMillis());

                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    //int measuredHeight = view.getMeasuredHeight();
                    //int height = view.getHeight();
                    //int scrollRange = ((MyWebView)view).getVerticalScrollRange();
                    //Log.d("WebinList", "WebView:" + message.getWebViewType() +
                    //        " scrollRange:" + scrollRange);
                    //view.setTag(R.id.webview_height, scrollRange);

                    //adjustHeight(view, scrollRange);

                    MyWebView myWebView = (MyWebView) view;
                    myWebView.setFinishLoadingTime(System.currentTimeMillis());

                    Log.d("WebinList", "WebView:" + myWebView.getTag() + " LoadingTime:" + myWebView.getLoadingTime());

                    listener.onPageFinished(view);
                }
            });

        } else {

            webView = cachedWebView;
            removeWebViewInChild(container);

            //remove parent view of cached webview
            RelativeLayout parentView = (RelativeLayout)cachedWebView.getParent();
            if (parentView != null) {
                MessageViewHolder holder = (MessageViewHolder) parentView.getTag();
                Message message = holder.getMessage();
                Log.d("WebinList", "**** remove webview type:" + message.getWebViewType() + " from msg:" + message.getMsgIndex());
                parentView.removeView(cachedWebView);
            }
            //add cached webview
            Log.d("WebinList", "----- add cached web view: " + cachedWebView.getTag());
            int height = webView.displayHeight == 0 ? MyWebView.WEB_VIEW_LOADING_HEIGHT : webView.displayHeight;
            container.addView(cachedWebView, getWebViewLayoutParam(height));
            String logChildrenViews = getChildViewNames(container);
            //Log.d("WebinList", "----- after adding " + logChildrenViews);

            WebViewCache.removeWebView(key);
        }

        container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout c = container;
                for (int i = 0 ; i < container.getChildCount(); i++) {
                    View childView = container.getChildAt(i);
                    if (childView instanceof MyWebView) {
                        MyWebView webView = (MyWebView) childView;

                    }
                }
            }
        });
    }

    public String getChildViewNames(RelativeLayout container) {
        String logChildrenViews = "";
        for (int i = 0 ; i < container.getChildCount(); i++) {
            View childView = container.getChildAt(i);
            logChildrenViews += childView.getClass().getSimpleName();
            if (i != container.getChildCount() - 1) {
                logChildrenViews += ";";
            }
        }
        return logChildrenViews;
    }

    public void removeWebViewInChild(RelativeLayout container) {
        for (int i = 0; i < container.getChildCount(); i++) {
            View childView = container.getChildAt(i);
            if(childView instanceof MyWebView) {
                MessageViewHolder holder = (MessageViewHolder) container.getTag();
                Message message = holder.getMessage();
                //Log.d("WebinList", "**** remove webview type:" + message.getWebViewType() + " from msg:" + message.getMsgIndex());
                container.removeView(childView);
            }
        }
    }

    public void setData(Message msg) {
        message = msg;

        setupWebView();
    }

    public String getKey() {
        return message.getWebViewkey();
    }

    public MyWebView getWebView() {
        return webView;
    }

    public Message getMessage() {
        return message;
    }

    private RelativeLayout.LayoutParams getWebViewLayoutParam(int height) {
        // must use a fixed height.  if using wrap_content height, it causes problem that when the
        //webview height changes in the listView, the onMeasure of ListView called which calls the
        // getView and onMovedToScrapHeap for all the items on first page
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, height);
        layoutParams.addRule(RelativeLayout.BELOW, R.id.text_embeded_webview);

        return layoutParams;
    }

    private void adjustHeight(WebView webView, int height) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) webView.getLayoutParams();
        layoutParams.height = height;
        webView.setLayoutParams(layoutParams);

    }

}
