package edu.pdx.cs410j.tlan2.phonebill;

import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;

public class PrintPhoneBillActivity extends AppCompatActivity {

    private EditText nameInput;
    private String name;
    private Button printBill;
    private ArrayAdapter<String> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_phone_bill);

        configureNameInput();
        configureBackToMainMenu();

        ListView resultsView = findViewById(R.id.results);
        results = new ArrayAdapter<>(PrintPhoneBillActivity.this,android.R.layout.simple_list_item_1);
        resultsView.setAdapter(results);
    }

    private void configureNameInput() {
        nameInput = (EditText) findViewById(R.id.printName);
        printBill = (Button) findViewById(R.id.printBill);
        printBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nameInput.getText().toString().trim().toLowerCase();
                PhoneBill bill = readFile(name);
                if(bill == null){
                    Toast.makeText(PrintPhoneBillActivity.this, "Customer does not exist", Toast.LENGTH_LONG).show();
                } else {
                    String name = bill.getCustomer().substring(0,1).toUpperCase() + bill.getCustomer().substring(1);
                    results.add(name);
                    Collection<PhoneCall> calls = bill.sortPhoneCalls();
                    for (PhoneCall call:calls){
                        results.add(call.getAllCallInfo());
                    }
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(nameInput.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                }
            }
        });
    }

    private void configureBackToMainMenu() {
        Button backToMainMenu = (Button) findViewById(R.id.backPrintPhoneBill);
        backToMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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
        }

        return null;
    }
}