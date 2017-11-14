package myPackage;

import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		/*
		GrafoNPGenerator.aleatorioConProbabilidad(50, 0.5);
		GrafoNPGenerator.aleatorioConPorcentajeAdyacencia(60, 0.5);
		GrafoNPGenerator.regularConGrado(30, 5);
		GrafoNPGenerator.regularConPorcentajeAdyacencia(40, 0.5);
		GrafoNPGenerator.nPartito(60, 35);
		
		GrafoPGenerator.aleatorioConProbabilidad(50, 0.5, 60);
		GrafoPGenerator.aleatorioConPorcentajeAdyacencia(60, 0.5, 50);
		GrafoPGenerator.regularConGrado(30, 5, 70);
		GrafoPGenerator.regularConPorcentajeAdyacencia(40, 0.5, 30);
		GrafoPGenerator.nPartito(60, 35, 40);
		*/
		
		/*
		GrafoNDNP grafo = new GrafoNDNP("grafo.in");
		grafo.coloreoSecuencial(10);
		grafo.coloreoWelshPowell(10);
		grafo.coloreoMatula(10);
		*/
		
		/*
		GrafoNDP grafo = new GrafoNDP("grafo.in");
		grafo.coloreoSecuencial(10);
		grafo.coloreoWelshPowell(10);
		grafo.coloreoMatula(10);
		*/
		
		// ProbadorColoreoGrafoNP probador = new ProbadorColoreoGrafoNP("grafo.in", "coloreado.out");
		
		// ProbadorColoreoGrafoP probador = new ProbadorColoreoGrafoP("grafo.in", "coloreado.out");
		
		/*
		if (probador.probar())
			System.out.println("BIEN COLOREADO");
		else
			System.out.println("MAL COLOREADO");
		*/
		
		/*
		GrafoNDP grafo = new GrafoNDP("grafo.in");
		Dijkstra dijkstra = new Dijkstra(grafo, 0);
		dijkstra.ejecutar();
		*/
		
		/*
		GrafoNDP grafo = new GrafoNDP("grafo.in");
		Kruskal kruskal = new Kruskal(grafo);
		kruskal.ejecutar();
		*/
		
		/*
		GrafoNDP grafo = new GrafoNDP("grafo.in");
		Prim prim = new Prim(grafo);
		prim.ejecutar();
		*/
		
		/*
		GrafoDP digrafo = new GrafoDP("grafo.in");
		digrafo.coloreoSecuencial(10);
		digrafo.coloreoWelshPowell(10);
		digrafo.coloreoMatula(10);
		*/
		
		/*
		GrafoDP digrafo = new GrafoDP("grafo.in");
		Floyd floyd = new Floyd(digrafo);
		floyd.ejecutar();
		*/
		
		/*
		GrafoDP digrafo = new GrafoDP("grafo.in");
		Warshall warshall = new Warshall(digrafo);
		warshall.ejecutar();
		*/
		
		/*
		GrafoNDNP grafo = new GrafoNDNP("grafo.in");
		DFS dfs = new DFS(grafo, 4);
		dfs.ejecutar();
		*/
		
		/*
		GrafoNDNP grafo = new GrafoNDNP("grafo.in");
		BFS bfs = new BFS(grafo, 4);
		bfs.ejecutar();
		*/
	}
}
