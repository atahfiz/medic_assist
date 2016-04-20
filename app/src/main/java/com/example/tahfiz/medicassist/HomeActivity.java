package com.example.tahfiz.medicassist;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;

import com.example.tahfiz.medicassist.Contacts.ContactActivity;

public class HomeActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NearbyPanel nearbyPanel;
    private GraphPanel graphPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        nearbyPanel = new NearbyPanel();
        setFragment(nearbyPanel);

        graphPanel = new GraphPanel();
        setFragment(graphPanel);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()){
            case R.id.action_settings:
                return true;

            case R.id.action_add:
                startActivity(new Intent(HomeActivity.this,ContactActivity.class));
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setFragment(Fragment fragment) {
        FragmentManager manager = getFragmentManager();

        if (manager.findFragmentById(R.id.nearby_panel_container) == null && fragment == nearbyPanel) {
            manager.beginTransaction().add(R.id.nearby_panel_container, fragment).commit();

        }

        if (manager.findFragmentById(R.id.graph_panel_container) == null && fragment == graphPanel){
            manager.beginTransaction().add(R.id.graph_panel_container, fragment).commit();
        }
    }
}
