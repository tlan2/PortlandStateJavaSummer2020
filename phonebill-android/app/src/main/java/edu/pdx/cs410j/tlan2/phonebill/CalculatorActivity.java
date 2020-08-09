package edu.pdx.cs410j.tlan2.phonebill;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CalculatorActivity extends AppCompatActivity {

    private double sum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        Button execute = findViewById(R.id.execute);
        execute.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                EditText leftOperand = findViewById(R.id.leftOperand);
                EditText rightOperand = findViewById(R.id.rightOperand);

                double left = Double.parseDouble(leftOperand.getText().toString());
                double right = Double.parseDouble(rightOperand.getText().toString());

                sum = left + right;

                TextView result = findViewById(R.id.result);
                result.setText(String.valueOf(sum));
            }
        });

        Button backToMain = findViewById(R.id.backToMain);
        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CalculatorActivity.this, MainActivity.class);
                intent.putExtra("Sum", sum);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}