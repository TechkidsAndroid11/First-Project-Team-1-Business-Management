package com.example.haihoang.managemaster.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.haihoang.managemaster.R;
import com.example.haihoang.managemaster.activities.ListEmployeeActivity;
import com.example.haihoang.managemaster.models.Group;

import java.util.ArrayList;

/**
 * Created by Linh Phan on 10/25/2017.
 */

public class ListGroupAdapter extends BaseAdapter implements Filterable {
    public static final String NAME_GROUP="namegroup";
    private static final String TAG = "ListGroupAdapter";
    private Context context;
    private int resoure;
    private ArrayList<Group> listGroup;

    CustomFilter filter;
    ArrayList<Group> filterList;

    public ListGroupAdapter(Context context, int resource,ArrayList<Group> listGroup)
    {
        this.context=context;
        this.resoure=resource;

        this.listGroup=listGroup;
        this.filterList=listGroup;
    }
    @Override
    public int getCount() {
        Log.d(TAG, "getCount: "+listGroup.size());
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
            convertView= LayoutInflater.from(context).inflate(resoure,parent,false);
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
        viewHolder.tvNumOfPerson.setText("Số nhân viên: "+ listGroup.get(position).getNumberOfPerson());


        return convertView;
    }
    @Override
    public Filter getFilter() {
        // TODO Auto-generated method stub
        if(filter == null)
        {
            filter=new CustomFilter();
        }
        return filter;
    }
    //INNER CLASS
    class CustomFilter extends Filter
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            // TODO Auto-generated method stub
            FilterResults results=new FilterResults();
            if(constraint != null && constraint.length()>0)
            {
                //CONSTARINT TO UPPER
                constraint=constraint.toString().toUpperCase();
                ArrayList<Group> filters=new ArrayList<Group>();
                //get specific items
                for(int i=0;i<filterList.size();i++)
                {
                    if(filterList.get(i).getName().toUpperCase().contains(constraint))
                    {
                        Group p=new Group(filterList.get(i).getName(),filterList.get(i).getNumberOfPerson());
                        filters.add(p);

                    }
                }

                results.count=filters.size();
                results.values=filters;
            }else
            {
                results.count=filterList.size();
                results.values=filterList;
            }
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            // TODO Auto-generated method stub
            listGroup=(ArrayList<Group>) results.values;
            notifyDataSetChanged();
        }
    }
    public class ViewHolder
    {
        TextView tvGroup;
        TextView tvNumOfPerson;
        ImageView ivMoveListEmploee;
    }

}
