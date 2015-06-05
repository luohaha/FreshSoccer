package com.example.root.freshsoccernews.Fragment;

import android.support.v4.app.Fragment;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.root.freshsoccernews.ContentActivity.ContentActivity;
import com.example.root.freshsoccernews.DbModule.SinaPage.SinapageModule;
import com.example.root.freshsoccernews.HeadBar;
import com.example.root.freshsoccernews.MainActivity;
import com.example.root.freshsoccernews.MainAdapter.MainAdapter;
import com.example.root.freshsoccernews.Phoenix.PullToRefreshView;
import com.example.root.freshsoccernews.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by root on 15-5-23.
 */
public class Fragment1 extends Fragment {
    private View mView;
    private PullToRefreshView mPullToRefreshView;
    private HeadBar mHeadBar;
    private ListView mListView;
    private MainAdapter mAdapter;
    private ArrayList<HashMap<String, Object>> mList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mView = inflater.inflate(R.layout.fragment1, null);
        mListView = (ListView) mView.findViewById(R.id.activity_main_listview);
        //mList = new ArrayList<HashMap<String, Object>>();
        initHeadBar();
        initRefreshView();
        SinapageModule sinapageModule = new SinapageModule(getActivity(), mAdapter, mListView);
        sinapageModule.refreshDb(1);
        initListView();
        Log.i("fragment test->", "create view");
        //getDataFromDb();
        return mView;
    }



    private void initHeadBar() {
        mHeadBar = (HeadBar) getActivity().findViewById(R.id.main_headbar);
        mHeadBar.setLeftSecondButtonNoused();
        mHeadBar.setRightSecondButtonNoused();
    }

    private void initListView() {
        /*
        if (mAdapter == null) {
            mAdapter = new MainAdapter(mList, getActivity());
            mListView.setAdapter(mAdapter);
        } else {
            mAdapter.onDateChange(mList);
        }*/
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ContentActivity.class);
                intent.putExtra("id", String.valueOf(position+1));
                intent.putExtra("type", "sina");
                startActivity(intent);
            }
        });
    }

    private void getDataFromDb() {
        ContentResolver contentResolver = getActivity().getContentResolver();
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
        initListView();
    }

    private void initRefreshView() {
        mPullToRefreshView = (PullToRefreshView) mView.findViewById(R.id.main_pulltorefreshview);

        mPullToRefreshView.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPullToRefreshView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SinapageModule sinapageModule = new SinapageModule(getActivity(), mAdapter, mListView);
                        sinapageModule.refreshDb(1);
                        mPullToRefreshView.setRefreshing(false);
                    }
                }, 100);
            }
        });
    }

}
