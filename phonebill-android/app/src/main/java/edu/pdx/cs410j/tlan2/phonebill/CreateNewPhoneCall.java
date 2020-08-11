package edu.pdx.cs410j.tlan2.phonebill;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CreateNewPhoneCall extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_phone_call);

        configureBackToMainMenu();
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


//    EditText nameInput = findViewById(R.id.nameInput);
//    EditText callerNumberInput = findViewById(R.id.callerNumber);
//    EditText calleeNumberInput = findViewById(R.id.calleeNumber);
//    EditText startDateInput = findViewById(R.id.startDate);
//    EditText startTimeInput = findViewById(R.id.startTime);
//    EditText endDateInput = findViewById(R.id.endDate);
//    EditText endTimeInput = findViewById(R.id.endTime);

//        nameInput = findViewById(R.id.nameInput);
//        callerNumberInput = findViewById(R.id.callerNumber);
//        calleeNumberInput = findViewById(R.id.calleeNumber);
//        startDateInput = findViewById(R.id.startDate);
//        startTimeInput = findViewById(R.id.startTime);
//        endDateInput = findViewById(R.id.endDate);
//        endTimeInput = findViewById(R.id.endTime);
//
//
//
//        Button submitNewPhoneCall = (Button) findViewById(R.id.submitNewCall);
//
//        submitNewPhoneCall.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String name = nameInput.getText().toString();
//                String callerNumber = callerNumberInput.getText().toString();
//                String calleeNumber = calleeNumberInput.getText().toString();
//                String startDate = startDateInput.getText().toString();
//                String startTime = startTimeInput.getText().toString();
//                String endDate = endDateInput.getText().toString();
//                String endTime = endTimeInput.getText().toString();
//
//                if(!isValidPhoneNumber(calleeNumberInput) || !isValidPhoneNumber(calleeNumberInput)){
//                    return;
//                }
//
//                Intent intent = new Intent(CreateNewPhoneCall.this, MainActivity.class);
//
//                PhoneCall call = new PhoneCall(callerNumber, calleeNumber, startDate, startTime, endDate, endTime);
//
//                PhoneBill newBill = new PhoneBill(name);
//                intent.putExtra("PhoneBill", call);
//
//                setResult(RESULT_OK, intent);
//                finish();

//            }
//        });
//    }

//    public boolean isValidPhoneNumber(EditText phoneNumber){
//        String callNumber = phoneNumber.getText().toString().trim();
//
//        Pattern p = compile("\\d{3}-\\d{3}-\\d{4}");
//        if(callNumber.isEmpty()){
//           phoneNumber.setError("Field can't be empty");
//           return false;
//        } else if(!p.matcher(callNumber).matches()){
//            phoneNumber.setError("Please enter phone number as ###-###-###");
//            return false;
//        } else {
//            phoneNumber.setError(null);
//            return true;
//        }
//
//    }
//
//    public boolean isValidDate(String date){
//        Pattern p = compile("\\d{1,2}/\\d{1,2}/\\d{2,4}");
//        return p.matcher(date).matches();
//    }
//
//    public boolean isValidTime(String time){
//        Pattern p = compile("(\\d{1,2}:\\d{2})");
//        return p.matcher(time).matches();
//    }
//
//    public boolean isValidAMPM(String amPM){
//        Pattern p = compile("([AaPp][Mm])");
//        return p.matcher(amPM).matches();
//    }
//}