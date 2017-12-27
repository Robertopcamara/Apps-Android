package com.example.roberto.toys2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Spinner animais;
    TextView silabas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        animais = (Spinner) findViewById(R.id.spinneranimal);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.animais_array, android.R.layout.simple_spinner_item);
        animais.setAdapter(adapter);

        silabas = (TextView) findViewById(R.id.textViewsilaba);
        ArrayAdapter adapter1 = ArrayAdapter.createFromResource(this, R.array., android.R.layout.select_dialog_item);
        silabas.setAdapter(adapter1);

    }



}
