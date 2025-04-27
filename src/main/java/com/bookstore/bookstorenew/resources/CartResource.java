/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.bookstorenew.resources;


import com.bookstore.bookstorenew.exception.*;
import com.bookstore.bookstorenew.model.Book;
import com.bookstore.bookstorenew.model.CartItem;
import com.bookstore.bookstorenew.model.Customer;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;
import static com.bookstore.bookstorenew.resources.BookResource.books;
import static com.bookstore.bookstorenew.resources.CustomerResource.customers;

/**
 *
 * @author isira
 */

@Path("/customers/{customerId}/cart")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartResource {

    static HashMap<Integer, List<CartItem>> customerCarts = new HashMap<>();

    @POST
    @Path("/items")
    public String addItemToCart(@PathParam("customerId") int customerId, CartItem item) {
        if (item.getQuantity() <= 0) {
            throw new InvalidInputException("Quantity must be greater than 0");
        }
        Book book = books.get(item.getBookId());
        if (book == null) {
            throw new BookNotFoundException("Book with ID " + item.getBookId() + " not found.");
        }

        if (item.getQuantity() > book.getStock()) {
            throw new OutOfStockException("Requested quantity exceeds stock for book ID " + item.getBookId());
        }

        book.setStock(book.getStock() - item.getQuantity());

        customerCarts.putIfAbsent(customerId, new ArrayList<>());
        customerCarts.get(customerId).add(item);

        return "Item added to cart from customerId:" +customerId;
    }

    @GET
    public List<CartItem> getCart(@PathParam("customerId") int customerId) {
        if (customerId <= 0) {
            throw new InvalidInputException("Customer ID must be a positive integer.");
        }
        Customer c = customers.get(customerId);
        if (c == null) {
            throw new CustomerNotFoundException("Customer with ID " + customerId + " not found.");
        }
        return customerCarts.getOrDefault(customerId, new ArrayList<>());
    }

    @PUT
    @Path("/items/{bookId}")
    public String updateCartItem(@PathParam("customerId") int customerId, @PathParam("bookId") int bookId, CartItem updatedItem) {
        Book book1 = books.get(bookId);
        if (book1 == null) {
            throw new BookNotFoundException("Book with ID " + bookId + " does not exist");
        }
        if (updatedItem.getQuantity() <= 0) {
            throw new InvalidInputException("Quantity must be greater than 0");
        }

        Book book = books.get(bookId);
        if (book == null) {
            throw new BookNotFoundException("Book with ID " + bookId + " not found.");
        }

        List<CartItem> cart = customerCarts.get(customerId);
        if (cart != null) {
            for (CartItem item : cart) {
                if (item.getBookId() == bookId) {
                    int oldQty = item.getQuantity();
                    int newQty = updatedItem.getQuantity();
                    int stockChange = oldQty - newQty;

                    if (stockChange < 0 && Math.abs(stockChange) > book.getStock()) {
                        throw new OutOfStockException("Not enough stock to increase quantity.");
                    }

                    book.setStock(book.getStock() + stockChange);
                    item.setQuantity(newQty);

                    return "Item quantity updated:" +customerId;
                }
            }
        }
        throw new CartNotFoundException("Item not found in cart");
    }

    @DELETE
    @Path("/items/{bookId}")
    public String removeItem(@PathParam("customerId") int customerId, @PathParam("bookId") int bookId) {
        List<CartItem> cart = customerCarts.get(customerId);

        if (cart != null) {
            Iterator<CartItem> iterator = cart.iterator();
            while (iterator.hasNext()) {
                CartItem item = iterator.next();
                if (item.getBookId() == bookId) {
                    Book book = books.get(bookId);
                    if (book != null) {
                        book.setStock(book.getStock() + item.getQuantity());
                    }
                    iterator.remove();
                    return "Item removed from cart and stock restored:" +customerId;
                }
            }
            throw new CartNotFoundException("Item not found in cart");
        }
        throw new CartNotFoundException("Cart not found for customer ID: " + customerId);
    }
}
