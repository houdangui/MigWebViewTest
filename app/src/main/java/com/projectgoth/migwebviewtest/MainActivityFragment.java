package com.projectgoth.migwebviewtest;

import android.os.Build;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Random;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements AbsListView.RecyclerListener {

    private MyListView mMessageList;
    private MessageListAdapter mMessageListAdapter;
    private ArrayList<Message> mDummyMessages;
    static private final int MSG_NUM = 20;
    private Handler handler = new Handler();

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message_list, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMessageList = (MyListView) view.findViewById(R.id.message_list);
        mMessageListAdapter = new MessageListAdapter(getActivity());
        mMessageList.setRecyclerListener(this);
        mMessageListAdapter.setMsgDataList(getDummyMessages());
        //mMessageListAdapter.setMsgDataList(getDummyMessages());
        mMessageList.setAdapter(mMessageListAdapter);

    }


    private ArrayList<Message> getDummyMessages() {
        if (mDummyMessages == null) {
            mDummyMessages = new ArrayList<>();
            for (int i = 0; i < MSG_NUM; i++) {
                int index = new Random().nextInt(WebViewCache.posts.length);
                Message msg = new Message();
                msg.setWebViewIndex(index);
                msg.setWebViewkey(WebViewCache.posts[index]);
                msg.setMsgIndex(i);
                mDummyMessages.add(msg);
            }
        }

        return mDummyMessages;
    }

    private ArrayList<Message> getDummyMsgsWithLessWebView() {
        if (mDummyMessages == null) {
            mDummyMessages = new ArrayList<>();
            for (int i = 0; i < MSG_NUM; i++) {
                Message msg = new Message();
                msg.setMsgIndex(i);
                msg.setWebViewIndex(-1);
                msg.setWebViewkey(null);
                mDummyMessages.add(msg);
            }

            int webViewIndex = 0;

            /*Message msg0 = mDummyMessages.get(0);
            msg0.setWebViewIndex(webViewIndex);
            msg0.setWebViewkey(WebViewCache.posts[webViewIndex]);

            webViewIndex = 1;
            Message msg1 = mDummyMessages.get(1);
            msg1.setWebViewIndex(webViewIndex);
            msg1.setWebViewkey(WebViewCache.posts[webViewIndex]);

            webViewIndex = 2;
            Message msg2 = mDummyMessages.get(2);
            msg2.setWebViewIndex(webViewIndex);
            msg2.setWebViewkey(WebViewCache.posts[webViewIndex]);

            webViewIndex = 3;
            Message msg3 = mDummyMessages.get(3);
            msg3.setWebViewIndex(webViewIndex);
            msg3.setWebViewkey(WebViewCache.posts[webViewIndex]);*/

            webViewIndex = 4;
            Message msg10 = mDummyMessages.get(10);
            msg10.setWebViewIndex(webViewIndex);
            msg10.setWebViewkey(WebViewCache.posts[webViewIndex]);

        }

        return mDummyMessages;
    }

    @Override
    public void onMovedToScrapHeap(View view) {
        Object tag = view.getTag();
        //Log.d("WebinList", "onMovedToScrapHeap");

        //for testing webview
        if (tag != null && tag instanceof MessageViewHolder) {
            MessageViewHolder holder = (MessageViewHolder) tag;
            Log.d("WebinList", "onMovedToScrapHeap: view of msg " + holder.getMessage().getMsgIndex());
            String key = holder.getKey();
            if (key == null) {
                return;
            }

            MyWebView webView = holder.getWebView();
            Message message = holder.getMessage();
            Log.d("WebinList", "onMovedToScrapHeap msg index:" + message.getMsgIndex() + (webView == null ? "" : " webview type:" + webView.getTag()));
            if (webView != null) {
                Log.d("WebinList", "***** cacheWebView type " + message.getWebViewType());

                int type = (Integer) webView.getTag();
                String myKey = WebViewCache.posts[type];
                if (!myKey.equals(key)) {
                    Log.d("WebinList", "!!!!!!!" + "key:" + key + " myKey:" + myKey);
                }
                WebViewCache.addWebView(key, webView);
            }
        }
    }

    private boolean isMessageVisible(Message message) {
        int firstVisiblePos = mMessageList.getFirstVisiblePosition();
        int lastVisiblePos = mMessageList.getLastVisiblePosition();
        if (message.getMsgIndex() >= firstVisiblePos && message.getMsgIndex() <= lastVisiblePos) {
            return true;
        }

        return false;
    }

}
