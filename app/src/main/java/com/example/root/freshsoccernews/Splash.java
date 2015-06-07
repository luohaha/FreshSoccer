package com.example.root.freshsoccernews;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;

import com.example.root.freshsoccernews.DbModule.DatabaseClient;
import com.example.root.freshsoccernews.DbModule.SinaPage.SinapageModule;
import com.example.root.freshsoccernews.MainAdapter.MainAdapter;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by root on 15-6-2.
 */
public class Splash extends Activity {
    private MainAdapter mAdapter;
    private ListView mListView;
    private static int IS_FINISH = 1;
    private static int IS_SECOND_FINISH = 2;
    private String mGetUrl1 = "http://becool.sinaapp.com/sinadbjson.php?flag=all";
    private String mGetUrl2 = "http://becool.sinaapp.com/hupudbjson.php?flag=all";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO 自动生成的方法存根
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //启动后台加载线程
        new Thread(new LoadSinaThread()).start();
    }
    /**
     * 处理两个页面刷新完成后的操作
    * */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == IS_FINISH){
                Intent i = new Intent(Splash.this, MainActivity.class);
                //通过Intent打开最终真正的主界面Main这个Activity
                Splash.this.startActivity(i);    //启动Main界面
                Splash.this.finish();    //关闭自己这个开场屏
            }
        }
    };
    /**
    * 加载第一页的线程,在内部启动第二线程
    * */
    public class LoadSinaThread implements Runnable {
        @Override
        public void run() {
            Looper.prepare();
            Ion.with(Splash.this)
                    .load(mGetUrl1)
                    .asJsonObject()
                    .setCallback(new FutureCallback<JsonObject>() {
                        @Override
                        public void onCompleted(Exception e, JsonObject result) {
                            try {
                                DatabaseClient databaseClient = new DatabaseClient(Splash.this);
                                if (result != null) {
                                    Toast.makeText(getApplicationContext(), "正在努力加载中哦~~", Toast.LENGTH_SHORT).show();
                                    databaseClient.clearTablePage("sinapage");
                                    JsonArray array = result.getAsJsonArray("news");
                                    ContentResolver contentResolver = Splash.this.getContentResolver();
                                    Uri uri = Uri.parse("content://com.example.root.freshsoccernews.DbModule.SinaPage.SinapageProvider/sinapage");
                                    for (int i = 0; i < array.size(); i++) {
                                        ContentValues contentValues = new ContentValues();
                                        String tmps = array.get(i).getAsJsonObject().get("title").getAsString();
                                        String[] tmpsg = tmps.split("_");
                                        contentValues.put("title", tmpsg[0]);
                                        contentValues.put("img", array.get(i).getAsJsonObject().get("img").getAsString());
                                        Uri tmp = contentResolver.insert(uri, contentValues);
                                    }
                                    //在内部启动第二线程
                                    new Thread(new LoadHupuThread()).start();
                                } else {
                                    Toast.makeText(getApplicationContext(), "无网络链接~~", Toast.LENGTH_SHORT).show();
                                    Message message = Message.obtain();
                                    message.what = IS_SECOND_FINISH;
                                    handlerSecond.sendMessage(message);
                                }

                            } catch (Exception ee) {
                                ee.printStackTrace();
                            }
                        }
                    });
        }
        /**
         * 处理第二线程
         * */
        private Handler handlerSecond = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == IS_SECOND_FINISH) {
                    Message message = Message.obtain();
                    message.what = IS_FINISH;
                    handler.sendMessage(message);
                }
            }
        };
        /**
         * 第二线程
         * */
        public class LoadHupuThread implements Runnable {
            @Override
            public void run() {
                Ion.with(Splash.this)
                        .load(mGetUrl2)
                        .asJsonObject()
                        .setCallback(new FutureCallback<JsonObject>() {
                            @Override
                            public void onCompleted(Exception e, JsonObject result) {
                                try {
                                    DatabaseClient databaseClient = new DatabaseClient(Splash.this);
                                    if (result != null) {
                                        databaseClient.clearTablePage("hupupage");

                                        JsonArray array = result.getAsJsonArray("news");
                                        ContentResolver contentResolver = Splash.this.getContentResolver();
                                        Uri uri = Uri.parse("content://com.example.root.freshsoccernews.DbModule.HupuPage.HupupageProvider/hupupage");
                                        for (int i = 0; i < array.size(); i++) {
                                            ContentValues contentValues = new ContentValues();
                                            String tmps = array.get(i).getAsJsonObject().get("title").getAsString();
                                            String[] tmpsg = tmps.split("_");
                                            contentValues.put("title", tmpsg[0]);
                                            contentValues.put("img", array.get(i).getAsJsonObject().get("img").getAsString());
                                            Uri tmp = contentResolver.insert(uri, contentValues);
                                        }
                                    }
                                    Message message = Message.obtain();
                                    message.what = IS_SECOND_FINISH;
                                    handlerSecond.sendMessage(message);
                                } catch (Exception ee) {
                                    ee.printStackTrace();
                                }
                            }
                        });
            }
        }
    }


}
