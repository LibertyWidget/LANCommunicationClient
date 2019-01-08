package com.lancommunicationclient;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText enidIpView;
    private EditText editView;
    private CommunicationClientUtil mCommunicationClientUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.enidIpView = findViewById(R.id.editIpView);
        this.editView = findViewById(R.id.editView);
        findViewById(R.id.commView).setOnClickListener(this);
        findViewById(R.id.sendView).setOnClickListener(this);
        this.mCommunicationClientUtil = new CommunicationClientUtil();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commView: {
                if (null == enidIpView)
                    break;
                String text = enidIpView.getText().toString();
                if (!TextUtils.isEmpty(text) && null != mCommunicationClientUtil)
                    mCommunicationClientUtil.connect(text);
            }
            break;
            case R.id.sendView: {
                String text = editView.getText().toString();
                if (!TextUtils.isEmpty(text) && null != mCommunicationClientUtil)
                    mCommunicationClientUtil.sendMsg(text);
            }
            break;
        }
    }

}
