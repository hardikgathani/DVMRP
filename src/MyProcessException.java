

public class MyProcessException extends Exception
{
	private String error;
	public MyProcessException() {}
	public MyProcessException(String error) 
	{this.error=error;}
	public String toString()
	{return error;}
	
}
