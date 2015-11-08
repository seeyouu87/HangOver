package com.hangover;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by leechunhoe on 8/11/15.
 */
public class EventDetailsAdapter extends ArrayAdapter<Object>
{
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public EventDetailsAdapter(Context context, List<Object> objects)
    {
        super(context, R.layout.lv_item_event, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = null;

        if (getItem(position) instanceof User)
        {
            view = LayoutInflater.from(getContext()).inflate(R.layout.lv_item_people, parent, false);
            User user = (User) getItem(position);
            ImageView ivProfilePhoto = (ImageView) view.findViewById(R.id.iv_profile_photo);
            TextView tvName = (TextView) view.findViewById(R.id.tv_name);
            TextView tvStatus = (TextView) view.findViewById(R.id.tv_status);
            Button btnSendHome = (Button) view.findViewById(R.id.btn_send_home);
            Button btnSendHospital = (Button) view.findViewById(R.id.btn_send_hospital);
            tvName.setText(user.getName());
//            tvStatus.setText(user.getGender());


            if (position == 2 || position == 3 || position == 5)
            {
                tvStatus.setText("Present");
                btnSendHome.setVisibility(View.VISIBLE);
                btnSendHospital.setVisibility(View.VISIBLE);
            }
            else
            {
                tvStatus.setText("Not around");
                btnSendHome.setVisibility(View.INVISIBLE);
                btnSendHospital.setVisibility(View.INVISIBLE);
            }

            if (user.getImgUrl() != null)
            {
                Picasso.with(getContext()).load(Uri.parse(user.getImgUrl())).into(ivProfilePhoto);
            }
            else
            {
                Picasso.with(getContext()).load(R.mipmap.ic_launcher).into(ivProfilePhoto);
            }


            btnSendHome.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    new Thread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            String smsUrl = "http://hangoverthehub.azurewebsites.net/general/sendhome";
                            Util.sendHttpRequest(smsUrl, Common.REQUEST_METHOD_GET, null);
                            Intent intent = new Intent(getContext(), UberMapMockupActivity.class);
                            getContext().startActivity(intent);
                        }
                    }).start();
                }
            });

            btnSendHospital.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    new Thread(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            String smsUrl = "http://hangoverthehub.azurewebsites.net/general/sendhospital";
                            Util.sendHttpRequest(smsUrl, Common.REQUEST_METHOD_GET, null);
                            Intent intent = new Intent(getContext(), UberMapMockupActivity.class);
                            getContext().startActivity(intent);
                        }
                    }).start();
                }
            });
        }
        else if (getItem(position) instanceof Event)
        {
            view = LayoutInflater.from(getContext()).inflate(R.layout.lv_item_event_large, parent, false);
            final Event event = (Event) getItem(position);

            TextView tvEventTitle = (TextView) view.findViewById(R.id.tv_event_title);
            TextView tvEventDescription = (TextView) view.findViewById(R.id.tv_event_description);
            TextView tvEventDate = (TextView) view.findViewById(R.id.tv_event_date);
            TextView tvEventLocation = (TextView) view.findViewById(R.id.tv_event_location);

            Button btnJoin = (Button) view.findViewById(R.id.btn_join);

            tvEventTitle.setText(event.getName());
            tvEventDescription.setText(event.getDescription());
            tvEventDate.setText(event.getDate());
            tvEventLocation.setText(event.getAddress());

            try
            {
                String eventDateString = event.getDate().replace("T", " ");
                Date eventDate = format.parse(eventDateString);
                Date today = Calendar.getInstance().getTime();

                if (today.before(eventDate))
                {
                    btnJoin.setVisibility(View.VISIBLE);
                }
                else
                {
                    btnJoin.setVisibility(View.INVISIBLE);
                }

            } catch (ParseException e)
            {
                e.printStackTrace();
            }

            if (event.getEventprice() > 0)
            {
                btnJoin.setText("JOIN\n" + event.getEventprice() + "SGD");
            }
            else
            {
                btnJoin.setText("JOIN\nFREE");
            }
        }

        return view;
    }
}
