package com.hbtangxun.boxuegu.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hbtangxun.boxuegu.R;
import com.hbtangxun.boxuegu.Utils.AnalysisUtils;
import com.hbtangxun.boxuegu.activity.LoginActivity;
import com.hbtangxun.boxuegu.activity.SettingActivity;

//我的界面逻辑代码

public class MyInfoView implements View.OnClickListener {

    private LinearLayout ll_head;
    private ImageView ll_head_icon;
    private TextView tv_user_login;

    private RelativeLayout rl_course_history;
    private RelativeLayout rl_course_setting;

    private Activity mContext;
    private LayoutInflater mInflater;
    private View mCurrentView;

    public MyInfoView(Activity context) {
        mContext = context;
        //为之后将Layout转化为View时用
        mInflater = LayoutInflater.from(mContext);
    }

    private void createView() {
        initView();
    }

    /**
     * 获取布局控件
     */
    private void initView() {
        //设置布局文件
        mCurrentView = mInflater.inflate(R.layout.main_view_myinfo, null);
        ll_head = mCurrentView.findViewById(R.id.ll_head);
        ll_head_icon = mCurrentView.findViewById(R.id.ll_head_icon);
        tv_user_login = mCurrentView.findViewById(R.id.tv_user_login);
        rl_course_history = mCurrentView.findViewById(R.id.rl_course_history);
        rl_course_setting = mCurrentView.findViewById(R.id.rl_course_setting);

        mCurrentView.setVisibility(View.VISIBLE);
        setLoginParams(readLoginStatus());          //设置登陆时界面控件的状态

        ll_head.setOnClickListener(this);
        rl_course_history.setOnClickListener(this);
        rl_course_setting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == ll_head) {
            //判断是否已经登陆
            if (readLoginStatus()) {
                Toast.makeText(mContext, "登录成功，个人资料", Toast.LENGTH_SHORT).show();
                //进入 我的资料 界面
            } else {
                //未登录 跳转到 登录界面
                Intent intent = new Intent(mContext, LoginActivity.class);
                mContext.startActivity(intent);
            }

        } else if (v == rl_course_history) {
            if (readLoginStatus()) {
                Toast.makeText(mContext, "登录成功，播放记录", Toast.LENGTH_SHORT).show();
                //进入 播放记录 界面
            } else {
                //未登录 提示
                Toast.makeText(mContext, "您还未登录，请先登录", Toast.LENGTH_SHORT).show();
            }
        } else if (v == rl_course_setting) {
            if (readLoginStatus()) {
                Intent intent = new Intent(mContext, SettingActivity.class);
                mContext.startActivity(intent);
                //进入 设置 界面
            } else {
                //未登录 提示
                Toast.makeText(mContext, "您还未登录，请先登录", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //登录成功后设置我的界面的 用户名
    public void setLoginParams(boolean isLogin) {
        if (isLogin) {
            tv_user_login.setText(AnalysisUtils.readLoginUserName(mContext));
        } else {
            tv_user_login.setText("点击登录");
        }
    }

    //获取当前在导航栏上方对应的View
    public View getView() {
        if (mCurrentView == null) {
            createView();
        }
        return mCurrentView;
    }

    //显示当前导航栏上方所对应的View界面
    public void showView() {
        if (mCurrentView == null) {
            createView();
        }
        mCurrentView.setVisibility(View.VISIBLE);

    }

    //从SP中读取登录状态
    private boolean readLoginStatus() {
        SharedPreferences sp = mContext.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        boolean isLogin = sp.getBoolean("isLogin", false);
        return isLogin;
    }


}