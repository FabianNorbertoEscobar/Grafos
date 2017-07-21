package myPackage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BFS {

	private GrafoNDNP grafo;
	private int cantNodos;
	private int cantAristas;

	private int nodoInicial;
	private ArrayList<Arista> arbol;
	private boolean[] nodoTerminado;
	
	public GrafoNDNP getGrafo() {
		return grafo;
	}

	public int getCantNodos() {
		return cantNodos;
	}

	public int getCantAristas() {
		return cantAristas;
	}

	public int getNodoInicial() {
		return nodoInicial;
	}

	public BFS(GrafoNDNP grafo, int nodoInicial) {
		this.grafo = grafo;
		this.cantNodos = grafo.getCantNodos();
		this.cantAristas = grafo.getCantAristas();

		this.nodoInicial = nodoInicial;
		this.arbol = new ArrayList<Arista>();
		this.nodoTerminado = new boolean[this.cantNodos];
	}

	public void ejecutar() throws IOException {
		// cola que almacena los nodos visitados
		Queue<Integer> cola = new LinkedList<Integer>();
		// acolo el nodo inicial
		cola.offer(this.nodoInicial);
		
		int nodo, indice;
		
		// mientras la cola no está vacía
		while(!cola.isEmpty()) {
			// pispeo el primer nodo de la cola
			nodo = cola.peek();
			
			// genero todos los números de nodo
			for (int i = 0; i < this.cantNodos; i++) {
				// si el nodo generado no es el nodo actual y no fue terminado
				if (nodo != i && !this.nodoTerminado[i]) {
					// genero el índice para la matriz simétrica
					if (nodo < i) {
						indice = this.grafo.getGrafo().getIndice(nodo, i);
					} else {
						indice = this.grafo.getGrafo().getIndice(i, nodo);
					}
					
					// si existe una arista que una estos nodos
					if (this.grafo.getGrafo().hayArista(indice)) {
						// agrego la arista al árbol
						this.arbol.add(new Arista(nodo, i));
						// acolo el otro nodo de la arista para seguir recorriendo a partir de él
						cola.offer(i);
						// marco como terminado al nodo acolado
						this.nodoTerminado[i] = true;
					}
				}
			}
			
			// desacolo el primer nodo de la cola y lo marco como terminado
			nodo = cola.poll();
			this.nodoTerminado[nodo] = true;
		}
		
		// escribo la solucion en consola
		this.escribirSolucionEnConsola();

		// escribo la solucion completa en un archivo
		this.escribirSolucionEnArchivo("BFS" + "_" + this.cantNodos + "_"
				+ String.format("%.2f", this.getGrafo().getPtajeAdyacencia()) + ".out");
	}

	public void escribirSolucionEnConsola() {
		System.out.println("BFS: Búsqueda en Anchura:");
		System.out.println("Cantidad de Ramas del Árbol: " + this.arbol.size());
		System.out.println("Nodo Inicial del Recorrido (Raíz del Árbol): " + this.nodoInicial);
		System.out.println("Lista de Ramas:");
		for (Arista rama : arbol) {
			System.out.println(rama.getNodo1() + " " + rama.getNodo2());
		}
	}

	public void escribirSolucionEnArchivo(String path) throws IOException {
		FileWriter file = new FileWriter(path);
		BufferedWriter buffer = new BufferedWriter(file);

		buffer.write(String.valueOf(this.cantNodos));
		buffer.write(" ");
		buffer.write(String.valueOf(this.cantAristas));
		buffer.write(" ");
		buffer.write(String.valueOf(NumberFormat.getInstance().format(this.getGrafo().getPtajeAdyacencia())));
		buffer.write(" ");
		buffer.write(String.valueOf(this.getGrafo().getGradoMax()));
		buffer.write(" ");
		buffer.write(String.valueOf(this.getGrafo().getGradoMin()));
		buffer.newLine();

		buffer.write(String.valueOf(this.arbol.size()));
		buffer.write(" ");
		buffer.write(String.valueOf(this.nodoInicial));
		buffer.newLine();

		for (Arista rama : arbol) {
			buffer.write(String.valueOf(rama.getNodo1()));
			buffer.write(" ");
			buffer.write(String.valueOf(rama.getNodo2()));
			buffer.newLine();
		}

		buffer.close();
	}
}
