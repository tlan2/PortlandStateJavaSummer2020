package edu.pdx.cs410J.tlan2;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

public class PhoneBillTextParser implements PhoneBillParser {

    private final Reader reader;

    public PhoneBillTextParser(Reader reader){
        this.reader = reader;
    }


    @Override
    public PhoneBill parse() throws ParserException {
        BufferedReader br = new BufferedReader(this.reader);
        try {
            String customer = br.readLine();
            PhoneBill bill = new PhoneBill(customer);
            while (br.ready()) {
                String caller = br.readLine();
                if (caller == null){
                    break;
                }
                bill.addPhoneCall(new PhoneCall(caller));
            }

            return bill;

        } catch (IOException e) {
            throw new ParserException("While parsing", e);
        }
    }
}
