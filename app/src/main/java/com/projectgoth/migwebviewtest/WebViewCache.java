package com.projectgoth.migwebviewtest;

import android.support.v4.util.LruCache;

/**
 * Created by houdangui on 23/6/15.
 */
public class WebViewCache {

    public static final String baseURl = "https://twitter.com";

    public static final String[] posts = {

            "<blockquote class=\"twitter-tweet\" lang=\"en\"><p lang=\"en\" dir=\"ltr\">This is Singapore&#39;s bold plan to mold the world in its image <a href=\"http://t.co/06I6Ww1tXr\">http://t.co/06I6Ww1tXr</a> <a href=\"http://t.co/t0isFbSEMP\">pic.twitter.com/t0isFbSEMP</a></p>&mdash; Tech in Asia (@Techinasia) <a href=\"https://twitter.com/Techinasia/status/598756925388390400\">May 14, 2015</a></blockquote>\n" +
                    "<script async src=\"//platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>",

            "<blockquote class=\"twitter-tweet\" lang=\"en\"><p lang=\"en\" dir=\"ltr\">When it comes to the world’s most crucial stories, read the ones that matter most. Subscribe now for just S$15 <a href=\"http://t.co/w28w8Y4ykY\">http://t.co/w28w8Y4ykY</a></p>&mdash; The Economist (@TheEconomist) <a href=\"https://twitter.com/TheEconomist/status/597877668688728065\">May 11, 2015</a></blockquote>\n" +
                    "<script async src=\"//platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>",

            "<blockquote class=\"twitter-tweet\" lang=\"en\"><p lang=\"it\" dir=\"ltr\">Weak April trade data shows Indonesia economy lacks momentum <a href=\"http://t.co/LS6ImenAD1\">http://t.co/LS6ImenAD1</a></p>&mdash; ST Money Desk (@stmoneydesk) <a href=\"https://twitter.com/stmoneydesk/status/599051182452002817\">May 15, 2015</a></blockquote>\n" +
                    "<script async src=\"//platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>",

            "<blockquote class=\"twitter-tweet\" lang=\"en\"><p lang=\"en\" dir=\"ltr\">Some SIM students forced to retake exam due to missing table of statistics <a href=\"http://t.co/hxU6aPVm1Y\">http://t.co/hxU6aPVm1Y</a> <a href=\"https://twitter.com/TODAYonline\">@TODAYonline</a> <a href=\"http://t.co/auRE4YRg1K\">pic.twitter.com/auRE4YRg1K</a></p>&mdash; Channel NewsAsia (@ChannelNewsAsia) <a href=\"https://twitter.com/ChannelNewsAsia/status/599049877088182272\">May 15, 2015</a></blockquote>\n" +
                    "<script async src=\"//platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>",

            "<blockquote class=\"twitter-tweet\" lang=\"en\"><p lang=\"en\" dir=\"ltr\">[TODAY] Structured Internship Programme for polytechnic students launched <a href=\"http://t.co/k8umWOgJYl\">http://t.co/k8umWOgJYl</a></p>&mdash; Singapore News (@SGnews) <a href=\"https://twitter.com/SGnews/status/599048586349928448\">May 15, 2015</a></blockquote>\n" +
                    "<script async src=\"//platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>"
    };

    private static final int MAX_CACHE_SIZE = 10;

    private static LruCache<String, MyWebView> webViewCache = new LruCache<String, MyWebView>(MAX_CACHE_SIZE);

    public static void addWebView(String key, MyWebView webView) {
        webViewCache.put(key, webView);
    }

    public static MyWebView getWebView(String key) {
        MyWebView webView = webViewCache.get(key);
        return webView;
    }

    public static void removeWebView(String key) {
        webViewCache.remove(key);
    }

}
