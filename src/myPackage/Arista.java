package myPackage;

public class Arista {

	private int nodo1;
	private int nodo2;

	public Arista(int nodo1, int nodo2) {
		this.setNodo1(nodo1);
		this.setNodo2(nodo2);
	}

	public int getNodo1() {
		return nodo1;
	}

	public void setNodo1(int nodo1) {
		this.nodo1 = nodo1;
	}

	public int getNodo2() {
		return nodo2;
	}

	public void setNodo2(int nodo2) {
		this.nodo2 = nodo2;
	}

	public void mostrar() {
		System.out.println(this.nodo1 + " " + this.nodo2);
	}

}
