package funktionalitet;

import java.util.*;

public class BrugerValg {
	public String handling;

	/** Egenskab 'kontovalg' saettes fra JSP-side vaelg_konto.jsp */
	public void setUserValg(int id) {

	}

	public void setHandling(String h) {
		handling = h;
	}


	public void udfoerHandling() 
	{

		if (handling.equals("changePw"))
		{
			//TODO chagePW
		}
		else if (handling.equals("weight"))
		{	
		//TODO weight
		}
		else if (handling.equals("newUser"))
		{
					//TODO newUser
		}
		else if (handling.equals("seeUser"))
		{
			//TODO user
		}
		else
			System.out.println("Ukendt handling: " + handling);

		handling = null; 
	}

	@Override
	public String toString() {
		//TODO 
		return "";
	}

}