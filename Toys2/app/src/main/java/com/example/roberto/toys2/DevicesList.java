package com.example.roberto.toys2;

import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class DevicesList extends ListActivity {

    private BluetoothAdapter listBluetoothAdapter = null;
    static String ENDERECO_MAC = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayAdapter<String> ArrayBluetooth = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        listBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        Set<BluetoothDevice> devicespaired = listBluetoothAdapter.getBondedDevices();

        if(devicespaired.size()>0){
            for(BluetoothDevice device : devicespaired){
                String Btname = device.getName();
                String macBt = device.getAddress();
                ArrayBluetooth.add(Btname + "\n" + macBt);
            }
        }
        setListAdapter(ArrayBluetooth);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        String informacaoGeral = ((TextView) v).getText().toString();

        //Toast.makeText(getApplicationContext(), "Info:" + informacaoGeral, Toast.LENGTH_LONG).show();

        String macAdress = informacaoGeral.substring(informacaoGeral.length() - 17);

        Intent returMac = new Intent();
        returMac.putExtra(ENDERECO_MAC, macAdress);
        setResult(RESULT_OK, returMac);
        finish();
    }
}
