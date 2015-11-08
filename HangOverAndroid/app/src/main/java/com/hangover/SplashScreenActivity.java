package com.hangover;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SplashScreenActivity extends AppCompatActivity
{
    private MediaPlayer mp = null;
    private SurfaceView mSurfaceView = null;

    LoginButton loginButton;
    CallbackManager callbackManager;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm/dd/yyyy");

    public static final boolean IS_DEBUG = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

//        Intent intent = new Intent(SplashScreenActivity.this, MoreInfoActivity.class);
//        startActivity(intent);
//        finish();

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_splash_screen);

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_birthday");
        loginButton.setReadPermissions("user_location");

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>()
        {
            @Override
            public void onSuccess(LoginResult loginResult)
            {
                // App code
                getUserInfo(loginResult.getAccessToken());
            }

            @Override
            public void onCancel()
            {
                Toast.makeText(SplashScreenActivity.this, "Login cancelled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception)
            {
                Toast.makeText(SplashScreenActivity.this, "Error occured", Toast.LENGTH_SHORT).show();
            }
        });

        if (AccessToken.getCurrentAccessToken() != null && AccessToken.getCurrentAccessToken().getToken().length() > 0)
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {
            mp = new MediaPlayer();
            mSurfaceView = (SurfaceView) findViewById(R.id.surface);
        }
    }

    /**
     * Get user info
     * @param accessToken Access token
     */
    private void getUserInfo(AccessToken accessToken)
    {
        GraphRequest request = GraphRequest.newMeRequest(
                accessToken,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {

                        Toast.makeText(SplashScreenActivity.this, object.toString(), Toast.LENGTH_LONG).show();

                        String locationName = "";

                        try
                        {
                            locationName = object.getJSONObject("location").getString("name");
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                        catch (NullPointerException e)
                        {
                            e.printStackTrace();
                        }

                        String fbtoken = "";

                        try
                        {
                            fbtoken = object.getString("id");
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

                        String name = "";

                        try
                        {
                            name = object.getString("name");
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

                        String gender = "";

                        try
                        {
                            gender = object.getString("gender");
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

                        String birthday = "";
                        Date dob = null;

                        try
                        {
                            birthday = object.getString("birthday");

                            if (birthday != null)
                            {
                                dob = simpleDateFormat.parse(birthday);
                            }
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                        catch (ParseException e)
                        {
                            e.printStackTrace();
                        }

                        String email = "";

                        try
                        {
                            email = object.getString("email");
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

                        User user = new User();
                        user.setDob(birthday);
                        user.setEmail(email);
                        user.setName(name);
                        user.setGender(gender);
                        user.setFbtoken(fbtoken);
                        user.setAddress(locationName);
                        Util.setUserName(getApplicationContext(), name);

                        new AsyncTask<User, Void, Object[]>()
                        {
                            @Override
                            protected Object[] doInBackground(User... params)
                            {
                                return Util.sendHttpRequest(Common.URL_USERS, Common.REQUEST_METHOD_POST, params[0]);
                            }

                            @Override
                            protected void onPostExecute(Object[] results)
                            {
                                super.onPostExecute(results);

                                if (results != null && results.length > 1)
                                {
                                    int statusCode = (int) results[0];
                                    String result = (String) results[1];

                                    if (statusCode == HttpURLConnection.HTTP_CREATED)
                                    {
                                        Gson gson = new Gson();
                                        User user = (User) gson.fromJson(result, User.class);
                                        Util.setUserId(getApplicationContext(), user.getId());

                                        if (!IS_DEBUG)
                                        {
                                            Toast.makeText(SplashScreenActivity.this, "Register success!", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(SplashScreenActivity.this, MoreInfoActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                    else if (statusCode == HttpURLConnection.HTTP_OK)
                                    {
                                        Gson gson = new Gson();
                                        User user = (User) gson.fromJson(result, User.class);
                                        Util.setUserId(getApplicationContext(), user.getId());

                                        if (!IS_DEBUG)
                                        {
                                            Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                    else
                                    {
                                        Toast.makeText(SplashScreenActivity.this, "Register failed", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else
                                {
                                    Toast.makeText(SplashScreenActivity.this, "Register failed also", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }.execute(user);
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,birthday,location");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
