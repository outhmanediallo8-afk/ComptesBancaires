package ComptesBancaires;


public class Compte {

    // Attribut de classe : nombre de comptes créés
    private static int compteur = 0;

    // Attributs de chaque compte
    private final int numero;
    private final String titulaire;
    private double solde;
    private double decouvertMax;
    private double debitMax;

    // Constructeur avec les valeurs par défaut
    public Compte(String titulaire) {
        this(titulaire, 0, 1000, 800);
    }

    // Constructeur avec un solde initial
    public Compte(String titulaire, double soldeInitial) {
        this(titulaire, soldeInitial, 1000, 800);
    }

    // Constructeur avec toutes les valeurs
    public Compte(String titulaire, double soldeInitial,
                  double debitMax, double decouvertMax) {

        if (titulaire == null || titulaire.trim().isEmpty()) {
            throw new IllegalArgumentException(
                "Le nom du titulaire est obligatoire.");
        }

        if (debitMax < 0 || decouvertMax < 0) {
            throw new IllegalArgumentException(
                "Les plafonds ne peuvent pas être négatifs.");
        }

        if (soldeInitial < -decouvertMax) {
            throw new IllegalArgumentException(
                "Le solde initial dépasse le découvert autorisé.");
        }

        compteur++;
        this.numero = compteur;
        this.titulaire = titulaire;
        this.solde = soldeInitial;
        this.debitMax = debitMax;
        this.decouvertMax = decouvertMax;
    }

    // Accesseurs
    public int getNumero() {
        return numero;
    }

    public String getTitulaire() {
        return titulaire;
    }

    public double getSolde() {
        return solde;
    }

    public double getDecouvertMax() {
        return decouvertMax;
    }

    public double getDebitMax() {
        return debitMax;
    }

    // Calcul du découvert actuel
    public double getDecouvert() {
        if (solde < 0) {
            return -solde;
        }
        return 0;
    }

    public boolean estADecouvert() {
        return solde < 0;
    }

    // Montant maximal pouvant être retiré actuellement
    public double getDebitAutorise() {
        return Math.max(0,
            Math.min(debitMax, solde + decouvertMax));
    }

    // Créditer : ajouter un montant positif
    public void crediter(double montant) throws CompteException {
        if (montant <= 0) {
            throw new CompteException(this,
                "Le montant du crédit doit être positif.");
        }

        solde += montant;
    }

    // Débiter : retirer un montant positif
    public void debiter(double montant) throws CompteException {
        if (montant <= 0) {
            throw new CompteException(this,
                "Le montant du débit doit être positif.");
        }

        if (montant > debitMax) {
            throw new CompteException(this,
                "Le débit dépasse le maximum autorisé.");
        }

        if (montant > solde + decouvertMax) {
            throw new CompteException(this,
                "Le découvert maximal serait dépassé.");
        }

        solde -= montant;
    }

    // Virement vers un autre compte
    public void virer(double montant, Compte destinataire)
            throws CompteException {

        if (destinataire == null) {
            throw new CompteException(this,
                "Le compte destinataire est invalide.");
        }

        if (destinataire == this) {
            throw new CompteException(this,
                "Le virement vers le même compte est interdit.");
        }

        // Vérifier le retrait avant toute modification
        if (montant <= 0) {
            throw new CompteException(this,
                "Le montant du virement doit être positif.");
        }

        if (montant > debitMax) {
            throw new CompteException(this,
                "Le virement dépasse le débit maximal autorisé.");
        }

        if (montant > solde + decouvertMax) {
            throw new CompteException(this,
                "Solde insuffisant pour effectuer le virement.");
        }

        // Les deux opérations sont effectuées après vérification
        debiter(montant);
        destinataire.crediter(montant);
    }

    // Réviser les plafonds
    public void setDecouvertMax(double montant)
            throws CompteException {

        if (montant < 0 || solde < -montant) {
            throw new CompteException(this,
                "Découvert maximal invalide pour le solde actuel.");
        }

        decouvertMax = montant;
    }

    public void setDebitMax(double montant)
            throws CompteException {

        if (montant < 0) {
            throw new CompteException(this,
                "Le débit maximal ne peut pas être négatif.");
        }

        debitMax = montant;
    }

    // Afficher toutes les caractéristiques
    public void afficher() {
        System.out.println("----------------------------");
        System.out.println("Numéro : " + numero);
        System.out.println("Titulaire : " + titulaire);
        System.out.println("Solde : " + solde + " MRU");
        System.out.println("Découvert maximal : "
            + decouvertMax + " MRU");
        System.out.println("Débit maximal : " + debitMax + " MRU");
        System.out.println("Découvert actuel : "
            + getDecouvert() + " MRU");
        System.out.println("Débit autorisé actuellement : "
            + getDebitAutorise() + " MRU");

        if (estADecouvert()) {
            System.out.println("Attention : le compte est à découvert.");
        } else {
            System.out.println("Le compte n'est pas à découvert.");
        }
    }

    public void afficherSolde() {
        System.out.println("Solde du compte " + numero
            + " : " + solde + " MRU");
    }
}