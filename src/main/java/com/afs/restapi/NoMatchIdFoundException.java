package com.afs.restapi;

public class NoMatchIdFoundException extends RuntimeException{

    public NoMatchIdFoundException(){
        super("No match ID found");
    }

}
