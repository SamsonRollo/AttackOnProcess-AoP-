package exception;

public class CoreIncrementException extends Exception{
	public CoreIncrementException(String message){
		super(message);
	}

	public CoreIncrementException(){
		super("Cannot add core!");
	}
}