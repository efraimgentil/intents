package intents.efraimgentil.me.intents;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Point;
import android.net.Uri;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {

    static final int REQUEST_SELECT_CONTACT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.displayContactName).setEnabled(false);
        findViewById(R.id.displayContactPhone).setEnabled(false);

        findViewById(R.id.addContact).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_INSERT);
                intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
                intent.putExtra(ContactsContract.Intents.Insert.NAME, "Efraim Gentil");
                intent.putExtra(ContactsContract.Intents.Insert.EMAIL, "efraim.gentil@gmail.com");
                intent.putExtra(ContactsContract.Intents.Insert.PHONE, "987274131");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        findViewById(R.id.btnContactInfo).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType( ContactsContract.Contacts.CONTENT_TYPE );
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(intent, REQUEST_SELECT_CONTACT);
                }
            }
        });

        findViewById(R.id.btnAbrirGithub).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String url = "https://github.com/efraimgentil";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SELECT_CONTACT && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            Cursor cursor = getContentResolver().query( uri, null, null, null, null);
            if( cursor.moveToFirst() ){
                EditText contactName = (EditText) findViewById(R.id.displayContactName);
                int cursorId = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                contactName.setText( cursor.getString(cursorId)  );

                cursorId = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER );
                String hasPhone =  cursor.getString( cursorId );

                EditText contactPhone = (EditText) findViewById(R.id.displayContactPhone);
                String phoneNumber = "";
                if("1".equalsIgnoreCase( hasPhone ) ) {
                    cursorId = cursor.getColumnIndex(ContactsContract.Contacts._ID);
                    String id = cursor.getString(cursorId);
                    Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,null, null);
                    while (phones.moveToNext()) {
                        phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        break;
                    }
                    phones.close();
                }
                contactPhone.setText(phoneNumber);
            }
        }
    }

}