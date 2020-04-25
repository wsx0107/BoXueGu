package com.hbtangxun.boxuegu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hbtangxun.boxuegu.R;
import com.hbtangxun.boxuegu.Utils.ToolUtils;

//设置界面逻辑代码

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    //标头
    //标题栏的返回键

    private ImageView iv_back_title;
    //标题栏的文本
    private TextView title_text;
    private RelativeLayout rl_title_bar;

    //设置界面
    private RelativeLayout rl_modify_psw;
    private RelativeLayout rl_security_parent;
    private RelativeLayout rl_exit_login;

    public static SettingActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        instance = this;
        initView();
        initData();
    }

    private void initView() {
        iv_back_title = findViewById(R.id.iv_back_title);
        title_text = findViewById(R.id.title_text);
        rl_modify_psw = findViewById(R.id.rl_modify_psw);
        rl_security_parent = findViewById(R.id.rl_security_parent);
        rl_exit_login = findViewById(R.id.rl_exit_login);
        rl_title_bar = findViewById(R.id.rl_title_bar);
    }

    private void initData() {
        title_text.setText("设 置");
        rl_title_bar.setBackgroundColor(Color.parseColor("#30B4FF"));
        iv_back_title.setOnClickListener(this);
        rl_modify_psw.setOnClickListener(this);
        rl_security_parent.setOnClickListener(this);
        rl_exit_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == iv_back_title) {
            finish();
        } else if (v == rl_modify_psw) {
            ToolUtils.showToastShort(SettingActivity.this,"修改密码");
            //跳转到修改密码界面
            Intent intent = new Intent(this, ModifyPswActivity.class);
            startActivity(intent);
        } else if (v == rl_security_parent) {
            ToolUtils.showToastShort(SettingActivity.this,"设置密保");
            Intent intent = new Intent(this, FindPswActivity.class);
            intent.putExtra("from","security");
            startActivity(intent);
        } else if (v == rl_exit_login) {
            ToolUtils.showToastShort(SettingActivity.this,"退出登录");
            clearLoginStatus();
            //退出成功后 将 “退出登录状态” 传给MainActivity
            Intent data = new Intent();
            data.putExtra("isLogin", false);
            setResult(RESULT_OK, data);
            finish();
        }
    }

    /**
     * 清除SP中的登录状态和登录时的用户名
     */
    private void clearLoginStatus() {
        //获取SP对象
        SharedPreferences sp = getSharedPreferences("UserInfo", MODE_PRIVATE);
        //获取编辑器
        SharedPreferences.Editor edit = sp.edit();
        //清除登录状态
        edit.putBoolean("isLogin", false);
        //清除登录用户名
        edit.putString("loginUserName", "");
        //提交修改
        edit.commit();
    }

}
