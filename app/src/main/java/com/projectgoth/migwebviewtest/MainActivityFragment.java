package com.projectgoth.migwebviewtest;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements AbsListView.RecyclerListener, ViewTreeObserver.OnGlobalLayoutListener {

    private ListView mMessageList;
    private MessageListAdapter mMessageListAdapter;
    private ArrayList<Message> mDummyMessages;
    static private final int MSG_NUM = 20;

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

        mMessageList = (ListView) view.findViewById(R.id.message_list);
        mMessageListAdapter = new MessageListAdapter(getActivity());
        mMessageList.setRecyclerListener(this);
        mMessageListAdapter.setMsgDataList(getDummyMessages());
        mMessageList.setAdapter(mMessageListAdapter);
        mMessageList.getViewTreeObserver().addOnGlobalLayoutListener(this);

    }


    private ArrayList<Message> getDummyMessages() {
        if (mDummyMessages == null) {
            mDummyMessages = new ArrayList<>();
            for (int i=0; i<MSG_NUM; i++) {
                int index =  new Random().nextInt(WebViewCache.posts.length);
                Message msg = new Message();
                msg.setWebViewIndex(index);
                msg.setWebViewkey(WebViewCache.posts[index]);
                msg.setMsgIndex(i);
                mDummyMessages.add(msg);
            }
        }

        return mDummyMessages;
    }

    @Override
    public void onMovedToScrapHeap(View view) {
        Object tag = view.getTag(R.id.holder);
        Log.d("WebinList", "onMovedToScrapHeap " + view);

        if (!isLayoutInitialized) {
            return;
        }
        //for testing webview
        if (tag != null && tag instanceof MessageViewHolder ) {
            MessageViewHolder holder = (MessageViewHolder) tag;
            String key = holder.getKey();
            WebView webView = holder.getWebView();
            Message message = holder.getMessage();
            if (!isMessageVisible(message)) {
                Log.d("WebinList", "cacheWebView type " + message.getWebViewIndex());
                WebViewCache.addWebView(key, webView);
            }
        }
    }

    private boolean isMessageVisible(Message message) {
        int firstVisiblePos = mMessageList.getFirstVisiblePosition();
        int lastVisiblePos = mMessageList.getLastVisiblePosition();
        if (message.getMsgIndex() >= firstVisiblePos && message.getMsgIndex() <= lastVisiblePos ) {
            return true;
        }

        return false;
    }

    boolean isLayoutInitialized = false;
    @Override
    public void onGlobalLayout() {
        isLayoutInitialized = true;

        if (hasJellyBean()) {
            mMessageList.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        } else {
            mMessageList.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
    }

    public static boolean hasJellyBean() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }
}
