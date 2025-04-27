/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.bookstorenew.resources;

import com.bookstore.bookstorenew.exception.BookNotFoundException;
import com.bookstore.bookstorenew.exception.CartNotFoundException;
import com.bookstore.bookstorenew.exception.CustomerNotFoundException;
import com.bookstore.bookstorenew.exception.InvalidInputException;
import com.bookstore.bookstorenew.model.Order;
import com.bookstore.bookstorenew.model.CartItem;
import com.bookstore.bookstorenew.model.Book;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

/**
 *
 * @author isira
 */

@Path("/customers/{customerId}/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    public static HashMap<Integer, List<Order>> customerOrders = new HashMap<>();
    private static final Map<Integer, List<CartItem>> customerCarts = CartResource.customerCarts;
    private static final Map<Integer, Book> books = BookResource.books;

    private static int orderIdCounter = 1;

    @POST
    public Order placeOrder(@PathParam("customerId") int customerId) {
        List<CartItem> cart = customerCarts.get(customerId);

        if (cart == null || cart.isEmpty()) {
            throw new CartNotFoundException("Cart is empty for customer ID: " + customerId);
        }

        double total = 0.0;
        for (CartItem item : cart) {
            Book book = books.get(item.getBookId());
            if (book == null) {
                throw new BookNotFoundException("Book not found for ID: " + item.getBookId());
            }
            if (item.getQuantity() <= 0) {
                throw new InvalidInputException("Invalid quantity for book ID: " + item.getBookId());
            }
            total += book.getPrice() * item.getQuantity();
        }

        Order order = new Order(orderIdCounter++, customerId, new ArrayList<>(cart), total);
        customerOrders.putIfAbsent(customerId, new ArrayList<>());
        customerOrders.get(customerId).add(order);

        cart.clear();
        return order;
    }

    @GET
    public List<Order> getOrders(@PathParam("customerId") int customerId) {
        if (customerId <= 0) {
            throw new InvalidInputException("customer ID must be a positive integer.");
        }
        Book book = books.get(customerId);
        if (book == null) {
            throw new CustomerNotFoundException("customer with ID " + customerId + " does not exist");
        }
        return customerOrders.getOrDefault(customerId, new ArrayList<>());
    }

    @GET
    @Path("/{orderId}")
    public Order getOrder(@PathParam("customerId") int customerId,
                          @PathParam("orderId") int orderId) {

        List<Order> orders = customerOrders.get(customerId);
        if (orders != null) {
            for (Order order : orders) {
                if (order.getId() == orderId) {
                    return order;
                }
            }
        }
        throw new InvalidInputException("Order with ID " + orderId + " not found for customer ID: " + customerId);
    }
}
