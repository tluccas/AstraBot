package org.example.util.exceptions;

public class inGuildException extends RuntimeException {

    public inGuildException(String usuario) {
        super(
                "[ ERRO ] o usuário " + usuario + " não está no servidor"
        );
    }
}
