package com.hbtangxun.boxuegu.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hbtangxun.boxuegu.R;
import com.hbtangxun.boxuegu.Utils.AnalysisUtils;
import com.hbtangxun.boxuegu.Utils.MD5Utils;
import com.hbtangxun.boxuegu.Utils.ToolUtils;

//设置密保和找回密码
public class FindPswActivity extends Activity implements View.OnClickListener {

    //标头
    //标题栏的返回键
    private ImageView iv_back_title;
    //标题栏的文本
    private TextView title_text;
    private RelativeLayout rl_title_bar;

    private TextView tv_user_name, tv_validate_name;
    private TextView tv_reset_psw;
    private EditText et_user_name, et_validate_name;
    private Button btn_validate;
    //从哪个界面跳转过来的（from为security时是从设置密保界面跳转过来的，否则就是从登录界面跳转过来的）
    private String from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_psw);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //form  获取从登录界面和设置界面传递过来的数据
        from = getIntent().getStringExtra("from");
        initView();
        initData();
    }

    private void initView() {
        iv_back_title = findViewById(R.id.iv_back_title);
        title_text = findViewById(R.id.title_text);
        tv_user_name = findViewById(R.id.tv_user_name);
        et_user_name = findViewById(R.id.et_user_name);
        tv_validate_name = findViewById(R.id.tv_validate_name);
        et_validate_name = findViewById(R.id.et_validate_name);
        tv_reset_psw = findViewById(R.id.tv_reset_psw);
        btn_validate = findViewById(R.id.btn_validate);
    }

    private void initData() {
        if ("security".equals(from)) {
            title_text.setText("设置密保");
            btn_validate.setText("确  定");
        } else {
            title_text.setText("找回密码");
            tv_user_name.setVisibility(View.VISIBLE);
            et_user_name.setVisibility(View.VISIBLE);
        }
        iv_back_title.setOnClickListener(this);
        btn_validate.setOnClickListener(this);
    }

    //验证密码
    @Override
    public void onClick(View v) {
        if(v==iv_back_title){
            finish();
        } else if(v==btn_validate) {
            validatePsw();
        }
    }

    private void validatePsw() {

        String validateName = et_validate_name.getText().toString().trim();
        String userName = et_user_name.getText().toString().trim();
        if("security".equals(from)){//设置密保
            if(TextUtils.isEmpty(validateName)){
                ToolUtils.showToastShort(this,"请输入要验证的姓名");
            } else {
                //把密保保存到SP中
                saveSecurity(validateName);
                finish();  //完成
            }
        } else {//找回密码
            String sp_security = readSecurity(userName);
            if (TextUtils.isEmpty(userName)) {
                ToolUtils.showToastShort(this,"请输入您的用户名");
                return;
            } else if (!isExistUserName(userName)) {
                ToolUtils.showToastShort(this,"您输入的用户名不存在");
                return;
            } else if (TextUtils.isEmpty(validateName)) {
                ToolUtils.showToastShort(this,"请输入您的姓名");
                return;
            } else if (!validateName.equals(sp_security)) {
                ToolUtils.showToastShort(this,"您输入的密保不正确");
                return;
            } else {
                //输入的密保正确，重新给用户设置一个密码
                tv_reset_psw.setVisibility(View.VISIBLE);
                tv_reset_psw.setText("初始密码：123456");
                savePsw(userName);
            }
        }
    }

    //保存初始化的密码
    private void savePsw(String userName) {
        String md5Psw = MD5Utils.md5("123456");//把密码用Md5加密
        SharedPreferences sp = getSharedPreferences("UserInfo", MODE_PRIVATE);//获取SP
        SharedPreferences.Editor editor = sp.edit();//获取编辑器
        editor.putString(userName, md5Psw);
        editor.commit();//提交修改
    }

    //从SP中读取密保
    private String readSecurity(String userName) {
        SharedPreferences sp = getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String security = sp.getString(userName + "_security", "");
        return security;
    }

    //从SP中根据用户输入的用户名来判断是否有此用户名
    private boolean isExistUserName(String userName) {
        boolean hasUserName = false;
        SharedPreferences sp = getSharedPreferences("UserInfo", MODE_PRIVATE);
        String spPsw = sp.getString(userName, "");//判断有没有密码
        if (!TextUtils.isEmpty(spPsw)) {   //如果密码有的话是true
            hasUserName = true;
        }
        return hasUserName;
    }

    //保存密保到Sp中
    private void saveSecurity(String validateName) {
        SharedPreferences sp = getSharedPreferences("UserInfo", MODE_PRIVATE);//UserInfo表示文件名
        SharedPreferences.Editor editor = sp.edit();//获取编辑器
        //存入用户对应的密保
        editor.putString(AnalysisUtils.readLoginUserName(this) + "_security", validateName);
        editor.commit();//提交修改

        ToolUtils.showToastShort(this,"密保设置完成");
    }
}


