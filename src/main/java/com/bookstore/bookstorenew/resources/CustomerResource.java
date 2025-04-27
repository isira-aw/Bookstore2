/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.bookstorenew.resources;


import com.bookstore.bookstorenew.exception.CustomerNotFoundException;
import com.bookstore.bookstorenew.exception.InvalidInputException;
import com.bookstore.bookstorenew.model.Customer;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.HashMap;


/**
 *
 * @author isira
 */

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class CustomerResource {

    public static HashMap<Integer, Customer> customers = new HashMap<>();
    static {
        customers.put(1, new Customer(1, "Smith", "alice@example.com", "password123"));
        customers.put(2, new Customer(2, "Johnson", "bob@example.com", "securepass"));
        customers.put(3, new Customer(3, "Brown", "charlie@example.com", "mypassword"));
    }

    @POST
    public Customer addCustomer(Customer customer) {

        if (customer == null || customer.getId() == 0) {
            throw new InvalidInputException("JSON input is invalid.");
        }
            customers.put(customer.getId(), customer);
            return customer;
    }

    @GET
    public Collection<Customer> getAuthor() {
            return customers.values();
    }

    @GET
    @Path("/{id}")
    public Customer getAuthor(@PathParam("id") int id) {
        if (id <= 0) {
            throw new InvalidInputException("Customer ID must be a positive integer.");
        }
        Customer c = customers.get(id);
        if (c == null) {
            throw new CustomerNotFoundException("Customer with ID " + id + " not found.");
        }
        return customers.get(id);
    }

    @PUT
    @Path("/{id}")
    public Customer updateAuthor(@PathParam("id") int id, Customer author) {
        if (id <= 0) {
            throw new InvalidInputException("Customer ID must be a positive integer.");
        }
        Customer c = customers.get(id);
        if (c == null) {
            throw new CustomerNotFoundException("Customer with ID " + id + " not found.");
        }
        author.setId(id);
        customers.put(id, author);
        return author;
    }

    @DELETE
    @Path("/{id}")
    public String deleteAuthor(@PathParam("id") int id) {
        if (id <= 0) {
            throw new InvalidInputException("Customer ID must be a positive integer.");
        }
        Customer c = customers.get(id);
        if (c == null) {
            throw new CustomerNotFoundException("Customer with ID " + id + " not found.");
        }
        customers.remove(id);
        return "Author deleted ID dy " + id;
    }
}
