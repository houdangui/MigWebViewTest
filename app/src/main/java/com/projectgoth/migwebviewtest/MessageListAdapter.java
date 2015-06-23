package com.projectgoth.migwebviewtest;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by houdangui on 23/6/15.
 */
public class MessageListAdapter extends BaseAdapter implements AbsListView.RecyclerListener {

    private ArrayList<Message> msgDataList = new ArrayList<>();
    private LayoutInflater mInflater;
    private Activity mActivity;

    public MessageListAdapter(Activity activity) {
        super();
        mActivity = activity;
        mInflater = LayoutInflater.from(mActivity);
    }

    @Override
    public int getCount() {
        return msgDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return msgDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MessageViewHolder viewHolder = null;

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.holder_message_item, null);
            viewHolder = new MessageViewHolder(convertView);
            convertView.setTag(R.id.holder, viewHolder);
        } else {
            viewHolder = (MessageViewHolder)convertView.getTag(R.id.holder);
        }

        Message msg = (Message)getItem(position);
        viewHolder.setData(msg);

        return convertView;
    }

    public void setMsgDataList(ArrayList<Message> msgDataList) {
        this.msgDataList = msgDataList;
    }

    @Override
    public void onMovedToScrapHeap(View view) {
        Object tag = view.getTag();
        //for testing webview
        if (tag != null && tag instanceof MessageViewHolder ) {
            MessageViewHolder holder = (MessageViewHolder) tag;
            String key = holder.getKey();
            WebView webView = holder.getWebView();
            WebViewCache.addWebView(key, webView);
        }
    }
}
