package com.example.applauncher;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import java.util.List;

public class AppAdapter extends ArrayAdapter<PackageInfo> {
    private Context myContext;
    private int myResource;
    private PackageManager myPackageManager;

    public AppAdapter(@NonNull Context context, int resource, @NonNull List<PackageInfo> objects, PackageManager myPackageManager) {
        super(context, resource, objects);
        this.myContext = context;
        this.myResource = resource;
        this.myResource = resource;
        this.myPackageManager = myPackageManager;
    }

    public class MyViewHolder{
        TextView tvName;
        ImageView imgView;
        public MyViewHolder(View v) {
            tvName = v.findViewById(R.id.AppName);
            imgView = v.findViewById(R.id.AppIcon);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        MyViewHolder holder = null;
        if(convertView == null)
        {
            LayoutInflater inflater = LayoutInflater.from(myContext);
            convertView = inflater.inflate(myResource,parent,false);
            holder = new MyViewHolder(convertView);
            convertView.setTag(holder);
        }
        else
        {
            holder = (MyViewHolder) convertView.getTag();
        }
        PackageInfo packageInfo = getItem(position);
        String appName = myPackageManager.getApplicationLabel(packageInfo.applicationInfo).toString();
        Drawable appIcon = myPackageManager.getApplicationIcon(packageInfo.applicationInfo);
        holder.tvName.setText(appName);
        holder.imgView.setImageDrawable(appIcon);
        return convertView;
    }
}
