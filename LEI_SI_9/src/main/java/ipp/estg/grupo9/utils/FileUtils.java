package ipp.estg.grupo9.utils;

import ipp.estg.grupo9.database.repositories.exceptions.CannotWritetoFileException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtils<T> {

    private final String filePath;

    public FileUtils(String filePath) {
        this.filePath = filePath;
    }

    public List<T> readObjectListFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (List<T>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return new ArrayList<>(); // Retorna lista vazia se não conseguir ler
        }
    }

    public T readObjectFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null; // Retorna null se não conseguir ler
        }
    }

    public boolean writeObjectListToFile(List<T> records) throws CannotWritetoFileException {
        File file = new File(filePath);
        try {
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs(); // se não encontrar o ficheiro, vai riá-lo
            }
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(records);
            return true;
        } catch (IOException e) {
            throw new CannotWritetoFileException("Cannot write to file", e.getMessage());
        }
    }

    public boolean writeObjectToFile(T record) throws CannotWritetoFileException {
        File file = new File(filePath);
        try {
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs(); // se não encontrar o ficheiro, vai riá-lo
            }
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(record);
            return true;
        } catch (IOException e) {
            throw new CannotWritetoFileException("Cannot write to file", e.getMessage());
        }
    }
}