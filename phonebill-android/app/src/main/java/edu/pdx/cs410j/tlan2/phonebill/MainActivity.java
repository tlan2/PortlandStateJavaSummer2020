package edu.pdx.cs410j.tlan2.phonebill;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public static final int NEW_PHONE_CALL_RESULT = 43;
    public static final int PRINT_PHONE_BILL_RESULT = 43;

    private PhoneBillArrayList bills = new PhoneBillArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        configureCreatePhoneBill();
        configureCreatePhoneCall();
        configurePrintPhoneBill();
        configureHelpMenu();
    }

    private void configurePrintPhoneBill() {
        Button createPhoneCall = (Button) findViewById(R.id.printPhoneBill);
        createPhoneCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PrintPhoneBill.class);
                startActivityForResult(intent, PRINT_PHONE_BILL_RESULT);
            }
        });
    }

    private void configureCreatePhoneCall() {
        Button createPhoneCall = (Button) findViewById(R.id.createPhoneCall);
        createPhoneCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CreateNewPhoneCall.class);
                startActivityForResult(intent, NEW_PHONE_CALL_RESULT);;
            }
        });
    }

    private void configureHelpMenu() {
       Button helpMenu = (Button) findViewById(R.id.helpMenu);
       helpMenu.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(MainActivity.this, HelpMenu.class));
           }
       });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == NEW_PHONE_CALL_RESULT && resultCode == RESULT_OK){
            if (data != null){
                if (data.hasExtra("PhoneBill")){
                    PhoneBill bill = (PhoneBill) data.getSerializableExtra("PhoneBill");
                    Toast.makeText(this, "New Phone Bill created for " + bill.getCustomer(), Toast.LENGTH_LONG).show();
                    bills.addPhoneBill(bill);
//                    Toast.makeText(this, "There are " + bills.size() + " Phone Bills.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}