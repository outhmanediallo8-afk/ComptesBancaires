package ComptesBancaires;

public class TestCompte {

    public static void main(String[] args) {

        try {
            // 1. Création des deux comptes
            Compte c1 = new Compte("Ahmed", 1000);

            Compte c2 = new Compte(
                "Fati", 50000, 6000, 5000
            );

            // 2. Affichage initial
            System.out.println("=== ÉTAT INITIAL ===");
            c1.afficher();
            c2.afficher();

            // 3. Retraits
            c1.debiter(1300);
            c2.debiter(600);

            System.out.println("\n=== APRÈS LES RETRAITS ===");
            c1.afficher();
            c2.afficher();

            // 4. Dépôt
            c1.crediter(500);

            System.out.println("\n=== APRÈS LE DÉPÔT ===");
            c1.afficher();
            c2.afficher();

            // 5. Virement de Fati vers Ahmed
            c2.virer(1000, c1);

            System.out.println("\n=== APRÈS LE VIREMENT ===");
            c1.afficher();
            c2.afficher();

        } catch (CompteException e) {
            System.out.println("Erreur sur le compte "
                + e.getCompte().getNumero()
                + " : " + e.getMessage());
        }
    }
}