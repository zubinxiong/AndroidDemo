package me.ewriter.chapter9.systemtest;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import me.ewriter.chapter9.R;

public class AMProcessTestActivity extends AppCompatActivity {

    private ListView mListView;
    private List<AMProcessInfo> mAmProcessInfoList;
    private ActivityManager mActivityManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amprocess_test);

        mListView = (ListView) findViewById(R.id.listView_am_process);
        mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        mListView.setAdapter(new AMProcessAdapter(this, getRunningProcessInfo()));
    }

    private List<AMProcessInfo> getRunningProcessInfo() {
        mAmProcessInfoList = new ArrayList<AMProcessInfo>();

        List<ActivityManager.RunningAppProcessInfo> appProcessList =
                mActivityManager.getRunningAppProcesses();

        for (int i = 0; i < appProcessList.size(); i++) {
            ActivityManager.RunningAppProcessInfo info =
                    appProcessList.get(i);
            int pid = info.pid;
            int uid = info.uid;
            String processName = info.processName;
            int[] memoryPid = new int[]{pid};
            Debug.MemoryInfo[] memoryInfo = mActivityManager
                    .getProcessMemoryInfo(memoryPid);
            int memorySize = memoryInfo[0].getTotalPss();

            AMProcessInfo processInfo = new AMProcessInfo();
            processInfo.setPid("" + pid);
            processInfo.setUid("" + uid);
            processInfo.setMemorySize("" + memorySize);
            processInfo.setProcessName(processName);
            mAmProcessInfoList.add(processInfo);
        }
        return mAmProcessInfoList;
    }
}
