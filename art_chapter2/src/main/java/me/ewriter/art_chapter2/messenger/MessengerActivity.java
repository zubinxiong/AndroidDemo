package me.ewriter.art_chapter2.messenger;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import me.ewriter.art_chapter2.R;
import me.ewriter.art_chapter2.utils.MyConstants;

public class MessengerActivity extends AppCompatActivity {

    private static final String TAG = "MessengerActivity";

    private Messenger mService;

    // 为了接收服务端处理后返回的消息，客户端也需要一个接收消息的Messenger 和 Handler
    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MyConstants.MSG_FROM_SERVICE:
                    Log.i(TAG, "receive msg from service: " + msg.getData().getString("reply"));
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    private Messenger mGetReplyMessenger = new Messenger(new MessengerHandler());

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = new Messenger(service);
            // Message 能使用的载体只有what, arg1, arg2, Bundle 以及 replyTo
            Message msg = Message.obtain(null, MyConstants.MSG_FROM_CLIENT);
            Bundle data = new Bundle();
            data.putString("msg", "hello, this is client");
            msg.setData(data);
            // 需要把客户端的Messenger 对象传递给 replyTo 对象
            msg.replyTo = mGetReplyMessenger;
            try {
                // Messenger 传递数据必须放入 Message 中
                mService.send(msg);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);
        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }
}
