package com.example.ourfirstapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class RecordActivity extends AppCompatActivity {

    TextView mTextView;
    public localDB mDBTest;
    public EditText mName;
    public EditText mScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDBTest = new localDB(this, "DBTEST", null, 1);
        //mDBTest = DBTest.getInstance(this, "DBTEST", 1);

        mName = (EditText) findViewById(R.id.edit_text_1);
        mScore = (EditText) findViewById(R.id.edit_text_2);
        mTextView = (TextView) findViewById(R.id.text_view);
    }

    public void buttonClicked(View v) {
        EditText et_name = findViewById(R.id.edit_text_1); //获取你想存储数据的控件
        EditText et_score = findViewById(R.id.edit_text_2);//获取你想存储数据的控件

        //这里的代码是进行存储
        SharedPreferences spf = getSharedPreferences(
                "main_activity_data",    //存储文件名称自己定
                Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = spf.edit();
        editor.putString(
                "highest_score", //存储的key自己定
                et_score.getText().toString());
        editor.putString(
                "highest_score_player", //存储的key自己定
                et_name.getText().toString());

        editor.commit();

        // 这里的代码是进行读取 注意这里的spf仍是沿用前面的SharedPreferences
        String test = spf.getString(
                "highest_score_player",
                "none");
        test += " : ";
        test += spf.getString(
                "highest_score",
                "none");
        ((TextView) findViewById(R.id.text_view)).setText(test); //把读取的数据展示在页面上
    }

    public void saveFileInternalDirectly(View v) {
        EditText et_name = findViewById(R.id.edit_text_1);
        EditText et_score = findViewById(R.id.edit_text_2);

        // no try catch will throw error
        FileOutputStream fos;
        try {
            fos = openFileOutput("highest_score", MODE_PRIVATE);
            fos.write(et_name.getText().toString().getBytes());
            fos.write("#".getBytes());// 分隔符一定要选输出流里面不会出现的符号
            fos.write(et_score.getText().toString().getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // Now Test read
        FileInputStream fis;
        byte[] buf = new byte[1024];  // 2058
        try {
            fis = openFileInput("highest_score");
            int length = fis.read(buf);// length有什么用途
            StringBuilder strBuilder = new StringBuilder("");
            strBuilder.append(new String(buf, 0, length, "UTF-8"));
            String[] result = strBuilder.toString().split("#");

            //result[0]对应着用户名 result[1]对应得分
            TextView tv = (TextView) findViewById(R.id.text_view);
            tv.setText(result[0] + ":" + result[1]);

            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveFileInternalObject(View v) {
        EditText et_name = (EditText) findViewById(R.id.edit_text_1);
        EditText et_score = (EditText) findViewById(R.id.edit_text_2);

        Player p1 = new Player();
        p1.mName = et_name.getText().toString();
        p1.mScore = et_score.getText().toString();

        // save data   no try catch will throw error
        FileOutputStream fos;
        try {
            fos = openFileOutput("highest_score_object", MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(p1);
            oos.writeObject(null); // 方便读取 can help read using while
            oos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Now Test read
        FileInputStream fis;
        try {
            fis = openFileInput("highest_score_object");
            ObjectInputStream ois = new ObjectInputStream(fis);

            Player p1_copy = (Player) ois.readObject();
            TextView tv =  findViewById(R.id.text_view);
            tv.setText(p1_copy.mName + ":" + p1_copy.mScore);

            ois.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private boolean isExternalStorageWritable(){
        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            return true;
        }
        return false;
    }

    private boolean isExternalStorageReadable(){
        String state=Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public void saveFileExternalDirectly(View v) {
        if( !isExternalStorageWritable()) {
            Toast.makeText(this, "Check external storage", Toast.LENGTH_LONG).show();
            return;
        }

        //File file = new File(Environment.getExternalStoragePublicDirectory(
        //       Environment.DIRECTORY_DOCUMENTS), "Test08View2");
        // or 这两种new File的方法仅仅是一个声明，没有实际创建文件，需要下面的代码来创建
        File file = new File(this.getApplicationContext().
                getExternalFilesDir(null), "Test08View2");

        if(file.getParentFile().mkdir()) {
            if(!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }

        try {
            FileOutputStream fos = new FileOutputStream(file);
            EditText et_name = (EditText) findViewById(R.id.edit_text_1);
            fos.write(et_name.getText().toString().getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        //Read
        if( !isExternalStorageReadable()) {
            Toast.makeText(this, "Check external storage", Toast.LENGTH_LONG);
            return;
        }

        //File fileR = new File(Environment.getExternalStoragePublicDirectory(
        //        Environment.DIRECTORY_DOCUMENTS), "Test08View2");

        File fileR = new File(this.getApplicationContext().getExternalFilesDir(null), "Test08View2");

        try {
            FileInputStream fis = new FileInputStream(fileR);
            byte[] buf = new byte[1024];
            int length = fis.read(buf);

            StringBuilder strBuilder = new StringBuilder("");
            strBuilder.append(new String(buf, 0, length, "UTF-8"));

            TextView tv = (TextView) findViewById(R.id.text_view);
            tv.setText(strBuilder.toString());

            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    public void saveFileExternalObject(View v) {
        if( !isExternalStorageWritable()) {
            Toast.makeText(this, "Check external storage", Toast.LENGTH_LONG).show();
            return;
        }

        //File file = new File(Environment.getExternalStoragePublicDirectory(
        //       Environment.DIRECTORY_DOCUMENTS), "Test08View2");
        // or 这两种new File的方法仅仅是一个声明，没有实际创建文件，需要下面的代码来创建
        File file = new File(this.getApplicationContext().
                getExternalFilesDir(null), "Test08View2");

        if(file.getParentFile().mkdir()) {
            if(!file.exists()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }

        EditText et_name = (EditText) findViewById(R.id.edit_text_1);
        EditText et_score = (EditText) findViewById(R.id.edit_text_2);

        Player p1 = new Player();
        p1.mName=et_name.getText().toString();
        p1.mScore = et_score.getText().toString();

        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(p1);
            oos.writeObject(null);// 方便读取 can help read using while
            oos.close();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        //Read
        if( !isExternalStorageReadable()) {
            Toast.makeText(this, "Check external storage", Toast.LENGTH_LONG);
            return;
        }

        //File fileR = new File(Environment.getExternalStoragePublicDirectory(
        //        Environment.DIRECTORY_DOCUMENTS), "Test08View2");

        File fileR = new File(this.getApplicationContext().getExternalFilesDir(null), "Test08View2");

        try {
            FileInputStream fis = new FileInputStream(fileR);
            ObjectInputStream ois = new ObjectInputStream(fis);
            Player p1_copy = (Player) ois.readObject();
            TextView tv = (TextView) findViewById(R.id.text_view);
            tv.setText(p1_copy.mName + ":" + p1_copy.mScore);
            ois.close();
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void onClicked(View v) {
        //Test Insert
        SQLiteDatabase dbw = mDBTest.getWritableDatabase();
        ContentValues value = new ContentValues();
        value.put(localDB.UserInfo.COLUMN_NAME_PLAYER, mName.getText().toString());
        value.put(localDB.UserInfo.COLUMN_NAME_SCORE, mScore.getText().toString());
        dbw.insert(localDB.UserInfo.TABLE_NAME, null, value);
        //mDBTest.insertOnePlayerInfo(mName.getText().toString(),
        //        mScore.getText().toString()); // 把数据库代码封装到数据库类的方法中才是正道
        // dbw.delete(DBTest.UserInfo.TABLE_NAME, "1", null);
        dbw.close();
    }
}
