package com.projectgoth.migwebviewtest;

/**
 * Created by houdangui on 23/6/15.
 */
public class Message {

    private String webViewkey;
    private int webViewIndex;
    private int msgIndex;

    public String getWebViewkey() {
        return webViewkey;
    }

    public void setWebViewkey(String webViewkey) {
        this.webViewkey = webViewkey;
    }

    public int getWebViewType() {
        return webViewIndex;
    }

    public void setWebViewIndex(int webViewIndex) {
        this.webViewIndex = webViewIndex;
    }

    public int getMsgIndex() {
        return msgIndex;
    }

    public void setMsgIndex(int msgIndex) {
        this.msgIndex = msgIndex;
    }
}
