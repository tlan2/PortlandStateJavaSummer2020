package edu.pdx.cs410j.tlan2.phonebill;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

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
            if(data != null && data.hasExtra("Sum")){
                double result = data.getDoubleExtra("Sum", 0.0);
                Toast.makeText(this, "Result was " + result, Toast.LENGTH_LONG).show();
            }

        }
    }
}