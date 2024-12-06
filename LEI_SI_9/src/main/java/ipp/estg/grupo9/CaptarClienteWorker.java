package ipp.estg.grupo9;

import io.camunda.zeebe.spring.client.annotation.JobWorker;
import io.camunda.zeebe.spring.client.annotation.Variable;
import ipp.estg.grupo9.models.Email;
import ipp.estg.grupo9.models.Escola;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;

@Component
public class CaptarClienteWorker {
    ArrayList<Escola> escolas = new ArrayList<>();

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

        HashMap<String, Object> var = new HashMap<>();
        var.put("ID", id);
        var.put("escola", escolaCliente);
        var.put("morada", moradaCliente);
        var.put("email", emailCliente);
        var.put("telefone", telefoneCliente);
        var.put("cidade", cidadeCliente);
        var.put("detalhes", detalhesCliente);
        var.put("segmento", segmentoCliente);
        var.put("historicoInteracoes", historicoInteracoesCliente);
        var.put("codigoPostal", codigoPostalCliente);
        return var;
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

        HashMap<String, Object> var = new HashMap<>();
        var.put("ID", id);
        var.put("escola", escolaCliente);
        var.put("morada", moradaCliente);
        var.put("email", emailCliente);
        var.put("telefone", telefoneCliente);
        var.put("cidade", cidadeCliente);
        var.put("detalhes", detalhesCliente);
        var.put("segmento", segmentoCliente);
        var.put("historicoInteracoes", historicoInteracoesCliente);
        var.put("codigoPostal", codigoPostalCliente);
        return var;


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

}
