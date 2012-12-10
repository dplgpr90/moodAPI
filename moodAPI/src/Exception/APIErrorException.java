package Exception;

public class APIErrorException extends Exception {
	
	public APIErrorException() {
    }

    public APIErrorException(String msg) {
        super(msg);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
