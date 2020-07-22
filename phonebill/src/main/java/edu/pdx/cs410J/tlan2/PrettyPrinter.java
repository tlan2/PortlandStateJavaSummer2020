package edu.pdx.cs410J.tlan2;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.PhoneBillDumper;

import java.io.File;
import java.io.IOException;

public class PrettyPrinter implements PhoneBillDumper {

    private boolean printToFile;
    private File file = null;

    public PrettyPrinter(String name){this.file = new File(name);}


    @Override
    public void dump(AbstractPhoneBill abstractPhoneBill) throws IOException {

        PhoneBill bill = (PhoneBill) abstractPhoneBill;
        if(this.file.exists()){
            //Pretty Write to File

        } else {
            //Pretty Write to Console
            StringBuilder sb = new StringBuilder();
        }

    }
}
