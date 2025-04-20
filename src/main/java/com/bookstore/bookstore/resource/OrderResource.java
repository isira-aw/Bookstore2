package com.bookstore.bookstore.resource;

import com.bookstore.bookstore.model.Order;
import com.bookstore.bookstore.model.CartItem;
import com.bookstore.bookstore.model.Book;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

@Path("/customers/{customerId}/orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    private static final Map<Integer, List<Order>> customerOrders = new HashMap<>();
    private static final Map<Integer, List<CartItem>> customerCarts = CartResource.customerCarts; // access shared cart
    private static final Map<Integer, Book> books = BookResource.books; // access book data

    private static int orderIdCounter = 1;

    // POST /customers/{customerId}/orders
    @POST
    public Order placeOrder(@PathParam("customerId") int customerId) {
        List<CartItem> cart = customerCarts.getOrDefault(customerId, new ArrayList<>());
        if (cart.isEmpty()) {
            throw new BadRequestException("Cart is empty");
        }

        double total = 0.0;
        for (CartItem item : cart) {
            Book book = books.get(item.getBookId());
            if (book != null) {
                total += book.getPrice() * item.getQuantity();
            }
        }

        Order order = new Order(orderIdCounter++, customerId, new ArrayList<>(cart), total);
        customerOrders.putIfAbsent(customerId, new ArrayList<>());
        customerOrders.get(customerId).add(order);

        cart.clear(); // clear cart after placing order
        return order;
    }

    // GET /customers/{customerId}/orders
    @GET
    public List<Order> getOrders(@PathParam("customerId") int customerId) {
        return customerOrders.getOrDefault(customerId, new ArrayList<>());
    }

    // GET /customers/{customerId}/orders/{orderId}
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
        throw new NotFoundException("Order not found");
    }
}
