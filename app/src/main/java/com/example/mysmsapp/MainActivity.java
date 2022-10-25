package com.example.mysmsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Telephony;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainActivity extends AppCompatActivity {

   public class Conversation{String number; List<Message> message;
//       public Conversation(String number, List<android.os.Message> msg) {
//           this.number=number;
//           this.message=msg;
//       }
       public Conversation(String number, List<Message> msg) {
           this.number=number;
           this.message=msg;
       }
   }
   public class Message{String number; String body; String date;
       public Message(String number, String body, String smsDate) {
           this.number=number;
           this.body=body;
           this.date=smsDate;
       }
   }
    private ListView myTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myTextView =(ListView)findViewById(R.id.listView);
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);
        HashMap<String, List<Message> > newMap= getSmsConversation(this, "9999288548");
        System.out.println("hey");
//        for(Conversation conv: conversationContent){
////            System.out.println(conv.message.get(0).number);
////            System.out.println(conv.message.get(0).body);
//            if(conv.message.get(0).number=="AD-AIRTEL"){
//                System.out.println("hello");
//                System.out.println(conv.message.get(0).body);
//            }
//        }

//        System.out.println("this is it " + conversationContent.get(1).message.get(0).number);
//        System.out.println("this is it " + conversationContent.get(2).message.get(1).number);
//        System.out.println("this is it " + conversationContent.get(3).message.get(0).number);
        for (String name: newMap.keySet()) {
            String value = newMap.get(name).toString();
            System.out.println(name + " " + value);
        }

        System.out.println("this is the answer" + newMap.toString());
    }
    public void Read_SMS(View view){
        ArrayList<String> smsInbox = new ArrayList<String>();
Cursor cursor = getContentResolver().query(Uri.parse("content://sms/inbox"),new String[]{"_id", "address", "date", "body","type","read"},null,null,null  );
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
            String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
            String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
                    smsInbox.add(number);
//            }
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,smsInbox
                 );
        myTextView.setAdapter(arrayAdapter);
        System.out.println(smsInbox);
        HashMap<String, List<Message> > newMap= getSmsConversation(this, "9999288548");
        System.out.println("hey");
        System.out.println(newMap);
//myTextView.setText(cursor.getString(12));
    }

    public HashMap<String, List<Message> > getSmsConversation(Context context, String number) {
//        Cursor cursor = context.contentResolver.query(Telephony.Sms.CONTENT_URI, null, null, null, null);
        Cursor cursor =  getContentResolver().query(Uri.parse("content://sms"),new String[]{"_id", "address", "date", "body","type","read"},null,null,null  );

        ArrayList<String> numbers = new ArrayList<>();
        ArrayList<Message> messages = new ArrayList<>();
        ArrayList<Conversation> results = new ArrayList<>();

        while (cursor != null && cursor.moveToNext()) {
            String smsDate = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.DATE));
            String numberr = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.ADDRESS));
             String body = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.BODY));
            numbers.add(numberr);
            messages.add(new Message(numberr, body, smsDate));
        }
//        cursor?.close()
        HashMap<String, List<Message> > newMap= new HashMap<>();
for(String numberOf:numbers){
System.out.println(numberOf);

    if(!results.contains(numberOf)){

        for(Message msgs:messages){
            if(!newMap.containsKey(msgs.number)){
            newMap.put(msgs.number,new ArrayList<>());
        }
            newMap.get(msgs.number).add(msgs);
            if(msgs.number!=null)
            results.add(new Conversation(msgs.number, newMap.get(msgs.number)));
        }
System.out.println("final hashmap");
//        List<Message> msg = messages.stream().filter(message->message.number==numberOf).collect(Collectors.toCollection(ArrayList::new));
//            results.add(new Conversation(number,  msg));
    }
}
        if (number != null) {
            results = results.stream().filter(result->result.number==number).collect(Collectors.toCollection(ArrayList::new));
        }
//        if (number != null) {
//            results = results.filter { it.number == number } as ArrayList<Conversation>
//        }
        return newMap;
    }
    }

