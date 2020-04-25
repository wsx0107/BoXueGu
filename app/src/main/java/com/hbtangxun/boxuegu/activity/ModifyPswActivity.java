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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hbtangxun.boxuegu.R;
import com.hbtangxun.boxuegu.Utils.AnalysisUtils;
import com.hbtangxun.boxuegu.Utils.MD5Utils;
import com.hbtangxun.boxuegu.Utils.ToolUtils;

//修改密码界面的逻辑代码

public class ModifyPswActivity extends Activity implements View.OnClickListener {

    //标头 控件
    //标题栏的返回键
    private ImageView iv_back_title;
    //标题栏的文本
    private TextView title_text;
    private RelativeLayout rl_title_bar;

    //修改密码界面  控件
    private EditText et_original_paw;
    private EditText et_new_psw;
    private EditText et_new_psw_again;
    private Button btn_save;

    private String originalPaw, newPaw, newPawAgain;
    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_psw);
        userName = AnalysisUtils.readLoginUserName(this);      //读取username密码
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        initView();
        initData();
    }

    private void initView() {
        iv_back_title = findViewById(R.id.iv_back_title);
        title_text = findViewById(R.id.title_text);
        rl_title_bar = findViewById(R.id.rl_title_bar);
        et_original_paw = findViewById(R.id.et_original_paw);
        et_new_psw = findViewById(R.id.et_new_psw);
        et_new_psw_again = findViewById(R.id.et_new_psw_again);
        btn_save = findViewById(R.id.btn_save);
    }

    private void initData() {
        title_text.setText("修改密码");            //标头文字
        iv_back_title.setOnClickListener(this);
        btn_save.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == iv_back_title) {
            finish();
        } else if (v == btn_save) {
            modifyPsw();            //方法
        }
    }

    //修改密码  按钮的点击事件
    private void modifyPsw() {
        getEditString();  //获取文本内容
        if (TextUtils.isEmpty(originalPaw)) {
            ToolUtils.showToastShort(ModifyPswActivity.this, "请输入原始密码");
            return;
        } else if (!MD5Utils.md5(originalPaw).equals(readPsw())) {
            ToolUtils.showToastShort(ModifyPswActivity.this, "输入的密码与原始密码不一致");
            return;
        } else if (TextUtils.isEmpty(newPaw)) {
            ToolUtils.showToastShort(ModifyPswActivity.this, "请输入新密码");
            return;
        } else if (TextUtils.isEmpty(newPawAgain)) {
            ToolUtils.showToastShort(ModifyPswActivity.this, "请再次输入新密码");
            return;
        } else if (!newPaw.equals(newPawAgain)) {
            ToolUtils.showToastShort(ModifyPswActivity.this, "两次输入的新密码要一致");
            return;
        } else if (MD5Utils.md5(newPaw).equals(readPsw())) {
            ToolUtils.showToastShort(ModifyPswActivity.this, "输入的新密码与原始密码不能一致");
            return;
        } else {
            ToolUtils.showToastShort(ModifyPswActivity.this, "新密码设置成功");
            modifyPsw(newPaw);        //传入新密码
            //重新登录
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            ModifyPswActivity.this.finish();      //关掉修改密码界面
            SettingActivity.instance.finish();    //关掉设置界面
        }


    }

    //获取控件上的字符串
    private void getEditString() {
        originalPaw = et_original_paw.getText().toString().trim();
        newPaw = et_new_psw.getText().toString().trim();
        newPawAgain = et_new_psw_again.getText().toString().trim();
    }

    //从SP中读取原始密码
    private String readPsw() {
        SharedPreferences sp = getSharedPreferences("UserInfo", MODE_PRIVATE);
        String spPsw = sp.getString(userName, "");    //读取密码
        return spPsw;
    }

    //修改登录成功时保存在SP中的密码
    private void modifyPsw(String str) {
        String md5Psw = MD5Utils.md5(str);      //加密
        SharedPreferences sp = getSharedPreferences("UserInfo", MODE_PRIVATE);  //获取sp
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(userName, md5Psw);   //保存密码
        edit.commit();  //提交
    }

}
