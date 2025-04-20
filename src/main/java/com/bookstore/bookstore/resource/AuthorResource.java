package com.bookstore.bookstore.resource;

import com.bookstore.bookstore.model.Author;
import com.bookstore.bookstore.model.Book;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static com.bookstore.bookstore.resource.BookResource.books;

//    AuthorResource (/authors)
//    o POST /authors
//    o GET /authors
//    o GET /authors/{id}
//    o PUT /authors/{id}
//    o DELETE /authors/{id}
//    o GET /authors/{id}/books

@Path("/authors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthorResource {
    private static  HashMap<Integer, Author> authors = new HashMap<>();

    @POST
    public Author addAuthor(Author author){
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
        return authors.get(id);
    }

    @GET
    @Path("/{id}/books")
    public List<Book> getBooksByAuthor(@PathParam("id") int authorId) {
        return books.values()
                .stream()
                .filter(book -> book.getAuthorId() == authorId)
                .collect(Collectors.toList());
    }

    @PUT
    @Path("/{id}")
    public Author updateAuthor(@PathParam("id") int id, Author author) {
        author.setId(id);
        authors.put(id, author);
        return author;
    }

    @DELETE
    @Path("/{id}")
    public String deleteAuthor(@PathParam("id") int id) {
        authors.remove(id);
        return "Author deleted ID dy " + id;
    }
}
