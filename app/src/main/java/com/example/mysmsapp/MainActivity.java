package com.example.mysmsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.service.autofill.TextValueSanitizer;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
//    private static final int TYPE_INCOMING_MESSAGE = 1;
//    private ListView messageList;
//    private MessageListAdapter messageListAdapter;
//    private ArrayList<Message> recordsStored;
//    private ArrayList<Message> listInboxMessages;
//    private ProgressDialog progressDialogInbox;
//    private CustomHandler customHandler;

    private ListView myTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myTextView =(ListView)findViewById(R.id.listView);
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);
    }

    public void Read_SMS(View view){
        ArrayList<String> smsInbox = new ArrayList<String>();
Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"),new String[]{"_id", "address", "date", "body"},null,null,null  );
cursor.moveToFirst();
//        int i= new Integer(0);
        while  (cursor.moveToNext())
        {
            String address = cursor.getString(1);
            String body = cursor.getString(3);

//            System.out.println("Mobile number: "+address);
//            System.out.println("Text: "+body);
//            i++;
//            myTextView.setText(cursor.getString(2));
//            myTextView.setText(cursor.getString(2));
//            if (cursor.getCount() > 0) {

                    String number = cursor.getString(cursor.getColumnIndexOrThrow("address"));
                    String text = cursor.getString(cursor.getColumnIndexOrThrow("body"));
                    smsInbox.add(number);
//            }
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,smsInbox
                 );

        myTextView.setAdapter(arrayAdapter);
        System.out.println(smsInbox);
//myTextView.setText(cursor.getString(12));
    }

//    public ArrayList<Message> fetchInboxSms(int type) {
//        ArrayList<Message> smsInbox = new ArrayList<Message>();
//
//        Uri uriSms = Uri.parse("content://sms");
//
//        Cursor cursor = this.getContentResolver()
//                .query(uriSms,
//                        new String[] { "_id", "address", "date", "body",
//                                "type", "read" }, "type=" + type, null,
//                        "date" + " COLLATE LOCALIZED ASC");
//        if (cursor != null) {
//            cursor.moveToLast();
//            if (cursor.getCount() > 0) {
//
//                do {
//
//                    Message message = new Message();
//                    message.messageNumber = cursor.getString(cursor
//                            .getColumnIndex("address"));
//                    message.messageContent = cursor.getString(cursor
//                            .getColumnIndex("body"));
//                    smsInbox.add(message);
//                } while (cursor.moveToPrevious());
//            }
//        }
//
//        return smsInbox;
//
//    }
}