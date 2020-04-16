package com.example.ourfirstapp;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.app.Fragment;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private String TAG="MAIN_ACTIVITY";
    TextView mTextView;
    private Fragment mFragmentOne;
    private Fragment mFragmentTwo;
    private List<Map<String,Object>> mData;
    private SimpleAdapter mSimpleleAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG,"ONCREATE");
    }


    @Override

    protected void onStart() {
        super.onStart();
        Log.d(TAG,"ONSTART");
    }

    @Override
    protected void onResume() {
        super.onResume();
        ListView lv=findViewById(R.id.list_view);
        mData=getData();
        mSimpleleAdapter=new SimpleAdapter(
                this,
                mData,
                R.layout.list_view_item,
                new String[]{"name","http","state","from","admin"},
                new int[]{R.id.text_view_1,R.id.text_view_2,R.id.text_view_3,R.id.text_view_4,R.id.text_view_5});

        lv.setAdapter(mSimpleleAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView tv=view.findViewById(R.id.text_view_2);
                Intent intent=new Intent(MainActivity.this,DisplayActivity.class);
                intent.putExtra("msg",tv.getText());
                startActivity(intent);
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos=position;

                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete")
                        .setMessage("Do you want to delete this item")
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        mData.remove(pos);
                                        mSimpleleAdapter.notifyDataSetChanged();
                                    }
                                })
                        .setNegativeButton("Cancel",null)
                        .create().show();
                return true;
            }
        });

        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState) {
                    case SCROLL_STATE_FLING: {//手指离开屏幕，且列表在滚动
                    }
                    case SCROLL_STATE_IDLE: {
                    }
                    case SCROLL_STATE_TOUCH_SCROLL: {//手指接触屏幕且滚动
                    }
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem+visibleItemCount==totalItemCount){
                    Map<String,Object>map=new HashMap<>();
                    map.put("name","XXX");
                    map.put("http","所在小区：xxx小区");
                    map.put("state","目前发热情况：严重");
                    map.put("from","来源地：其他");
                    map.put("admin","防控员：yyy");
                    mData.add(map);
                    mSimpleleAdapter.notifyDataSetChanged();
                }

            }
        });
        Log.d(TAG,"ONRESUME");
    }

    private List<Map<String,Object>>getData() {
        List<Map<String,Object>>list= new ArrayList<>();
        Map<String,Object> map=new HashMap<>();
        map.put("name","XXX");
        map.put("http","所在小区：xxx小区");
        map.put("state","目前发热情况：严重");
        map.put("from","来源地：其他");
        map.put("admin","防控员：yyy");

        list.add(map);

        map=new HashMap<>();
        map.put("name","XXX");
        map.put("http","所在小区：xxx小区");
        map.put("state","目前发热情况：严重");
        map.put("from","来源地：其他");
        map.put("admin","防控员：yyy");

        list.add(map);
        return list;
    }

    @Override
    protected void onPause() {



        super.onPause();
        Log.d(TAG,"ONPAUSE");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG,"ONSTOP");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"ONDESTROY");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG,"ONRESTART");
    }
}
