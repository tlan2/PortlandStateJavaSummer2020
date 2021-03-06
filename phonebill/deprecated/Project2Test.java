package edu.pdx.cs410J.tlan2;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * A unit test for code in the <code>Project2</code> class.  This is different
 * from <code>Project2IT</code> which is an integration test (and can handle the calls
 * to {@link System#exit(int)} and the like.
 */
public class Project2Test {

    @Test
    public void readmeCanBeReadAsResource() throws IOException {
        StringBuilder readMeTxt = new StringBuilder();
        try (
                InputStream readme = Project2.class.getResourceAsStream("README.txt")
        ) {
            assertThat(readme, not(nullValue()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
            String line;
            while ((line = reader.readLine()) != null) {
                readMeTxt.append(line).append("\n");
            }
            assertThat(readMeTxt.toString(), containsString("Tom Lancaster - Project 2\n" +
                    "A program that inputs a customer's call log\n" +
                    "(phone numbers, start and end date and time)\n" +
                    "into a billing format."));
        }
    }

}
