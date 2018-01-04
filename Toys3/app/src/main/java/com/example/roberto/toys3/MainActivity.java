package com.example.roberto.toys3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    Spinner sp;
    TextView display_data;
    String animais[] = {"Co-ru-ja","Ca-va-lo","Ca-chor-ro","Pa-to","Ga-to","Va-ca","Gan-so","Bur-ru","Por-co","Lo-bo","Sa-po","A-be-lha","Ca-bra","Pe-ru","Co-bra","Tu-ca-no"};
    ArrayAdapter <String> adapter;
    String record= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = (Spinner)findViewById(R.id.spinner);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,animais);

        display_data = (TextView)findViewById(R.id.display_result);

        sp.setAdapter(adapter);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //use postion value

                switch (position)

                {

                    case 0:

                        record = "Co-ru-ja";

                        break;

                    case 1:

                        record = "Ca-va-lo";

                        break;

                    case 2:

                        record = "Ca-chor-ro";

                        break;

                    case 3:

                        record = "Pa-to";

                        break;

                    case 4:

                        record = "Ga-to";

                        break;

                    case 5:

                        record = "Va-ca";

                        break;

                    case 6:

                        record = "Gan-so";

                        break;

                    case 7:

                        record = "Bur-ru";

                        break;

                    case 8:

                        record = "Por-co";

                        break;

                    case 9:

                        record = "Lo-bo";

                        break;

                    case 10:

                        record = "Sa-po";

                        break;

                    case 11:

                        record = "A-be-lha";

                        break;

                    case 12:

                        record = "Ca-bra";

                        break;

                    case 13:

                        record = "Pe-ru";

                        break;

                    case 14:

                        record = "Co-bra";

                        break;

                    case 15:

                        record = "Tu-ca-no";

                        break;

                }

            }

            @Override

            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

    }

    //set display button click to show result

    public void displayResult(View view)

    {

        display_data.setTextSize(18);

        display_data.setText(record);

    }

}
