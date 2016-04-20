package com.example.tahfiz.medicassist;

import android.content.ComponentName;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;

import com.example.tahfiz.medicassist.NearbyPlaces.GPSTracker;

import me.tatarka.support.job.JobInfo;
import me.tatarka.support.job.JobScheduler;

public class SettingActivity extends AppCompatActivity {

    private static final int JOB_ID = 100;
    private JobScheduler jobScheduler;
    private CheckBox chckBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        chckBtn = (CheckBox) findViewById(R.id.chckbtn);

        if (chckBtn.isChecked()){
            jobScheduler = JobScheduler.getInstance(SettingActivity.this);
            constructJob();
        }
    }

    private void constructJob(){
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID,new ComponentName(this, GPSTracker.class));

        new GPSTracker(this);

        builder.setPeriodic(2000)//2 seconds
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true);

        jobScheduler.schedule(builder.build());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
