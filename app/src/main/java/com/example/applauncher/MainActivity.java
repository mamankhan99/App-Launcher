package com.example.applauncher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    List<PackageInfo> appList;
    List<PackageInfo> installedApps;
    ListView listView;
    PackageManager packageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.listView);
        packageManager = getPackageManager();
        appList = packageManager.getInstalledPackages(0);
        installedApps = new ArrayList<>();
        getInstalledApps(appList,installedApps);
        AppAdapter appAdapter = new AppAdapter(this,R.layout.app_list_item,installedApps,packageManager);
        listView.setAdapter(appAdapter);
        registerForContextMenu(listView);
    }

    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }

    private void getInstalledApps(List<PackageInfo> appList,List<PackageInfo> installedAppList) {
        for(PackageInfo pi: appList)
        {
            if(!isSystemPackage(pi))
                installedAppList.add(pi);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        menu.setHeaderTitle("Options");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.appOpen:
                PackageInfo pkgInfo = installedApps.get(info.position);
                Intent launchIntent = packageManager.getLaunchIntentForPackage(pkgInfo.packageName);
                if (launchIntent != null) {
                    startActivity(launchIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "Can't open application", Toast.LENGTH_LONG).show();
                }
                return true;
            case R.id.closeMenu:
                Toast.makeText(this,"Menu Closed",Toast.LENGTH_SHORT).show();
                return true;
        }
        return false;
    }
}
