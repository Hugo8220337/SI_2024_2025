package ipp.estg.grupo9;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import ipp.estg.grupo9.models.Email;
import ipp.estg.grupo9.models.EquipaVendas;
import ipp.estg.grupo9.models.Escola;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class CaptarClienteWorker implements CommandLineRunner {
    @Autowired
    private ZeebeClient zeebeClient;

    private final ArrayList<Escola> escolas = new ArrayList<>();
    private final EquipaVendas equipaVendas = new EquipaVendas();

    @JobWorker(type = "DB_Import", autoComplete = true)
    public HashMap<String, Object> importarDadosPotencialCliente(@Variable String escola, @Variable String morada, @Variable String email, @Variable String telefone, @Variable String cidade, @Variable String detalhes, @Variable String segmento, @Variable String historicoInteracoes, @Variable String codigoPostal) {
        System.out.println("Add client service task");

        Escola cliente = new Escola(escola, morada, email, telefone, cidade, detalhes, segmento, historicoInteracoes, codigoPostal);
        escolas.add(cliente);

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
        System.out.println("Save client to future campains service task");

        Escola cliente = new Escola(escola, morada, email, telefone, cidade, detalhes, segmento, historicoInteracoes, codigoPostal);
        escolas.add(cliente);

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
    public void enviarEmailsPersonalizados(@Variable int ID, @Variable String escola, @Variable String email) {
        System.out.println("Send Email service task");

        String escolaCliente = String.valueOf(escola);
        String emailCliente = String.valueOf(email);


        Email emailToSend = new Email(emailCliente, "Já ouviu falar do Luno", "Olá, " + escolaCliente + " já ouviu falar do Luno? é muito giro, compre por favor");
        escolas.forEach(escola1 -> {
            if (escola1.getId() == ID) {
                escola1.getEmails().add(emailToSend);
            }
        });
    }

    @JobWorker(type = "Send_Invite", autoComplete = true)
    public void convidarClienteParaWebinal(@Variable int ID, @Variable String escola, @Variable String email) {
        System.out.println("Invite client to Webinal service task");

        String escolaCliente = String.valueOf(escola);
        String emailCliente = String.valueOf(email);


        Email emailToSend = new Email(emailCliente, "Já ouviu falar do Luno", "Olá, " + escolaCliente + " venha ao nosso webinal, é muito giro, compre por favor");
        escolas.forEach(escola1 -> {
            if (escola1.getId() == ID) {
                escola1.getEmails().add(emailToSend);
            }
        });
    }

    @JobWorker(type = "SendForm", autoComplete = true)
    public void enviarFormulario(@Variable String escola, @Variable String email) {
        System.out.println("Send Form service task");

        // simular envio de formulário por email
        String mensagemFormulario = "Olá, " + escola +
                " porque nos detesta? O que nós lhe fizemos? Por favor, preencha este formulário.";
        escolas.forEach(escola1 -> {
            if (escola1.getNome().equals(escola)) {
                escola1.getEmails().add(new Email(email, "Formulário de satisfação", mensagemFormulario));
            }
        });


        // Simular envio do formulário por email
        System.out.println("Formulario enviado para: " + email);

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
        System.out.println("Notificar Equipa de Vendas");

        Email emailToSend = new Email(email, "Novo cliente", "Olá, " + escola + " é um novo cliente, foi marcada uma reunião " + tipoReuniao + " para a data " + dataReuniao + " às " + horaReuniao);
        equipaVendas.sendEmail(emailToSend);
    }


    // Simulate the form received
    @Override
    public void run(String... args) {
        System.out.println("CaptarClienteWorker is running");

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
