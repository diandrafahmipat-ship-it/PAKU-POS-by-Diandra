/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author acer_
 */
public class CashPayment implements IPayment, IChangeCalculate {

    private double moneyReceived;

    public CashPayment(double moneyReceived) {
        this.moneyReceived = moneyReceived;
    }

    @Override
    public boolean pay(double totalPrice) {
        if (moneyReceived == totalPrice) {
            System.out.println("Uang pas, cukup untuk membayar");
            return true;
        } else if (moneyReceived > totalPrice) {
            System.out.println("Uang ada kembalian, cukup untuk membayar");
            return true;
        } else {
            System.out.println("Uang kurang, tidak cukup untuk membayar");
            return false;
        }
    }

    @Override
    public String getMethodName() {
        return "Cash";
    }

    @Override
    public double getChange(double totalPrice, double moneyReceived) {
        if (moneyReceived >= totalPrice) {
            return moneyReceived - totalPrice;
        }

        return 0.0;
    }
}
