/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.bookstorenew.resources;

import com.bookstore.bookstorenew.exception.InvalidInputException;
import com.bookstore.bookstorenew.model.Book;
import com.bookstore.bookstorenew.exception.BookNotFoundException;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;


/**
 *
 * @author isira
 */
@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource {

    public static HashMap<Integer, Book> books = new HashMap<>();

    static {
        books.put(1, new Book(1, "The Lord of the Rings", "J.R.R. Tolkien", 1, "978-0-618-05326-7", 1954, 20.99, 100));
        books.put(2, new Book(2, "1984", "George Orwell", 2, "978-0-452-28423-4", 1949, 15.99, 50));
        books.put(3, new Book(3, "To Kill a Mockingbird", "Harper Lee", 3, "978-0-06-112008-4", 1960, 18.99, 80));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Book createBook(Book book) {

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if (book.getPublicationYear() > currentYear) {
            throw new InvalidInputException("Publication year cannot be in the future.");
        }
        if ( book == null || book.getId() == 0 || book.getTitle() == null || book.getStockQuantity() < 0) {
            throw new InvalidInputException("JSON input is invalid.");
        }
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
        if (id <= 0) {
            throw new InvalidInputException("Book ID must be a positive integer.");
        }
        Book book = books.get(id);
        if (book == null) {
            throw new BookNotFoundException("Book with ID " + id + " does not exist");
        }
        return book;
    }


    @PUT
    @Path("/{id}")
    public Book updateBook(@PathParam("id") int id, Book book) {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        if (book.getPublicationYear() > currentYear) {
            throw new InvalidInputException("Publication year cannot be in the future.");
        }
        Book book1 = books.get(id);
        if (book1 == null) {
            throw new BookNotFoundException("Book with ID " + id + " does not exist");
        }
        book.setId(id);
        books.put(id, book);
        return book;
    }

    @DELETE
    @Path("/{id}")
    public String deleteBook(@PathParam("id") int id) {
        if (id <= 0) {
            throw new InvalidInputException("Book ID must be a positive integer.");
        }
        Book book1 = books.get(id);
        if (book1 == null) {
            throw new BookNotFoundException("Book with ID " + id + " does not exist");
        }
        books.remove(id);
        return "Book deleted ID by " + id;
    }
}
