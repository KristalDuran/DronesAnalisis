
package Model;

/**
 * This clase extends of Exception 
 * @author Kris
 */
public class OwnException extends Exception{
    
    //This array has the messaje of all exceptions
    private String[] arrayExceptions = {"A data that is not numeric has been entered",
                                        "The size of the tracks is too small with respect to the number of stations",
                                        "It is not possible to send the requested number of drones in the allotted time"};
    
    private int numberException;
    
    public OwnException(int numExcpetion) {
        String message = msg(numExcpetion);
        
    }
    
    public OwnException(String msg){
        super(msg);
    }
    
    /**
     * Take the message of the expecion
     * @param num
     * @return String
     */
    public String msg(int num){
        return arrayExceptions[num];
    }
    
    /**
     * This method validate if the string only has numerics 
     * @param value
     * @return boolean
     */
    public boolean isNumeric(String value){
        for (int i = 0; i < value.length(); i++) {
            if (!Character.isDigit(value.charAt(i))) {
                return true;
            }
        }
        return false;
    }
}
