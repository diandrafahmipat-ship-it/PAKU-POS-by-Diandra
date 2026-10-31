/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.util.List;
import java.util.ArrayList;
import model.CartItem;
import model.Product;

/**
 *
 * @author acer_
 */
public class CartService {

    private List<CartItem> items = new ArrayList<>();

    public void addProduct(Product product) {
        CartItem exItems = null;
        for (CartItem item : items) {
            if (item.getProduct().getId() == product.getId()) {
                exItems = item;
                break;
            }
        }

        if (exItems != null) {
            exItems.addQuantity();

        } else {
            items.add(new CartItem(product, 1));

        }
    }

    public void removeProduct(Product product) {
        CartItem targetItem = null;
        for (CartItem item : items) {
            if (item.getProduct().getId() == product.getId()) {
                targetItem = item;
                break;
            }
        }

        if (targetItem != null) {
            targetItem.reduceQuantity();
            if (targetItem.getQuantity() <= 0) {
                items.remove(targetItem);
            }
        }
    }

    public List<CartItem> getItems() {
        return items;
    }

    public double getTotalPrice() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getSubTotal();
        }

        return total;
    }

    public String getItemFormatDetail() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            CartItem item = items.get(i);
            sb.append(item.getQuantity())
                    .append("x ")
                    .append(item.getProduct().getName())
                    .append(" (Rp")
                    .append(item.getSubTotal())
                    .append(")");
            if (i < items.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }

    public void clearCart() {
        items.clear();
    }
}
