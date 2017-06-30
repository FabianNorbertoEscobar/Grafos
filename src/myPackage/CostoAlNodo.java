package myPackage;

import java.util.ArrayList;

public class CostoAlNodo implements Comparable<CostoAlNodo> {

	private int nodo;
	private int costoMinimo;
	private ArrayList<Integer> caminoMasCorto;
	
	public CostoAlNodo(int nodo, int costoMinimo) {
		this.nodo = nodo;
		this.costoMinimo = costoMinimo;
		this.caminoMasCorto = new ArrayList<Integer>();
	}
	
	public int getNodo() {
		return nodo;
	}

	public void setNodo(int nodo) {
		this.nodo = nodo;
	}

	public int getCostoMinimo() {
		return costoMinimo;
	}

	public void setCostoMinimo(int costoMinimo) {
		this.costoMinimo = costoMinimo;
	}

	public ArrayList<Integer> getCaminoMasCorto() {
		return caminoMasCorto;
	}

	public void setCaminoMasCorto(ArrayList<Integer> caminoMasCorto) {
		this.caminoMasCorto = caminoMasCorto;
	}
	
	public void agregarNodoAlCamino(int nodo) {
		this.caminoMasCorto.add(nodo);
	}

	@Override
	public int compareTo(CostoAlNodo nodo) {
		return this.costoMinimo - nodo.getCostoMinimo();
	}
	
}
