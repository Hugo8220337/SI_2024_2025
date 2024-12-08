package ipp.estg.grupo9.database.repositories;

import ipp.estg.grupo9.database.files.DatabaseFiles;
import ipp.estg.grupo9.database.models.Email;
import ipp.estg.grupo9.database.models.Escola;
import ipp.estg.grupo9.database.repositories.exceptions.CannotWritetoFileException;
import ipp.estg.grupo9.database.repositories.interfaces.IEscolaRepository;
import ipp.estg.grupo9.utils.FileUtils;

import java.util.List;

public class RepositorioEscola implements IEscolaRepository {
    private final FileUtils<Escola> fileUtils = new FileUtils<>(DatabaseFiles.ESCOLAS_FILE);

    @Override
    public Escola findByEmail(String email) {
        List<Escola> escolas = fileUtils.readObjectListFromFile();
        for (Escola escola : escolas) {
            if (escola.getEmail().equals(email)) {
                return escola;
            }
        }
        return null;
    }

    @Override
    public Escola add(Escola escola) throws CannotWritetoFileException {
        List<Escola> escolas = fileUtils.readObjectListFromFile();
        escolas.add(escola);
        fileUtils.writeObjectListToFile(escolas);
        return escola;
    }

    @Override
    public boolean delete(int id) throws CannotWritetoFileException {
        List<Escola> escolas = fileUtils.readObjectListFromFile();
        for (Escola escola : escolas) {
            if (escola.getId() == id) {
                escolas.remove(escola);
                fileUtils.writeObjectListToFile(escolas);
                return true;
            }
        }
        return false;
    }

    @Override
    public Escola findById(int id) {
        List<Escola> escolas = fileUtils.readObjectListFromFile();
        for (Escola escola : escolas) {
            if (escola.getId() == id) {
                return escola;
            }
        }
        return null;
    }

    @Override
    public List<Escola> findAll() {
        return fileUtils.readObjectListFromFile();
    }

    @Override
    public List<Email> getEmails(int id) {
        List<Escola> escolas = fileUtils.readObjectListFromFile();
        for (Escola escola : escolas) {
            if (escola.getId() == id) {
                return escola.getEmails();
            }
        }
        return null;
    }

    @Override
    public void sendEmail(int id, Email email) throws CannotWritetoFileException {
        List<Escola> escolas = fileUtils.readObjectListFromFile();
        for (Escola escola : escolas) {
            if (escola.getId() == id) {
                escola.sendEmail(email);
                fileUtils.writeObjectListToFile(escolas);
            }
        }
    }
}
