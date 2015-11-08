package com.hangover;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.net.HttpURLConnection;

public class SetLocationActivity extends FragmentActivity implements OnMapReadyCallback
{
    private GoogleMap mMap;
    private User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        LatLng singapore = new LatLng(1.3, 103.8);
//      mMap.addMarker(new MarkerOptions().position(singapore).title("Marker in Singapore"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(singapore, 11));

        initializeUser();

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener()
        {
            @Override
            public void onMapClick(LatLng latLng)
            {
                if (user != null)
                {
                    user.setAddress("Blk123-11-5, Ang Mo Kio Ave 10, Singapore 121212");
                    user.setGeocode("1.3711938,103.8361509");

                    updateUser();
                }
                else
                {
                    Toast.makeText(SetLocationActivity.this, "User is null, try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initializeUser()
    {
        new AsyncTask<Void, Void, Object[]>()
        {
            @Override
            protected Object[] doInBackground(Void... params)
            {
                int userId = Util.getUserId(getApplicationContext());

                return Util.sendHttpRequest(Common.URL_USERS + userId, Common.REQUEST_METHOD_GET, null);
            }

            @Override
            protected void onPostExecute(Object[] objects)
            {
                super.onPostExecute(objects);
                if (objects != null && objects.length > 1)
                {
                    Gson gson = new Gson();
                    String userJson = (String) objects[1];
                    user = gson.fromJson(userJson, User.class);
                }
            }
        }.execute();
    }

    private void updateUser()
    {
        new AsyncTask<Void, Void, Object[]>()
        {
            @Override
            protected Object[] doInBackground(Void... params)
            {
                return Util.sendHttpRequest(Common.URL_USERS, Common.REQUEST_METHOD_PUT, user);
            }

            @Override
            protected void onPostExecute(Object[] objects)
            {
                super.onPostExecute(objects);
                if (objects != null && objects.length > 1)
                {
                    int statusCode = (int) objects[0];

                    if (statusCode == HttpURLConnection.HTTP_NO_CONTENT)
                    {
                        Intent intent = new Intent(SetLocationActivity.this, MoreInfoActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(SetLocationActivity.this, "Update failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }.execute();
    }
}