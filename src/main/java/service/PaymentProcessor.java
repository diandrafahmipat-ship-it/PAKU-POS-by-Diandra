/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import model.IPayment;
import model.Transaction;

/**
 *
 * @author acer_
 */
public class PaymentProcessor {

    public boolean processTransaction(String time, String itemsDetail, double total, IPayment paymentMethod) {
        if (paymentMethod.pay(total) == true) {
            Transaction transaction = new Transaction(0, time, itemsDetail, paymentMethod.getMethodName(), total);
            transaction.insert();
            return true;
        }

        return false;
    }
}
