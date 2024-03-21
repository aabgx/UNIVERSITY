package UI;

import validators.ValidationException;
import service.Service;

import java.util.Scanner;

public class Console {
    private Scanner scanner;
    public final Service service;

    public Console(Service service) {
        this.service = service;
        this.scanner=new Scanner(System.in);
    }

    public void start(){
        String optiune;
        while(true){
            print_menu();
            optiune=scanner.nextLine();
            switch (optiune){
                case "1" -> adauga_utilizator();
                case "2" -> sterge_utilizator();
                case "3" ->adauga_prietenie();
                case "4" ->sterge_prietenie();
                case "5" ->numarul_de_comunitati();
                case "6" ->cea_mai_sociabila_comunitate();
                case "X" -> {return;}
                case "x" -> {return;}
                default ->System.out.println("Alegere Gresita!");
                }
            }
    }

    private void print_menu(){
        System.out.println("1. Adauga utilizator");
        System.out.println("2. Sterge utilizator");
        System.out.println("3. Adauga prietenie");
        System.out.println("4. Sterge Prietenie");
        System.out.println("5. Numarul de comunitati");
        System.out.println("6. Afiseaza cea mai sociabila comunitate");
        System.out.println("X. Exit");
    }

    private String citire(String txt){
        System.out.println(txt);
        return scanner.nextLine();
    }

    private void adauga_utilizator(){
        String nume=citire("Nume: "),prenume=citire("Prenume: "),email=citire("Email: "),parola=citire("Parola: ");
        try {
            String s = service.adaugaUtilizator(nume,prenume,email,parola);
            System.out.println(s);
        }
        catch (ValidationException v){
            System.out.println(v.getMessage());
        }
    }

    private void print_menu_sterge_utilizator(){
        System.out.println("1. Sterge dupa email si parola");
        System.out.println("2. Sterge dupa id");
        System.out.println("X. Un pas inapoi");
    }

    private void print_menu_adauga_prietenie(){
        System.out.println("1. Adauga prietenie dupa email");
        System.out.println("2. Adauga prietenie dupa id");
        System.out.println("X. Un pas inapoi");
    }

    private void print_menu_sterge_prietenie(){
        System.out.println("1. Sterge prietenie dupa email");
        System.out.println("2. Sterge prietenie dupa id");
        System.out.println("X. Un pas inapoi");
    }
    private void sterge_utilizator(){
        while(true){
            print_menu_sterge_utilizator();
            String optiune = citire("Optiune: ");
            switch (optiune){
                case "1" ->sterge_util_email_parola();
                case "2" ->sterge_util_id();
                case "x" -> {return;}
                case "X" -> {return;}
                default ->System.out.println("Alegere Gresita!");
            }
        }
    }
    private void sterge_util_email_parola(){
        try{
            String s=service.stergeUtilizator(citire("Email: "),citire("Parola: "));
            System.out.println(s);
        }catch (ValidationException e){
            System.out.println(e.getMessage());
        }
    }

    private void sterge_util_id(){
        try{
            String s = service.stergeUtilizator_dupa_id(citire("Id:"));
            System.out.println(s);
        }catch(ValidationException e){
            System.out.println(e.getMessage());
        }
    }

    private void adauga_prietenie(){
        while(true){
            print_menu_adauga_prietenie();
            String optiune = citire("Optiune: ");
            switch (optiune){
                case "1"->adauga_prietenie_email();
                case "2"->adauga_prietenie_id();
                case "x"->{return;}
                case "X"->{return;}
                default ->System.out.println("Alegere Gresita!");
            }
        }
    }

    private void adauga_prietenie_email(){
        try{
            String s=service.adaugaPrietenie(citire("Email1: "),citire("Email2: "));
            System.out.println(s);
        }catch (ValidationException e){
            System.out.println(e.getMessage());
        }
    }
    private void adauga_prietenie_id(){
        try{
            String s=service.adaugaPrietenie_dupa_id(citire("Id1: "),citire("Id2: "));
            System.out.println(s);
        }catch (ValidationException e){
            System.out.println(e.getMessage());
        }
    }

    private void sterge_prietenie(){
        while(true){
            print_menu_sterge_prietenie();
            String optiune=citire("Optiune: ");

            switch (optiune){
                case "1" ->sterge_prietenie_email();
                case "2" ->sterge_prietenie_id();
                case "x"->{return;}
                case "X"->{return;}
                default ->System.out.println("Alegere Gresita!");
            }
        }
    }
    private void sterge_prietenie_email(){
        try{
            String s = service.stergePrietenie(citire("Email1: "),citire("Email2: "));
            System.out.println(s);
        }catch(ValidationException e){
            System.out.println(e.getMessage());
        }
    }
    private void sterge_prietenie_id(){
        try{
            String s = service.stergePrietenie_dupa_id(citire("Id1: "),citire("Id2: "));
            System.out.println(s);
        }catch(ValidationException e){
            System.out.println(e.getMessage());
        }
    }

    private void numarul_de_comunitati(){
        System.out.println("Numarul de comunitati este: "+service.get_numar_componente_conexe());
    }

    private void cea_mai_sociabila_comunitate(){
        System.out.println("Cea mai sociabila comunitate e formata din: : "+service.get_comp_conecta());
    }
}
