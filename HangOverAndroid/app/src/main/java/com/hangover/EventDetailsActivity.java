package com.hangover;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.ad.intromi.Profile;
import com.ad.intromi.Register;
import com.ad.intromi.ServiceManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class EventDetailsActivity extends AppCompatActivity
{
    ListView list;
    EventDetailsAdapter adapter;

    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        list = (ListView) findViewById(android.R.id.list);

        final int eventId = getIntent().getIntExtra(Common.KEY_EVENT_ID, -1);

        new AsyncTask<Void, Void, Object[]>()
        {
            @Override
            protected Object[] doInBackground(Void... params)
            {
                return Util.sendHttpRequest(Common.URL_EVENTS + eventId, Common.REQUEST_METHOD_GET, null);
            }

            @Override
            protected void onPostExecute(Object[] objects)
            {
                super.onPostExecute(objects);

                Gson gson = new Gson();
                event = gson.fromJson((String) objects[1], Event.class);
                getUsers();
            }
        }.execute();

        Register register = Register.getInstance();

        String myName = Util.getUserName(getApplicationContext());
        int myUserId = Util.getUserId(getApplicationContext());

        register.doRegistration(getApplicationContext(), "userId", myName + ";" + myUserId);

        BroadcastReceiver mMessageReceiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                if (ServiceManager.MESSAGE.equals(intent.getAction())){
                    Profile p = new Profile();
                    Bundle data = new Bundle();
                    data = intent.getExtras();
                    p = data.getParcelable("Profile");

                    String name = p.getName();

                    if (name != null && name.contains(";"))
                    {
                        String userIdString = name.substring(name.indexOf(";") + 1, name.length());
                        String userName = name.substring(0, name.indexOf(";"));

                        try
                        {
                            int userId = Integer.parseInt(userIdString);
                            User user = new User();
                            user.setId(userId);
                            user.setName(userName);

                            int position = adapter.getPosition(user);

                            ((User) adapter.getItem(position)).setName("haha");

//                            adapter.insert(user, 0);

                            // adding to the UI have to happen in UI thread
                            runOnUiThread(new Runnable()
                            {
                                @Override
                                public void run()
                                {
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        }
                        catch(Exception e)
                        {

                        }
                    }
                }
            }
        };

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter(ServiceManager.ERRORS));
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter(ServiceManager.MESSAGE));
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter(ServiceManager.BT_DISCOVERY_FINISHED));

        ServiceManager serviceManager = ServiceManager.getInstance(getApplicationContext());
        serviceManager.startManualScan();
    }

    private void getUsers()
    {
        new AsyncTask<Void, Void, Object[]>()
        {
            @Override
            protected Object[] doInBackground(Void... params)
            {
                return Util.sendHttpRequest(Common.URL_USERS, Common.REQUEST_METHOD_GET, null);
            }

            @Override
            protected void onPostExecute(Object[] objects)
            {
                super.onPostExecute(objects);

                String result = (String) objects[1];

                Gson gson = new Gson();
                Type type = new TypeToken<List<User>>(){}.getType();
                List<Object> users = gson.fromJson(result, type);

                users.add(0, event);

                adapter = new EventDetailsAdapter(EventDetailsActivity.this, users);

                list.setAdapter(adapter);


            }
        }.execute();
    }
}
