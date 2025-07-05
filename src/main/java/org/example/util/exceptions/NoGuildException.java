package org.example.util.exceptions;

public class NoGuildException extends RuntimeException {
    public NoGuildException(String usuario) {
        super("OPS " +usuario+", Esse comando só pode ser usado em um servidor! <:astra_triste:1390823447227142204>");
    }
}
