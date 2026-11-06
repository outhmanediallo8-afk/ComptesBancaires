package ComptesBancaires;


public class CompteException extends Exception {

    private Compte compte;

    public CompteException(Compte compte, String message) {
        super(message);
        this.compte = compte;
    }

    public Compte getCompte() {
        return compte;
    }
}