package com.example.builds.javafileexplorer;

import android.os.Bundle;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import android.widget.Toast;
import android.os.Environment;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.app.ActivityCompat;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.content.Intent;
import android.net.*;
import android.content.Context;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends ListActivity  {

    private List<String> item = null;
    private List<String> path = null;
    private String root;
    private TextView myPath;
    public static final int RequestPermissionCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (checkPermission()) {
            setContentView(R.layout.activity_main);

            myPath = (TextView) findViewById(R.id.path);
            root = Environment.getRootDirectory().getParent();
            getDirectory(root);
        }
        else
        {
            requestPermission();
        }
    }

    private void getDirectory(String dirPath)
    {
        myPath.setText("Location: " + dirPath);
        item = new ArrayList<String>();
        path = new ArrayList<String>();
        File f = new File(dirPath);
        File[] files = f.listFiles();

        if(!dirPath.equals(root))
        {
            item.add(root);
            path.add(root);
            item.add("../");
            path.add(f.getParent());
        }

        for(int i=0; i < files.length; i++)
        {
            File file = files[i];

            if(!file.isHidden() && file.canRead()){
                path.add(file.getPath());
                if(file.isDirectory()){
                    item.add(file.getName() + "/");
                }else{
                    item.add(file.getName());
                }
            }
        }
        ArrayAdapter<String> fileList = new ArrayAdapter<String>(this, R.layout.row, item);
        setListAdapter(fileList);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {

        File file = new File(path.get(position));

        if (file.isDirectory())
        {
            if(file.canRead()){
                getDirectory(path.get(position));
            }else{
                Toast.makeText(MainActivity.this, "This location can't be accessed", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            //FileOpen.openFile(getApplicationContext(), file); // for Nougat and Oreo
            FileOpen.openFile(MainActivity.this, file); // for Marshmallow and Pie
        }
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]
                {WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length> 0) {
                    boolean WriteStoragePermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean ReadStoragePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (WriteStoragePermission && ReadStoragePermission) {
                        Toast.makeText(MainActivity.this, "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MainActivity.this,"Permission Denied",Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }
}


