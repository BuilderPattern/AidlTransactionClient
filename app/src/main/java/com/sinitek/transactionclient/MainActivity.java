package com.sinitek.transactionclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.Toast;

import com.sinitek.aidl.IMathAidl;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = new Intent("com.sinitek.aidl.service");
        intent.setPackage("com.sinitek.transactionserver");//5.0之后启动其他程序service要设置包名，该项目 minSdkVersion 21
        bindService(intent, mConn, Context.BIND_AUTO_CREATE);
    }

    private ServiceConnection mConn = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IMathAidl mathAidl = IMathAidl.Stub.asInterface(service);
            try {
                double result = mathAidl.add(1, 1);
                Toast.makeText(MainActivity.this, "计算结果为：" + result, Toast.LENGTH_SHORT).show();
                mathAidl.play(result + "'西京一村夫'的SINITEK冲刺之路.mp4");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };
}