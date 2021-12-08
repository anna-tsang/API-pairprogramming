package com.afs.restapi.exception;

public class NoMatchIdFoundException extends RuntimeException{

    public NoMatchIdFoundException(){
        super("No match ID found");
    }

}
