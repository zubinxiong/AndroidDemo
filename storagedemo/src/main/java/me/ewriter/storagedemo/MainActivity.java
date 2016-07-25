package me.ewriter.storagedemo;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.storage.StorageManager;
import android.support.v4.provider.DocumentFile;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    private static final int REQUEST_DOCUMENT_TREE_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.text);
    }


    public void BtnExternal1(View view) {
        String output = "";
        //  getExternalStorageDirectory 获取的是 外置sd 卡1 的根目录
        output += "A:getExternalStorageDirectory=  " + Environment.getExternalStorageDirectory().getAbsolutePath() + "\n";

        output += "getExternalFilesDir=   " + getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + "\n";

        output += "B:getExternalCacheDir= " + getExternalCacheDir();
        textView.setText(output);


    }

    public void BtnExternal2(View view) {

        // 4.4 之后 Context.getExternalFilesDirs(null)(注意最后多了一个’s’)，它返回的是一个字符串数组。第0个就是主存储路径，第1个是副存储路径（如果有的话）
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
//            // 这个返回的 file 类型就是 data/package/files
//            // File[] files = this.getExternalFilesDirs(null);
//            // 如果指定了 type ，返回的就是 data/package/files/Download
//            File[] files = this.getExternalFilesDirs(Environment.DIRECTORY_DOWNLOADS);
//            String output = "";
//            for (int i = 0; i < files.length; i++) {
//                output += files[i].getAbsolutePath() + "\n";
//            }
//            textView.setText(output);
//        }

        // 4.4 以下 最好使用反射,然后通过 uri 来操作
        Intent intent = new Intent();
        intent.setAction("android.intent.action.OPEN_DOCUMENT_TREE");
        startActivityForResult(intent, REQUEST_DOCUMENT_TREE_CODE);
//
//        String extSdcardPath = System.getenv("ENV_EXTERNAL_STORAGE");
//        Toast.makeText(MainActivity.this, extSdcardPath, Toast.LENGTH_SHORT).show();


//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
//            File[] test = this.getExternalCacheDirs();
//            for (int i = 0; i < test.length; i++) {
//                Log.d("abc", test[i].getAbsolutePath());
//            }
//
//            writeSomeThingToSD("sd.text", test[1].getAbsolutePath());
//        } else {
//
//        }

//        List<StorageInfo> fileList = listAvaliableStorage(this);
//        String path = fileList.get(1).path + File.separator + "Android" + File.separator +
//                "data" + File.separator + "me.ewriter.storagedemo" + File.separator +
//                "cache" + File.separator + "test";
//
//        writeSomeThingToSD("123.txt", path);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_DOCUMENT_TREE_CODE && resultCode == RESULT_OK) {
            // 返回的inte 是 dat=content://com.android.externalstorage.documents/tree/C431-11E2: flg=0xc3
            Log.d("abc", data.toString());

            // http://stackoverflow.com/questions/26744842/how-to-use-the-new-sd-card-access-api-presented-for-lollipop
            // 这个 Uri 最好记录下来，不然每次都得让用户选择
            Uri uri = data.getData();
            DocumentFile pickedDir = DocumentFile.fromTreeUri(this, uri);
            Log.d("abc", "DocumentFile canwrite = " + pickedDir.canWrite() + "; canread = " + pickedDir.canRead());

            // List all existing files inside picked directory
            for (DocumentFile file : pickedDir.listFiles()) {
                Log.d("abc", file.getName() + " with size " + file.length());
            }

            // Create new File and write into it
            DocumentFile newFile = pickedDir.createFile("text/plain", "My Test");
            pickedDir.createDirectory("documentfile");
            try {
                OutputStream out = getContentResolver().openOutputStream(newFile.getUri());
                out.write("A longlong agp， 加个中文".getBytes());
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public List listAvaliableStorage(Context context) {
        ArrayList storagges = new ArrayList();
        StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        try {
            Class<?>[] paramClasses = {};
            Method getVolumeList = StorageManager.class.getMethod("getVolumeList", paramClasses);
            getVolumeList.setAccessible(true);
            Object[] params = {};
            Object[] invokes = (Object[]) getVolumeList.invoke(storageManager, params);
            if (invokes != null) {
                StorageInfo info = null;
                for (int i = 0; i < invokes.length; i++) {
                    Object obj = invokes[i];
                    Method getPath = obj.getClass().getMethod("getPath", new Class[0]);
                    String path = (String) getPath.invoke(obj, new Object[0]);
                    info = new StorageInfo(path);
                    File file = new File(info.path);
                    // 6.0 上还没读写的时候返回的是false，sd卡需要自己申请
                    Log.d("abc", "file = " + file.getAbsolutePath() + ";exits = " + file.exists() + "; isdirectory = " + file.isDirectory() + "; canwrite= " + file.canWrite() + ";canread= " + file.canRead());
                    if ((file.exists()) && (file.isDirectory())) {
                        Method isRemovable = obj.getClass().getMethod("isRemovable", new Class[0]);
                        String state = null;
                        try {
                            Method getVolumeState = StorageManager.class.getMethod("getVolumeState", String.class);
                            state = (String) getVolumeState.invoke(storageManager, info.path);
                            info.state = state;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (info.isMounted()) {
                            info.isRemoveable = ((Boolean) isRemovable.invoke(obj, new Object[0])).booleanValue();
                            storagges.add(info);
                        }
                    }
                }
            }
        } catch (NoSuchMethodException e1) {
            e1.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        storagges.trimToSize();

        return storagges;
    }


    public void BtnInternal(View view) {
        String output = "Internal\n";

        output += "A:  " + this.getFilesDir().getAbsolutePath() + "\n";
        // 会新建 data/data/package/app_demo
        output += "B:  " + this.getDir("demo", Context.MODE_PRIVATE).getAbsolutePath() + "\n";

        textView.setText(output);
    }

    public void writeSomeThingToSD(String fileName, String path) {

        try {
            File file = new File(path, fileName);

            if (!file.exists())
                file.mkdirs();

            FileOutputStream fos = new FileOutputStream(file);

            String output = "sd卡";

            byte[] bytes = output.getBytes();
            fos.write(bytes);
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readSomeThingFromSD(String fileName, String path) {

        try {
            File file = new File(path, fileName);
            FileInputStream fileInputStream = new FileInputStream(file);

            byte[] buffer = new byte[fileInputStream.available()];
            fileInputStream.read(buffer);


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }



    public class StorageInfo {
        public String path;
        public String state;
        public boolean isRemoveable;

        public StorageInfo(String path) {
            this.path = path;
        }

        public boolean isMounted() {
            return "mounted".equals(state);
        }
    }

}
