package com.ansh.user.service.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(){
        super("Resource not Found on Server");
    }

    public ResourceNotFoundException(String message){
        super(message);
    }
}
