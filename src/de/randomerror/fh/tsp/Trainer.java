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
        return IntStream.range(0, REPRODUCTION)
                .map(i -> r.nextInt(population.size()))
                .mapToObj(parentIndex -> population.get(parentIndex))
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
            LinkedList<Node> path = new LinkedList<>();
            List<Node> nodeList = graph.getNodes();
            Collections.shuffle(nodeList, r);
            Node start = nodeList.get(0);
            path.add(start);

            for (Node _node : nodeList) {
                nodeList.stream()
                        .filter(o -> !path.contains(o))
                        .filter(node -> node != path.getLast())
                        .limit(1)
                        .forEach(path::add);
            }

            Path p = new Path();
            p.solution = path;
            population.add(p);
        }
        System.out.println("init: ");
        population.forEach(path -> {
            System.out.println(path.solution.size() + " " +  path.getLength());
        });
        System.out.println("init: ");
    }

    public int getGeneration() {
        return generation;
    }
}
