package com.example.mrweij.demo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mrweij.demo.mode.MessageMode;
import com.example.mrweij.demo.mode.OtherMessageMode;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class OnePageActivity extends Activity {
    private static final String TAG = "tw";
    private Context mContext;
    private TextView mTvMsgText;
    private Button mBtSendMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_one_page);
        mTvMsgText = (TextView) findViewById(R.id.tv_onepage_text);
        mBtSendMsg = (Button) findViewById(R.id.bt_onepage_sendmsg);
        mBtSendMsg.setOnClickListener(mBtSendMsgOnClickListener);

    }


    private View.OnClickListener mBtSendMsgOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EventBus.getDefault().post(new MessageMode("MessageMode"));
            EventBus.getDefault().post(new OtherMessageMode(01));
            EventBus.getDefault().postSticky(new MessageMode("Sticky MessageMode"));
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND, priority = 0)
    public void getOtherMessageMode(OtherMessageMode mOtherMessageMode) {
//        Looper.prepare();
        Log.i(TAG, "getOtherMessageMode: ");
//        Log.i(TAG, "run: Looper.myLooper() == Looper.getMainLooper() " + (Looper.myLooper() == Looper.getMainLooper())
//                + " myLooperId: " + (Looper.myLooper().getThread().getId())
//                + " mainLooperId: " + (Looper.getMainLooper().getThread().getId()));
//        mTvMsgText.setText(String.valueOf(mOtherMessageMode.getmOtherMessage()));
        mTvMsgText.setText("ninininini");
//        Looper.loop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMessage(MessageMode MessageMode, OtherMessageMode mOtherMessageMode) {
        Toast.makeText(mContext, String.valueOf(MessageMode.getmMessage() + "  " + mOtherMessageMode.getmOtherMessage()), Toast.LENGTH_LONG).show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getTwoOtherMessageMode(OtherMessageMode mOtherMessageMode, OtherMessageMode mOtherMessageMode02) {
        Toast.makeText(mContext, String.valueOf(mOtherMessageMode.getmOtherMessage() + "  " + mOtherMessageMode02.getmOtherMessage()), Toast.LENGTH_LONG).show();
    }
}
