package com.example.mrweij.demo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mrweij.demo.mode.MessageMode;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends Activity {
    private Context mContext;
    private TextView mTextView;
    private TextView mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_main);
        mTextView = (TextView) findViewById(R.id.textview);
        mButton = (Button) findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, OnePageActivity.class));
            }
        });
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
//        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessageMode(MessageMode mMessageMode) {
        Log.i("Wj", "getMessageMode: ");
        mTextView.setText(mMessageMode.getmMessage());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessageModeToast(MessageMode mMessageMode) {
        Log.i("Wj", "getMessageModeToast: ");
        Toast.makeText(mContext, mMessageMode.getmMessage(), Toast.LENGTH_LONG).show();
    }
}
