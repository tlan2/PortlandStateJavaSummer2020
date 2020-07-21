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
 * Unit tests for the {@link TextDumper} class.
 */
public class TextDumperTest {
    //============ VARIABLES =============================================
    private PhoneCall validPhoneCall1() {
        return new PhoneCall("755-733-2222", "111-222-3333", "12/31/2020",
                "9:00", "AM", "12/31/2020", "10:00", "am");
    }
    private PhoneCall validPhoneCall2() {
        return new PhoneCall("755-733-2222", "000-111-3333", "12/31/2020",
                "11:00", "am", "12/31/2020", "12:00", "pm");
    }
    private PhoneCall validPhoneCall3() {
        return new PhoneCall("755-733-2222", "444-555-6666", "12/31/2020",
                "1:00", "pm", "12/31/2020", "2:00", "pm");
    }
    String PATH = "C:\\Users\\thoma\\Documents\\6. Summer 2020" +
            "\\1. Advanced Java\\PortlandStateJavaSummer2020\\phonebill" +
            "\\src\\test\\java\\edu\\pdx\\cs410J\\tlan2\\testFiles\\";

//    m

    @Test
    public void createsNewFile() throws IOException {
        PhoneBill bill = new PhoneBill("newFile");
        PhoneCall call1 = validPhoneCall1();
        PhoneCall call2 = validPhoneCall2();
        PhoneCall call3 = validPhoneCall3();
        bill.addPhoneCall(call1);
        bill.addPhoneCall(call2);
        bill.addPhoneCall(call3);

        TextDumper td = new TextDumper(PATH + "newFile.txt");
        td.dump(bill);
        File newFile;

        assertThat((newFile = new File(PATH + "newFile.txt")).isFile(), equalTo(true));
    }

    @Test
    public void fileContainsContent() throws IOException {
        PhoneBill bill = new PhoneBill("fileContainsContent");
        PhoneCall call1 = validPhoneCall1();
        PhoneCall call2 = validPhoneCall2();
        PhoneCall call3 = validPhoneCall3();
        bill.addPhoneCall(call1);
        bill.addPhoneCall(call2);
        bill.addPhoneCall(call3);

        TextDumper td = new TextDumper(PATH + "fileContainsContent.txt");
        td.dump(bill);
        File fileContainsContent = new File(PATH + "fileContainsContent.txt");

        assertThat(fileContainsContent.length() > 0, equalTo(true));
    }

}
