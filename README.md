#JsynkManager
* This Library is help to make android or java messaging apllication.

##Make Chat

* Make Chat UI.
  
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
                android:layout_width="fill_parent"
                android:layout_height="fill_parent"
                android:orientation="vertical"
                android:background="#fff">

    <FrameLayout
            android:id="@+id/actionBar"
            android:layout_width="match_parent"
            android:layout_height="48dp"
            android:layout_alignParentTop="true"
            android:background="@drawable/actionbar_bg" >

        <LinearLayout android:layout_width="wrap_content" android:layout_height="match_parent"
                      android:layout_alignParentLeft="true" android:id="@+id/linearLayout">
            <ImageButton android:id="@+id/unsubImageButton" android:layout_width="48dp" android:layout_height="48dp"
                         android:background="@drawable/action_bar_bg_selector"
                         android:src="@drawable/todaymovie_history_icon_back"/>
            <LinearLayout android:id="@+id/actionBarDivderLayout2" android:layout_width="0.5dp"
                          android:layout_height="match_parent" android:layout_marginBottom="2dp"
                          android:background="@drawable/actionbar_stroke_v" android:orientation="vertical"/>
        </LinearLayout>
        <TextView
                android:id="@+id/subjectTextView"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_gravity="center"
                android:text="채팅"
                android:textColor="#ffffff"
                android:textSize="22sp"
                android:textStyle="bold" />
    </FrameLayout>

    <ListView
            android:id="@+id/messageListView"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:divider="#777"
            android:dividerHeight="0px"
            android:layout_below="@+id/actionBar"
            android:layout_above="@+id/formRelativeLayout">
    </ListView>
    <RelativeLayout
            android:id="@+id/formRelativeLayout"
            android:layout_width="fill_parent"
            android:layout_height="wrap_content"
            android:layout_alignParentBottom="true"
            android:layout_alignParentLeft="true"
            android:orientation="vertical" >


        <FrameLayout
                android:layout_width="match_parent"
                android:layout_height="2dp"
                android:background="#32649B">
        </FrameLayout>
        <EditText
                android:id="@+id/messageEditText"
                android:layout_width="245dp"
                android:layout_height="wrap_content"
                android:layout_alignParentLeft="true"
                android:inputType="text"/>
        <Button
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="전송"
                android:id="@+id/sendButton" android:layout_alignParentRight="true"/>
    </RelativeLayout>

</RelativeLayout>



* Make Channel

.. code-block::

    private static JSynkManager mJSynkManager = null;
    
    private PresenceChannel mPresenceChannel;
    
    public void onCreate(Bundle savedInstanceState) {
        mJSynkManager = JSynkManager.getInstance();
        mJSynkManager.setUser(UserInfo.getUserInfo(getActivity()));
        mJSynkManager.connect();
        mPresenceChannel = mJSynkManager.getPusher().subscribePresence(CHANNELNAME, this, "client-chat-message");
    }

    @Override
    public void onEvent(String channelName, String eventName, String data) {
        String msg = String.format("Event received: [%s] [%s] [%s]", channelName, eventName, data);
        Log.d("jsynk", msg);
        new ApplyEventTask().execute(data);
    }
    
    private void trigger(String data)
    {
      if(mJSynkManager.getPusher().getConnection().getState() == ConnectionState.CONNECTED)
      {
          mPresenceChannel.trigger("client-chat-message", data);
      }
    }
    
    class ApplyEventTask extends AsyncTask<String, Void, Message> {

      @Override
      protected Message doInBackground(String... params) {
          String data = params[0];
          
          TODO
          processing event data.
      }
      
      @Override
      protected void onPostExecute(Message message) {
          
          TODO
          Apply Data in UI Thread.
      }
    }
    
* GCM Connect

.. code-block::

    private void initializeGCM()
    {
        GCMRegistrar.checkDevice(this);
        GCMRegistrar.checkManifest(this);
        registerToken();
    }

    private void registerToken()
    {
        // registration ID
        final String regId = GCMRegistrar.getRegistrationId(this);
        if (regId.equals(""))
        {
            GCMRegistrar.register(this, "612122059883");
        }
        else
        {
            jSynkManager.setGcmKey(regId);
        }
    }

