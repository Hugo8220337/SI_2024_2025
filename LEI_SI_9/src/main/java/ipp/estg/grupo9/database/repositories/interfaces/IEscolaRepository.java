package ipp.estg.grupo9.database.repositories.interfaces;

import ipp.estg.grupo9.database.models.Email;
import ipp.estg.grupo9.database.models.Escola;
import ipp.estg.grupo9.database.repositories.exceptions.CannotWritetoFileException;

import java.util.List;

public interface IEscolaRepository extends IRepository<Escola> {
    Escola findByEmail(String email);
    Escola add(Escola escola) throws CannotWritetoFileException;

    List<Email> getEmails(int id);

    void sendEmail(int id, Email email) throws CannotWritetoFileException;
}
