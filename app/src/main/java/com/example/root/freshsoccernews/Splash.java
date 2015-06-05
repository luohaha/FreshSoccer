package com.example.root.freshsoccernews;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import com.example.root.freshsoccernews.MainAdapter.MainAdapter;

/**
 * Created by root on 15-6-2.
 */
public class Splash extends Activity {
    private MainAdapter mAdapter;
    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO 自动生成的方法存根
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        new Handler().postDelayed(new Runnable(){
            // 为了减少代码使用匿名Handler创建一个延时的调用
            public void run() {
                Intent i = new Intent(Splash.this, MainActivity.class);
                //通过Intent打开最终真正的主界面Main这个Activity
                Splash.this.startActivity(i);    //启动Main界面
                Splash.this.finish();    //关闭自己这个开场屏
            }
        }, 2000);   //2秒
    }
}
