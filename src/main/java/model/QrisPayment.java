/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author acer_
 */
public class QrisPayment implements IPayment {

    @Override
    public boolean pay(double totalUang) {
        return true;
    }

    @Override
    public String getMethodName() {
        return "Qris";
    }
}
