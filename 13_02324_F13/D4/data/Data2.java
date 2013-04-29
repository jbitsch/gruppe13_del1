package data;

import java.util.ArrayList;

public class Data2 implements IData2 {

	private ArrayList<OperatoerDTO2> personer;

	public Data2()
	{
		personer = new ArrayList<OperatoerDTO2>();

		// Tilføj personer
		personer.add(new OperatoerDTO2(11, "Ib Olsen","IB" ,"112233-4455","12"));
		personer.add(new OperatoerDTO2(10, "Ole Jensen","Oj" ,"112233-4455","12"));
		personer.add(new OperatoerDTO2(12, "Eva Hansen","EH" ,"112233-4455","12Qwerty"));
		personer.add(new OperatoerDTO2(14, "Peter Jensen","PJ" ,"112233-4455","12Qwerty"));
		personer.add(new OperatoerDTO2(10, "Albert Svanesen","AS" ,"112233-4455",">02324it!<"));
	}

	public OperatoerDTO2 getOperatoer(int oprId) 
	{

		for (int i = 0; personer.size() > i; i++)
		{
			if (personer.get(i).getOprId()==oprId)
			{
				return personer.get(i);
			}
		}
		return null;
	}

	public ArrayList<OperatoerDTO2> getOperatoerList()
	{
		ArrayList<OperatoerDTO2> temp = new ArrayList<OperatoerDTO2>();
		
		for (int i = 0; personer.size() > i; i++)
		{
			if (personer.get(i).getOprId()!=10)
			{
				temp.add(personer.get(i));
			}
		}
		return temp;
	}
	public ArrayList<OperatoerDTO2> getAllOperatoer()
	{
		return personer;
	}
	
	public void createOperatoer(OperatoerDTO2 opr)
	{
		personer.add(opr);
	}
	public void updateOperatoer(OperatoerDTO2 opr)
	{	
		for (int i = 0; personer.size() > i; i++)
		{
			if(personer.get(i).getOprId()==opr.getOprId())
			{
				personer.set(i, opr);
			}
		}
	}
	public void deleteOperatoer(OperatoerDTO2 opr) 
	{	
		for (int i = 0; personer.size() > i; i++)
		{
			if(personer.get(i).getOprId()==opr.getOprId())
			{
				personer.remove(i);
			}
		}
	}
	
	public boolean attemptLogin(int ID, String password) {
		boolean loginOk = false;
	
		for (int i = 0; personer.size() > i; i++)
		{
			if(personer.get(i).getOprId()==ID)
			{
				if (personer.get(i).getPassword().equals(password)){
					loginOk = true;
				} 
			}
		}
		return loginOk;
	}
}
