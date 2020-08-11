package edu.pdx.cs410j.tlan2.phonebill;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HelpMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_menu);

        configureReadMe();
        configureBackToMainMenu();
    }

    private void configureReadMe() {
        Button aboutApp = (Button) findViewById(R.id.aboutApp);
        aboutApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HelpMenu.this, ReadMe.class));
            }
        });
    }

    private void configureBackToMainMenu() {
        Button helpBackToMainMenu = (Button) findViewById(R.id.helpBackToMainMenu);
        helpBackToMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}