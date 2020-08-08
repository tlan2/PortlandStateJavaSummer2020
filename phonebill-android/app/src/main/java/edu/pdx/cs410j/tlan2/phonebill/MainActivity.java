package edu.pdx.cs410j.tlan2.phonebill;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

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
                startActivity(intent);
            }
        });
    }
}