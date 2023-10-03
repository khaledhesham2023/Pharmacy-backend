package com.khaledamin.pharmacy.util;

public class InvoiceNumberGenerator {

    private int currentNumber;
    private int length;

    public InvoiceNumberGenerator(int currentNumber, int length) {
        this.currentNumber = currentNumber;
        this.length = length;
    }

    public String generateNextInvoiceNumber(){
        String invoiceNumber = String.format("%0" + length + "d",currentNumber);
        currentNumber ++;
        return invoiceNumber;
    }
}
