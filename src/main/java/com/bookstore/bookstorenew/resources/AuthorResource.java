/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.bookstorenew.resources;

import com.bookstore.bookstorenew.exception.AuthorNotFoundException;
import com.bookstore.bookstorenew.exception.InvalidInputException;
import com.bookstore.bookstorenew.model.Author;
import com.bookstore.bookstorenew.model.Book;
import static com.bookstore.bookstorenew.resources.BookResource.books;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


/**
 *
 * @author isira
 */

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorResource {
    static  HashMap<Integer, Author> authors = new HashMap<>();

    static {
        authors.put(1, new Author(1, "J.R.R. Tolkien", "Author of 'The Lord of the Rings' and 'The Hobbit'."));
        authors.put(2, new Author(2, "George Orwell", "Known for '1984' and 'Animal Farm'."));
        authors.put(3, new Author(3, "Harper Lee", "Famous for 'To Kill a Mockingbird'."));
    }

    @POST
    public Author addAuthor(Author author){
        if (author == null || author.getId() == 0) {
            throw new InvalidInputException("JSON input is invalid.");
        }
            authors.put(author.getId(), author);
            return author;

    }
    @GET
    public Collection<Author> getAuthor() {
            return authors.values();
    }

    @GET
    @Path("/{id}")
    public Author getAuthor(@PathParam("id") int id) {
        if (id <= 0) {
            throw new InvalidInputException("Customer ID must be a positive integer.");
        }
            Author a = authors.get(id);
        if (a == null) {
            throw new AuthorNotFoundException("Author with ID " + id + " not found.");
        }
            return authors.get(id);
    }

    @GET
    @Path("/{id}/books")
    public List<Book> getBooksByAuthor(@PathParam("id") int id) {
        Author a = authors.get(id);
        if (a == null) {
            throw new AuthorNotFoundException("Author with ID " + id + " not found.");
        }
        if (id <= 0) {
            throw new InvalidInputException("Customer ID must be a positive integer.");
        }
        return books.values().stream().filter(book -> book.getAuthorId() == id).collect(Collectors.toList());
    }

    @PUT
    @Path("/{id}")
    public Author updateAuthor(@PathParam("id") int id, Author author) {
        if (id <= 0) {
            throw new InvalidInputException("Customer ID must be a positive integer.");
        }
        Author a = authors.get(id);
        if (a == null) {
            throw new AuthorNotFoundException("Author with ID " + id + " not found.");
        }
        author.setId(id);
        authors.put(id, author);
        return author;
    }

    @DELETE
    @Path("/{id}")
    public String deleteAuthor(@PathParam("id") int id) {
        Author a = authors.get(id);
        if (a == null) {
            throw new AuthorNotFoundException("Author with ID " + id + " not found.");
        }
        if (id <= 0) {
            throw new InvalidInputException("Customer ID must be a positive integer.");
        }
        authors.remove(id);
        return "Author deleted ID dy " + id;
    }
}
