package ipp.estg.grupo9.database.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Escola implements Serializable {
    private int id;

    private String escola;
    private String morada;
    private String email;
    private String telefone;
    private String cidade;
    private String detalhes;
    private String segmento;
    private String historicoInteracoes;
    private String codigoPostal;
    private final List<Email> emails;


    private static int count = 0;

    public Escola(String escola, String morada, String email, String telefone, String cidade, String detalhes, String segmento, String historicoInteracoes, String codigoPostal) {
        this.id = count++;
        this.escola = escola;
        this.morada = morada;
        this.email = email;
        this.telefone = telefone;
        this.cidade = cidade;
        this.detalhes = detalhes;
        this.segmento = segmento;
        this.historicoInteracoes = historicoInteracoes;
        this.codigoPostal = codigoPostal;
        this.emails = new ArrayList<>();
    }




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return escola;
    }

    public void setNome(String escola) {
        this.escola = escola;
    }

    public String getMorada() {
        return morada;
    }

    public void setMorada(String morada) {
        this.morada = morada;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getDetalhes() {
        return detalhes;
    }

    public void setDetalhes(String detalhes) {
        this.detalhes = detalhes;
    }

    public String getSegmento() {
        return segmento;
    }

    public void setSegmento(String segmento) {
        this.segmento = segmento;
    }

    public String getHistoricoInteracoes() {
        return historicoInteracoes;
    }

    public void setHistoricoInteracoes(String historicoInteracoes) {
        this.historicoInteracoes = historicoInteracoes;
    }

    public List<Email> getEmails() {
        return emails;
    }

    public void sendEmail(Email email){
        this.emails.add(email);
    }

    public void removeEmail(Email email){
        this.emails.remove(email);
    }

    public static void removeCount(){
        count--;
    }

}
