package ipp.estg.grupo9.database.repositories;

import ipp.estg.grupo9.database.files.DatabaseFiles;
import ipp.estg.grupo9.database.models.Email;
import ipp.estg.grupo9.database.models.EquipaVendas;
import ipp.estg.grupo9.database.repositories.exceptions.CannotWritetoFileException;
import ipp.estg.grupo9.database.repositories.interfaces.IEquipaIntegracaoRepository;
import ipp.estg.grupo9.utils.FileUtils;

import java.util.ArrayList;
import java.util.List;

public class RepositorioEquipaIntegracao implements IEquipaIntegracaoRepository {
    private final FileUtils<EquipaVendas> fileUtils = new FileUtils<>(DatabaseFiles.EQUIPA_INTEGRACAO_FILE);

    @Override
    public List<Email> getEmails() {
        EquipaVendas equipaVendas = fileUtils.readObjectFromFile();

        if(equipaVendas == null) {
            return new ArrayList<>(); // returns an empty list
        }

        return equipaVendas.getEmails();
    }

    @Override
    public void sendEmail(Email email) throws CannotWritetoFileException {
        EquipaVendas equipaVendas = fileUtils.readObjectFromFile();

        if(equipaVendas == null) {
            equipaVendas = new EquipaVendas();
        }

        equipaVendas.sendEmail(email);
        fileUtils.writeObjectToFile(equipaVendas);
    }
}
