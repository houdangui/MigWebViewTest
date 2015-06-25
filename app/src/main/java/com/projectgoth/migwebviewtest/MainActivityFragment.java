package com.projectgoth.migwebviewtest;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

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
        mMessageList.setRecyclerListener(mMessageListAdapter);
        mMessageListAdapter.setMsgDataList(getDummyMessages());
        mMessageList.setAdapter(mMessageListAdapter);
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
}
