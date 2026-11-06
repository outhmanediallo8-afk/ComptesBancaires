package ComptesBancaires;


import java.util.ArrayList;
import java.util.Scanner;

public class MenuCompte {

    private static Scanner clavier = new Scanner(System.in);
    private static ArrayList<Compte> comptes = new ArrayList<>();

    // Rechercher un compte par son numéro
    private static Compte rechercherCompte(int numero) {
        for (Compte c : comptes) {
            if (c.getNumero() == numero) {
                return c;
            }
        }
        return null;
    }

    private static Compte demanderCompte() {
        System.out.print("Numéro du compte : ");
        int numero = clavier.nextInt();

        Compte c = rechercherCompte(numero);

        if (c == null) {
            System.out.println("Compte introuvable.");
        }

        return c;
    }

    public static void main(String[] args) {

        int choix;

        do {
            System.out.println("\n===== MENU BANCAIRE =====");
            System.out.println("1 : Créer un compte");
            System.out.println("2 : Débiter un compte");
            System.out.println("3 : Créditer un compte");
            System.out.println("4 : Virement entre comptes");
            System.out.println("5 : Afficher un compte");
            System.out.println("6 : Afficher le solde d'un compte");
            System.out.println("0 : Quitter");
            System.out.print("Votre choix : ");

            choix = clavier.nextInt();
            clavier.nextLine();

            try {
                switch (choix) {

                    case 1: {
                        System.out.print("Nom du titulaire : ");
                        String nom = clavier.nextLine();

                        System.out.print("Solde initial : ");
                        double solde = clavier.nextDouble();

                        System.out.print("Débit maximal : ");
                        double debitMax = clavier.nextDouble();

                        System.out.print("Découvert maximal : ");
                        double decouvertMax = clavier.nextDouble();

                        Compte c = new Compte(
                            nom, solde, debitMax, decouvertMax
                        );

                        comptes.add(c);

                        System.out.println(
                            "Compte créé. Numéro : " + c.getNumero()
                        );
                        break;
                    }

                    case 2: {
                        Compte c = demanderCompte();

                        if (c != null) {
                            System.out.print("Montant à retirer : ");
                            double montant = clavier.nextDouble();

                            c.debiter(montant);
                            System.out.println("Retrait effectué.");
                            c.afficherSolde();
                        }
                        break;
                    }

                    case 3: {
                        Compte c = demanderCompte();

                        if (c != null) {
                            System.out.print("Montant à déposer : ");
                            double montant = clavier.nextDouble();

                            c.crediter(montant);
                            System.out.println("Dépôt effectué.");
                            c.afficherSolde();
                        }
                        break;
                    }

                    case 4: {
                        System.out.println("Compte à débiter :");
                        Compte source = demanderCompte();

                        if (source == null) {
                            break;
                        }

                        System.out.println("Compte destinataire :");
                        Compte destination = demanderCompte();

                        if (destination == null) {
                            break;
                        }

                        System.out.print("Montant du virement : ");
                        double montant = clavier.nextDouble();

                        source.virer(montant, destination);

                        System.out.println("Virement effectué.");
                        source.afficherSolde();
                        destination.afficherSolde();
                        break;
                    }

                    case 5: {
                        Compte c = demanderCompte();

                        if (c != null) {
                            c.afficher();
                        }
                        break;
                    }

                    case 6: {
                        Compte c = demanderCompte();

                        if (c != null) {
                            c.afficherSolde();
                        }
                        break;
                    }

                    case 0:
                        System.out.println("Fin du programme.");
                        break;

                    default:
                        System.out.println("Choix invalide.");
                }

            } catch (CompteException e) {
                System.out.println(
                    "Erreur sur le compte "
                    + e.getCompte().getNumero()
                    + " : " + e.getMessage()
                );

            } catch (IllegalArgumentException e) {
                System.out.println("Erreur : " + e.getMessage());
            }

        } while (choix != 0);

        clavier.close();
    }
}