package edu.pdx.cs410J.tlan2;

import edu.pdx.cs410J.ParserException;
import org.junit.Test;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

/**
 * Unit tests for the {@link TextParser} class.
 */
public class TextParserTest {

    String PATH = "C:\\Users\\thoma\\Documents" +
            "\\6. Summer 2020\\1. Advanced Java" +
            "\\PortlandStateJavaSummer2020\\phonebill\\src" +
            "\\test\\java\\edu\\pdx\\cs410J\\tlan2\\testFiles\\";

    @Test
    public void phoneBillFromTextFileContainsCustomerName() throws ParserException {
        TextParser tp = new TextParser(PATH + "PhoneBillWithCustomer.txt");
        PhoneBill newBill = tp.parse();

        assertThat(newBill.getCustomer(), equalTo("Customer"));
    }

    @Test
    public void phoneBillFromTextFileContainsPhoneCallCallee() throws ParserException {
        TextParser tp = new TextParser(PATH + "PhoneBillWithCustomer.txt");
        PhoneBill newBill = tp.parse();
        ArrayList<PhoneCall> phoneCalls = (ArrayList<PhoneCall>) newBill.getPhoneCalls();
        PhoneCall firstCall = phoneCalls.get(0);

        assertThat(firstCall.getCallee(), equalTo("111-222-3333"));
    }

    @Test
    public void phoneBillFromTextFileContainsPhoneCallStartInfo() throws ParserException {
        TextParser tp = new TextParser(PATH + "PhoneBillWithCustomer.txt");
        PhoneBill newBill = tp.parse();
        ArrayList<PhoneCall> phoneCalls = (ArrayList<PhoneCall>) newBill.getPhoneCalls();
        PhoneCall firstCall = phoneCalls.get(0);

        assertThat(firstCall.getStartTimeString(), equalTo("1/1/2020 9:00"));
    }

    @Test
    public void phoneBillFromTextFileContainsPhoneCallEndInfo() throws ParserException {
        TextParser tp = new TextParser(PATH + "PhoneBillWithCustomer.txt");
        PhoneBill newBill = tp.parse();
        ArrayList<PhoneCall> phoneCalls = (ArrayList<PhoneCall>) newBill.getPhoneCalls();
        PhoneCall firstCall = phoneCalls.get(0);

        assertThat(firstCall.getEndTimeString(), equalTo("1/1/2020 10:00"));
    }

    @Test
    public void phoneBillFromTextFileContainsAllCallInfo() throws ParserException {
        TextParser tp = new TextParser(PATH + "PhoneBillWithCustomer.txt");
        PhoneBill newBill = tp.parse();
        ArrayList<PhoneCall> phoneCalls = (ArrayList<PhoneCall>) newBill.getPhoneCalls();
        PhoneCall firstCall = phoneCalls.get(0);

        assertThat(firstCall.toString(), equalTo("Phone call from 123-456-7890 to 111-222-3333 " +
                "from 1/1/2020 9:00 to 1/1/2020 10:00"));
    }

    @Test (expected = ParserException.class)
    public void parseMethodFileNotFoundException() throws ParserException {
        TextParser tp = new TextParser(PATH + "fileDoesNotExist.txt");
        tp.parse();
    }
}
