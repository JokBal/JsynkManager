package com.pionworks.movitles.common;

import android.util.Log;
import com.pionworks.movitles.vo.User;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionState;
import com.pusher.client.connection.ConnectionStateChange;
import com.pusher.client.util.HttpAuthorizer;

import java.util.HashMap;

public class JSynkManager implements ConnectionEventListener {
    private final String HOST = "j4.link-to-rink.com";
    private final int PORT = 8000;

    private Pusher mPusher = null;
    private User mUser = null;

    private String mGcmKey = "test";

    private static JSynkManager jSynkManager = null;

    public JSynkManager()
    {
    }

    public static JSynkManager getInstance()
    {
        if(jSynkManager == null)
        {
            synchronized (JSynkManager.class)
            {
                if(jSynkManager == null)
                {
                    jSynkManager = new JSynkManager();
                }
            }
        }
        return jSynkManager;
    }

    public void connect()
    {
        PusherOptions pusherOptions = new PusherOptions().setHost(HOST).setWsPort(PORT);
        pusherOptions.setEncrypted(false);
        pusherOptions.setAuthorizer(getHttpAuthorizer());
        mPusher = new Pusher("8817c5eeccfb1ea2d1c6", pusherOptions);
        if(mPusher.getConnection().getState() == ConnectionState.DISCONNECTED)
        {
            mPusher.connect(this);
        }
    }

    public Pusher getPusher() {
        return mPusher;
    }

    public void setPusher(Pusher mPusher) {
        this.mPusher = mPusher;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User mUser) {
        this.mUser = mUser;
    }

    public String getGcmKey() {
        return mGcmKey;
    }

    public void setGcmKey(String mGcmKey) {
        this.mGcmKey = mGcmKey;
    }

    private HttpAuthorizer getHttpAuthorizer()
    {
        HttpAuthorizer authorizer = new HttpAuthorizer("http://"+ HOST + "/pushertok/pusher_auth.php");
        HashMap<String, String> userMap = new HashMap<String, String>();
        userMap.put("user_id", mUser.getId() + "");
        userMap.put("user_token", mUser.getToken());
        userMap.put("mobile_key", mGcmKey);
        authorizer.setQueryStringParameters(userMap);
        return authorizer;
    }

    @Override
    public void onConnectionStateChange(ConnectionStateChange connectionStateChange) {
        String msg = String.format("Connection state changed from [%s] to [%s]", connectionStateChange.getPreviousState(), connectionStateChange.getCurrentState());
        Log.d("jsynk", msg);

        if(connectionStateChange.getCurrentState() == ConnectionState.DISCONNECTED)
        {
            mPusher.disconnect();
        }
    }

    @Override
    public void onError(String message, String code, Exception e) {
        String msg = String.format("Connection error: [%s] [%s] [%s]", message, code, e);
        Log.d("jsynk", msg);
    }

}

