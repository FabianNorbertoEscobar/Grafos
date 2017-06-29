package myPackage;

public class AristaPonderada extends Arista {

	private int peso;

	public AristaPonderada(int nodo1, int nodo2, int peso) {
		super(nodo1, nodo2);
		this.setPeso(peso);
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	@Override
	public void mostrar() {
		System.out.println(this.getNodo1() + " " + this.getNodo2() + " " + this.getPeso());
	}
}
