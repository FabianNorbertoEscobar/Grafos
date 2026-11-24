# Prácticas de algoritmos sobre grafos

Repositorio académico con implementaciones en Java de algoritmos clásicos sobre grafos. Incluye recorridos, caminos mínimos, árboles abarcadores, coloreo, generación de instancias y archivos de ejemplo para experimentar y comparar resultados.

## Stack y herramientas

- **Java 8 o superior**; el proyecto de Eclipse está configurado para JavaSE-1.8.
- **JDK** para compilar y ejecutar (`javac` y `java`).
- **Eclipse** como entorno de desarrollo opcional.
- No se utiliza Maven, Gradle ni dependencias externas: las clases usan la biblioteca estándar de Java.

## Funcionalidades

### Algoritmos

| Tema | Implementaciones |
| --- | --- |
| Recorridos | Búsqueda en anchura (**BFS**) y búsqueda en profundidad (**DFS**) |
| Caminos mínimos | **Dijkstra** desde un nodo de origen y **Floyd** entre todos los pares |
| Alcanzabilidad | **Warshall**, para determinar si existe un camino entre pares de nodos |
| Árbol abarcador mínimo | **Prim** y **Kruskal** |
| Coloreo | Heurísticas secuencial aleatoria, **Welsh–Powell** y **Matula** |
| Validación de coloreo | Probadores para grafos no ponderados y ponderados |

Las heurísticas de coloreo buscan una asignación válida con pocos colores; no garantizan encontrar el mínimo. Dijkstra está pensado para pesos no negativos, como los de las instancias generadas. Prim y Kruskal se aplican a grafos no dirigidos ponderados y su uso presupone que el grafo esté conectado.

### Generación de grafos

`GrafoNPGenerator` y `GrafoPGenerator` crean grafos no dirigidos no ponderados y ponderados, respectivamente. Incluyen variantes aleatorias por probabilidad o porcentaje de adyacencia, regulares y n-partitas. Las variantes ponderadas reciben también un límite para los pesos.

### Representaciones

- `GrafoNDNP`: grafo **no dirigido y no ponderado**, almacenado con una matriz simétrica.
- `GrafoNDP`: grafo **no dirigido y ponderado**, con matriz simétrica y pesos por arista.
- `GrafoDP`: grafo **dirigido y ponderado**, representado mediante una matriz de costos.

## Estructura del repositorio

```text
src/myPackage/                 Implementaciones Java
ejemplos algoritmos/           Entradas, salidas e imágenes por algoritmo
ejemplos grafos generados/     Instancias de grafos generadas
prueba coloreo grafo DP/       Pruebas de coloreo para grafos dirigidos ponderados
prueba coloreo grafo NDNP/     Pruebas de coloreo para grafos no dirigidos no ponderados
prueba coloreo grafo NDP/      Pruebas de coloreo para grafos no dirigidos ponderados
documentación algoritmos/      Documentos y notas de complejidad
bin/                           Clases compiladas incluidas en el repositorio
```

Los documentos de cada algoritmo y las notas de complejidad están en `documentación algoritmos/`. Los ejemplos de entrada y sus resultados están organizados en `ejemplos algoritmos/`.

## Compilar y ejecutar

Desde la raíz del repositorio, con un JDK instalado:

```bash
mkdir -p bin
javac -encoding UTF-8 -d bin src/myPackage/*.java
java -cp bin myPackage.Main
```

`Main.java` sirve como punto de entrada de prácticas, pero actualmente sus ejemplos están comentados. Por eso, ejecutar `Main` tal cual no lanza un algoritmo. Para probar uno, descomenta o agrega la práctica deseada en `src/myPackage/Main.java`, recompila y ejecuta desde la raíz del repositorio. Las rutas de entrada y los archivos de salida se resuelven respecto del directorio de trabajo.

Por ejemplo, para ejecutar BFS sobre el grafo de muestra:

```java
GrafoNDNP grafo = new GrafoNDNP(
    "ejemplos algoritmos/búsqueda en anchura (BFS)/grafo.in"
);
new BFS(grafo, 0).ejecutar();
```

Para un algoritmo que utiliza pesos, como Dijkstra:

```java
GrafoNDP grafo = new GrafoNDP("ejemplos algoritmos/dijkstra/grafo.in");
new Dijkstra(grafo, 0).ejecutar();
```

Los algoritmos imprimen resultados en consola y escriben archivos `.out` en el directorio desde el que se ejecuta Java. Los nombres de salida incluyen el algoritmo y datos de la instancia; por ejemplo, `BFS_9_0,27.out` o `DIJKSTRA_6_0,33.out`.

También se puede importar el proyecto en Eclipse como proyecto existente: el repositorio incluye `.project`, `.classpath` y la configuración de Java 8.

## Formato de las instancias

Los archivos de entrada son texto plano. La primera línea contiene los metadatos del grafo:

```text
cantidad_de_nodos cantidad_de_aristas porcentaje_adyacencia grado_maximo grado_minimo
```

Después se escribe una arista por línea. En grafos no ponderados se indican dos extremos; en grafos ponderados, dos extremos y el peso:

```text
# No ponderado
origen destino

# Ponderado
origen destino peso
```

Los nodos se numeran desde `0`. En grafos dirigidos, los extremos indican el sentido del arco. El porcentaje de adyacencia aparece con coma decimal en varios ejemplos existentes; para crear nuevas entradas, utiliza el formato numérico esperado por la configuración regional de Java en la que se ejecuta el programa.

## Prácticas sugeridas

1. Ejecutar BFS y DFS con distintos nodos iniciales y comparar los árboles de recorrido.
2. Comparar Dijkstra y Floyd en una instancia ponderada; estudiar las diferencias entre un origen y todos los pares.
3. Aplicar Prim y Kruskal a la misma instancia conectada y comparar el costo del árbol resultante.
4. Ejecutar las tres heurísticas de coloreo en una misma instancia y verificar sus archivos de salida con los probadores.
5. Generar instancias de diferentes densidades y tamaños y analizar el efecto sobre los resultados.

Las clases de coloreo reciben como parámetro la cantidad de corridas, por ejemplo `grafo.coloreoWelshPowell(10)`. En cada corrida se explora un orden de nodos distinto y se conserva la mejor solución encontrada.
