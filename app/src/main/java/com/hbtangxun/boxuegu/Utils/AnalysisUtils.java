package com.hbtangxun.boxuegu.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 用于获取用户名
 */
public class AnalysisUtils {
    /**
     * 从SP中读取用户名
     *
     * @param context
     * @return
     */
    public static String readLoginUserName(Context context) {
        SharedPreferences sp = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        String loginUserName = sp.getString("loginUserName", "");
        return loginUserName;
    }

}