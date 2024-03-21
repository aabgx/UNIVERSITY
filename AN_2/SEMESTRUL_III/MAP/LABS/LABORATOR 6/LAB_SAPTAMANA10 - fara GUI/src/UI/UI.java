package UI;

import domain.Prietenie;
import domain.Utilizator;
import domain.validators.ValidationException;
import service.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class UI {
    private final Scanner scanner;
    private final Service service;

    private boolean authenticated = false;

    public UI(Service service){
        this.service=service;
        this.scanner=new Scanner(System.in); //citeste de la standard imput
    }

    public void start() {
        int alegere;
        while (true) {
            printeazaMeniu();
            System.out.println("Alegerea dvs. este: ");
            alegere=scanner.nextInt();

            //!!!!!!vezi ca atunci cand citesti int baga \n in urmatoarea linie de citit, deasta fac un scanner.nextLine() gol, sa bage new line ul ala
            scanner.nextLine();

            try {
                if (authenticated) {
                    switch (alegere) {
                        case 1 -> adaugaPrieten();
                        case 2 -> stergePrieten();
                        case 3 -> obtinePrietenii();
                        case 4 -> deconectare();
                        case 5 -> stergeCont();
                        case 6 -> afisNrComunitati();
                        case 7 -> afisCeaMaiSociabilaComunitate();
                        default -> System.out.println("Alegere invalida!");
                    }
                } else {
                    switch (alegere) {
                        case 1 -> conectare();
                        case 2 -> creareCont();
                        case 3 -> System.exit(-1);
                        default -> System.out.println("Alegere invalida!");
                    }
                }
            } catch (Error e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void conectare() {
        String email = citire("Email: ");
        String parola = citire("Parola: ");
        authenticated = service.conectareUtilizator(email, parola);
        if (authenticated) {
            Utilizator u = service.getUtilizatorCurent();
            System.out.println("Te-ai conectat ca: " + u.getNume() + " " + u.getPrenume());
        }
    }

    private void creareCont() {
        String nume = citire("Nume: ");
        String prenume = citire("Prenume: ");
        String email = citire("Email: ");
        String parola = citire("Parola: ");

        authenticated = service.creareUtilizator(nume,prenume, email, parola);
        if (!authenticated) {
            System.out.println("Nu ati introdus bine datele!");
        }
    }

    private void deconectare() {
        service.deconectareUtilizator();
        authenticated = false;
    }
    private void adaugaPrieten() {
        String IdPrieten = citire("FiendId: ");
        LocalDateTime data=LocalDateTime.now();
        service.adaugaPrieten(IdPrieten,data);
    }

    private void stergePrieten() {
        String IdPrieten = citire("FriendId: ");
        service.stergePrieten(service.getIdCurent(),IdPrieten);
    }

    private void obtinePrietenii() {
        System.out.println("Prieteni: ");
        for (Utilizator pr : service.obtineUtilizatoriPrieteni()) {
            System.out.println(pr.getNume()+" "+pr.getPrenume());
        }
        System.out.println("---------");
    }

    private void stergeCont() {
        service.stergeCont();
        System.out.println("Contul a fost sters!");
        authenticated = false;
    }

    private void printeazaMeniu(){
        if (authenticated) {
            System.out.println("1. Adauga prieten.");
            System.out.println("2. Sterge prieten");
            System.out.println("3. Afiseaza lista de prieteni");
            System.out.println("4. Deconectare");
            System.out.println("5. Stergere cont");
            System.out.println("6. Afiseaza numar de comunitati");
            System.out.println("7. Afiseaza cea mai mare comunitate");
            //System.out.println("8. Update nume");
        } else {
            System.out.println("1. Intra in cont");
            System.out.println("2. Creare cont");
            System.out.println("3. Exit");
        }
    }

    private String citire(String text) {
        System.out.println(text);
        return scanner.nextLine();
    }

    private void afisNrComunitati(){
        System.out.println("Numarul de comunitati este: "+service.nrComunitati());
    }

    private void afisCeaMaiSociabilaComunitate(){
        System.out.println("Cea mai sociabila comunitate are "+service.ceaMaiSociabilaComunitate()+" membrii");
    }


}
