package com.hbtangxun.boxuegu.activity;

import androidx.annotation.Nullable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hbtangxun.boxuegu.R;
import com.hbtangxun.boxuegu.view.MyInfoView;

//底部导航栏逻辑代码

public class MainActivity extends Activity implements View.OnClickListener {
    //中间内容栏
    private FrameLayout mBodyLayout;

    //标题栏的返回键
    private ImageView iv_back_title;
    //标题栏的文本
    private TextView title_text;

    private LinearLayout main_bottom_bar;

    private TextView bottom_bar_text_course;
    private ImageView bottom_bar_img_course;
    private TextView bottom_bar_text_exercises;
    private ImageView bottom_bar_img_exercises;
    private TextView bottom_bar_text_my;
    private ImageView bottom_bar_img_my;

    private RelativeLayout rl_title_bar;

    //触发三个按钮点击事件
    private RelativeLayout courseBtn;
    private RelativeLayout exercisesBtn;
    private RelativeLayout myBtn;

    //界面 我的
    private MyInfoView mMyInfoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initView();
        initData();
        setListener();
        setInitStatus();
    }

    private void initView() {
        //中间内容控件
        mBodyLayout = findViewById(R.id.main_frame);
        //标头控件
        iv_back_title = findViewById(R.id.iv_back_title);
        title_text = findViewById(R.id.title_text);
        rl_title_bar = findViewById(R.id.rl_title_bar);
        //获取底部导航栏控件
        main_bottom_bar = findViewById(R.id.main_bottom_bar);
        bottom_bar_text_course = findViewById(R.id.bottom_bar_text_course);
        bottom_bar_img_course = findViewById(R.id.bottom_bar_img_course);
        bottom_bar_text_exercises = findViewById(R.id.bottom_bar_text_exercises);
        bottom_bar_img_exercises = findViewById(R.id.bottom_bar_img_exercises);
        bottom_bar_text_my = findViewById(R.id.bottom_bar_text_my);
        bottom_bar_img_my = findViewById(R.id.bottom_bar_img_my);
        courseBtn = findViewById(R.id.bottom_bar_rl_course_btn);
        exercisesBtn = findViewById(R.id.bottom_bar_rl_exercises_btn);
        myBtn = findViewById(R.id.bottom_bar_rl_my_btn);
    }

    private void initData() {
        iv_back_title.setVisibility(View.GONE);       //返回键消失
        title_text.setText("博学谷课堂");
        rl_title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));
    }

    //底部三个按钮的点击监听事件
    private void setListener() {
        for (int i = 0; i < main_bottom_bar.getChildCount(); i++) {
            main_bottom_bar.getChildAt(i).setOnClickListener(this);
        }
    }

    //控件的点击事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //课程点击事件
            case R.id.bottom_bar_rl_course_btn:
                clearBottomImageStats();
                selectDisplayView(0);
                break;
             //习题点击事件
            case R.id.bottom_bar_rl_exercises_btn:
                clearBottomImageStats();
                selectDisplayView(1);
                break;
            //习题点击事件
            case R.id.bottom_bar_rl_my_btn:
                clearBottomImageStats();
                selectDisplayView(2);
                if (mMyInfoView != null) {
                    mMyInfoView.setLoginParams(readLoginStatus());
                }
                break;
        }
    }

    //清除底部导航栏的选中状态
    private void clearBottomImageStats() {
        bottom_bar_text_course.setTextColor(Color.parseColor("#666666"));
        bottom_bar_text_exercises.setTextColor(Color.parseColor("#666666"));
        bottom_bar_text_my.setTextColor(Color.parseColor("#666666"));

        bottom_bar_img_course.setImageResource(R.drawable.main_course_icon);
        bottom_bar_img_exercises.setImageResource(R.drawable.main_exercises_icon);
        bottom_bar_img_my.setImageResource(R.drawable.main_my_icon);

        for (int i = 0; i < main_bottom_bar.getChildCount(); i++) {
            main_bottom_bar.getChildAt(i).setSelected(false);
        }
    }

    //设置底部导航栏选中状态
    private void setSelectedStatus(int index) {

        switch (index) {
            case 0:
                courseBtn.setSelected(true);
                bottom_bar_img_course.setImageResource(R.drawable.main_course_icon_selected);
                bottom_bar_text_course.setTextColor(Color.parseColor("#0097E7"));
                title_text.setText("博学谷课堂");
                rl_title_bar.setVisibility(View.VISIBLE);
                break;
            case 1:
                exercisesBtn.setSelected(true);
                bottom_bar_img_exercises.setImageResource(R.drawable.main_exercises_icon_selected);
                bottom_bar_text_exercises.setTextColor(Color.parseColor("#0097E7"));
                title_text.setText("博学谷习题");
                rl_title_bar.setVisibility(View.VISIBLE);
                break;
            case 2:
                myBtn.setSelected(true);
                bottom_bar_img_my.setImageResource(R.drawable.main_my_icon_selected);
                bottom_bar_text_my.setTextColor(Color.parseColor("#0097E7"));
                rl_title_bar.setVisibility(View.GONE);
                break;
        }

    }

    //移除不需要视图
    private void removeAllView() {
        for (int i = 0; i < mBodyLayout.getChildCount(); i++) {
            mBodyLayout.getChildAt(i).setVisibility(View.GONE);
        }
    }

    //界面View初始化
    private void setInitStatus() {
        clearBottomImageStats();
        setSelectedStatus(0);
        createView(0);
    }

    //显示对应的页面
    private void selectDisplayView(int index) {
        removeAllView();
        createView(index);
        setSelectedStatus(index);
    }

    //选择对应界面
    private void createView(int viewIndex) {
        switch (viewIndex) {
            case 0:
                //课堂界面
                break;
            case 1:
                //习题界面
                break;
            case 2:
                //我的界面
                if (mMyInfoView == null) {
                    mMyInfoView = new MyInfoView(this);
                    mBodyLayout.addView(mMyInfoView.getView());
                } else {
                    mMyInfoView.getView();
                }
                mMyInfoView.showView();
                break;

        }
    }

    private long exitTime;  //记录第一次点击事件

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(MainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {

                finish();

                if (readLoginStatus()) {
                    clearLoginStatus();
                }
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 获取SP的登录状态
     *
     * @return
     */
    private boolean readLoginStatus() {
        SharedPreferences sp = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        boolean isLogin = sp.getBoolean("isLogin", false);
        return isLogin;
    }

    //清除SP中的登陆状态
    private void clearLoginStatus() {
        SharedPreferences sp = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();   //获取编辑器
        edit.putBoolean("isLogin", false);           //清除登陆状态
        edit.putString("loginUserName", "");         //清除登录时的用户名
        edit.commit();                               //提交修改
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            boolean isLogin = data.getBooleanExtra("isLogin", false);
            if (isLogin) {
                clearBottomImageStats();
                selectDisplayView(0);
            }
            if (mMyInfoView != null) {
                mMyInfoView.setLoginParams(isLogin);
            }
        }
    }
}