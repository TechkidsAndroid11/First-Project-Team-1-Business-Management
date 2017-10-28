package com.example.haihoang.managemaster.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.haihoang.managemaster.R;
import com.example.haihoang.managemaster.models.EmployeeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Linh Phan on 10/26/2017.
 */

public class ListSalaryAdapter extends ArrayAdapter<EmployeeModel> {
    private Context context;
    private int resource;
    private ArrayList<EmployeeModel> listEmployee;
    public ListSalaryAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<EmployeeModel> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.listEmployee=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null)
        {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(resource,parent,false);
            viewHolder.ivAvatar = convertView.findViewById(R.id.ivAvatar);
            viewHolder.tvName = convertView.findViewById(R.id.tvName);
            viewHolder.tvPreviousMonthSalary= convertView.findViewById(R.id.tvPreviousMonthSalary);
            viewHolder.tvDaySalary = convertView.findViewById(R.id.tvSalary);
            viewHolder.tvTotalSalary = convertView.findViewById(R.id.tvMonthSalary);
            convertView.setTag(convertView);
        }
        else
            viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.tvName.setText("Name: "+listEmployee.get(position).getName());
        viewHolder.tvPreviousMonthSalary.setText("Previous Month Salary: "+listEmployee.get(position).getPreviousSalary());
        viewHolder.tvDaySalary.setText("Salary (/day): "+listEmployee.get(position).getDaySalary());
        viewHolder.tvTotalSalary.setTextColor(Color.RED);
        viewHolder.tvTotalSalary.setText("Month Salary: "+listEmployee.get(position).getTotalSalary());

        String[] base64 = listEmployee.get(position).getAvatar().split(",");
        Bitmap bitmap = BitmapFactory.decodeByteArray(
                Base64.decode(base64[0],Base64.DEFAULT),
                0,// offset: vị trí bđ
                (Base64.decode(base64[0],Base64.DEFAULT)).length

        );

        viewHolder.ivAvatar.setImageBitmap(bitmap);
        return convertView;
    }
    public class ViewHolder
    {
        ImageView ivAvatar;
        TextView tvName;
        TextView tvPreviousMonthSalary;
        TextView tvDaySalary;
        TextView tvTotalSalary;

    }
}
