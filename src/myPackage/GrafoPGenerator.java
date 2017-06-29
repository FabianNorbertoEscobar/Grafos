package myPackage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GrafoPGenerator {

	public static void aleatorioConProbabilidad(int cantNodos, double probabilidad, int pesoLimite) throws IOException {
		ArrayList<AristaPonderada> array = new ArrayList<AristaPonderada>();
		Random rand = new Random();
		int cantMaximaAristas = ((cantNodos * (cantNodos - 1)) / 2);
		int cantAristas = 0;
		for (int i = 0; i < cantNodos - 1; i++) {
			for (int j = i + 1; j < cantNodos; j++) {
				if (rand.nextFloat() < probabilidad) {
					if (i < j)
						array.add(new AristaPonderada(i, j, (int) (rand.nextFloat() * pesoLimite + 1)));
					else
						array.add(new AristaPonderada(j, i, (int) (rand.nextFloat() * pesoLimite + 1)));
					cantAristas++;
				}
			}
		}

		Arista grados = calcularGrado(array, cantNodos);
		double porcentajeAdyacencia = (double) cantAristas / cantMaximaAristas;
		String path = "ALEATORIO_PROB_" + cantNodos + "_" + probabilidad + "_PONDERADO.txt";

		escribirGrafoEnArchivo(path, array, cantNodos, cantAristas, porcentajeAdyacencia, grados.getNodo1(),
				grados.getNodo2());
	}

	public static void aleatorioConPorcentajeAdyacencia(int cantNodos, double porcentaje, int pesoLimite)
			throws IOException {
		ArrayList<AristaPonderada> array = new ArrayList<AristaPonderada>();
		ArrayList<RandomParDeNodos> random = new ArrayList<RandomParDeNodos>();
		Random rand = new Random();
		int cantMaximaAristas = ((cantNodos * (cantNodos - 1)) / 2);
		int cantAristas = 0;
		for (int i = 0; i < cantNodos - 1; i++) {
			for (int j = i + 1; j < cantNodos; j++) {
				if (i < j)
					random.add(new RandomParDeNodos(new AristaPonderada(i, j, (int) (rand.nextFloat() * pesoLimite + 1)), rand.nextDouble()));
				else
					random.add(new RandomParDeNodos(new AristaPonderada(j, i, (int) (rand.nextFloat() * pesoLimite + 1)), rand.nextDouble()));

			}
		}

		Collections.sort(random);

		for (int i = 0; i < cantMaximaAristas * porcentaje; i++) {
			array.add(random.get(i).getNodos());
			cantAristas++;
		}

		Arista grados = calcularGrado(array, cantNodos);
		String path = "ALEATORIO_PTAJE_" + cantNodos + "_" + String.format("%.2f", porcentaje) + "_PONDERADO.txt";

		escribirGrafoEnArchivo(path, array, cantNodos, cantAristas, porcentaje, grados.getNodo1(), grados.getNodo2());
	}

	public static void regularConGrado(int cantNodos, int grado, int pesoLimite) throws IOException {
		ArrayList<AristaPonderada> array = new ArrayList<AristaPonderada>();
		Random rand = new Random();
		int cantAristas = 0;
		int gradoActual = 2;
		int cantMaximaAristas = (cantNodos * (cantNodos - 1)) / 2;
		int salto = 0;
		int j = 0;

		int nodo1;
		int nodo2;

		if (grado % 2 != 0 && cantNodos % 2 != 0) {
			System.out.println("No se puede generar un grafo regular de grado impar con N impar");
			return;
		}
		if (grado >= cantNodos) {
			System.out.println("El grado no puede ser igual, o mayor a la cantidad de nodos");
			return;
		}

		if (grado % 2 != 0) { // si el grado es impar, genero la cruz
			for (int i = 0; i < cantNodos / 2; i++) {
				nodo1 = i;
				nodo2 = i + cantNodos / 2;
				if (nodo1 < nodo2)
					array.add(new AristaPonderada(nodo1, nodo2, (int) (rand.nextFloat() * pesoLimite + 1)));
				else
					array.add(new AristaPonderada(nodo2, nodo1, (int) (rand.nextFloat() * pesoLimite + 1)));
				cantAristas++;
			}
		}

		if (grado > 1) {
			while (gradoActual <= grado) {
				salto = gradoActual / 2;
				j = 0;
				for (int i = 0; i < cantNodos; i++) {
					if (i + salto < cantNodos) {
						nodo1 = i;
						nodo2 = i + salto;
						if (nodo1 < nodo2)
							array.add(new AristaPonderada(nodo1, nodo2, (int) (rand.nextFloat() * pesoLimite + 1)));
						else
							array.add(new AristaPonderada(nodo2, nodo1, (int) (rand.nextFloat() * pesoLimite + 1)));
					} else {
						if (i < j)
							array.add(new AristaPonderada(i, j, (int) (rand.nextFloat() * pesoLimite + 1)));
						else
							array.add(new AristaPonderada(j, i, (int) (rand.nextFloat() * pesoLimite + 1)));
						j++;
					}
					cantAristas++;
				}
				gradoActual += 2;
			}
		}

		Arista grados = calcularGrado(array, cantNodos);
		double porcentajeAdyacencia = (double) cantAristas / cantMaximaAristas;
		String path = "REGULAR_" + cantNodos + "_" + String.format("%.2f", porcentajeAdyacencia) + "_PONDERADO.txt";

		escribirGrafoEnArchivo(path, array, cantNodos, cantAristas, porcentajeAdyacencia, grados.getNodo1(),
				grados.getNodo2());
	}

	public static void regularConPorcentajeAdyacencia(int cantNodos, double porcentaje, int pesoLimite)
			throws IOException {
		int cantMaximaAristas = (cantNodos * (cantNodos - 1)) / 2;
		int grado = (int) Math.ceil(((porcentaje * cantMaximaAristas) / (cantNodos / 2)));
		double minimo = (double) (cantNodos / 2) / cantMaximaAristas;

		if (porcentaje < minimo) {
			System.out.println(
					"El porcentaje de adyacencia ingresado es demasiado bajo para generar un grafo regular (el minimo es: "
							+ String.format("%1.3f", minimo) + ")");
			System.exit(1);
		}
		if (porcentaje > 1) {
			System.out.println("El porcentaje de adyacencia ingresado es superior al 100%");
			System.exit(1);
		}

		regularConGrado(cantNodos, grado, pesoLimite);
	}

	public static void nPartito(int cantNodos, int cantConjuntos, int pesoLimite) throws IOException {
		ArrayList<AristaPonderada> array = new ArrayList<AristaPonderada>();
		ArrayList<Integer> conjuntos = new ArrayList<Integer>();
		Random rand = new Random();
		int random;
		int cantMaximaAristas = (cantNodos * (cantNodos - 1)) / 2;
		int cantAristas = 0;

		if (cantConjuntos > cantNodos) {
			System.out.println("La cantidad de conjuntos no puede ser mayor a la cantidad de nodos");
			System.exit(1);
		}

		for (int i = 0; i < cantNodos; i++) {
			random = rand.nextInt(cantConjuntos);
			conjuntos.add(random);
			System.out.println(random);
		}

		for (int i = 0; i < cantNodos - 1; i++) {
			for (int j = i + 1; j < cantNodos; j++) {
				if (conjuntos.get(i) != conjuntos.get(j)) { // Si el nodo1 y el
															// nodo2 están en
															// conjuntos
															// diferentes
					if (i < j)
						array.add(new AristaPonderada(i, j, (int) (rand.nextFloat() * pesoLimite + 1)));
					else
						array.add(new AristaPonderada(j, i, (int) (rand.nextFloat() * pesoLimite + 1)));
					cantAristas++;
				}
			}
		}

		Arista grados = calcularGrado(array, cantNodos);
		double porcentajeAdyacencia = (double) cantAristas / cantMaximaAristas;
		String path = cantConjuntos + "_PARTITO" + "_PONDERADO.txt";

		escribirGrafoEnArchivo(path, array, cantNodos, cantAristas, porcentajeAdyacencia, grados.getNodo1(),
				grados.getNodo2());
	}

	private static Arista calcularGrado(ArrayList<AristaPonderada> array, int cantNodos) {
		int[] grados = new int[cantNodos];
		int gradoMaximo = 0;
		int gradoMinimo = Integer.MAX_VALUE;
		int max = 0;
		int min = 0;

		for (int i = 0; i < cantNodos; i++)
			grados[i] = 0;

		for (int i = 0; i < array.size(); i++) {
			grados[array.get(i).getNodo1()] += 1;
			grados[array.get(i).getNodo2()] += 1;
		}

		for (int i = 0; i < cantNodos; i++) {
			max = grados[i];
			min = grados[i];
			if (max > gradoMaximo)
				gradoMaximo = max;
			if (min < gradoMinimo && min > 0)
				gradoMinimo = min;
		}

		return new Arista(gradoMaximo, gradoMinimo);
	}

	private static void escribirGrafoEnArchivo(String path, ArrayList<AristaPonderada> array, int cantNodos, int cantAristas,
			double porcentajeAdyacencia, int gradoMaximo, int gradoMinimo) throws IOException {
		FileWriter file = new FileWriter(path);
		BufferedWriter buffer = new BufferedWriter(file);
		System.out.println();
		buffer.write(String.valueOf(cantNodos));
		buffer.write(" ");
		buffer.write(String.valueOf(cantAristas));
		buffer.write(" ");
		buffer.write(String.format("%1.2f", porcentajeAdyacencia));

		buffer.write(" ");
		buffer.write(String.valueOf(gradoMaximo));
		buffer.write(" ");
		buffer.write(String.valueOf(gradoMinimo));
		buffer.newLine();

		for (int i = 0; i < array.size(); i++) {
			buffer.write(String.valueOf(array.get(i).getNodo1()));
			buffer.write(" ");
			buffer.write(String.valueOf(array.get(i).getNodo2()));
			buffer.write(" ");
			buffer.write(String.valueOf(array.get(i).getPeso()));
			buffer.newLine();
		}

		buffer.close();

		if (gradoMinimo == 0) {
			System.out.println("El grafo generado tiene 0 como grado mínimo, genere un nuevo grafo...");
			System.exit(0);
		}

	}

	private static class RandomParDeNodos implements Comparable<RandomParDeNodos> {
		private AristaPonderada arista;
		private double probabilidad;

		public RandomParDeNodos(AristaPonderada arista, double probabilidad) {
			this.arista = arista;
			this.probabilidad = probabilidad;
		}

		public AristaPonderada getNodos() {
			return this.arista;
		}

		@SuppressWarnings("unused")
		public double getProbabilidad() {
			return this.probabilidad;
		}

		@SuppressWarnings("unused")
		public void mostrar() {
			System.out.println(this.arista.getNodo1() + " " + this.arista.getNodo2() + " " + this.arista.getPeso() + " " + this.probabilidad);
		}

		@Override
		public int compareTo(RandomParDeNodos o) {
			if (this.probabilidad > o.probabilidad)
				return -1;
			if (this.probabilidad < o.probabilidad)
				return 1;
			return 0;
		}
	}

}
