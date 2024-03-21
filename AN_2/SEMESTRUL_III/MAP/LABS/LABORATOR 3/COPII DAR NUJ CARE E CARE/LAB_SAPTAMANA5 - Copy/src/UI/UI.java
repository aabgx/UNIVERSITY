package UI;

import domain.Prietenie;
import domain.Utilizator;
import domain.validators.ValidationException;
import service.Service;

import java.util.List;
import java.util.Scanner;

public class UI {
    private final Scanner scanner;
    private final Service service;

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

            switch(alegere){
                case 1 -> adaugaPrietenie();
                case 2 -> stergePrietenie();

                case 3 -> printeazaPrietenii();

                case 4 -> adaugaUtilizator();
                case 5 -> stergeUtilizator();
                case 6 -> printeazaUtilizatori();
                case 7 -> afisNrComunitati();
                case 8 -> afisCeaMaiSociabilaComunitate();
                case 9 -> updateUtilizator();
                case 10 -> System.exit(0);
                default -> System.out.println("Alegere invalida!");
            }
        }
    }

    private void printeazaMeniu(){
        System.out.println("\n1. Adauga prietenie");
        System.out.println("2. Sterge prietenie");
        System.out.println("3. Vezi lista prietenii\n");
        System.out.println("4. Adauga utilizator");
        System.out.println("5. Sterge utilizator");
        System.out.println("6. Vezi lista de utilizatori\n");
        System.out.println("7. Numarul de comunitati");
        System.out.println("8. Cea mai sociabila comunitate\n");
        System.out.println("9. Update utilizator\n");
        System.out.println("10. Exit\n");
    }

    private void printeazaPrietenii(){
        List<Prietenie> lista1 = service.getAllPrietenii();
        for(Prietenie prietenie: lista1)
        {System.out.println(prietenie);}
    }

    private String citire(String text) {
        System.out.println(text);
        return scanner.nextLine();
    }
    private void adaugaPrietenie(){
        String string1,string2;
        string1=citire("Id prieten 1: ");
        string2=citire("Id prieten 2: ");
        try{
            service.adaugaPrietenie(string1,string2);
        }
        catch(ValidationException e){
            System.out.println("\u001B[31m"+e.getMessage()+"\u001B[0m");
        }
    }
    private void stergePrietenie(){
        String string;
        string=citire("Id prietenie: ");
        try{
            service.stergePrietenie(string);
        }
        catch(ValidationException e){
            System.out.println("\u001B[31m"+e.getMessage()+"\u001B[0m");
        }

    }

    private void adaugaUtilizator(){
        String string1=citire("Email: ");
        String string2=citire("Parola: ");
        String string3=citire("Nume: ");
        String string4=citire("Prenume: ");
        try{
            service.adaugaUtilizator(string1,string2,string3,string4);
        }
        catch(ValidationException e){
            System.out.println("\u001B[31m"+e.getMessage()+"\u001B[0m");
        }
    }

    private void stergeUtilizator(){
        String string;
        string=citire("Id utilizator: ");
        try{
            service.stergeUtilizator(string);
        }
        catch(ValidationException e){
            System.out.println("\u001B[31m"+e.getMessage()+"\u001B[0m");
        }
    }

    private void printeazaUtilizatori(){
        List<Utilizator> lista2 = service.getAllUtilizatori();
        for(Utilizator utilizator: lista2)
        {System.out.println(utilizator);}
    }

    private void afisNrComunitati(){
        System.out.println("Numarul de comunitati este: "+service.nrComunitati());
    }

    private void afisCeaMaiSociabilaComunitate(){
        System.out.println("Cea mai sociabila comunitate are "+service.ceaMaiSociabilaComunitate()+" membrii");
    }

    private void updateUtilizator(){
        String Id,Nume,Prenume,Email,Parola;
        Id=citire("Id utilizator: ");
        Nume=citire("Nume nou: ");
        Prenume=citire("Prenume nou: ");
        Email=citire("Email nou: ");
        Parola=citire("Parola noua: ");
        try{
            service.updateUtilizator(Id,Nume,Prenume,Email,Parola);
        }
        catch(ValidationException e){
            System.out.println("\u001B[31m"+e.getMessage()+"\u001B[0m");
        }
    }
}
