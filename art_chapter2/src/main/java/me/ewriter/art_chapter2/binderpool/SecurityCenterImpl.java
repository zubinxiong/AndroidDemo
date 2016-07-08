package me.ewriter.art_chapter2.binderpool;

import android.os.RemoteException;

/**
 * Created by Zubin on 2016/7/8.
 * 业务模块的 AIDL 的实现,定义是 ISecurityCenter.aidl
 */
public class SecurityCenterImpl extends ISecurityCenter.Stub{

    private static final char SECRET_CODE = '^';

    @Override
    public String encrypt(String content) throws RemoteException {
        char[] chars = content.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] ^= SECRET_CODE;
        }
        return new String(chars);
    }

    @Override
    public String decrypt(String password) throws RemoteException {
        return encrypt(password);
    }
}
