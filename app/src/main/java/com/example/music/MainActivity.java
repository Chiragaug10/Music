package com.example.music;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.sql.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.ListView);
        Dexter.withContext(this)// It is used to take the permission from the user
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        Toast.makeText(MainActivity.this, "Successfully Granted", Toast.LENGTH_SHORT).show();
                        ArrayList<File> mySong=fetchSong(Environment.getExternalStorageDirectory());
                        String [] songs=new String[mySong.size()];
                        for(int i=0;i<mySong.size();i++)
                        {
                            songs[i]=mySong.get(i).getName().replace(".mp3"," ");
                        }
                        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,songs);
                        listView.setAdapter(arrayAdapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                        {
                            @Override
                            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                                    long arg3)
                            {
                                Intent intent=new Intent(MainActivity.this,NewActivity.class);
                                String currentSong=listView.getItemAtPosition(position).toString();
                                intent.putExtra("song",songs);
                                intent.putExtra("Current Song",currentSong);
                                intent.putExtra("position",position);
                                startActivity(intent);

                            }
                        });

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.cancelPermissionRequest();

                    }
                })
                .withErrorListener(new PermissionRequestErrorListener() {
                    @Override public void onError(DexterError error) {
                        Log.e("Dexter", "There was an error: " + error.toString());
                    }
                }).check();

    }
    public ArrayList<File> fetchSong(File file) // It return all the mp3 files
    {
        ArrayList arrayList=new ArrayList();
        File [] song=file.listFiles();
        if(song!=null)
        {
            for(File each:song)
            {
                if(each.isDirectory())
                {
                    arrayList.addAll(fetchSong(each));
                }
                else
                {
                    if(each.getName().endsWith(".mp3"))
                    {
                        arrayList.add(each);
                    }
                }
            }
        }
        return arrayList;
    }
}