package edu.pdx.cs410j.tlan2.phonebill;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int CALCULATOR_RESULT = 43;
    private ArrayAdapter<Double> results;

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

        ListView resultsView = findViewById(R.id.results);
        results = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        resultsView.setAdapter(results);

//        numbers.add(1.0);
//        numbers.add(2.0);
//        numbers.add(3.0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode, data); // like get or fetch

        if (requestCode == CALCULATOR_RESULT && resultCode == RESULT_OK && data != null){
            if(data != null){
                if (data.hasExtra("Sum")) {
                    Operation result = (Operation) data.getSerializableExtra("Sum");
                    Toast.makeText(this, "Result was " + result, Toast.LENGTH_LONG).show();
                    results.add(result.getValue());
                }
                if (data.hasExtra("PhoneCall")){
                    PhoneCall result = (PhoneCall) data.getSerializableExtra("PhoneCall");
                    Toast.makeText(this, "PhoneCall was " + result, Toast.LENGTH_LONG).show();
                }

                if (data.hasExtra("PhoneBill")){
                    PhoneBill result = (PhoneBill) data.getSerializableExtra("PhoneBill");
                    Toast.makeText(this, "PhoneBill was " + result, Toast.LENGTH_LONG).show();
                }
            }

        }
    }

    private class ResultsAdapter extends ArrayAdapter<Double> {
        public ResultsAdapter(@NonNull Context context, int resource, @NonNull List<Double> objects){
            super(context, resource, objects);
        }
    }
}