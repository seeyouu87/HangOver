package com.hangover;

import android.content.Context;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by leechunhoe on 7/11/15.
 */
public class Util
{
    /**
     * Send HTTP request
     * @param url Url
     * @param requestMethod GET, POST, PUT, DELETE
     * @param body Body for POST, PUT, DELETE
     * @return { (int) status code, (String) result }
     */
    public static Object[] sendHttpRequest(String url, String requestMethod, Serializable body)
    {
        MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        OkHttpClient client = new OkHttpClient();

        if (body != null)
        {
            String bodyJson = (new Gson()).toJson(body);
            RequestBody requestBody = RequestBody.create(JSON, bodyJson);
            Request.Builder requestBuilder = new Request.Builder()
                    .url(url);

            if (requestMethod == null)
            {
                return null;
            }

            if (requestMethod.equals(Common.REQUEST_METHOD_POST))
            {
                requestBuilder.post(requestBody);
            }
            else if (requestMethod.equals(Common.REQUEST_METHOD_PUT))
            {
                requestBuilder.put(requestBody);
            }
            else if (requestMethod.equals(Common.REQUEST_METHOD_DELETE))
            {
                requestBuilder.delete(requestBody);
            }
            else
            {
                return null;
            }

            Request request = requestBuilder.build();

            try
            {
                Response response = client.newCall(request).execute();

                return new Object[]{ response.code(), response.body().string() };

            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            Request.Builder requestBuilder = new Request.Builder()
                    .url(url);

            if (requestMethod == null)
            {
                return null;
            }

            if (requestMethod.equals(Common.REQUEST_METHOD_GET))
            {
                requestBuilder.get();
            }
            else
            {
                return null;
            }

            Request request = requestBuilder.build();

            try
            {
                Response response = client.newCall(request).execute();

                return new Object[]{ response.code(), response.body().string() };

            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static void setUserId(Context context, int userId)
    {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(Common.SHARED_PREFERENCE_KEY_USER_ID, userId).commit();
    }

    public static int getUserId(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(Common.SHARED_PREFERENCE_KEY_USER_ID, -1);
    }

    public static void setUserName(Context context, String userName)
    {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(Common.SHARED_PREFERENCE_KEY_USER_NAME, userName).commit();
    }

    public static String getUserName(Context context)
    {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(Common.SHARED_PREFERENCE_KEY_USER_NAME, "");
    }
}
