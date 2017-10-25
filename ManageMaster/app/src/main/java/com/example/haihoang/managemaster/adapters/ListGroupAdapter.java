package com.example.haihoang.managemaster.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.haihoang.managemaster.R;
import com.example.haihoang.managemaster.activities.ListEmployeeActivity;
import com.example.haihoang.managemaster.models.Group;

import java.util.ArrayList;

/**
 * Created by Linh Phan on 10/25/2017.
 */

public class ListGroupAdapter extends BaseAdapter {
    public static final String NAME_GROUP="namegroup";
    private static final String TAG = "ListGroupAdapter";
    private Context contex;
    private int resoure;
    private ArrayList<Group> listGroup;
    public ListGroupAdapter(Context context, int resource,ArrayList<Group> listGroup)
    {
        this.contex=context;
        this.resoure=resource;
        this.listGroup=listGroup;
    }
    @Override
    public int getCount() {

        return listGroup.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null)
        {
            viewHolder = new ViewHolder();
            convertView= LayoutInflater.from(contex).inflate(resoure,parent,false);
            viewHolder.tvGroup = convertView.findViewById(R.id.tv_Group);
            viewHolder.tvNumOfPerson = convertView.findViewById(R.id.tv_numberOfPeople);
            viewHolder.ivMoveListEmploee = convertView.findViewById(R.id.iv_moveListEmployee);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvGroup.setText("Vị trí: "+ listGroup.get(position).getName());
        viewHolder.tvNumOfPerson.setText("Số: "+ listGroup.get(position).getNumberOfPerson());
        viewHolder.ivMoveListEmploee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addListener(listGroup.get(position).getName());
            }
        });

        return convertView;
    }
    public void addListener(String nameGroup)
    {
        Intent intent = new Intent(contex, ListEmployeeActivity.class);
        intent.putExtra(NAME_GROUP,nameGroup);
        contex.startActivity(intent);
    }
    public class ViewHolder
    {
        TextView tvGroup;
        TextView tvNumOfPerson;
        ImageView ivMoveListEmploee;
    }
}
