/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bookstore.bookstorenew.exception;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Map;

/**
 *
 * @author isira
 */
@Provider
public class BookNotFoundExceptionMapper implements ExceptionMapper<BookNotFoundException> {
    @Override
    public Response toResponse(BookNotFoundException ex) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(Map.of("error", "Book Not Found", "message", ex.getMessage()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
