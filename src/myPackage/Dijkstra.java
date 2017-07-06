package myPackage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Dijkstra {

	private GrafoNDP grafo;
	private int nodoInicial;

	private int cantNodos;
	boolean nodoTerminado[];
	private ArrayList<CostoAlNodo> costos;

	private static final int INFINITO = -1;

	public int getNodoInicial() {
		return nodoInicial;
	}

	public GrafoNDP getGrafo() {
		return grafo;
	}

	public int getCantNodos() {
		return this.cantNodos;
	}

	public Dijkstra(GrafoNDP grafo, int nodoInicial) {
		this.grafo = grafo;
		this.nodoInicial = nodoInicial;
		this.cantNodos = grafo.getGrafo().getOrdenMatriz();
		this.nodoTerminado = new boolean[cantNodos];
		costos = new ArrayList<CostoAlNodo>();
	}

	@SuppressWarnings("unchecked")
	public void ejecutar() throws IOException {
		// cola de prioridad para almacenar los nodos adyacentes que se van visitando
		Queue<CostoAlNodo> cola = new PriorityQueue<CostoAlNodo>();
		CostoAlNodo nodoActual = new CostoAlNodo(this.nodoInicial, 0);
		// acolo el nodo inicial
		cola.add(nodoActual);

		int indice;
		int nodo = nodoActual.getNodo();
		CostoAlNodo actualizado;

		// inicializo la lista de costos
		for (int i = 0; i < this.cantNodos; i++) {
			if (i != nodo) {
				costos.add(new CostoAlNodo(i, INFINITO));
			} else {
				costos.add(new CostoAlNodo(i, 0));
				costos.get(i).agregarNodoAlCamino(nodo);;
			}
		}

		// mientras la cola no está vacía
		while (!cola.isEmpty()) {
			nodoActual = cola.poll(); // desacolo al primero
			nodo = nodoActual.getNodo();
			
			// genero todos los números de nodo
			for (int i = 0; i < this.cantNodos; i++) {
				// si el nodo generado es distinto al nodo actual y no está terminado
				if (nodo != i && !this.nodoTerminado[i]) {
					// genero el índice para la matriz simétrica
					if (nodo < i) {
						indice = this.grafo.getGrafo().getIndice(nodo, i);
					} else {
						indice = this.grafo.getGrafo().getIndice(i, nodo);
					}

					// si hay arista que una los nodos
					if (this.grafo.getGrafo().hayArista(indice)) {
						// si el nodo que estoy visitando no fue visitado o su costo mínimo es mayor que la distancia recorrida para visitarlo
						if (this.costos.get(i).getCostoMinimo() == INFINITO || this.costos.get(nodo).getCostoMinimo() + this.getGrafo().getPesoArista(indice) < this.costos.get(i).getCostoMinimo()) {
							// referencio al nodo
							actualizado = this.costos.get(i);
							// actualizo su costo al dado hasta el nodo actual más el peso de la arista entre ambos
							actualizado.setCostoMinimo(this.costos.get(nodo).getCostoMinimo() + this.getGrafo().getPesoArista(indice));
							// le cambio el camino más corto por el camino más corto hasta el nodo actual
							actualizado.setCaminoMasCorto((ArrayList<Integer>)this.costos.get(nodo).getCaminoMasCorto().clone());
							// agrego el nodo visitado
							actualizado.agregarNodoAlCamino(i);
							
							// acolo el nodo visitado para luego buscar caminos desde él
							if (!cola.contains(actualizado)) {
								cola.add(actualizado);
							}
						}
					}
				}
			}
			
			// marco el nodo como terminado ya que su costo mínimo y camino más corto fueron hallados
			this.nodoTerminado[nodo] = true;
		}
		
		// muestro solución en consola
		this.escribirSolucionEnConsola();
		
		// escribo la solución completa en un archivo
		this.escribirSolucionEnArchivo("DIJKSTRA" + "_" + this.cantNodos + "_"
				+ String.format("%.2f", this.getGrafo().getPtajeAdyacencia()) + ".out");
	}

	private void escribirSolucionEnConsola() {
		int costo;
		System.out.println("DIJKSTRA: Nodo Inicial: " + this.nodoInicial);
		for (int i = 0; i < this.costos.size(); i++) {
			costo = costos.get(i).getCostoMinimo();
			if (costo == INFINITO) {
				System.out.println("Nodo Final: " + i + " Costo del Camino Más Corto: INFINITO");
			} else {
				System.out.println("Nodo Final: " + i + " Costo del Camino Más Corto: " + costo);
			}
		}		
	}

	private void escribirSolucionEnArchivo(String path) throws IOException {
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
		
		for (CostoAlNodo nodoFinal : costos) {
			buffer.write(String.valueOf(this.getNodoInicial()));
			buffer.write(" ");
			buffer.write(String.valueOf(nodoFinal.getNodo()));
			buffer.write(" ");
			buffer.write(String.valueOf(nodoFinal.getCostoMinimo()));
			buffer.write(" ");
			buffer.write(String.valueOf(nodoFinal.getCaminoMasCorto().size()));
			buffer.write(" ");
			for (Integer nodoDelCamino : nodoFinal.getCaminoMasCorto()) {
				buffer.write(String.valueOf(nodoDelCamino));
				buffer.write(" ");
			}
			buffer.newLine();
		}
		
		buffer.close();
	}
	
}
