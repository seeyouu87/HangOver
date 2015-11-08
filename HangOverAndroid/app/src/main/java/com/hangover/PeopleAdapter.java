package com.hangover;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by leechunhoe on 8/11/15.
 */
public class PeopleAdapter extends ArrayAdapter<User>
{
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    public PeopleAdapter(Context context, List<User> users)
    {
        super(context, R.layout.lv_item_event, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.lv_item_event, parent, false);

        final User user = getItem(position);

        TextView tvEventTitle = (TextView) view.findViewById(R.id.tv_event_title);
        TextView tvEventDescription = (TextView) view.findViewById(R.id.tv_event_description);

        Button btnJoin = (Button) view.findViewById(R.id.btn_join);



        tvEventTitle.setText(user.getName());



        return view;
    }
}
