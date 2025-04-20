package com.bookstore.bookstore.resource;

import com.bookstore.bookstore.model.CartItem;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

@Path("/customers/{customerId}/cart")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartResource {

    static final Map<Integer, List<CartItem>> customerCarts = new HashMap<>();

    @POST
    @Path("/items")
    public String addItemToCart(@PathParam("customerId") int customerId, CartItem item) {
        customerCarts.putIfAbsent(customerId, new ArrayList<>());
        customerCarts.get(customerId).add(item);
        return "Item added to cart";
    }

    // Get entire cart
    @GET
    public List<CartItem> getCart(@PathParam("customerId") int customerId) {
        return customerCarts.getOrDefault(customerId, new ArrayList<>());
    }

    // Update quantity of a specific item
    @PUT
    @Path("/items/{bookId}")
    public String updateCartItem(@PathParam("customerId") int customerId,
                                 @PathParam("bookId") int bookId,
                                 CartItem updatedItem) {
        List<CartItem> cart = customerCarts.get(customerId);
        if (cart != null) {
            for (CartItem item : cart) {
                if (item.getBookId() == bookId) {
                    item.setQuantity(updatedItem.getQuantity());
                    return "Item quantity updated";
                }
            }
        }
        throw new NotFoundException("Item not found in cart");
    }

    // Remove an item from the cart
    @DELETE
    @Path("/items/{bookId}")
    public String removeItem(@PathParam("customerId") int customerId,
                             @PathParam("bookId") int bookId) {
        List<CartItem> cart = customerCarts.get(customerId);
        if (cart != null) {
            cart.removeIf(item -> item.getBookId() == bookId);
            return "Item removed from cart";
        }
        throw new NotFoundException("Cart not found");
    }
}
