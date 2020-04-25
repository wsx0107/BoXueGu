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

//登录界面逻辑代码

public class LoginActivity extends Activity implements View.OnClickListener {

    //标题栏的返回键
    private ImageView iv_back_title;
    //标题栏的文本
    private TextView title_text;

    private EditText login_user;
    private EditText login_psw;
    private Button login_btn;
    private TextView login_register;
    private TextView login_find_psw;

    private String userName, loginPsw, spPsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initView();
        initData();
    }

    private void initView() {
        iv_back_title = findViewById(R.id.iv_back_title);
        title_text = findViewById(R.id.title_text);
        login_user = findViewById(R.id.login_user);
        login_psw = findViewById(R.id.login_psw);
        login_btn = findViewById(R.id.login_btn);
        login_register = findViewById(R.id.login_register);
        login_find_psw = findViewById(R.id.login_find_psw);
    }

    private void initData() {

        iv_back_title.setOnClickListener(this);
        title_text.setText("登 录");

        login_btn.setOnClickListener(this);

        login_register.setOnClickListener(this);
        login_find_psw.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == iv_back_title) {
            finish();
        } else if (v == login_btn) {
            btnLogin();
        } else if (v == login_register) {
            //跳转到注册界面
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivityForResult(intent, 1);
        } else if (v == login_find_psw) {
            //跳转到忘记密码界面
            Intent intent=new Intent(this,FindPswActivity.class);
            startActivity(intent);
        }
    }

    private void btnLogin() {

        userName = login_user.getText().toString().trim();
        loginPsw = login_psw.getText().toString().trim();

        String md5Psw = MD5Utils.md5(loginPsw);
        spPsw = readSP(userName);
        if (TextUtils.isEmpty(userName)) {
            Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(loginPsw)) {
            Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
            return;
        } else if (md5Psw.equals(spPsw)) {
            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
            //保存登录状态和登录的用户
            saveLoginStatus(true, userName);
            //把登录成功状态传递给MainActivity中
            Intent data = new Intent();
            data.putExtra("isLogin", true);
            setResult(RESULT_OK, data);
            finish();
            return;
        } else if (!TextUtils.isEmpty(spPsw) && !md5Psw.equals(spPsw)) {
            Toast.makeText(LoginActivity.this, "输入的用户名或密码不正确", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Toast.makeText(LoginActivity.this, "此用户名不存在", Toast.LENGTH_SHORT).show();
            return;
        }

    }

    /**
     * 保存登录状态和登录的用户到SP中
     *
     * @param status
     * @param userName
     */
    private void saveLoginStatus(boolean status, String userName) {
        SharedPreferences sp = getSharedPreferences("UserInfo", MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putBoolean("isLogin", status);
        edit.putString("loginUserName", userName);
        edit.commit();
    }

    /**
     * 从SP中根据用户名来读取密码
     *
     * @param userName
     * @return
     */
    private String readSP(String userName) {
        SharedPreferences sp = getSharedPreferences("UserInfo", MODE_PRIVATE);
        return sp.getString(userName, "");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {

            //从注册界面得到注册用户名
            String name = data.getStringExtra("userName");

            if (!TextUtils.isEmpty(name)) {
                login_user.setText(name);
                login_user.setSelection(name.length());
            }
        }
    }
}