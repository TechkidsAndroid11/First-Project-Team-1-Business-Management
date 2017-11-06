package com.example.haihoang.managemaster.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.haihoang.managemaster.R;
import com.example.haihoang.managemaster.databases.DatabaseHandle;
import com.example.haihoang.managemaster.models.Group;
import com.example.haihoang.managemaster.utils.CircleTransform;
import com.example.haihoang.managemaster.utils.FormatNumber;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Linh Phan on 10/26/2017.
 */

public class ListSalaryAdapter extends BaseExpandableListAdapter{
    private Context context;
    private int resourceGroup;
    private int resourceEmployee;
    private ArrayList<Group> listGroup;

    public ListSalaryAdapter(Context context, int resourceGroup,int resourceEmployee, ArrayList<Group> listGroup) {
        this.context = context;
        this.resourceGroup = resourceGroup;
        this.resourceEmployee = resourceEmployee;
        this.listGroup = listGroup;
    }

    @Override
    public int getGroupCount() {
        return listGroup.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listGroup.get(groupPosition).getListEmployee().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listGroup.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listGroup.get(groupPosition).getListEmployee().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override

    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolderGroup viewHolderGroup = null;

        if(convertView==null)
        {
            viewHolderGroup = new ViewHolderGroup();
            convertView = LayoutInflater.from(context).inflate(resourceGroup,parent,false);
            viewHolderGroup.tvGroupName= convertView.findViewById(R.id.tv_Group);
            viewHolderGroup.tvNumberOfPeople=convertView.findViewById(R.id.tv_numberOfPeople);
//            viewHolderGroup.tvGroupName.setHeight(120);
//            viewHolderGroup.tvGroupName.setTextSize(20);
            viewHolderGroup.ivArrow= convertView.findViewById(R.id.iv_arrow);
            convertView.setTag(viewHolderGroup);
        }
        else
            viewHolderGroup = (ViewHolderGroup) convertView.getTag();
        int numberPerson = DatabaseHandle.getInstance(context).getCountEmployeeInGroup(listGroup.get(groupPosition).getName());
        viewHolderGroup.tvGroupName.setText("Vị trí: "+listGroup.get(groupPosition).getName());
        viewHolderGroup.tvNumberOfPeople.setText("Số nhân viên: "+numberPerson);
        if(isExpanded)
        {
            viewHolderGroup.ivArrow.setImageResource(R.drawable.ic_arrow_drop_up_black_24dp);
        }
        else
            viewHolderGroup.ivArrow.setImageResource(R.drawable.ic_arrow_drop_down_black_24dp);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolderEmployee viewHolder;
        if(convertView==null)
        {
            viewHolder = new ViewHolderEmployee();
            convertView = LayoutInflater.from(context).inflate(resourceEmployee,parent,false);
            viewHolder.ivAvatar = convertView.findViewById(R.id.ivAvatar);
            viewHolder.tvName = convertView.findViewById(R.id.tvName);
            viewHolder.tvDaySalary = convertView.findViewById(R.id.tvSalary);
            viewHolder.tvTotalSalary = convertView.findViewById(R.id.tvMonthSalary);
            convertView.setTag(viewHolder);
        }
        else
            viewHolder = (ViewHolderEmployee) convertView.getTag();

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        viewHolder.tvName.setText("Name: "+listGroup.get(groupPosition).getListEmployee().get(childPosition).getName());
        viewHolder.tvDaySalary.setText("Lương (/ngày): "+ FormatNumber.formatNumber(listGroup.get(groupPosition).getListEmployee().get(childPosition).getDaySalary())+"đ");
        viewHolder.tvTotalSalary.setTextColor(Color.RED);
        viewHolder.tvTotalSalary.setText("Lương tháng " + (cal.get(Calendar.MONTH) + 1) + ": "
                +FormatNumber.formatNumber(listGroup.get(groupPosition).getListEmployee().get(childPosition).getTotalSalary())+"đ");

        String[] base64 = listGroup.get(groupPosition).getListEmployee().get(childPosition).getAvatar().split(",");
        Bitmap bitmap = BitmapFactory.decodeByteArray(
                Base64.decode(base64[0],Base64.DEFAULT),
                0,// offset: vị trí bđ
                (Base64.decode(base64[0],Base64.DEFAULT)).length

        );

        CircleTransform circleTransform = new CircleTransform();
        viewHolder.ivAvatar.setImageBitmap(circleTransform.transform(bitmap));

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    public class ViewHolderGroup
    {

        ImageView ivArrow;
        TextView tvNumberOfPeople;
        TextView tvGroupName;
    }
    public class ViewHolderEmployee
    {
        ImageView ivAvatar;
        TextView tvName;
        TextView tvDaySalary;
        TextView tvTotalSalary;

    }

}
