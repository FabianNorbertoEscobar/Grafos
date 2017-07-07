package myPackage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;

public class Prim {

	private GrafoNDP grafo;
	private int cantNodos;
	private int cantAristas;

	private ArrayList<AristaPonderada> arbol;
	private int[] padres;

	private ArrayList<Integer> nodosConectados;
	private ArrayList<Integer> nodosNoConectados;

	private int minimoCosto;
	private static final int INFINITO = -1;

	public GrafoNDP getGrafo() {
		return grafo;
	}

	public int getCantNodos() {
		return cantNodos;
	}

	public int getCantAristas() {
		return cantAristas;
	}

	public int getMinimoCosto() {
		return minimoCosto;
	}

	public Prim(GrafoNDP grafo) {
		this.grafo = grafo;
		this.cantNodos = this.grafo.getGrafo().getOrdenMatriz();
		this.cantAristas = this.grafo.getCantAristas();

		new ArrayList<AristaPonderada>();
		this.arbol = new ArrayList<AristaPonderada>();
		this.padres = new int[this.cantNodos];
		this.inicializarPadres();
		this.minimoCosto = 0;

		this.nodosNoConectados = new ArrayList<Integer>();
		this.nodosConectados = new ArrayList<Integer>();
	}

	public void ejecutar() throws IOException {
		// agregamos todos los nodos al conjunto de nodos no conectados
		for (int i = 0; i < this.cantNodos; i++) {
			this.nodosNoConectados.add(i);
		}

		// elijo un nodo al azar para comenzar
		int nodoInicial = (int) (Math.random() * this.cantNodos);
		this.nodosNoConectados.remove(this.nodosNoConectados.indexOf(nodoInicial));
		// lo agrego al conjunto de nodos conectados
		this.nodosConectados.add(nodoInicial);

		int indice;
		int nodoCreceRama = 0;
		int nodoMasCercano = 0;
		// mientras el árbol no conecte todas los nodos
		while (this.arbol.size() < this.cantNodos - 1) {
			// pongo la distancia en infinito ya que no conocemos el nodo más cercano
			int distancia = INFINITO;

			// para cada nodo del conjunto de nodos conectados
			for (int nodoActual : this.nodosConectados) {
				// para cada nodo del conjunto de nodos no conectados
				for (int nodo : this.nodosNoConectados) {
					// genero el índice de la arista entre el par de nodos
					if (nodoActual < nodo) {
						indice = this.grafo.getGrafo().getIndice(nodoActual, nodo);
					} else {
						indice = this.grafo.getGrafo().getIndice(nodo, nodoActual);
					}

					// si el grafo tiene esta arista
					if (this.grafo.getGrafo().hayArista(indice)) {
						// si todavía no se determinó el nodo más cercano o se encontró
						// uno más cercano
						if (distancia == INFINITO || this.grafo.getPesoArista(indice) < distancia) {
							// si la arista no cierra ciclo
							if (find(nodoActual) != find(nodo)) {
								// el nodo es el conectable más cercano
								distancia = this.grafo.getPesoArista(indice);
								nodoMasCercano = nodo;
								nodoCreceRama = nodoActual;
							}
						}
					}
				}
			}
			
			// le pongo el mismo padre a los nodos
			union(nodoCreceRama, nodoMasCercano);
			// agrego la arista al arbol abarcador mínimo
			if (nodoCreceRama < nodoMasCercano) {
				this.arbol.add(new AristaPonderada(nodoCreceRama, nodoMasCercano, distancia));
			} else {
				this.arbol.add(new AristaPonderada(nodoMasCercano, nodoCreceRama, distancia));
			}
			
			// acumulo el peso de la arista al costo mínimo
			this.minimoCosto += distancia;
			// quito ese nodo del conjunto de nodos no conectados
			this.nodosNoConectados.remove(this.nodosNoConectados.indexOf(nodoMasCercano));
			// lo agrego al conjunto de nodos conectados
			this.nodosConectados.add(nodoMasCercano);
		}

		// escribo la solucion en consola
		this.escribirSolucionEnConsola();

		// escribo la solucion completa en un archivo
		this.escribirSolucionEnArchivo("PRIM" + "_" + this.cantNodos + "_"
				+ String.format("%.2f", this.getGrafo().getPtajeAdyacencia()) + ".out");

	}

	public void inicializarPadres() {
		for (int i = 0; i < padres.length; i++) {
			this.padres[i] = i;
		}
	}

	public int find(int nodo) {
		return this.padres[nodo] == nodo ? nodo : find(this.padres[nodo]);
	}

	public void union(int nodo1, int nodo2) {
		this.padres[find(nodo1)] = this.padres[find(nodo2)];
	}

	public void escribirSolucionEnConsola() {
		System.out.println("PRIM:");
		System.out.println("Costo del Árbol Abarcador Mínimo: " + this.minimoCosto);
		System.out.println("Cantidad de Ramas del Árbol: " + this.arbol.size());
		System.out.println("Lista de Ramas:");
		for (AristaPonderada rama : arbol) {
			System.out.println(rama.getNodo1() + " " + rama.getNodo2());
		}
	}

	public void escribirSolucionEnArchivo(String path) throws IOException {
		FileWriter file = new FileWriter(path);
		BufferedWriter buffer = new BufferedWriter(file);

		buffer.write(String.valueOf(this.cantNodos));
		buffer.write(" ");
		buffer.write(String.valueOf(this.getGrafo().getCantAristas()));
		buffer.write(" ");
		buffer.write(String.valueOf(NumberFormat.getInstance().format(this.getGrafo().getPtajeAdyacencia())));
		buffer.write(" ");
		buffer.write(String.valueOf(this.getGrafo().getGradoMax()));
		buffer.write(" ");
		buffer.write(String.valueOf(this.getGrafo().getGradoMin()));
		buffer.newLine();

		buffer.write(String.valueOf(this.minimoCosto));
		buffer.write(" ");
		buffer.write(String.valueOf(this.arbol.size()));
		buffer.newLine();

		for (AristaPonderada rama : arbol) {
			buffer.write(String.valueOf(rama.getNodo1()));
			buffer.write(" ");
			buffer.write(String.valueOf(rama.getNodo2()));
			buffer.write(" ");
			buffer.write(String.valueOf(rama.getPeso()));
			buffer.newLine();
		}

		buffer.close();
	}
}
