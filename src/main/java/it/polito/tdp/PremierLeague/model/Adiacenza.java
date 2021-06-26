package it.polito.tdp.PremierLeague.model;

public class Adiacenza implements Comparable{
	
	Team a;
	int peso;
	
	public Adiacenza(Team a, int peso) {
		super();
		this.a = a;
		this.peso = peso;
	}

	public Team getA() {
		return a;
	}

	public void setA(Team a) {
		this.a = a;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		Adiacenza a = (Adiacenza) o;
		return this.peso - a.peso;
	}
	
	

}
