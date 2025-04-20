package com.bookstore.bookstore.resource;

//CustomerResource (/customers)
//POST /customers
//o GET /customers
//o GET /customers/{id}
//o PUT /customers/{id}
//o DELETE /customers/{id}

import com.bookstore.bookstore.model.Customer;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.HashMap;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class CustomerResource {
    public static HashMap<Integer, Customer> customers = new HashMap<>();

    @POST
    public Customer addCustomer(Customer customer) {
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
        return customers.get(id);
    }

    @PUT
    @Path("/{id}")
    public Customer updateAuthor(@PathParam("id") int id, Customer author) {
        author.setId(id);
        customers.put(id, author);
        return author;
    }

    @DELETE
    @Path("/{id}")
    public String deleteAuthor(@PathParam("id") int id) {
        customers.remove(id);
        return "Author deleted ID dy " + id;
    }
}
