package com.example.Contacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.R;

import java.util.List;
/**
 * Created by Administrator on 2018/3/18.
 */

public class GroupListAdapter extends ArrayAdapter<Group> {
    private int resourceId;
    public GroupListAdapter(Context context, int textViewResourceId, List<Group> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Group group = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.GroupImage = (ImageView) view.findViewById (R.id.TX_image);
            viewHolder.GroupName = (TextView) view.findViewById (R.id.username);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.GroupImage.setImageResource(group.getImageId());
        viewHolder.GroupName.setText(group.getName());
        return view;
    }

    class ViewHolder {
        ImageView GroupImage;
        TextView GroupName;
    }

}

