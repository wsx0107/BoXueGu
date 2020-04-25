package com.hbtangxun.boxuegu.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hbtangxun.boxuegu.R;
import com.hbtangxun.boxuegu.Utils.MD5Utils;

//注册界面逻辑代码

public class RegisterActivity extends Activity implements View.OnClickListener {

    //标题栏的返回键
    private ImageView iv_back_title;
    //标题栏的文本
    private TextView title_text;
    //用户名
    private EditText et_user_name;
    //密码
    private EditText et_psw;
    //再次确认密码
    private EditText et_psw_again;
    //注册按钮
    private Button btn_register;
    //设置三个变量来盛放内容
    private String userName, psw, againPsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);           //注册界面
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initView();
        initData();
    }

    /**
     * 进行一些的FindViewById
     * 或者
     * 一些关于View的代码
     */
    private void initView() {
        iv_back_title = findViewById(R.id.iv_back_title);             //复制id
        title_text = findViewById(R.id.title_text);                   //用fid
        et_user_name = findViewById(R.id.et_user_name);
        et_psw = findViewById(R.id.et_psw);
        et_psw_again = findViewById(R.id.et_psw_again);
        btn_register = findViewById(R.id.btn_register);
    }

    private void initData() {

        title_text.setText("注册");
        iv_back_title.setOnClickListener(this);
        btn_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == iv_back_title) {
            finish();
        } else if (v == btn_register) {
            getEditString();
            if (TextUtils.isEmpty(userName)) {
                Toast.makeText(RegisterActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                return;
            } else if (TextUtils.isEmpty(psw)) {
                Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                return;
            } else if (TextUtils.isEmpty(againPsw)) {
                Toast.makeText(RegisterActivity.this, "请再次输入密码", Toast.LENGTH_SHORT).show();
                return;
            } else if (!psw.equals(againPsw)) {
                Toast.makeText(RegisterActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                return;
            } else if (isExistUserName(userName)) {
                Toast.makeText(RegisterActivity.this, "此账号已存在", Toast.LENGTH_SHORT).show();
                return;
            } else {
                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                //将账号密码存入SP（本地存储）
                saveSP(userName, psw);
                //将账号传给LoginActivity
                Intent data = new Intent();                               //用来传递信息
                data.putExtra("userName", userName);
                setResult(RESULT_OK, data);                              //标识（）1对1
                finish();
            }
        }

    }

    /**
     * 获取EditText控件的文本内容
     */
    private void getEditString() {
        userName = et_user_name.getText().toString();
        psw = et_psw.getText().toString();
        againPsw = et_psw_again.getText().toString();
    }

    /**
     * 将账号密码存入SP中
     */
    private void saveSP(String userName, String psw) {
        String md5Psw = MD5Utils.md5(psw);
        SharedPreferences sp = getSharedPreferences("UserInfo", MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(userName, md5Psw);
        edit.commit();
    }

    /**
     * 判断用户名是否重复
     *
     * @return
     */
    private boolean isExistUserName(String userName) {
        boolean has_userName = false;
        SharedPreferences sp = getSharedPreferences("UserInfo", MODE_PRIVATE);
        String spName = sp.getString(userName, "");                             //先把数据库的名称弄出来
        if (!TextUtils.isEmpty(spName)) {                                                 //判断是否有这个名称
            has_userName = true;
        }

        return has_userName;
    }

}