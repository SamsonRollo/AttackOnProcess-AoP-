package exception;

public class TokenException extends Exception{
	public TokenException(String message){
		super(message);
	}

	public TokenException(){
		super("Invalid Token!");
	}
}
