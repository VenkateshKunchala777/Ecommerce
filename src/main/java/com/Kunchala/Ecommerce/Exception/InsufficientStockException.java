package com.Kunchala.Ecommerce.Exception;

public class InsufficientStockException  extends RuntimeException{
    public InsufficientStockException(String msg) {
        super(msg);
    }
}
