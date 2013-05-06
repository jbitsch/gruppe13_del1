package exceptions;

import java.io.IOException;

public class BigTaraException extends IOException
{ 
	//declare information that will be passed to the handlers

	
	public BigTaraException()
	{	
		super("Ups, tarra skal være større end brutto");
	}
	
	//override the "toString" method
	public String toString()
	{
		return "Ups, tarra skal være større end brutto";
	}
}
