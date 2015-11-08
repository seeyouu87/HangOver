package com.hangover;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by leechunhoe on 8/11/15.
 */
public class EventAdapter extends ArrayAdapter<Event>
{
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public EventAdapter(Context context, List<Event> events)
    {
        super(context, R.layout.lv_item_event, events);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.lv_item_event, parent, false);

        final Event event = getItem(position);

        TextView tvEventTitle = (TextView) view.findViewById(R.id.tv_event_title);
        TextView tvEventDescription = (TextView) view.findViewById(R.id.tv_event_description);

        Button btnJoin = (Button) view.findViewById(R.id.btn_join);



        tvEventTitle.setText(event.getName());
//        tvEventDescription.setText(event.getDescription());

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

        tvEventDescription.setText(event.getDate());

        if (event.getEventprice() > 0)
        {
            btnJoin.setText("JOIN\n" + event.getEventprice() + "SGD");
        }
        else
        {
            btnJoin.setText("JOIN\nFREE");
        }

        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getContext(), EventDetailsActivity.class);
                intent.putExtra(Common.KEY_EVENT_ID, event.getId());
                getContext().startActivity(intent);
            }
        });

        return view;
    }
}
