package com.hangover;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment
{
    private User user;

    TextView tvName;
    TextView tvEmail;
    TextView tvPhone;
    TextView tvAddress;
    TextView tvEmergencyContactPerson;
    TextView tvEmergencyContactPhone;
    ImageView ivProfilePhoto;
    Button btnLinkToUber;

    public ProfileFragment()
    {
        // Required empty public constructor


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {



        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);


        ivProfilePhoto = (ImageView) rootView.findViewById(R.id.iv_profile_photo);
        tvName = (TextView) rootView.findViewById(R.id.tv_name);
        tvEmail = (TextView) rootView.findViewById(R.id.tv_email);
        tvPhone = (TextView) rootView.findViewById(R.id.tv_phone);
        tvAddress = (TextView) rootView.findViewById(R.id.tv_address);
        tvEmergencyContactPerson = (TextView) rootView.findViewById(R.id.tv_emergency_contact_person);
        tvEmergencyContactPhone = (TextView) rootView.findViewById(R.id.tv_emergency_contact_phone);
        btnLinkToUber = (Button) rootView.findViewById(R.id.btn_link_to_uber);

        btnLinkToUber.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String uberUrl = "https://login.uber.com/oauth/authorize?response_type=code&client_id=gftPPgvMUH1qPdA_c3jEmRYoM-qpBi7t";

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uberUrl));
                startActivity(browserIntent);
            }
        });

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        initializeUser();
    }

    private void initializeUser()
    {
        new AsyncTask<Void, Void, Object[]>()
        {
            @Override
            protected Object[] doInBackground(Void... params)
            {
                int userId = Util.getUserId(getActivity().getApplicationContext());

                return Util.sendHttpRequest(Common.URL_USERS + 2, Common.REQUEST_METHOD_GET, null);
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

                    tvName.setText(user.getName());
                    tvEmail.setText(user.getEmail());
                    tvPhone.setText(user.getPhone());
                    tvAddress.setText(user.getAddress());
                    tvEmergencyContactPerson.setText(user.getEmgrelationship());
                    tvEmergencyContactPhone.setText(user.getEmgcontact());

                    if (user.getImgUrl() != null)
                    {
                        Picasso.with(getActivity()).load(Uri.parse(user.getImgUrl())).into(ivProfilePhoto);
                    }

                }
            }
        }.execute();
    }
}
