package edu.pdx.cs410J.tlan2;

import edu.pdx.cs410J.AbstractPhoneBill;
import edu.pdx.cs410J.AbstractPhoneCall;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This class represents a <code>PhoneBill</code>.
 * Has an access method to get the customer's name and a
 * method to add PhoneCall objects.
 */

public class PhoneBill extends AbstractPhoneBill<PhoneCall> {
    private String customer;
    private ArrayList<PhoneCall> phoneCalls;


    public PhoneBill(){
        this.customer = new String();
        this.phoneCalls = new ArrayList<>();
    }
    /**
     * Creates a new <code>PhoneBill</code>
     *
     * @param customer
     * The person whose phone bill we're modeling.
     * @param phoneCalls
     * A collection of all the phone calls placed by the customer.
     */

    public PhoneBill(String customer) {
        this.customer = customer;
        this.phoneCalls = new ArrayList<PhoneCall>();
    }

    public void addCustomer(String customer) {
        this.customer = customer;
    }
    /**
     * @return the customer on the phone bill's name.
     */
    @Override
    public String getCustomer() {
        return this.customer;
    }

    /**
     * This method adds a phone call object to the related customer's bill.
     * @param call
     *          The PhoneCall object containing the caller's
     *          phone number, the callee's phone number, the phone
     *          call's start date and time, and the phone call's end date
     *          and time.
     */
    @Override
    public void addPhoneCall(PhoneCall call) {
        phoneCalls.add(call);
    }

    /**
     * @return  all phone calls related to the customer on the phone bill.
     */

    @Override
    public Collection<PhoneCall> getPhoneCalls() {
        return this.phoneCalls;
    }
}
