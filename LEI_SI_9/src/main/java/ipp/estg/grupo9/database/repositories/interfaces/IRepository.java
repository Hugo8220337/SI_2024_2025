package ipp.estg.grupo9.database.repositories.interfaces;

import ipp.estg.grupo9.database.repositories.exceptions.CannotWritetoFileException;

import java.util.List;

public interface IRepository<T> {
    boolean delete(int id) throws CannotWritetoFileException;
    T findById(int id);
    List<T> findAll();
}
