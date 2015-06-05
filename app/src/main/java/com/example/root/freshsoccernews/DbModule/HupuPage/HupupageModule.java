package com.example.root.freshsoccernews.DbModule.HupuPage;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.widget.ListView;

import com.example.root.freshsoccernews.DbModule.DatabaseClient;
import com.example.root.freshsoccernews.MainAdapter.MainAdapter;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by root on 15-6-1.
 */
public class HupupageModule {
    private String mGetUrl = "http://becool.sinaapp.com/hupudbjson.php?flag=all";
    private ArrayList<HashMap<String, Object>> mList;
    private Context mContext;
    private MainAdapter mAdapter;
    private ListView mListView;
    private ProgressDialog mProgressDialog;
    public HupupageModule(Context context, MainAdapter mainAdapter, ListView listView) {
        this.mContext = context;
        this.mAdapter = mainAdapter;
        this.mListView = listView;
    }

    public void refreshDb(final int flag) {
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setTitle("努力加载中~~");
        mProgressDialog.show();
        Ion.with(mContext)
                .load(mGetUrl)
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        try {
                            DatabaseClient databaseClient = new DatabaseClient(mContext);
                            if (result != null) {
                                databaseClient.clearTablePage("hupupage");

                                JsonArray array = result.getAsJsonArray("news");
                                ContentResolver contentResolver = mContext.getContentResolver();
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
                            if (flag == 1) {
                                initData();
                            }
                        } catch (Exception ee) {
                            ee.printStackTrace();
                        }
                    }
                });
    }

    private void initData() {
        mList = new ArrayList<HashMap<String, Object>>();
        ContentResolver contentResolver = mContext.getContentResolver();
        Uri uri = Uri.parse("content://com.example.root.freshsoccernews.DbModule.HupuPage.HupupageProvider/hupupage");
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
        initView();
    }

    private void initView() {
        if (mAdapter == null) {
            mAdapter = new MainAdapter(mList, mContext);
            mListView.setAdapter(mAdapter);
        } else {
            mAdapter.onDateChange(mList);
        }
        mProgressDialog.dismiss();
    }
}
