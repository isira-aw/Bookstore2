/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.bookstorenew.model;

/**
 *
 * @author isira
 */
public class CartItem {
    private static int idCounter = 1;

    private int id;
    private int bookId;
    private int quantity;

    public CartItem() {
        this.id = idCounter++;
    }

    public CartItem(int bookId, int quantity) {
        this.id = idCounter++;
        this.bookId = bookId;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

//public class CartItem {
//    private int Id;
//    private int bookId;
//    private int quantity;
//
//    public CartItem() {}
//
//    public CartItem(int bookId, int quantity) {
//        this.bookId = bookId;
//        this.quantity = quantity;
//    }
//
//    public int getId() {
//        return Id;
//    }
//
//    public void setId(int id) {
//        Id = id;
//    }
//
//    public int getBookId() {
//        return bookId;
//    }
//
//    public void setBookId(int bookId) {
//        this.bookId = bookId;
//    }
//
//    public int getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(int quantity) {
//        this.quantity = quantity;
//    }
//}
//