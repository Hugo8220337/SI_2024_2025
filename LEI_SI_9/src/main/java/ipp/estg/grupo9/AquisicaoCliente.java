package ipp.estg.grupo9;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import ipp.estg.grupo9.models.Email;
import ipp.estg.grupo9.models.EquipaIntegracao;
import ipp.estg.grupo9.models.Escola;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class AquisicaoCliente implements CommandLineRunner  {
    @Autowired
    private ZeebeClient zeebeClient;

    private final ArrayList<Escola> escolas = new ArrayList<>();
    private final EquipaIntegracao equipaIntegracao = new EquipaIntegracao();

    @JobWorker(type = "Notificar_Equipa_Integracao", autoComplete = true)
    public void NotificarEquipaIntegracao(@Variable String nomeEscola) {
        System.out.println("Notificar equipa de integração");

        String emailCliente = String.valueOf(nomeEscola);
        Email emailToSend = new Email("integracao@integracao.com", "Nova escola para integracao", "Nova escola para integracao é a " + emailCliente);
        equipaIntegracao.sendEmail(emailToSend);
    }

    @JobWorker(type = "Notificar_Cliente_Nova_Proposta", autoComplete = true)
    public void notificarClienteSobreReuniao(@Variable String nomeEscola, @Variable String email, @Variable String ajustesNaProposta) {
        System.out.println("Notificar cliente da nova proposta task");

        String emailCliente = String.valueOf(email);
        Email emailToSend = new Email(emailCliente, "Nova propsota", "Ajustes na proposta: " + ajustesNaProposta + "\n é pegar ou largar.");
        escolas.forEach(escola1 -> {
            if (escola1.getNome().equals(nomeEscola)) {
                escola1.getEmails().add(emailToSend);
            }
        });

        // Simular envio do formulário por email
        System.out.println("Formulário enviado para: " + email);

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
        System.out.println("Captar ClienteWorker está a executar");

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
