package com.trilogyed.levelupservice.exception;

public class MembershipNotFoundException extends RuntimeException {

    public MembershipNotFoundException() {
    }

    public MembershipNotFoundException(String message) {
        super(message);
    }
}
