package edu.pdx.cs410J.tlan2;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * This class represents a <code>TextParser</code>.
 * Contains a parse method that takes a customer's file of
 * phone bill contents and puts the information into a PhoneBill
 * object.
 */
public class TextParser implements PhoneBillParser {

  private final File newFile;

  /**
   * Creates a new <code>PhoneBill</code> based on file name or the file itself.
   *
   * @param newFile
   * The file to be read and placed into PhoneBill object.
   */
  public TextParser(String fileName){ this.newFile = new File(fileName); }

  public TextParser(File file){ this.newFile = file; }

  /**
   * A method that takes a text file and places its contents in a PhoneBill object
   * @throws ParserException
   */
    @Override
    public PhoneBill parse() throws ParserException {
      PhoneBill newBill = new PhoneBill();

      try {
        Scanner sc = new Scanner(this.newFile);
        String customerName = sc.nextLine();
        Pattern number = compile("\\d{3}-\\d{3}-\\d{4}");
        Pattern date = compile("\\d{1,2}/\\d{1,2}/\\d{2}");
        Pattern time = compile("\\d{1,2}:\\d{2}");
        Pattern amPM = compile("([AaPp][Mm])");

        if(customerName.substring(0, 1).matches("\\d")){
          throw new ParserException("Error: Malformatted name in the file.");
        }
        newBill.addCustomer(customerName);

        while (sc.hasNextLine()) {
          String callInfo = sc.nextLine();
          String[] data = callInfo.split("\\s+");

          data[2] = data[2].replace(",", "");

          data[5] = data[5].replace(",", "");

          for(int i=0; i < data.length; i++){
            if(!number.matcher(data[0]).matches() || !number.matcher(data[1]).matches()) {
              throw new ParserException("\n\nError: Malformatted phone number(s) in file.");
            } else if(!date.matcher(data[2]).matches() || !date.matcher(data[5]).matches()){
              throw new ParserException("\n\nError: Malformatted date(s) in file.");
            } else if(!time.matcher(data[3]).matches() || !time.matcher(data[6]).matches()){
              throw new ParserException("\n\nError: Malformatted time(s) in file.");
            } else if(!amPM.matcher(data[4]).matches() || !amPM.matcher(data[7]).matches()){
              throw new ParserException("\n\nError: Malformatted AM/PM(s) in file.");
            }
          }
//          PhoneCall call = new PhoneCall(data[0], data[1], data[2], data[3],
//                  data[4], data[5]);
//          newBill.addPhoneCall(call);
        }
      } catch (FileNotFoundException ex) {
        throw new ParserException("While parsing file", ex);
      }
      return newBill;
    }
}
