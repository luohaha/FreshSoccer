package com.example.root.freshsoccernews.DetailActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.example.root.freshsoccernews.HeadBar;
import com.example.root.freshsoccernews.R;

/**
 * Created by root on 15-6-2.
 */
public class DetailActivity extends Activity {
    private HeadBar mHeadBar;
    private RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initHeadBar();
    }

    private void initHeadBar() {
        mHeadBar = (HeadBar) findViewById(R.id.detail_headbar);
        radioGroup = (RadioGroup) findViewById(R.id.rg_tab);
        radioGroup.setVisibility(View.GONE);
        mHeadBar.setTitleVisiable();
        mHeadBar.setTitleText("说明");
        mHeadBar.setLeftSecondButtonNoused();
        mHeadBar.setRightButtonNoused();
        mHeadBar.setRightSecondButtonNoused();
    }
}
