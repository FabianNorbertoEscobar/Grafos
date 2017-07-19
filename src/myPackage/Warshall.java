package myPackage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;

public class Warshall {

	private static final int INFINITO = -1;
	private GrafoDP grafo;
	private int cantNodos;
	
	private boolean[][] caminos;

	public GrafoDP getGrafo() {
		return grafo;
	}

	public int getCantNodos() {
		return cantNodos;
	}

	public boolean[][] getCaminos() {
		return caminos;
	}

	public Warshall(GrafoDP grafo) {
		this.grafo = grafo;
		this.cantNodos = grafo.getCantNodos();
		this.caminos = new boolean[this.cantNodos][this.cantNodos];	
	}
	
	public void ejecutar() throws IOException {
		// armo la matriz booleana para evaluar si hay caminos
		for (int i = 0; i < this.cantNodos; i++) {
			for (int j = 0; j < this.cantNodos; j++) {
				if (this.grafo.getGrafo()[i][j] != INFINITO && this.grafo.getGrafo()[i][j] != 0) {
					this.caminos[i][j] = true;
				} else {
					this.caminos[i][j] = false;
				}
			}
		}
		
		boolean anterior, ik, kj, actual;
		
		// para cada iteración k del algoritmo
		for (int k = 0; k < this.cantNodos; k++) {
			// para cada fila de la matriz
			for (int i = 0; i < this.cantNodos; i++) {
				// para cada columna de la matriz
				for (int j = 0; j < this.cantNodos; j++) {
					
					// si el pivot ij no está en la diagonal principal ni en la fila k o columna k
					if (i != j && k != i && k != j) {
						// existe camino en la iteración anterior
						anterior = this.caminos[i][j];
						
						// existe camino usando como intermediario al nodo k
						ik = this.caminos[i][k];
						kj = this.caminos[k][j];
						actual = (ik && kj);
								
						// existe algún camino
						this.caminos[i][j] = (anterior || actual);
					}
				}
			}
		}
		
		// muestro solución en consola
		this.escribirSolucionEnConsola();
				
		// escribo la solución completa en un archivo
		this.escribirSolucionEnArchivo("WARSHALL" + "_" + this.cantNodos + "_"
						+ String.format("%.2f", this.getGrafo().getPtajeAdyacencia()) + ".out");
			
	}

	private void escribirSolucionEnConsola() {
		System.out.println("WARSHALL: Caminos entre todos los nodos ");
		for (int i = 0; i < this.cantNodos; i++) {
			for (int j = 0; j < this.cantNodos; j++) {
				System.out.println("Nodo Inicial: " + i + " Nodo Final: " + j + " Existe camino: " + this.caminos[i][j]);
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
		
		for (int i = 0; i < this.cantNodos; i++) {
			for (int j = 0; j < this.cantNodos; j++) {
				buffer.write(String.valueOf(i));
				buffer.write(" ");
				buffer.write(String.valueOf(j));
				buffer.write(" ");
				buffer.write(String.valueOf(this.caminos[i][j]));
				buffer.newLine();
			}
		}	
		
		buffer.close();
	}
}
