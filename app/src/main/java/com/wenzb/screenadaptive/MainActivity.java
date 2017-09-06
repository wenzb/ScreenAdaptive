package com.wenzb.screenadaptive;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    DecimalFormat df = new DecimalFormat("0.0");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new Runnable() {
            @Override
            public void run() {
                saveDimens(320);
                saveDimens(360);
                saveDimens(431);
                saveDimens(480);
                saveDimens(540);
            }
        }).start();
    }

    /**
     * 以sw360dp为基准在sd卡根目录生成对应dimens.xml 文件
     */
    private void saveDimens(int swDp){
        String str="<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<resources>\n";
        for (int i = 1; i < 640; i++) {
            double a=i;
            String b=df.format(a*swDp/360);
            str+="<dimen name=\"dp_"+i+"\">"+b+"dp</dimen>\n";
        }
        str+="</resources>";

        File file=new File(Environment.getExternalStorageDirectory()+"/"+"values-sw"+swDp+"dp");
        if (!file.exists()){
            file.mkdirs();
        }

        byte[] buff=str.getBytes();
        try {
            FileOutputStream out=new FileOutputStream(file.getPath()+"/dimens.xml");
            out.write(buff, 0, buff.length);
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
