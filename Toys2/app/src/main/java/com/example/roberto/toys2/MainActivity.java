package com.example.roberto.toys2;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
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

import java.io.IOException;
import java.util.Random;
import java.util.UUID;

import static java.lang.String.valueOf;

public class MainActivity extends AppCompatActivity {
    private static final int SOLICITA_ATIVACAO = 1;
    private static final int SOLICITA_CONEXAO = 2;
    BluetoothAdapter toyBluetoothAdapter = null;
    BluetoothDevice toyDevice = null;
    BluetoothSocket toySocket = null;
    Spinner animais;
    ListView silabas;
    Button btnaleatorio, btnConexao;
    TextView display_data;
    Context context = this;
    boolean conexao = false;

    private static String MAC = null;
    UUID TOY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");



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
            if(conexao){
                //Will desconnect
                try{
                    toySocket.close();
                    conexao = false;
                    btnConexao.setText("Conectar");
                    Toast.makeText(getApplicationContext(), "O bluetooth foi desconectado", Toast.LENGTH_LONG).show();
                } catch (IOException erro){
                    Toast.makeText(getApplicationContext(), "Correu um erro" +erro, Toast.LENGTH_LONG).show();

                }
            }else {
                //Will connect
                Intent openlist = new Intent(MainActivity.this, DevicesList.class);
                startActivityForResult(openlist, SOLICITA_CONEXAO);
            }
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
                if(resultCode == Activity.RESULT_OK){
                    Toast.makeText(getApplicationContext(), "Bluetooth ativado", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Bluetooth não ativado, o aplicativo será encerrado", Toast.LENGTH_LONG).show();
                    finish();
                }
                break;

            case SOLICITA_CONEXAO:
                if(resultCode == Activity.RESULT_OK){
                    MAC = data.getExtras().getString(DevicesList.ENDERECO_MAC);
                    //Toast.makeText(getApplicationContext(), "Mac:" +MAC, Toast.LENGTH_LONG).show();
                    toyDevice = toyBluetoothAdapter.getRemoteDevice(MAC);
                    try {

                        toySocket = toyDevice.createRfcommSocketToServiceRecord(TOY_UUID);
                        toySocket.connect();
                        conexao = true;
                        btnConexao.setText("Desconectar");
                        Toast.makeText(getApplicationContext(), "Conectado com: " + MAC, Toast.LENGTH_LONG).show();

                    } catch (IOException erro) {
                        conexao = false;
                        Toast.makeText(getApplicationContext(), "Ocorreu um erro" + erro, Toast.LENGTH_LONG).show();
                    }

                }else {
                    Toast.makeText(getApplicationContext(), "Falha na conexão", Toast.LENGTH_LONG).show();
                }
        }
    }
}
