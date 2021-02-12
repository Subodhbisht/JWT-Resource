package sb.resource.jwtresource.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(String excpetionString){
        super(excpetionString);
    }
}
