package ipp.estg.grupo9.database.repositories;

import ipp.estg.grupo9.database.files.DatabaseFiles;
import ipp.estg.grupo9.database.models.Email;
import ipp.estg.grupo9.database.models.EquipaIntegracao;
import ipp.estg.grupo9.database.repositories.exceptions.CannotWritetoFileException;
import ipp.estg.grupo9.database.repositories.interfaces.IEquipaVendasRepository;
import ipp.estg.grupo9.utils.FileUtils;

import java.util.List;

public class RepositorioEquipaVendas implements IEquipaVendasRepository {
    private final FileUtils<EquipaIntegracao> fileUtils = new FileUtils<>(DatabaseFiles.EQUIPA_VENDAS_FILE);

    @Override
    public List<Email> getEmails() {
        EquipaIntegracao equipaIntegracao = fileUtils.readObjectFromFile();
        return equipaIntegracao.getEmails();
    }

    @Override
    public void sendEmail(Email email) throws CannotWritetoFileException {
        EquipaIntegracao equipaIntegracao = fileUtils.readObjectFromFile();

        if(equipaIntegracao == null) {
            equipaIntegracao = new EquipaIntegracao();
        }

        equipaIntegracao.sendEmail(email);
        fileUtils.writeObjectToFile(equipaIntegracao);
    }
}
