package ipp.estg.grupo9.models;

import java.util.ArrayList;
import java.util.List;

public class EquipaIntegracao {
    private List<Email> emails;

    public EquipaIntegracao(List<Email> emails) {
        this.emails = emails;
    }

    public EquipaIntegracao() {
        this.emails = new ArrayList<>();
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
}
