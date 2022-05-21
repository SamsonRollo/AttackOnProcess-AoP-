package aop.exception;

public class BulletUpgradeException extends Exception{
	public BulletUpgradeException(String message){
		super(message);
	}

	public BulletUpgradeException(){
		super("Invalid Upgrade!");
	}
}
