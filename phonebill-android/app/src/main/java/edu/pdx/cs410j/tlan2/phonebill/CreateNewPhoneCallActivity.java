package edu.pdx.cs410j.tlan2.phonebill;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

public class CreateNewPhoneCallActivity extends AppCompatActivity {

    private EditText nameInput, callerNumberInput, calleeNumberInput, startDateInput,
            startTimeInput, startAMPMInput, endDateInput, endTimeInput, endAMPMInput;

    private String name, callerNumber, calleeNumber, startDate, startTime, startAMPM, endDate, endTime, endAMPM;

    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_phone_call);

        // Retrieve User Input
        nameInput = (EditText) findViewById(R.id.nameInput);
        callerNumberInput = (EditText) findViewById(R.id.callerNumber);
        calleeNumberInput = (EditText) findViewById(R.id.calleeNumber);
        startDateInput = (EditText) findViewById(R.id.startDate);
        startTimeInput = (EditText) findViewById(R.id.startTime);
        startAMPMInput = (EditText) findViewById(R.id.startAMPM);
        endDateInput = (EditText) findViewById(R.id.endDate);
        endTimeInput = (EditText) findViewById(R.id.endTime);
        endAMPMInput = (EditText) findViewById(R.id.endAMPM);

        submit = findViewById(R.id.submitNewCall);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Pull Out Values as Strings
                enterCall();
            }
        });

        configureBackToMainMenu();
    }

    private void enterCall() {
        initialize();
        if(!validate()) {
            Toast.makeText(this, "Could Not Enter Phone Call", Toast.LENGTH_LONG).show();
        } else {
            PhoneCallEntered();
        }
    }

    private void PhoneCallEntered() {
        Intent intent = new Intent(CreateNewPhoneCallActivity.this, MainActivity.class);

        intent.putExtra("Name", name);
        PhoneCall call = new PhoneCall(callerNumber, calleeNumber, startDate, startTime,
                startAMPM, endDate, endTime, endAMPM);
        intent.putExtra("PhoneCall", call);

        setResult(RESULT_OK, intent);
        finish();
    }

    private boolean validate() {
        boolean valid = true;
        Pattern number = compile("\\d{3}-\\d{3}-\\d{4}");
        Pattern date = compile("\\d{1,2}/\\d{1,2}/\\d{2,4}");
        Pattern time = compile("(\\d{1,2}:\\d{2})");
        Pattern amPM = compile("([AaPp][Mm])");

        if(name.isEmpty()){
            nameInput.requestFocus();
            nameInput.setError("Field cannot be empty");
            valid = false;
        }
        if(callerNumber.isEmpty() || !number.matcher(callerNumber).matches()){
            callerNumberInput.requestFocus();
            callerNumberInput.setError("Invalid Number (###-###-###)");
            valid = false;
        }
        if(calleeNumber.isEmpty() || !number.matcher(calleeNumber).matches()){
            calleeNumberInput.requestFocus();
            calleeNumberInput.setError("Invalid Number (###-###-###)");
            valid = false;
        }
        if(startDate.isEmpty() || !date.matcher(startDate).matches()){
            startDateInput.requestFocus();
            startDateInput.setError("Invalid Date (##/##/##)");
            valid = false;
        }
        if(startTime.isEmpty() || !time.matcher(startTime).matches()){
            startTimeInput.requestFocus();
            startTimeInput.setError("Invalid Time (##:##)");
            valid = false;
        }
        if(startAMPM.isEmpty() || !amPM.matcher(startAMPM).matches()){
            startAMPMInput.requestFocus();
            startAMPMInput.setError("Invalid input (AM/PM)");
            valid = false;
        }
        if(endDate.isEmpty() || !date.matcher(endDate).matches()){
            endDateInput.requestFocus();
            endDateInput.setError("Invalid Date (##/##/##)");
            valid = false;
        }
        if(endTime.isEmpty() || !time.matcher(endTime).matches()){
            endTimeInput.requestFocus();
            endTimeInput.setError("Invalid Time (##:##)");
            valid = false;
        }
        if(endAMPM.isEmpty() || !amPM.matcher(endAMPM).matches()){
            endAMPMInput.requestFocus();
            endAMPMInput.setError("Invalid input (AM/PM)");
            valid = false;
        }

        return valid;
    }

    private void initialize() {
        name = nameInput.getText().toString().trim();
        callerNumber = callerNumberInput.getText().toString().trim();
        calleeNumber = calleeNumberInput.getText().toString().trim();
        startDate = startDateInput.getText().toString().trim();
        startTime = startTimeInput.getText().toString().trim();
        startAMPM = startAMPMInput.getText().toString().trim();
        endDate = endDateInput.getText().toString().trim();
        endTime = endTimeInput.getText().toString().trim();
        endAMPM = endAMPMInput.getText().toString().trim();
    }

    private void configureBackToMainMenu() {
        Button backToMainMenu = (Button) findViewById(R.id.backNewPhoneCall);
        backToMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
