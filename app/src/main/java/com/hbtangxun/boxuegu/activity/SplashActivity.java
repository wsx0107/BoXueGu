package com.hbtangxun.boxuegu.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.widget.TextView;

import com.hbtangxun.boxuegu.R;

import java.util.Timer;
import java.util.TimerTask;

//欢迎界面逻辑代码

public class SplashActivity extends Activity {

    private TextView text_splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        text_splash = findViewById(R.id.text_splash);
        init();

    }

    private void init() {

        try {
            //获取程序的信息
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            text_splash.setText("V"+packageInfo.versionName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //延迟3秒跳转
        Timer timer=new Timer();
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
        //在延迟3秒后自动执行
        timer.schedule(task,3000);
    }
}
