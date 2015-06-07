package com.example.root.freshsoccernews.DbModule.SinaPage;

import android.app.ProgressDialog;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.Toast;

import com.example.root.freshsoccernews.DbModule.DatabaseClient;
import com.example.root.freshsoccernews.MainActivity;
import com.example.root.freshsoccernews.MainAdapter.MainAdapter;
import com.example.root.freshsoccernews.Phoenix.PullToRefreshView;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by root on 15-5-23.
 */
public class SinapageModule {
    private String mGetUrl = "http://becool.sinaapp.com/sinadbjson.php?flag=all";
    private ArrayList<HashMap<String, Object>> mList;
    private Context mContext;
    private MainAdapter mAdapter;
    private ListView mListView;
    private PullToRefreshView mPullToRefreshView;
    private static int NO_NET = 2;
    private static int HAVE_NET = 3;
    public SinapageModule(Context context, MainAdapter mainAdapter, ListView listView, PullToRefreshView pullToRefreshView) {
        this.mContext = context;
        this.mAdapter = mainAdapter;
        this.mListView = listView;
        this.mPullToRefreshView = pullToRefreshView;
    }

    public void ReadDb() {
        initData();
        initView();
    }

    public void refreshDb() {
        Ion.with(mContext)
                .load(mGetUrl)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        try {
                            new Thread(new SinaDbThread(result)).start();
                        } catch (Exception ee) {
                            ee.printStackTrace();
                        }
                    }
                });
    }

    public class SinaDbThread implements Runnable {
        JsonObject mResult;
        public SinaDbThread (JsonObject result) {
            this.mResult = result;
        }
        @Override
        public void run() {
            try {
                Message message = Message.obtain();
                DatabaseClient databaseClient = new DatabaseClient(mContext);
                if (mResult != null) {
                    databaseClient.clearTablePage("sinapage");
                    JsonArray array = mResult.getAsJsonArray("news");
                    ContentResolver contentResolver = mContext.getContentResolver();
                    Uri uri = Uri.parse("content://com.example.root.freshsoccernews.DbModule.SinaPage.SinapageProvider/sinapage");
                    for (int i = 0; i < array.size(); i++) {
                        ContentValues contentValues = new ContentValues();
                        String tmps = array.get(i).getAsJsonObject().get("title").getAsString();
                        String[] tmpsg = tmps.split("_");
                        contentValues.put("title", tmpsg[0]);
                        contentValues.put("img", array.get(i).getAsJsonObject().get("img").getAsString());
                        Uri tmp = contentResolver.insert(uri, contentValues);
                    }
                    message.obj = HAVE_NET;
                } else {
                    message.obj = NO_NET;
                }
                initData();
                handler.sendMessage(message);
            } catch (Exception ee) {
                ee.printStackTrace();
            }
        }

    }

    private void initData() {
        mList = new ArrayList<HashMap<String, Object>>();
        ContentResolver contentResolver = mContext.getContentResolver();
        Uri uri = Uri.parse("content://com.example.root.freshsoccernews.DbModule.SinaPage.SinapageProvider/sinapage");
        Cursor cursor = contentResolver.query(uri, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("title", cursor.getString(cursor.getColumnIndex("title")));
                map.put("img", cursor.getString(cursor.getColumnIndex("img")));
                mList.add(map);
            }
        }
        cursor.close();
        //initView();
    }

    private void initView() {
        if (mAdapter == null) {
            mAdapter = new MainAdapter(mList, mContext);
            mListView.setAdapter(mAdapter);
        } else {
            mAdapter.onDateChange(mList);
        }

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.obj == HAVE_NET) {
                initView();
                mPullToRefreshView.setRefreshing(false);
            } else {
                Toast.makeText(mContext, "无网络链接~~", Toast.LENGTH_SHORT).show();
                mPullToRefreshView.setRefreshing(false);
            }
        }
    };
}
