package com.alexaguilar.activarbluetooth;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int CODIGO_SOLICITUD_PERMISO = 1;
    private static final int CODIGO_SOLICITUD_HABILITAR_BLUETOOTH = 0;
    private Context context;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        activity = this;
    }

    public void habilitarBluetooth(View v){

        solicitarPermiso();

        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if ( mBluetoothAdapter == null ){
            Toast.makeText(MainActivity.this, "Tu dispositivo no tiene Bluetooth",Toast.LENGTH_SHORT).show();
        }

        if( !mBluetoothAdapter.isEnabled() ){
            Intent habilitarBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(habilitarBluetooth, CODIGO_SOLICITUD_HABILITAR_BLUETOOTH);
        }

    }

    public boolean checarEstatusPermiso(){
        int resultado = ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH);

        if ( resultado == PackageManager.PERMISSION_GRANTED){
            return true;
        }else {
            return false;
        }
    }

    public void solicitarPermiso(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.BLUETOOTH)){
            Toast.makeText(MainActivity.this,"El permiso ya fue otorgado, si lo deseas puedes activarlo en los ajustes de la aplicacion", Toast.LENGTH_LONG).show();
        }else{
            ActivityCompat.requestPermissions(activity,new String[] {Manifest.permission.BLUETOOTH}, CODIGO_SOLICITUD_PERMISO);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch(requestCode){
            case CODIGO_SOLICITUD_PERMISO:

                if( checarEstatusPermiso() ){
                    Toast.makeText(MainActivity.this,"Ya esta activo el permiso para Bluetooth", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this,"No esta activo el permiso para Bluetooth", Toast.LENGTH_LONG).show();
                }


                break;

        }

    }
}
