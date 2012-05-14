package Exceptions;

public class NotValidLocationException extends Exception
{
	String error;
	
	public NotValidLocationException(String string) {
		error = string;	
		}

	public String getError()
	{
		return this.error;
	}
}
