package edu.pdx.cs410J.tlan2;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/**
 * This class represents a <code>TextParser</code>.
 * Contains a parse method that takes a customer's file of
 * phone bill contents and puts the information into a PhoneBill
 * object.
 */
public class TextParser implements PhoneBillParser {
//  String PATH = "C:\\Users\\thoma\\Documents" +
//          "\\6. Summer 2020\\1. Advanced Java" +
//          "\\PortlandStateJavaSummer2020\\phonebill\\src" +
//          "\\test\\java\\edu\\pdx\\cs410J\\tlan2\\testFiles\\";

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
        newBill.addCustomer(sc.nextLine());

        while (sc.hasNextLine()) {
          String callInfo = sc.nextLine();
          String[] data = callInfo.split("\\s+");
          PhoneCall call = new PhoneCall(data[0], data[1], data[2], data[3],
                  data[4], data[5]);
          newBill.addPhoneCall(call);
        }
      } catch (FileNotFoundException ex) {
        throw new ParserException("While parsing file", ex);
      }
      return newBill;
    }
}
