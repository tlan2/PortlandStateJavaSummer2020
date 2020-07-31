package edu.pdx.cs410J.tlan2;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * This class represents a <code>PhoneBillParser</code>.
 * Contains a parse method that reads in an HTTP response and returns
 * response in a PhoneBill object.
 */
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
            Boolean completeCall = true;
            String callString = "";
            String[] callInfoStrings;

            while ((callString = br.readLine()) != null) {
                ArrayList<String> callInfo = new ArrayList<String>();

                callInfoStrings = callString.split(" ");

                for(String info:callInfoStrings){
                    if (info.equals("null")){
                        completeCall = false;
                        break;
                    }
                    callInfo.add(info);
                }

                if(completeCall){
                    bill.addPhoneCall(new PhoneCall(callInfo.get(0), callInfo.get(1), callInfo.get(2), callInfo.get(3),
                            callInfo.get(4), callInfo.get(5), callInfo.get(6),callInfo.get(7)));
                } else {
                    throw new ParserException("\nMissing phone call value.\n");
                }
            }

            return bill;

        } catch (IOException e) {
            throw new ParserException("While parsing", e);
        }
    }
}
