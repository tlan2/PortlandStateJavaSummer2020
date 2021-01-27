package edu.pdx.cs410j.tlan2.phonebill;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_PHONE_CALL_RESULT = 43;
    public static final int PRINT_PHONE_BILL_RESULT = 44;
    private static final int SEARCH_PHONE_BILL_RESULT = 45;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureCreatePhoneCall();
        configurePrintPhoneBill();
        configureSearchPhoneBill();
        configureHelpMenu();
    }

    private void configureSearchPhoneBill() {
        Button search = (Button) findViewById(R.id.searchButton);
//        search.setEnabled(false);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchPhoneBillActivity1.class);
                startActivityForResult(intent, SEARCH_PHONE_BILL_RESULT);
            }
        });
    }

    private void configureCreatePhoneCall() {
        Button createPhoneCall = (Button) findViewById(R.id.openCreatePhoneCallActivity);
        createPhoneCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateNewPhoneCallActivity.class);
                startActivityForResult(intent, NEW_PHONE_CALL_RESULT);;
            }
        });
    }

    private void configurePrintPhoneBill() {
        Button printPhoneBill = (Button) findViewById(R.id.printPhoneBill);
        printPhoneBill.setOnClickListener(new View.OnClickListener() {
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

    private void saveResults(String name, PhoneBill bill, PhoneCall call) throws IOException {
        File dir = getDataDir();
        String fileName = name + ".txt";
        File file = new File(dir, fileName);
        try (PrintWriter pw = new PrintWriter(new FileWriter(file), true)) {
            pw.println(bill.getCustomer());
            Collection<PhoneCall> calls = bill.getPhoneCalls();
            for (PhoneCall c : calls) {
                pw.println(c.getAllCallInfo());
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
                    PhoneCall call = (PhoneCall) data.getSerializableExtra("PhoneCall");
                    Toast.makeText(this, "New Phone Call Added: " + call.toString(), Toast.LENGTH_LONG).show();

                    PhoneBill bill = readFile(name);
                    bill.addPhoneCall(call);
                    
                    try {
                        saveResults(name, bill, call);
                    } catch (IOException e) {
                        Toast.makeText(this, "While writing file " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                }
            }
        }
    }

    private PhoneBill readFile(String name) {
        File dir = getDataDir();
        String fileName = name + ".txt";
        File file = new File(dir, fileName);
        boolean onFile = file.exists(); //file.length() != 0
        if (onFile) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                String customerOnFile = br.readLine();
                PhoneBill bill = new PhoneBill(customerOnFile);
                while ((line = br.readLine()) != null) {
                    String callInfo = line;
                    String[] data = callInfo.split("\\s+");
                    PhoneCall call = new PhoneCall(data[0], data[1], data[2], data[3],
                            data[4], data[5], data[6], data[7]);
                    bill.addPhoneCall(call);
                }
                return bill;
            } catch (IOException e) {
                Toast.makeText(this, "While reading file " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else {
            return new PhoneBill(name);
        }
        return null;
    }
}