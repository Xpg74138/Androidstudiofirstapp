package com.example.first;

import android.animation.ObjectAnimator;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
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
        setContentView(R.layout.activity_main_linear);

        Log.d(TAG,"ONCREATE");
        }

    public void onButtonClicked(View v){
        Intent intent=new Intent(this,DisplayActivity.class);
        Intent intent2=new Intent(this,DisplayActivity.class);
        //startActivity(intent);
        PackageManager pm = this.getPackageManager();
        TextView tv=findViewById(R.id.textView2);
        String str=tv.getText().toString();
        intent.putExtra("abc",str);
        //Intent intent =new Intent();
        //intent.setAction(Intent.ACTION_DIAL);
        if(intent.resolveActivity(pm)!=null)
            Log.d(TAG, "right intent.");
        else {
            Log.d(TAG,"something wrong!");
        }

        startActivity(intent);
    }

    public void onButtonClicked2(View v){
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        startActivity(intent);
    }

    public void onButtonClick3(View v){
        FragmentManager manager=getFragmentManager();
        FragmentTransaction trans=manager.beginTransaction();
        switch(v.getId()){
            case R.id.button1:{
                if(mFragmentOne==null){
                    mFragmentOne=new BlankFragment1();
                }
                trans.replace(R.id.frame_layout,mFragmentOne);
                break;
            }
            case R.id.button2:{
                if(mFragmentTwo==null){
                    mFragmentTwo=new BlankFragment2();
                }
                trans.replace(R.id.frame_layout,mFragmentTwo);
                break;
            }
            default:
                break;
        }
        trans.commit();
    }
    @Override

    protected void onStart() {
        super.onStart();

        mTextView=findViewById(R.id.textView2);
        ObjectAnimator anim1=ObjectAnimator.ofFloat(mTextView,"TranslationY",300.0f,0.0f);
        anim1.setDuration(3000);
        anim1.setRepeatCount(3);
        anim1.setRepeatMode(ObjectAnimator.RESTART);
        anim1.start();

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
                new String[]{"img","title","http"},
                new int[]{R.id.image_view,R.id.text_view_1,R.id.text_view_2});

        lv.setAdapter(mSimpleleAdapter);
        Log.d(TAG,"ONRESUME");
    }

    private List<Map<String,Object>>getData() {
        List<Map<String,Object>>list= new ArrayList<>();
        Map<String,Object> map=new HashMap<>();
        map.put("img",R.drawable.ic_launcher_background);
        map.put("title","Grossini");
        map.put("http","google.com");

        list.add(map);

        map=new HashMap<>();
        map.put("img",R.drawable.ic_launcher_background);
        map.put("title","Hello");
        map.put("http","This is the second");

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
