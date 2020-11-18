package com.iiht.userservice.exceptions;


public class UserNotFound extends Throwable {

    public UserNotFound(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

}
