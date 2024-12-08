package ipp.estg.grupo9.workers;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import ipp.estg.grupo9.database.models.Email;
import ipp.estg.grupo9.database.models.Escola;
import ipp.estg.grupo9.database.repositories.RepositorioEquipaVendas;
import ipp.estg.grupo9.database.repositories.RepositorioEscola;
import ipp.estg.grupo9.database.repositories.interfaces.IEquipaVendasRepository;
import ipp.estg.grupo9.database.repositories.interfaces.IEscolaRepository;
import ipp.estg.grupo9.utils.AppLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class AtrairClienteWorker implements CommandLineRunner {
    private static final AppLogger LOGGER = AppLogger.getLogger(AtrairClienteWorker.class);
    private static final Logger log = LoggerFactory.getLogger(AtrairClienteWorker.class);

    @Autowired
    private ZeebeClient zeebeClient;

    private final IEscolaRepository escolaRepository = new RepositorioEscola();
    private final IEquipaVendasRepository equipaVendasRepository = new RepositorioEquipaVendas();

    @JobWorker(type = "DB_Import", autoComplete = true)
    public HashMap<String, Object> importarDadosPotencialCliente(@Variable String escola, @Variable String morada, @Variable String email, @Variable String telefone, @Variable String cidade, @Variable String detalhes, @Variable String segmento, @Variable String historicoInteracoes, @Variable String codigoPostal) {
        LOGGER.info("Importar cleinte de base de dados de leads começou");

        Escola cliente = new Escola(escola, morada, email, telefone, cidade, detalhes, segmento, historicoInteracoes, codigoPostal);
        try {
            escolaRepository.add(cliente);
        } catch (Exception e) {
            LOGGER.error("Não foi possível guardar o cliente na base de dados");
        }

        String id = String.valueOf(cliente.getId());
        String escolaCliente = String.valueOf(escola);
        String moradaCliente = String.valueOf(morada);
        String emailCliente = String.valueOf(email);
        String telefoneCliente = String.valueOf(telefone);
        String cidadeCliente = String.valueOf(cidade);
        String detalhesCliente = String.valueOf(detalhes);
        String segmentoCliente = String.valueOf(segmento);
        String historicoInteracoesCliente = String.valueOf(historicoInteracoes);
        String codigoPostalCliente = String.valueOf(codigoPostal);

        HashMap<String, Object> variables = new HashMap<>();
        variables.put("Id", id);
        variables.put("escola", escolaCliente);
        variables.put("morada", moradaCliente);
        variables.put("email", emailCliente);
        variables.put("telefone", telefoneCliente);
        variables.put("cidade", cidadeCliente);
        variables.put("detalhes", detalhesCliente);
        variables.put("segmento", segmentoCliente);
        variables.put("historicoInteracoes", historicoInteracoesCliente);
        variables.put("codigoPostal", codigoPostalCliente);

        // variável que será futuramente usada para verificar se o formulário foi recebido
        variables.put("formularioRecebido", "sim");


        return variables;
    }

    @JobWorker(type = "DB_Save", autoComplete = true)
    public HashMap<String, Object> guardarLeadParaCampanhasFuturas(@Variable String escola, @Variable String morada, @Variable String email, @Variable String telefone, @Variable String cidade, @Variable String detalhes, @Variable String segmento, @Variable String historicoInteracoes, @Variable String codigoPostal) {
        LOGGER.info("Guardar cliente para campanhas futuras começou");

        Escola cliente = new Escola(escola, morada, email, telefone, cidade, detalhes, segmento, historicoInteracoes, codigoPostal);
        ;
        try {
            escolaRepository.add(cliente);
        } catch (Exception e) {
            LOGGER.error("Não foi possível guardar o cliente na base de dados");
        }

        String id = String.valueOf(cliente.getId());
        String escolaCliente = String.valueOf(escola);
        String moradaCliente = String.valueOf(morada);
        String emailCliente = String.valueOf(email);
        String telefoneCliente = String.valueOf(telefone);
        String cidadeCliente = String.valueOf(cidade);
        String detalhesCliente = String.valueOf(detalhes);
        String segmentoCliente = String.valueOf(segmento);
        String historicoInteracoesCliente = String.valueOf(historicoInteracoes);
        String codigoPostalCliente = String.valueOf(codigoPostal);

        HashMap<String, Object> variables = new HashMap<>();
        variables.put("Id", id);
        variables.put("escola", escolaCliente);
        variables.put("morada", moradaCliente);
        variables.put("email", emailCliente);
        variables.put("telefone", telefoneCliente);
        variables.put("cidade", cidadeCliente);
        variables.put("detalhes", detalhesCliente);
        variables.put("segmento", segmentoCliente);
        variables.put("historicoInteracoes", historicoInteracoesCliente);
        variables.put("codigoPostal", codigoPostalCliente);
        return variables;


//        Escola.removeCount(); // count = count - 1
//        this.escolas.removeIf(escola1 -> escola1.getId() == ID);
    }

    @JobWorker(type = "Send_Email", autoComplete = true)
    public void enviarEmailsPersonalizados(@Variable int id, @Variable String escola, @Variable String email) {
        LOGGER.info("Enviar email personalizado começou");

        String escolaCliente = String.valueOf(escola);
        String emailCliente = String.valueOf(email);


        Email emailToSend = new Email(emailCliente, "Já ouviu falar do Luno", "Olá, " + escolaCliente + " já ouviu falar do Luno? é muito giro, compre por favor");
        try {
            escolaRepository.sendEmail(id, emailToSend);
        } catch (Exception e) {
            LOGGER.error("Não foi possível enviar o email");
        }
    }


    @JobWorker(type = "Send_Invite", autoComplete = true)
    public void convidarClienteParaWebinal(@Variable int id, @Variable String escola, @Variable String email) {
        LOGGER.info("Convidar cliente para webinal começou");

        String escolaCliente = String.valueOf(escola);
        String emailCliente = String.valueOf(email);


        Email emailToSend = new Email(emailCliente, "Já ouviu falar do Luno", "Olá, " + escolaCliente + " venha ao nosso webinal, é muito giro, compre por favor");
        try {
            escolaRepository.sendEmail(id, emailToSend);
        } catch (Exception e) {
            LOGGER.error("Não foi possível enviar o email");
        }
    }

    @JobWorker(type = "SendForm", autoComplete = true)
    public void enviarFormulario(@Variable int id, @Variable String escola, @Variable String email) {
        LOGGER.info("Enviar formulário começou");

        // simular envio de formulário por email
        Email mensagemFormulario = new Email(email, "Formulário de feedback", "Olá, " + escola +
                " porque nos detesta? O que nós lhe fizemos? Por favor, preencha este formulário.");
        try {
            escolaRepository.sendEmail(id, mensagemFormulario);
        } catch (Exception e) {
            LOGGER.error("Não foi possível enviar o email com o formulário");
        }


        // Simular envio do formulário por email
        LOGGER.info("Formulário enviado para: " + email);

        // Mandar mensagems para a receive task
        zeebeClient.newPublishMessageCommand()
                .messageName("Message_Resposta_Cliente") // Nome da mensagem
                .correlationKey("sim")   // Correlation key
                .variables("{\"status\": \"Formulário enviado\"}") // Variáveis adicionais
                .send()
                .join();
    }

    @JobWorker(type = "Notificar_Equipa_vendas", autoComplete = true)
    public void notificarEquipaVendas(@Variable String escola, @Variable String email, @Variable String tipoReuniao, @Variable String dataReuniao, @Variable String horaReuniao) {
        LOGGER.info("Notificar equipa de vendas começou");

        Email emailToSend = new Email(email, "Novo cliente", "Olá, " + escola + " é um novo cliente, foi marcada uma reunião " + tipoReuniao + " para a data " + dataReuniao + " às " + horaReuniao);
        try {
            equipaVendasRepository.sendEmail(emailToSend);
        } catch (Exception e) {
            LOGGER.error("Não foi possível enviar o email para a equipa de vendas");
        }
    }


    // Simulate the form received
    @Override
    public void run(String... args) {
        LOGGER.info("Captar ClienteWorker está a executar");

        final HashMap<String, Object> variables = new HashMap<>();
        variables.put("clienteInteressado", "sim");

        zeebeClient.newPublishMessageCommand()
                .messageName("Message_Resposta_Cliente")
                .correlationKey("sim")
                .variables(variables)
                .send()
                .join();

    }
}
