package com.manolo.workermanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import android.os.Bundle;
import android.util.Log;

import com.manolo.workermanager.thread.Speaker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    WorkRequest work;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onResume() {
        super.onResume();
        crearHilo();
    }

    public void crearHilo(){
        cancell();
        work = new OneTimeWorkRequest.Builder(
                Speaker.class
        )
                .build();
        WorkManager.getInstance(this).enqueue(work);

        WorkManager.getInstance(this)
                .getWorkInfoByIdLiveData(work.getId())
                .observe(this, workInfo -> {
                    if(workInfo != null){
                        String resp = workInfo.getProgress().getString(Speaker.TAG);

                        if(workInfo.getState() == WorkInfo.State.FAILED){
                            crearHilo();
                        }
                        if(workInfo.getState() == WorkInfo.State.RUNNING && resp != null ){
                            Log.d("Manolo",resp);
                            String timeStamp = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
                            Log.d("Manolo",timeStamp);
                        }

                    }
                });

        Log.d("Manolo","created");

    }

    public void cancell(){
        WorkManager.getInstance(this).cancelAllWork();
    }

    @Override
    protected void onStop() {
        super.onStop();
        cancell();
        //WorkManager.getInstance(this).cancelWorkById(work.getId());
        Log.d("Manolo","Destroy");
    }
}