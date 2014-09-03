package com.slygon.mytodoapp;

import com.loopj.android.http.*;

/**
 * Created by slygon on 03/09/14.
 */
public class TodoClient
{
    private static final String BASE_URL = "http://elad-site.herokuapp.com/";;

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl)
    {
        return BASE_URL + relativeUrl;
    }

}