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
public class InvalidInputExceptionMapper implements ExceptionMapper<InvalidInputException> {
    @Override
    public Response toResponse(InvalidInputException ex) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(Map.of("error", "Invalid Input", "message", ex.getMessage()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}

