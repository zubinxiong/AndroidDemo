// IBinderPool.aidl
package me.ewriter.art_chapter2.binderpool;

// Declare any non-default types here with import statements

interface IBinderPool {

     /**
     * Binder 连接池
     * @param binderCode, the unique token of specific Binder<br/>
     * @return specific Binder who's token is binderCode.
     */
    IBinder queryBinder(int binderCode);
}
