package ipp.estg.grupo9.database.repositories.exceptions;

public class CannotWritetoFileException extends Exception {
    public CannotWritetoFileException(String message, String eMessage) {
        super(message);
    }
}
