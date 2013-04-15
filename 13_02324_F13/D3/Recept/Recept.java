package Recept;

import java.util.*;

public class Recept {
	int receptNr;
	int vareNr;
	String vNavn;
	double netto;
	double tolerance;
	Date date;
	
	public String toString()
	{
		String toReturn = receptNr +"\n"+ vareNr +"\n"+ vNavn+"\n"+netto+"\n"+tolerance+"\n"+date.toString();
		return toReturn;
	}
	public int getReceptNr() {
		return receptNr;
	}

	public void setReceptNr(int receptNr) {
		this.receptNr = receptNr;
	}

	public int getVareNr() {
		return vareNr;
	}

	public void setVareNr(int vareNr) {
		this.vareNr = vareNr;
	}

	public String getvNavn() {
		return vNavn;
	}

	public void setvNavn(String vNavn) {
		this.vNavn = vNavn;
	}

	public double getNetto() {
		return netto;
	}

	public void setNetto(double netto) {
		this.netto = netto;
	}

	public double getTolerance() {
		return tolerance;
	}

	public void setTolerance(double tolerance) {
		this.tolerance = tolerance;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
