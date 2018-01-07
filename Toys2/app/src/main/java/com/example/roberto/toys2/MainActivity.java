package com.example.roberto.toys2;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import static java.lang.String.valueOf;

public class MainActivity extends AppCompatActivity {
    private static final int SOLICITA_ATIVACAO = 1;
    BluetoothAdapter toyBluetoothAdapter = null;
    Spinner animais;
    ListView silabas;
    Button btnaleatorio, btnConexao;
    TextView display_data;
    Context context = this;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnConexao = (Button)findViewById(R.id.btnConexao);
        toyBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(toyBluetoothAdapter == null){
            Toast.makeText(getApplicationContext(), "Seu dispositivo não possui Bluetooth", Toast.LENGTH_LONG).show();
        } else if(!toyBluetoothAdapter.isEnabled()){
            Intent ativaBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(ativaBluetooth, SOLICITA_ATIVACAO);

        }

        btnConexao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        animais = (Spinner) findViewById(R.id.spinneranimal);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.animais_array, android.R.layout.simple_spinner_item);
        animais.setAdapter(adapter);

        display_data = (TextView) findViewById(R.id.display_result);


        Button btnaleatorio = (Button) findViewById(R.id.btnaleatorio);

        btnaleatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] array = context.getResources().getStringArray(R.array.silabas_array);
                String randomStr = array[new Random().nextInt(array.length)];





                // aqui voce faz alguma coisa ou pode chamar uma funcao
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case SOLICITA_ATIVACAO:
                if(resultCode== Activity.RESULT_OK){
                    Toast.makeText(getApplicationContext(), "Bluetooth ativado", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Bluetooth não ativado, o aplicativo será encerrado", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
        }
    }
}
