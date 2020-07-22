package edu.pdx.cs410J.tlan2;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * Unit tests for the {@link PrettyPrinterTest} class.
 */

public class PrettyPrinterTest {
    // Variables
    PhoneCall call1 = new PhoneCall("503-777-7777", "222-222-2222",
                                        "03/03/2020", "09:00", "aM",
                                        "03/03/2020", "10:00", "AM");
    PhoneCall call2 = new PhoneCall("503-777-7777", "333-333-3333",
                                        "02/04/2020", "09:00", "aM",
                                        "2/04/2020", "10:00", "AM");
    PhoneCall call3 = new PhoneCall("503-777-7777", "111-222-3344",
                                        "5/04/2020", "1:00", "Pm",
                                        "5/04/2020", "2:00", "PM");
    PhoneCall call4 = new PhoneCall("503-777-7777", "665-665-6656",
                                        "7/15/2020", "1:00", "Pm",
                                        "7/16/2020", "2:00", "PM");
    PhoneCall call5 = new PhoneCall("503-777-7777", "999-999-9999",
                                        "11/11/2020", "1:00", "Pm",
                                        "11/11/2020", "2:00", "PM");




//    @Test
//    public void prettyPrintPhoneBillToConsole() {
//        PhoneBill bill = new PhoneBill("PPTestCustomer");
//        bill.addPhoneCall(call1);
//        bill.addPhoneCall(call2);
//        bill.addPhoneCall(call3);
//        bill.addPhoneCall(call4);
//        bill.addPhoneCall(call5);
//
//        PrettyPrinter pp = new PrettyPrinter("");
//
//
//        assertThat(pp.dump(bill), containsString("Customer: PPTestCustomer"));
//
//    }

//    public void prettyPrintPhoneBillToFile() {
//        PhoneBill bill = new PhoneBill("PPTestCustomer");
//        bill.addPhoneCall(call1);
//        bill.addPhoneCall(call2);
//        bill.addPhoneCall(call3);
//        bill.addPhoneCall(call4);
//        bill.addPhoneCall(call5);
//
//        PrettyPrinter pp = new PrettyPrinter(true);
//
//
//        assertThat(pp.dump(bill), containsString("Customer: PPTestCustomer"));
//
//    }
}
