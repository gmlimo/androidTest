package edu.upbc.broadcastreceiver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.IntentFilter;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        IntentFilter intentFilter = new IntentFilter("edu.upbc.DATA_TRANSFER");
        MyReceiver objReceiver = new MyReceiver();
        registerReceiver(objReceiver, intentFilter);
    }
}