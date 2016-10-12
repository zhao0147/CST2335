package com.example.sulin.lab1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ChatWindow extends AppCompatActivity {

    protected static final String ACTIVITY_NAME = "ChatWindow";

    protected ListView lvShow;
    protected EditText etMessage;
    protected Button btnSend;
    protected ArrayList<String> alChatMessage;
    protected ChatAdapter messageAdapter;

    protected SQLiteDatabase dbWrite;   //database for write

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        alChatMessage = new ArrayList<>();
        lvShow = (ListView)findViewById(R.id.lvShow);
        etMessage = (EditText)findViewById(R.id.textMessage);
        btnSend = (Button)findViewById(R.id.btnSend);

        //create database
        ChatDatabaseHelper dbHelper = new ChatDatabaseHelper(this);
        dbWrite = dbHelper.getWritableDatabase();

        //read message from table
        Cursor cursor = dbWrite.query(ChatDatabaseHelper.TABLE_NAME, new String[]{ChatDatabaseHelper.KEY_MESSAGE},
                null, null, null, null, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast() ) {
            String strTmp = cursor.getString(cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE));
            alChatMessage.add(strTmp);
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + strTmp);
            cursor.moveToNext();
        }
        Log.i(ACTIVITY_NAME, "Cursor's column count =" + cursor.getColumnCount());
        for (int i = 0; i < cursor.getColumnCount(); i++) {
            Log.i(ACTIVITY_NAME, "Column" + (i+1) + "; " + cursor.getColumnName(i));
        }

        //button click event
        btnSend.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String strTmp = etMessage.getText().toString();
                alChatMessage.add(strTmp);
                messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount()/ getView()
                etMessage.setText("");

                //save message to database
                ContentValues cValue = new ContentValues();
                cValue.put(ChatDatabaseHelper.KEY_MESSAGE, strTmp);
                dbWrite.insert(ChatDatabaseHelper.TABLE_NAME, "NULL", cValue);
            }
        });

        //in this case, “this” is the ChatWindow, which is-A Context object
        messageAdapter = new ChatAdapter( this );
        lvShow.setAdapter(messageAdapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //close database
        dbWrite.close();
    }

    private class ChatAdapter extends ArrayAdapter<String> {
        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount() {
            return alChatMessage.size();
        }

        public String getItem(int position) {
            return alChatMessage.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();

            View result = null ;
            if(position%2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);

            TextView message = (TextView)result.findViewById(R.id.message_text);
            message.setText(getItem(position)); // get the string at position
            return result;
        }
    }
}
