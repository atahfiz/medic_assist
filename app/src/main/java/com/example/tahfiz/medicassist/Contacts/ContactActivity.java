package com.example.tahfiz.medicassist.Contacts;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.tahfiz.medicassist.R;

public class ContactActivity extends AppCompatActivity implements ContactPickFragment.ContactPickListener {

    private Toolbar toolbar;
    private ContactData contact;
    private ContactListFragment contactListFragment;
    private ContactRepo repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        contactListFragment = new ContactListFragment();
        repo = new ContactRepo(this);

        setFragment(contactListFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contact, menu);
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

        if (id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void sendContactData(Intent data) {
        Cursor cursor = null;
        Cursor phoneCursor = null;
        contact = new ContactData();

        try {
            String name = null;
            String phoneNum = null;
            String photo = null;

            Uri uri = data.getData();

            //Query the content uri
            cursor = getContentResolver().query(uri, null, null, null, null);
            cursor.moveToFirst();

            String phoneID = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

            String[] phoneProj = new String[]{
                    ContactsContract.CommonDataKinds.Phone.NUMBER,
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID
            };

            phoneCursor = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    phoneProj,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                    new String[]{phoneID},
                    null
            );

            name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

            if (phoneCursor.moveToFirst()){
                phoneNum = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                photo = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
            }
            cursor.close();
            phoneCursor.close();

            contact.setName(name);
            contact.setPhoneNum(phoneNum);
            contact.setPhoto(photo);

            //Check wheter Contact Data exists or nor
            if (contact != null && (!repo.checkContact(contact.getPhoneNum()))){
                repo.insertContact(contact);
                System.out.println("Contact has been insert");
            }else {
                Toast.makeText(this,"Contact already exist",Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setFragment(Fragment fragment) {
        FragmentManager manager = getFragmentManager();

        if (manager.findFragmentById(R.id.listContainer) == null){
            manager.beginTransaction().add(R.id.listContainer,fragment).commit();
        }
    }
}
