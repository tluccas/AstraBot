package org.example.util.exceptions;

public class ApiRequestException extends RuntimeException {
    public ApiRequestException(String message) {
        super("[ API ERROR ]: Resposta nula ou em um formato incompatível");
    }
}
