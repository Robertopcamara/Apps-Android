package com.example.roberto.toys2;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
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
    ListView silabas;
    Button btnaleatorio, btnConexao, btnEscolher;
    TextView display_result;
    Context context = this;
    boolean conexao = false;

    private static String MAC = null;
    UUID TOY_UUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final MediaPlayer vacaSom = MediaPlayer.create(this, R.raw.vaca);
        final MediaPlayer bemteviSom = MediaPlayer.create(this, R.raw.bemtevi);
        final MediaPlayer cachorroSom = MediaPlayer.create(this, R.raw.cachorro);
        final MediaPlayer cavaloSom = MediaPlayer.create(this, R.raw.cavalo);
        final MediaPlayer elefanteSom = MediaPlayer.create(this, R.raw.elefante);
        final MediaPlayer galinhaSom = MediaPlayer.create(this, R.raw.galinha);
        final MediaPlayer gatoSom = MediaPlayer.create(this, R.raw.gato);
        final MediaPlayer golfinhoSom = MediaPlayer.create(this, R.raw.golfinho);
        final MediaPlayer griloSom = MediaPlayer.create(this, R.raw.grilo);
        final MediaPlayer leaoSom = MediaPlayer.create(this, R.raw.leao);
        final MediaPlayer loboSom = MediaPlayer.create(this, R.raw.lobo);
        final MediaPlayer macacoSom = MediaPlayer.create(this, R.raw.macaco);
        final MediaPlayer patoSom = MediaPlayer.create(this, R.raw.pato);
        final MediaPlayer peruSom = MediaPlayer.create(this, R.raw.peru);
        final MediaPlayer porcoSom = MediaPlayer.create(this, R.raw.porco);
        final MediaPlayer sapoSom = MediaPlayer.create(this, R.raw.sapo);
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






        btnaleatorio = (Button) findViewById(R.id.btnaleatorio);
        display_result = (TextView) findViewById(R.id.display_result);

        final String silabasitem[] = {"Ga-to","Ca-chor-ro","Lo-bo","Le-ão","Ma-ca-co","Ca-va-lo","Ga-li-nha","E-le-fan-te","Gri-lo","Gol-fi-nho","Pa-to","Pe-ru","Va-ca","Por-co","Sa-po","Bem-te-vi"};

        btnaleatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random randomGen = new Random();

                final int rando = randomGen.nextInt(16);
                display_result.setText(silabasitem[rando]);

                if(rando == 0 ){
                    connectedThread.enviar("gato");
                    gatoSom.start();
                }
                if(rando == 1 ){
                    connectedThread.enviar("cachorro");
                    cachorroSom.start();
                }
                if(rando == 2 ){
                    connectedThread.enviar("lobo");
                    loboSom.start();
                }
                if(rando == 3 ){
                    connectedThread.enviar("leao");
                    leaoSom.start();
                }
                if(rando == 4 ){
                    connectedThread.enviar("macaco");
                    macacoSom.start();
                }
                if(rando == 5 ){
                    connectedThread.enviar("cavalo");
                    cavaloSom.start();
                }
                if(rando == 6 ){
                    connectedThread.enviar("galinha");
                    galinhaSom.start();
                }
                if(rando == 7 ){
                    connectedThread.enviar("elefante");
                    elefanteSom.start();
                }
                if(rando == 8 ){
                    connectedThread.enviar("grilo");
                    griloSom.start();
                }
                if(rando == 9 ){
                    connectedThread.enviar("golfinho");
                    golfinhoSom.start();
                }
                if(rando == 10 ){
                    connectedThread.enviar("pato");
                    patoSom.start();
                }
                if(rando == 11 ){
                    connectedThread.enviar("peru");
                    peruSom.start();
                }
                if(rando == 12 ){
                    connectedThread.enviar("vaca");
                    vacaSom.start();
                }
                if(rando == 13 ){
                    connectedThread.enviar("porco");
                    porcoSom.start();
                }
                if(rando == 14 ){
                    connectedThread.enviar("sapo");
                    sapoSom.start();
                }
                if(rando == 15 ){
                    connectedThread.enviar("bemtevi");
                    bemteviSom.start();
                }

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
           /* while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);

                    String dadosBt = new String(buffer, 0, bytes);

                    // Send the obtained bytes to the UI activity
                    mHandler.obtainMessage(MESSAGE_READ, bytes, -1, dadosBt).sendToTarget();
                } catch (IOException e) {
                    break;
                }
            }*/
        }

        public void enviar(String dadosEnviar) {
            byte[] msgBuffer = dadosEnviar.getBytes();
            try {
                mmOutStream.write(msgBuffer);
            } catch (IOException e) { }
        }

    }
}
