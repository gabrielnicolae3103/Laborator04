package lab03.eim.systems.cs.pub.contactsmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.provider.SyncStateContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button showAdditionalFields;
    EditText name;
    EditText phone;
    EditText email;
    EditText address;
    EditText job;
    EditText company;
    EditText website;
    EditText im;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        this.showAdditionalFields = findViewById(R.id.showAdditionalFields);
        this.name = findViewById(R.id.name);
        this.phone = findViewById(R.id.phone);
        this.email = findViewById(R.id.email);
        this.address = findViewById(R.id.address);
        this.job = findViewById(R.id.job);
        this.company = findViewById(R.id.company);
        this.website = findViewById(R.id.website);
        this.im = findViewById(R.id.im);

        this.showAdditionalFields.setOnClickListener(new ViewListener());

        Intent intent = getIntent();
        if (intent != null) {
            String phone2 = intent.getStringExtra("ro.pub.cs.systems.eim.lab04.contactsmanager.PHONE_NUMBER_KEY");
            if (phone2 != null) {
                phone.setText(phone2);
            } else {
                Toast.makeText(this, getResources().getString(R.string.phone_error), Toast.LENGTH_LONG).show();
            }
        }
    }

    public class ViewListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.showAdditionalFields: {
                    LinearLayout second = findViewById(R.id.secondContainer);
                    if (second.getVisibility() == View.GONE) {
                        second.setVisibility(View.VISIBLE);
                    } else {
                        second.setVisibility(View.GONE);
                    }
                    break;
                }
                case R.id.save: {
                    save();
                    break;
                }
                case R.id.cancel: {
                    setResult(Activity.RESULT_CANCELED, new Intent());
                    break;
                }
            }
        }
    }

    public void save() {
        Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
        intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
        if (name != null) {
            intent.putExtra(ContactsContract.Intents.Insert.NAME, name.toString());
        }
        if (phone != null) {
            intent.putExtra(ContactsContract.Intents.Insert.PHONE, phone.toString());
        }
        if (email != null) {
            intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email.toString());
        }
        if (address != null) {
            intent.putExtra(ContactsContract.Intents.Insert.POSTAL, address.toString());
        }
        if (job != null) {
            intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, job.toString());
        }
        if (company != null) {
            intent.putExtra(ContactsContract.Intents.Insert.COMPANY, company.toString());
        }
        ArrayList<ContentValues> contactData = new ArrayList<ContentValues>();
        if (website != null) {
            ContentValues websiteRow = new ContentValues();
            websiteRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Website.CONTENT_ITEM_TYPE);
            websiteRow.put(ContactsContract.CommonDataKinds.Website.URL, website.toString());
            contactData.add(websiteRow);
        }
        if (im != null) {
            ContentValues imRow = new ContentValues();
            imRow.put(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE);
            imRow.put(ContactsContract.CommonDataKinds.Im.DATA, im.toString());
            contactData.add(imRow);
        }
        intent.putParcelableArrayListExtra(ContactsContract.Intents.Insert.DATA, contactData);
//        startActivity(intent);
        startActivityForResult(intent, 0);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {
            case 0:
                setResult(resultCode, new Intent());
                finish();
                break;
        }
    }

}