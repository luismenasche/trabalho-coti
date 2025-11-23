package com.example.trabalho_coti.exception;

import java.util.UUID;

public class IdNotFoundException extends RuntimeException{
    public IdNotFoundException(UUID id) {
        super(id.toString());
    }
}
