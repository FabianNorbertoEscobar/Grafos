package myPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ProbadorColoreoGrafoNP {

	private ArrayList<Arista> entrada;
	private ArrayList<NodoColoreado> salida;
	private int cantNodos;

	public ProbadorColoreoGrafoNP(String pathIn, String pathOut) throws FileNotFoundException {
		File file = new File(pathIn);
		Scanner scan = new Scanner(file);
		this.entrada = new ArrayList<Arista>();
		this.salida = new ArrayList<NodoColoreado>();

		int cantAristas;
		int fil;
		int col = 0;
		this.cantNodos = scan.nextInt();
		cantAristas = scan.nextInt();
		scan.nextDouble();
		scan.nextInt();
		scan.nextInt();

		for (int i = 0; i < cantAristas; i++) {
			fil = scan.nextInt();
			col = scan.nextInt();
			this.entrada.add(new Arista(fil, col));
		}

		scan.close();
		file = new File(pathOut);
		scan = new Scanner(file);

		scan.nextInt();
		scan.nextInt();
		scan.nextInt();
		scan.nextDouble();
		scan.nextInt();
		scan.nextInt();

		for (int i = 0; i < this.cantNodos; i++) {
			fil = scan.nextInt(); // nodo
			col = scan.nextInt(); // color
			salida.add(new NodoColoreado(fil, col));
		}

		scan.close();
	}

	public boolean probar() {
		int nodoColoreado;
		int nodo1;
		int nodo2;
		int color1 = -1;
		int color2 = -1;
		boolean flagColor1 = false;
		boolean flagColor2 = false;

		for (int i = 0; i < this.entrada.size(); i++) {
			nodo1 = this.entrada.get(i).getNodo1();
			nodo2 = this.entrada.get(i).getNodo2();
			for (int j = 0; j < this.salida.size(); j++) {
				nodoColoreado = this.salida.get(j).getNodo();
				if (nodoColoreado == nodo1 && !flagColor1) {
					color1 = this.salida.get(j).getColor();
					flagColor1 = true;
				} else {
					if (nodoColoreado == nodo2 && !flagColor2) {
						color2 = this.salida.get(j).getColor();
						flagColor2 = true;
					}
				}
				if (flagColor1 && flagColor2) {
					if (color1 == color2 || (nodoColoreado == nodo1 && nodoColoreado == nodo2)) // nodos
																								// adyacentes
																								// con
																								// el
																								// mismo
																								// color,
																								// o
																								// nodo
																								// coloreado
																								// mas
																								// de
																								// 1
																								// vez
						return false;
				}
			}
			if (!flagColor1 || !flagColor2) // hay algún nodo sin colorear
				return false;
			flagColor1 = false;
			flagColor2 = false;
		}

		return true;
	}

	public void mostrarEntrada() {
		for (int i = 0; i < this.entrada.size(); i++) {
			System.out.println(this.entrada.get(i).getNodo1() + " " + this.entrada.get(i).getNodo2());
		}
	}

	public void mostrarSalida() {
		for (int i = 0; i < this.salida.size(); i++)
			System.out.println(this.salida.get(i).getNodo() + " " + this.salida.get(i).getColor());
	}
}
