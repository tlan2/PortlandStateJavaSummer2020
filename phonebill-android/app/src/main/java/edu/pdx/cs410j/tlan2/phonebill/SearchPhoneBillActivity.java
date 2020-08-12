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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

public class SearchPhoneBillActivity extends AppCompatActivity {

    private EditText nameInput, startDateInput, startTimeInput, startAMPMInput, endDateInput, endTimeInput, endAMPMInput;
    private String name, callerNumber, calleeNumber, startDate, startTime, startAMPM, endDate, endTime, endAMPM;
    Button submit;
    private ArrayAdapter<String> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_phone_bill);

        configureSearchBill();
        configureBackToMainMenu();

        ListView resultsView = findViewById(R.id.results);
        results = new ArrayAdapter<>(SearchPhoneBillActivity.this,android.R.layout.simple_list_item_1);
        resultsView.setAdapter(results);
    }

    private void configureSearchBill() {
        nameInput = (EditText) findViewById(R.id.searchNameInput);
        startDateInput = (EditText) findViewById(R.id.searchStartDate);
        startTimeInput = (EditText) findViewById(R.id.searchStartTime);
        startAMPMInput = (EditText) findViewById(R.id.searchStartAMPM);
        endDateInput = (EditText) findViewById(R.id.searchEndDate);
        endTimeInput = (EditText) findViewById(R.id.searchEndTime);
        endAMPMInput = (EditText) findViewById(R.id.searchEndAMPM);

        submit = (Button) findViewById(R.id.searchBillButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nameInput.getText().toString().trim().toLowerCase();
                startDate = startDateInput.getText().toString().trim();
                startTime = startTimeInput.getText().toString().trim();
                startAMPM = startAMPMInput.getText().toString().trim();
                endDate = endDateInput.getText().toString().trim();
                endTime = endTimeInput.getText().toString().trim();
                endAMPM = endAMPMInput.getText().toString().trim();

                PhoneBill bill = readFile(name);
                if(bill == null){
                    Toast.makeText(SearchPhoneBillActivity.this, "Customer does not exist", Toast.LENGTH_LONG).show();
                } else {
                    String name = bill.getCustomer().substring(0,1).toUpperCase() + bill.getCustomer().substring(1);
                    results.add(name);

                    String start = startDate + " " + startTime + " " + startAMPM;
                    String end = endDate + " " + endTime + " " + endAMPM;
                    Date minDate = stringToDateConverter(start);
                    Date maxDate = stringToDateConverter(end);

                    Collection<PhoneCall> calls = bill.sortPhoneCalls();
                    for (PhoneCall call:calls){
                        Date beginDate = call.getStartTime();

                        if (beginDate.after(minDate) && beginDate.before(maxDate)) {
                            results.add(call.getAllCallInfo());
                        }
                    }

                    if(results.getCount() == 0){
                        results.add("No phone calls found between " + start + " and " + end);
                    }

                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(nameInput.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                }
            }
        });
    }

    public static Date stringToDateConverter(String dateString) {
        Date date = new Date();
        try {
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
            date = format.parse(dateString);
        } catch (ParseException ex) {
            System.err.println("\nInvalid start date and/or time format. " +
                    "Correct format: mm/dd/yyyy nn:nn am/pm.");
            System.exit(1);
        }

        return date;
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