package ipp.estg.grupo9.workers;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import ipp.estg.grupo9.database.models.Email;
import ipp.estg.grupo9.database.repositories.RepositorioEquipaIntegracao;
import ipp.estg.grupo9.database.repositories.RepositorioEscola;
import ipp.estg.grupo9.database.repositories.interfaces.IEquipaIntegracaoRepository;
import ipp.estg.grupo9.database.repositories.interfaces.IEscolaRepository;
import ipp.estg.grupo9.utils.AppLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class FecharVendasWorker implements CommandLineRunner  {
    private static final AppLogger LOGGER = AppLogger.getLogger(FecharVendasWorker.class);

    @Autowired
    private ZeebeClient zeebeClient;

    private final IEscolaRepository escolaRepository = new RepositorioEscola();
    private final IEquipaIntegracaoRepository equipaVendasRepository = new RepositorioEquipaIntegracao();

    @JobWorker(type = "Notificar_Equipa_Integracao", autoComplete = true)
    public void NotificarEquipaIntegracao(@Variable String nomeEscola) {
        LOGGER.info("Notificar equipa de integração task");

        String emailCliente = String.valueOf(nomeEscola);
        Email emailToSend = new Email("integracao@integracao.com", "Nova escola para integracao", "Nova escola para integracao é a " + emailCliente);
        try {
            equipaVendasRepository.sendEmail(emailToSend);
            LOGGER.info("Email enviado para a equipa de integração");
        } catch (Exception e) {
            LOGGER.error("Não foi possível guardar a escola na base de dados");
        }
    }

    @JobWorker(type = "Notificar_Cliente_Nova_Proposta", autoComplete = true)
    public void notificarClienteSobreReuniao(@Variable String nomeEscola, @Variable String email, @Variable String ajustesNaProposta) {
        LOGGER.info("Notificar cliente da nova proposta task");

        String emailCliente = String.valueOf(email);
        Email emailToSend = new Email(emailCliente, "Nova propsota", "Ajustes na proposta: " + ajustesNaProposta + "\n é pegar ou largar.");
        int escolaId = escolaRepository.findByEmail(emailCliente).getId();
        try {
            escolaRepository.sendEmail(escolaId, emailToSend);
            LOGGER.info("Email enviado para a escola");
        } catch (Exception e) {
            LOGGER.error("Não foi possível guardar a escola na base de dados");
        }


        // Mandar mensagems para a receive task
        zeebeClient.newPublishMessageCommand()
                .messageName("Message_Contact_cliente") // Nome da mensagem
                .correlationKey("sim")   // Correlation key
                .variables("{\"status\": \"Formulário enviado\"}") // Variáveis adicionais
                .send()
                .join();
    }

    // Simulate the form received
    @Override
    public void run(String... args) {
        LOGGER.info("Captar ClienteWorker está a executar");

        final HashMap<String, Object> variables = new HashMap<>();
        variables.put("clienteQuerComprar", "sim");

        zeebeClient.newPublishMessageCommand()
                .messageName("Message_Contact_cliente")
                .correlationKey("indeciso")
                .variables(variables)
                .send()
                .join();

    }
}
