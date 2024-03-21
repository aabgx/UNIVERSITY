package socialnetwork.com.validators;

public class ValidationException extends RuntimeException{

    /***
     * Suprascrie metoda
     * @param mesaj Mesajul erorii
     */
    public ValidationException(String mesaj){
        super(mesaj);
    }

    /***
     * Suprascrie metoda
     * @param mesaj Mesajul erorii
     * @param cauza Cauza erorii
     */
    public ValidationException(String mesaj, Throwable cauza) {
        super(mesaj, cauza);
    }

    /***
     * Suprascrie metoda
     * @param cauza Cauza erorii
     */
    public ValidationException(Throwable cauza) {
        super(cauza);
    }



}
