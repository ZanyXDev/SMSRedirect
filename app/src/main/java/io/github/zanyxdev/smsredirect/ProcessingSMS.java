package io.github.zanyxdev.smsredirect;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class ProcessingSMS extends Service {
    private static final String DEBUG_TAG = "SMSREDIRECT";
    private static int MAX_THREAD_POOL = 3;
    private static int MAX_TERMINATION_TIME = 5;
    ExecutorService executorService;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // Todo add binding
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(DEBUG_TAG, "ProcessingSMS onCreate");
        executorService = Executors.newFixedThreadPool(MAX_THREAD_POOL);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(DEBUG_TAG, "ProcessingSMS onDestroy");
        try {
            Log.d(DEBUG_TAG, "Processing executorService.shutdown()");
            executorService.shutdown();
            executorService.awaitTermination(MAX_TERMINATION_TIME, TimeUnit.SECONDS);

        } catch (InterruptedException e) {
            e.printStackTrace();
            Log.d(DEBUG_TAG,"Task interrupted.");
        }finally {
            if (!executorService.isTerminated()){
                Log.d(DEBUG_TAG,"Cancel not finished task.");
            }
            executorService.shutdownNow();
            Log.d(DEBUG_TAG,"ExecutorService shutdown Now");
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(DEBUG_TAG, "MyService onStartCommand");
/*
        int time = intent.getIntExtra(MainActivity.PARAM_TIME, 1);
        int task = intent.getIntExtra(MainActivity.PARAM_TASK, 0);

        MyRun mr = new MyRun(startId, time, task);
        executorService.execute(mr);
*/
        return super.onStartCommand(intent, flags, startId);
    }
}
