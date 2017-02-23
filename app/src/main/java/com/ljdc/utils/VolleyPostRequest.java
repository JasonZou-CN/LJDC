package com.ljdc.utils;

import android.content.Context;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ljdc.app.Config;

import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * User:邹旭
 * Date:2017/2/23 0023
 * Time:下午 7:38
 * Desc:略
 */
public class VolleyPostRequest {
    public String response;//转码的返回值
    private Context ctx;

    public VolleyPostRequest(Context context) {
        ctx = context;
    }

    public void postRequest(Map<String, String> parms, String url, Response.Listener<String> listener) {

        RequestQueue mQueue = Volley.newRequestQueue(ctx);
        PostStringRequest request = new PostStringRequest(parms, url, listener, null);
        mQueue.add(request);
    }

    public class PostStringRequest extends StringRequest {
        Map<String, String> parms;
        private String response;

        /**
         * Creates a new request with the given method.
         *
         * @param url           URL to fetch the string at
         * @param listener      Listener to receive the String response
         * @param errorListener Error listener, or null to ignore errors
         */
        public PostStringRequest(Map<String, String> parms, String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
            super(Method.POST, url, listener, errorListener);
            this.parms = parms;
        }

        /**
         * Returns a Map of parameters to be used for a POST or PUT request.  Can throw
         * {@link AuthFailureError} as authentication may be required to provide these values.
         * <p>
         * <p>Note that you can directly override {@link #getBody()} for custom data.</p>
         *
         * @throws AuthFailureError in the event of auth failure
         */
        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            return parms;
        }
    }
}
