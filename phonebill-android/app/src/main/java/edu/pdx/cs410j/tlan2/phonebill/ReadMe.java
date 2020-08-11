package edu.pdx.cs410j.tlan2.phonebill;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ReadMe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_me);

        configureBackToHelpMenu();
    }

    private void configureBackToHelpMenu() {
        Button backToHelpMenu = (Button) findViewById(R.id.backToHelpMenu);
        backToHelpMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}