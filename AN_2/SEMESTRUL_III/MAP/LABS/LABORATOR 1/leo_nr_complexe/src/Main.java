
public class Main {
    public static void main(String[] args) {
        int lungime = args.length;
        System.out.println("Lungimea sirului de argumente este " + lungime);
        int i = 0;
        int real = 0;
        int temp_real = 0;
        int imaginar = 0;
        int temp_imaginar = 0;
        int poz_neg;
        int j;
        int lungime_expresie;
        String expresie;
        String semn = new String();
        while(i<=lungime-1)
        {

            if(i%2==0){
                expresie = args[i];
                //daca este args par inseamna ca este expresie
                System.out.println("Expresie: " + expresie);

                lungime_expresie = expresie.length();
                j = 0;

                temp_real = 0;
                //Verificam daca numarul este negativ sau pozitiv
                if(expresie.charAt(j)=='-')
                {
                    poz_neg = -1;
                    j = j + 1;
                }
                else {
                    poz_neg = 1;
                }
                while(expresie.charAt(j)!='-' && expresie.charAt(j)!='+')
                {
                    temp_real = temp_real*10+expresie.charAt(j)-'0';
//                    System.out.println(temp_real);
                    j = j+1;
                }
                temp_real = temp_real*poz_neg;
                System.out.println("Partea reala: " + temp_real);

                temp_imaginar = 0;
                if(expresie.charAt(j)=='-')
                {
                    poz_neg = -1;
                }
                else {
                    poz_neg = 1;
                }
                j = j + 1;
                if(expresie.charAt(j)=='i')
                    temp_imaginar = 1;
                else {
                    while (expresie.charAt(j) != '*') {
                        temp_imaginar = temp_imaginar * 10 + expresie.charAt(j) - '0';
                        j = j + 1;
                    }
                }
                temp_imaginar=temp_imaginar*poz_neg;
                System.out.println("Partea imaginara: " + temp_imaginar);
            }
            else {
                semn = args[i];
                System.out.println(semn);
            }
            //in cazul de mai sus facem setarile necesare

            if(i==0){
                //punem valoarea de inceput
                real = temp_real;
                imaginar = temp_imaginar;
            }
            else if(i%2==0&&i>1){
                //si mai apoi facem caluclele
//                System.out.println("Semn: " + semn);
                if(semn.charAt(0)=='+'){
                    System.out.println("Se face adunare");
                    real = real + temp_real;
                    imaginar = imaginar + temp_imaginar;
//                    System.out.println("Ecuatia finala: " + real + imaginar + "*i");
                }
                else if(semn.charAt(0)=='-'){
                    System.out.println("Se face sacdere");
                    real = real - temp_real;
                    imaginar = imaginar - temp_imaginar;
//                    System.out.println("Ecuatia finala: " + real + imaginar + "*i");
                }
                else if(semn.charAt(0)=='/')
                {
                    System.out.println("Se face impartire");
                    real = real / temp_real;
                    imaginar = imaginar / temp_imaginar;
                }
                else if(semn.charAt(0)=='*'){
                    System.out.println("Se face inmultire");
                    real = real * temp_real;
                    imaginar = imaginar * temp_imaginar;
                }
//                daca nu, inseamna ca este operatie
            }
            System.out.println("");
            i++;
        }
//        System.out.println("Semne: " + semn);
        if(imaginar<0)
            System.out.println("Ecuatia finala: " + real + imaginar + "*i");
        else
            System.out.println("Ecuatia finala: " + real + "+" + imaginar + "*i");
    }
}