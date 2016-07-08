package me.ewriter.art_chapter2.binderpool;

import android.os.RemoteException;

/**
 * Created by Zubin on 2016/7/8.
 * 业务模块的 AIDL 的实现,定义是 ICompute.aidl
 */
public class ComputeImpl extends ICompute.Stub {
    @Override
    public int add(int a, int b) throws RemoteException {
        return a + b;
    }
}
