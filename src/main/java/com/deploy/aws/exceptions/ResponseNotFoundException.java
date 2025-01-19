package com.deploy.aws.exceptions;

// Creating custom exception class
public class ResponseNotFoundException extends RuntimeException{

    public ResponseNotFoundException(String message){
        super(message);
    }
}
