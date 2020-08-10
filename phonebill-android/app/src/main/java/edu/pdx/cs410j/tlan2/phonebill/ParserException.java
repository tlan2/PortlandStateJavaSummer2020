package edu.pdx.cs410j.tlan2.phonebill;

public class ParserException extends Exception {
    public ParserException(String description) {
        super(description);
    }

    public ParserException(String description, Throwable cause) {
        super(description, cause);
    }
}
