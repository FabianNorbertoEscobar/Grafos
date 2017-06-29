package myPackage;

public class NodoColoreado {

	private int nodo;
	private int color;
	
	public NodoColoreado(int nodo, int color) {
		this.setNodo(nodo);
		this.setColor(color);
	}

	public int getNodo() {
		return nodo;
	}

	public void setNodo(int nodo) {
		this.nodo = nodo;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}
	
}
