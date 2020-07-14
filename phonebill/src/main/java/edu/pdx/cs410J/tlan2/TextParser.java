package edu.pdx.cs410J.tlan2;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.PhoneBillParser;
import org.w3c.dom.Text;

import java.io.File;

public class TextParser implements PhoneBillParser {
  File newFile;

  public TextParser(String fileName){
      this.newFile = new File(fileName);
  }

    @Override
    public PhoneBill parse() throws ParserException {
      return null;
    }
}
