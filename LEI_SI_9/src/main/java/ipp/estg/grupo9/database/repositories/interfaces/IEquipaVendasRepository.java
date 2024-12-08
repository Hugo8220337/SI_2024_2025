package ipp.estg.grupo9.database.repositories.interfaces;

import ipp.estg.grupo9.database.models.Email;
import ipp.estg.grupo9.database.repositories.exceptions.CannotWritetoFileException;

import java.util.List;

public interface IEquipaVendasRepository {
    List<Email> getEmails();

    void sendEmail(Email email) throws CannotWritetoFileException;
}
