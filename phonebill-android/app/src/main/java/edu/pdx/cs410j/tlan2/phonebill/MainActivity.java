package edu.pdx.cs410j.tlan2.phonebill;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_PHONE_CALL_RESULT = 43;
    public static final int PRINT_PHONE_BILL_RESULT = 43;

    private PhoneBillArrayList phoneBills = new PhoneBillArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button createPhoneCall = (Button) findViewById(R.id.openCreatePhoneCallActivity);
        createPhoneCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateNewPhoneCallActivity.class);
                startActivityForResult(intent, NEW_PHONE_CALL_RESULT);;
            }
        });

//        configureCreatePhoneCall();
        configurePrintPhoneBill();
        configureSearchPhoneBill();
        configureHelpMenu();
    }

    private void configurePrintPhoneBill() {
        Button createPhoneCall = (Button) findViewById(R.id.printPhoneBill);
        createPhoneCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PrintPhoneBillActivity.class);
                startActivityForResult(intent, PRINT_PHONE_BILL_RESULT);
            }
        });
    }

    private void configureSearchPhoneBill() {
        Button createPhoneCall = (Button) findViewById(R.id.searchPhoneBill);
        createPhoneCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PrintPhoneBillActivity.class);
                startActivityForResult(intent, PRINT_PHONE_BILL_RESULT);
            }
        });
    }

    private void configureHelpMenu() {
       Button helpMenu = (Button) findViewById(R.id.helpMenu);
       helpMenu.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(MainActivity.this, HelpMenuActivity.class));
           }
       });
    }

    private void saveResults(String name, PhoneBill bill) throws IOException {
        File dir = getDataDir();
        String fileName = name + ".txt";
        File file = new File(dir, fileName);
        try (PrintWriter pw = new PrintWriter(new FileWriter(file), true)) {
            pw.println(bill.getCustomer());
            Collection<PhoneCall> calls = bill.getPhoneCalls();
            for (PhoneCall call:calls) {
                pw.println(call.getAllCallInfo());
            }
            pw.flush();
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == NEW_PHONE_CALL_RESULT && resultCode == RESULT_OK){
            if (data != null){
                if (data.hasExtra("Name") && data.hasExtra("PhoneCall")){
                    String name = data.getStringExtra("Name");
                    Toast.makeText(this, "Name was " + name, Toast.LENGTH_LONG).show(); //*
                    PhoneCall call = (PhoneCall) data.getSerializableExtra("PhoneCall");
                    Toast.makeText(this, "New Phone Call Added: " + call.toString(), Toast.LENGTH_LONG).show();

                    PhoneBill bill = phoneBills.findPhoneBill(name);
                    bill.addPhoneCall(call);

                    try {
                        saveResults(name, bill);
                    } catch (IOException e) {
                        Toast.makeText(this, "While writing file " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            }
        }
    }
}