package edu.pdx.cs410j.tlan2.phonebill;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final int CALCULATOR_RESULT = 43;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button searchPhoneBill = findViewById(R.id.searchPhoneBill);
        searchPhoneBill.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
//                Log.i("MainActivity", "The button was clicked");
//                Toast.makeText(MainActivity.this, "I was clicked", Toast.LENGTH_LONG).show(); // "I was clicked" appears for a bit
                Intent intent = new Intent(MainActivity.this, CalculatorActivity.class);
                startActivityForResult(intent, CALCULATOR_RESULT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode, data); // like get or fetch

        if (requestCode == CALCULATOR_RESULT && resultCode == RESULT_OK && data != null){
            if(data != null){
                if (data.hasExtra("Sum")) {
                    Operation result = (Operation) data.getSerializableExtra("Sum");
                    Toast.makeText(this, "Result was " + result, Toast.LENGTH_LONG).show();
                }
                if (data.hasExtra("PhoneCall")){
                    PhoneCall result = (PhoneCall) data.getSerializableExtra("PhoneCall");
                    Toast.makeText(this, "PhoneCall was " + result, Toast.LENGTH_LONG).show();
                }
            }

        }
    }
}