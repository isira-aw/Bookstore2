package com.bookstore.bookstore.resource;

import com.bookstore.bookstore.model.Book;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;


@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {

    public static HashMap<Integer, Book> books = new HashMap<>();
    private static int currentId = 1;

    @POST
    public Book createBook(Book book) {
        book.setId(currentId++);
        books.put(book.getId(), book);
        return book;
    }

    @GET
    public Collection<Book> getBooks() {
        return books.values();
    }

    @GET
    @Path("/{id}")
    public Book getBook(@PathParam("id") int id) {
        return books.get(id);
    }

    @PUT
    @Path("/{id}")
    public Book updateBook(@PathParam("id") int id, Book book) {
        book.setId(id);
        books.put(id, book);
        return book;
    }

    @DELETE
    @Path("/{id}")
    public String deleteBook(@PathParam("id") int id) {
        books.remove(id);
        return "Book deleted";
    }
}
