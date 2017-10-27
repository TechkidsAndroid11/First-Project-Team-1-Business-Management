package com.example.haihoang.managemaster.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.haihoang.managemaster.R;
import com.example.haihoang.managemaster.databases.DatabaseHandle;
import com.example.haihoang.managemaster.models.EmployeeModel;
import com.example.haihoang.managemaster.models.Group;

import java.util.ArrayList;

/**
 * Created by Linh Phan on 10/25/2017.
 */

public class ListEmployeeAdapter extends BaseAdapter implements Filterable {
    boolean checkStatus=true;
    private Context context;
    private int resource;
    private ArrayList<EmployeeModel> listEmployee;

    CustomFilter filter;
    ArrayList<EmployeeModel> filterList;
    public ListEmployeeAdapter(Context context, int resource, ArrayList<EmployeeModel> listEmployee) {
        this.context = context;
        this.resource = resource;
        this.listEmployee = listEmployee;
        this.filterList=listEmployee;
    }

    @Override
    public int getCount() {
        return listEmployee.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView==null)
        {
            viewHolder = new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(resource,parent,false);
            viewHolder.tvName=convertView.findViewById(R.id.tvName);
            viewHolder.tvDate=convertView.findViewById(R.id.tvDOB);
            viewHolder.tvStartTime=convertView.findViewById(R.id.tvStartTime);
            viewHolder.tvSalary=convertView.findViewById(R.id.tvSalary);
            viewHolder.tvStatus=convertView.findViewById(R.id.btnStatus);
            viewHolder.ivAvatar = convertView.findViewById(R.id.ivAvatar);
            convertView.setTag(viewHolder);
        }
        else
            viewHolder= (ViewHolder) convertView.getTag();

        viewHolder.tvName.setText("Name: "+listEmployee.get(position).getName());
        viewHolder.tvDate.setText("Date of birth: "+listEmployee.get(position).getDate());
        viewHolder.tvStartTime.setText("Start Time: "+listEmployee.get(position).getFirstDayWork());
        viewHolder.tvSalary.setText("Salary (/day): "+listEmployee.get(position).getDaySalary());

        if(listEmployee.get(position).getStatus()==1)
        {
            viewHolder.tvStatus.setText("Present");
            viewHolder.tvStatus.setBackground(context.getResources().getDrawable(R.drawable.custom_button_present));
        }
        else
        {
            viewHolder.tvStatus.setText("Absent");
            viewHolder.tvStatus.setBackground(context.getResources().getDrawable(R.drawable.custom_button_absent));
        }

        String[] base64 = listEmployee.get(position).getAvatar().split(",");
        Bitmap bitmap = BitmapFactory.decodeByteArray(
                Base64.decode(base64[0],Base64.DEFAULT),
                0,// offset: vị trí bđ
                (Base64.decode(base64[0],Base64.DEFAULT)).length

        );

        viewHolder.ivAvatar.setImageBitmap(bitmap);

        getCheckStatus(position);
        viewHolder.tvStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatabaseHandle handle = DatabaseHandle.getInstance(context);
                if(!checkStatus)
                {
                    viewHolder.tvStatus.setText("Present");
                    viewHolder.tvStatus.setBackground(context.getResources().getDrawable(R.drawable.custom_button_present));
                    handle.updateStatus(listEmployee.get(position));

                   // handle.addSalarytoTotal(listEmployee.get(position));
                    checkStatus=true;
                }
                else
                {
                    viewHolder.tvStatus.setText("Absent");
                    viewHolder.tvStatus.setBackground(context.getResources().getDrawable(R.drawable.custom_button_absent));
                    handle.updateStatus(listEmployee.get(position));
                   // handle.minusSalarytoTotal(listEmployee.get(position));
                    checkStatus=false;
                }


            }
        });
        return convertView;
    }
    public void getCheckStatus(int position)
    {
        if(listEmployee.get(position).getStatus()==1)
        {
            checkStatus=true;

        }
        else
        {
            checkStatus=false;
        }
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
                ArrayList<EmployeeModel> filters=new ArrayList<EmployeeModel>();
                //get specific items
                for(int i=0;i<filterList.size();i++)
                {
                    if(filterList.get(i).getName().toUpperCase().contains(constraint))
                    {
                        EmployeeModel p=new EmployeeModel(filterList.get(i).getName(),filterList.get(i).getDate(),filterList.get(i).getFirstDayWork(),
                                filterList.get(i).getAvatar(),filterList.get(i).getDaySalary(),filterList.get(i).getStatus());
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
            listEmployee=(ArrayList<EmployeeModel>) results.values;
            notifyDataSetChanged();
        }
    }
    public class ViewHolder
    {
        private ImageView ivAvatar;
        private TextView tvName;
        private TextView tvDate;
        private TextView tvStartTime;
        private TextView tvSalary;
        private TextView tvStatus;

    }
}
