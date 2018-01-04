package com.example.roberto.toys2;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Spinner animais;
    ListView silabas;
    Button buttonaleatorio;
    TextView display_data;
    Context context = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        animais = (Spinner) findViewById(R.id.spinneranimal);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.animais_array, android.R.layout.simple_spinner_item);
        animais.setAdapter(adapter);


        display_data = (TextView) findViewById(R.id.display_result);

        Button buttonaleatorio = (Button) findViewById(R.id.buttonaleatorio);

        buttonaleatorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] array = context.getResources().getStringArray(R.array.silabas_array);
                String randomStr = array[new Random().nextInt(array.length)];// aqui voce faz alguma coisa ou pode chamar uma funcao
            }
        });


    }
}
