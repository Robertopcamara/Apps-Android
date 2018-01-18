package com.example.roberto.toys2;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.Buffer;
import java.util.Random;
import java.util.UUID;

import static java.lang.String.valueOf;

public class MainActivity extends AppCompatActivity {
    private static final int SOLICITA_ATIVACAO = 1;
    private static final int SOLICITA_CONEXAO = 2;
    private static final int MESSAGE_READ = 3;
    ConnectedThread connectedThread;
    Handler mHandler;
    StringBuilder dadosBluetooth = new StringBuilder();
    BluetoothAdapter toyBluetoothAdapter = null;
    BluetoothDevice toyDevice = null;
    BluetoothSocket toySocket = null;
    Spinner animais;
    ListView silabas;
    Button btnaleatorio, btnConexao, btnEscolher;
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

        btnEscolher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(conexao) {
                    connectedThread.enviar("");
                } else {
                    Toast.makeText(getApplicationContext(), "O bluetooth não está conectado", Toast.LENGTH_LONG).show();
                }
            }
        });
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == MESSAGE_READ) {
                    String recebidos = (String) msg.obj;
                    dadosBluetooth.append(recebidos);

                    int fimInfo = dadosBluetooth.indexOf("}");

                    if(fimInfo > 0){
                        String dadosCompletos = dadosBluetooth.substring(0, fimInfo);

                        int tamanhoInfo = dadosCompletos.length();

                        if(dadosBluetooth.charAt(0) == '{') {
                            String dadosFinais = dadosBluetooth.substring(1, tamanhoInfo);
                            Log.d("Recebidos", dadosFinais);
                        }
                        dadosBluetooth.delete(0, dadosBluetooth.length());

                    }
                }

            }
        };
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
                        connectedThread = new ConnectedThread(toySocket);
                        connectedThread.start();
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
    }private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1024];  // buffer store for the stream
            int bytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);

                    String dadosBt = new String(buffer, 0, bytes);

                    // Send the obtained bytes to the UI activity
                    mHandler.obtainMessage(MESSAGE_READ, bytes, -1, dadosBt).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }
        }

        public void enviar(String dadosEnviar) {
            byte[] msgBuffer = dadosEnviar.getBytes();
            try {
                mmOutStream.write(msgBuffer);
            } catch (IOException e) { }
        }

    }
}
