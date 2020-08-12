package edu.pdx.cs410j.tlan2.phonebill;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SearchPhoneBillActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_phone_bill1);

        configureBackToMainMenu();
    }

    private void configureBackToMainMenu() {
        Button backToMainMenu = (Button) findViewById(R.id.backSearchPhoneBill);
        backToMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}