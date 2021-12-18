package com.manolo.workermanager.thread;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;


public class Speaker extends Worker {

    public static String TAG = "PROGRESS";
    boolean cicle = true;
    int count = 0;

    public Speaker(@NonNull Context context,
                   @NonNull WorkerParameters params){
        super(context,params);
    }

    @NonNull
    @Override
    public Result doWork() {
        try{
            operation();
            return Result.success();
        }catch(Exception e){
            return Result.failure();
        }
    }


    public void operation(){
        while (cicle){

            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(isStopped()){
                cicle = false;
            }else{
                setProgressAsync(
                        new Data.Builder()
                                .putString(TAG, Integer.toString(count++))
                                .build()
                );
            }

        }

    }


}
