package it.polito.tdp.PremierLeague.model;

public class Partita {
	
	Team a;
	Team b;
	int risultato;
	public Partita(Team a, Team b, int risultato) {
		super();
		this.a = a;
		this.b = b;
		this.risultato = risultato;
	}
	public Team getA() {
		return a;
	}
	public void setA(Team a) {
		this.a = a;
	}
	public Team getB() {
		return b;
	}
	public void setB(Team b) {
		this.b = b;
	}
	public int getRisultato() {
		return risultato;
	}
	public void setRisultato(int risultato) {
		this.risultato = risultato;
	}
	
	

}
