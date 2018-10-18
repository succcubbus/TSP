package de.randomerror.fh.tsp;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Trainer {
    private static Random r = new Random(1337);
    private Graph graph;
    private List<Path> population;
    private final int PSIZE = 100;
    private final int MUTATION = 1;
    private final int REPRODUCTION = 200;

    private int generation = 0;

    public Trainer(Graph graph) {
        this.graph = graph;
    }

    /**
     * @param path
     * @return anti-fitness
     */
    public Path fitness(Path path) {
        path.setLength(graph.getPathLength(path));
        return path;
    }

    public Path mutate(Path path) {
        Path child = path.dulicate();
        for (int i = 0; i < MUTATION; i++) {
            int randomIndex1, randomIndex2;
            randomIndex1 = r.nextInt(path.solution.size());
            randomIndex2 = r.nextInt(path.solution.size());
            child.swap(randomIndex1, randomIndex2);
        }
        return child;
    }

    public List<Path> recombine() {
        List<Path> selectedParents = IntStream.range(0, REPRODUCTION)
                .mapToObj(i -> r.nextInt(population.size()))
                .map(parentIndex -> population.get(parentIndex))
                .collect(Collectors.toList());

        return Graph.sliding(selectedParents, 2)
                .map(parents -> {
                    int crossover = r.nextInt(population.size());
                    List<Node> firstNodes = parents.get(0)
                            .solution.stream()
                            .limit(crossover)
                    .collect(Collectors.toList());

                    parents.get(1).solution.stream()
                            .skip(crossover)
                            .forEach(node -> {
                                while(firstNodes.contains(node)) {
                                    int nodeIndex = parents.get(0).solution.indexOf(node);
                                    node = parents.get(1).solution.get(nodeIndex);
                                    System.out.println(node);
                                }
                                firstNodes.add(node);
                            });

                    Path child = new Path();

                    child.solution = new LinkedList<>(firstNodes);

                    System.out.println("child: " + graph.getPathString(child));

                    return child;
                })
                .map(this::mutate)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public void train() {
        List<Path> children = recombine();
        population = Stream.concat(population.stream(), children.stream())
                .map(this::fitness)
                .sorted(Comparator.comparing(path -> path.getLength()))
                .limit(PSIZE)
                .collect(Collectors.toList());

        population.forEach(path -> {
            System.out.println(path.solution.size() + " " +  path.getLength());
        });
        graph.setDisplayPath(population.get(0));
        generation++;
    }

    public void initPopulation() {
        population = new LinkedList<>();
        for (int i = 0; i < PSIZE; i++) {
            LinkedList<Node> nodeList = new LinkedList<>(graph.getNodes());
            Collections.shuffle(nodeList, r);
            Path p = new Path();
            p.solution = nodeList;
            population.add(p);
        }
    }

    public int getGeneration() {
        return generation;
    }

    public void setGeneration(int generation) {
        this.generation = generation;
    }
}
