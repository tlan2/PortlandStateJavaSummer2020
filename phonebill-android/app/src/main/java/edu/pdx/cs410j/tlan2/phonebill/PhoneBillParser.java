package edu.pdx.cs410j.tlan2.phonebill;

public interface PhoneBillParser<T extends AbstractPhoneBill> {
    T parse() throws ParserException;
}
