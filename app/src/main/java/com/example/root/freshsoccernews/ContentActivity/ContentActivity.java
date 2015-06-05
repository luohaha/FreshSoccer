package com.example.root.freshsoccernews.ContentActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.root.freshsoccernews.HeadBar;
import com.example.root.freshsoccernews.R;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

/**
 * Created by root on 15-5-23.
 */
public class ContentActivity extends Activity{
    private String mGetUrl = "http://becool.sinaapp.com/sinadbjson.php?flag=";
    private String mGetUrl2 = "http://becool.sinaapp.com/hupudbjson.php?flag=";
    private ImageView mImagView;
    private TextView mTitle;
    private TextView mDetail;
    private HeadBar mHeadBar;
    private String mImgUrl;
    private ProgressDialog mProgressDialog;
    private RadioGroup mRadioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        initHeadBar();
        initView();
        refreshUI();
    }

    private void initHeadBar() {
        mHeadBar = (HeadBar) findViewById(R.id.content_headbar);
        mRadioGroup = (RadioGroup) findViewById(R.id.rg_tab);
        mRadioGroup.setVisibility(View.GONE);
        mHeadBar.setTitleVisiable();
        mHeadBar.setTitleText("news");
        mHeadBar.setLeftSecondButtonNoused();
        mHeadBar.setRightSecondButtonNoused();
        mHeadBar.setRightButtonNoused();
    }

    private void initView() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setTitle("努力加载中~~");
        mProgressDialog.show();
        mImagView = (ImageView) findViewById(R.id.content_background_img);
        mTitle = (TextView) findViewById(R.id.content_title);
        mDetail = (TextView) findViewById(R.id.content_detail);
    }

    private void refreshUI() {
        Intent intent = getIntent();
        String mGetTmp;
        if (intent.getStringExtra("type").equals("sina")) {
            mGetTmp = mGetUrl;
        } else {
            mGetTmp = mGetUrl2;
        }
        Ion.with(this)
                .load(mGetTmp+intent.getStringExtra("id"))
                .asJsonObject()
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        try {
                            mImgUrl = result.get("img").getAsString();
                            String pp = result.get("news").getAsString();
                            String tmp = result.get("title").getAsString();
                            String[] titlep = tmp.split("_");
                            mTitle.setText(titlep[0]);
                            mDetail.setText(Html.fromHtml(pp));
                            mProgressDialog.dismiss();
                            Ion.with(mImagView)
                                    .placeholder(R.drawable.loading)
                                    .error(R.drawable.error)
                                    .load(mImgUrl);

                        } catch (Exception ee) {
                            ee.printStackTrace();
                        }
                    }
                });
    }

}
