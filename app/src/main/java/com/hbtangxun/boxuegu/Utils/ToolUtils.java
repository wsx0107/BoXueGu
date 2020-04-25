package com.hbtangxun.boxuegu.Utils;

import android.content.Context;
import android.widget.Toast;

public class ToolUtils {

    //短吐司
    public static void showToastShort(Context context,String str){
        Toast.makeText(context,str,Toast.LENGTH_SHORT).show();
    }

    //长吐司
    public  void showToastLong(Context context,String str){
        Toast.makeText(context,str,Toast.LENGTH_SHORT).show();
    }

}
