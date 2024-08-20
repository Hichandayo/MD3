package ra.entity;

import java.io.Serializable;

public class OrdersDetail implements Serializable {
    private int productId,orderId;
    private String name;
    private double unitPrice;
    private int quantity;

    public OrdersDetail() {
    }

    public OrdersDetail(int orderId, int productId, String name, double unitPrice, int quantity) {
        this.orderId = orderId;
        this.productId = productId;
        this.name = name;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return
                "productId=" + productId +
                ", orderId=" + orderId +
                ", name='" + name +
                ", unitPrice=" + unitPrice +
                ", quantity=" + quantity ;
    }
}
