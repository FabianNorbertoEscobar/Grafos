package myPackage;

public class Nodo {

	private int nroNodo;
	private int grado = 0;
	
	public int getNroNodo() {
		return nroNodo;
	}

	public void setNroNodo(int nroNodo) {
		this.nroNodo = nroNodo;
	}

	public int getGrado() {
		return grado;
	}

	public void setGrado(int grado) {
		this.grado = grado;
	}

	public Nodo(int nodo) {
		this.setNroNodo(nodo);
	}
	
	public void incrementarGrado() {
		this.grado++;
	}

}
