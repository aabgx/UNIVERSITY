package socialnetwork.com.UI;


import socialnetwork.com.domain.User;
import socialnetwork.com.service.Service;
import socialnetwork.com.validators.ValidationException;

import java.util.Scanner;

public class Console {
    private Scanner scanner;
    public final Service service;

    public Console(Service service) {
        this.service = service;
        this.scanner=new Scanner(System.in);
    }

/*    public void cautaEmail(){
        String email=citire("Email: ");
        System.out.println(service.cautaDupaEmail(email));
    }*/

/*    public void cautaPrenume(){
        String prenume=citire("Prenume: ");
        System.out.println(service.cautaDupaPrenume(prenume));
    }*/

/*    public void cautaNume(){
        String nume=citire("Nume: ");
        System.out.println(service.cautaDupaNume(nume));
    }*/
    public void printMenuCautaUtilizator(){
        System.out.println("1. Cauta Email");
        System.out.println("2. Cauta Nume");
        System.out.println("3. Cauta Prenume");
        System.out.println("X. Un pas inapoi");
    }
/*    public void cautaUtilizator(){
        while(true){
            printMenuCautaUtilizator();
            String cmd=citire("Optiunea dvs: ");
            switch (cmd){
                case "1" ->cautaEmail();
                case "2" ->cautaNume();
                case "3" ->cautaPrenume();
                case "x" ->{return;}
                case "X" ->{return;}
            }
        }
    }*/
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
                case "7" ->actualizeaza_utilizator();
                //case "8" ->cautaUtilizator();
                case "X" -> {return;}
                case "x" -> {return;}
                case "p" ->afiseaza_prietenii();
                case "P" ->afiseaza_prietenii();
                case "U" ->afiseaza_utilizatori();
                case "u" ->afiseaza_utilizatori();
                default ->System.out.println("Alegere Gresita!");
                }
            }
    }

    private void print_menu(){
        System.out.println("1. Adauga utilizator");
        System.out.println("2. Sterge utilizator");
        System.out.println("3. Adauga prietenie");
        System.out.println("4. Sterge Friend");
        System.out.println("5. Numarul de comunitati");
        System.out.println("6. Afiseaza cea mai sociabila comunitate");
        System.out.println("7. Actualizeaza utilizator");
        //System.out.println("8. Cauta utilizator");
        System.out.println("U. Afiseaza toti utilizatorii");
        System.out.println("P. Afiseaza toate prieteniile");
        System.out.println("X. Exit");
    }

    private String citire(String txt){
        System.out.println(txt);
        return scanner.nextLine();
    }

    private void adauga_utilizator(){
        String nume=citire("Nume: "),prenume=citire("Prenume: "),email=citire("Email: "),parola=citire("Parola: ");
        try {
            String s = service.addUser(nume,prenume,email,parola);
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
            User s=service.deleteUser(citire("Email: "),citire("Parola: "));
            System.out.println(s);
        }catch (ValidationException e){
            System.out.println(e.getMessage());
        }
    }

    private void sterge_util_id(){
        try{
            String s = service.deleteUserById(Integer.parseInt(citire("Id:")));
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
            String s=service.addFriend(citire("Email1: "),citire("Email2: "));
            System.out.println(s);
        }catch (ValidationException e){
            System.out.println(e.getMessage());
        }
    }
    private void adauga_prietenie_id(){
        try{
            String s=service.addFriendById(Integer.parseInt(citire("Id1: ")),Integer.parseInt(citire("Id2: ")));
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

    private void afiseaza_utilizatori(){
        System.out.println(service.getAllUsers());
    }

    private void afiseaza_prietenii(){
        System.out.println(service.getAllFriends());
    }
    private void sterge_prietenie_email(){
        try{
            String s = service.deleteFrind(citire("Email1: "),citire("Email2: "));
            System.out.println(s);
        }catch(ValidationException e){
            System.out.println(e.getMessage());
        }
    }
    private void sterge_prietenie_id(){
        try{
            String s = service.deleteFriendById(Integer.parseInt(citire("Id1: ")),Integer.parseInt(citire("Id2: ")));
            System.out.println(s);
        }catch(ValidationException e){
            System.out.println(e.getMessage());
        }
    }

    private void numarul_de_comunitati(){
        System.out.println("Numarul de comunitati este: "+service.getConexNumber());
    }

    private void cea_mai_sociabila_comunitate(){
        System.out.println("Cea mai sociabila comunitate e formata din: : "+service.getCompConexNumber());
    }

    private void print_menu_actualizeaza_utilizator(){
        System.out.println("1. Actualizeaza dupa Email si Parola.");
        System.out.println("2. Actualizeaza dupa ID.");
        System.out.println("X. Un pas inapoi.");
    }

    private void actualizeaza_utilizator_email(){
        String email=citire("Email:");
        String parola=citire("Parola:");
        System.out.println("Noile campuri: (lasa liber daca nu se modifica)");
        String new_lastname=citire("Noul Nume: ");
        String new_fistname=citire("Noul Prenume: ");
        String new_email=citire("Noul Email: ");
        String new_parola=citire("Noua Parola: ");
        try{
            service.updateUser(email,parola,new_fistname,new_lastname,new_email,new_parola);
        }
        catch (ValidationException e){
            System.out.println(e.getMessage());
        }
    }

    private void actualizeaza_utilizator_id(){
        Integer id=Integer.parseInt(citire("ID:"));
        System.out.println("Noile campuri: (lasa liber daca nu se modifica)");
        String new_lastname=citire("Noul Nume: ");
        String new_fistname=citire("Noul Prenume: ");
        String new_email=citire("Noul Email: ");
        String new_parola=citire("Noua Parola: ");
        try{
            service.updateUserById(id,new_fistname,new_lastname,new_email,new_parola);
        }
        catch (ValidationException e){
            System.out.println(e.getMessage());
        }
    }

    private void actualizeaza_utilizator(){
        while(true){
            print_menu_actualizeaza_utilizator();
            String opt=citire("Optiune: ");
            switch (opt){
                case "1"->actualizeaza_utilizator_email();
                case "2"->actualizeaza_utilizator_id();
                case "x"->{return;}
                case "X"->{return;}
            }
        }
    }
}
