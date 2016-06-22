package me.ewriter.art_chapter2.manager;

import android.os.IBinder;

import me.ewriter.art_chapter2.aidl.IBookManager;

/**
 * Created by Zubin on 2016/6/22.
 */
public class BookManager {

    private IBookManager mBookManager;

    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            if (mBookManager == null)
                return;;
            mBookManager.asBinder().unlinkToDeath(mDeathRecipient, 0);
            mBookManager = null;
            // TODO: 2016/6/22 这里重新绑定远程Service
        }
    };
}
