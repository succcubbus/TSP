package de.randomerror.fh.tsp;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Graph {

    private List<Node> nodes;
    private Path displayPath = new Path();

    public Path getDisplayPath() {
        return displayPath;
    }

    public void setDisplayPath(Path displayPath) {
        this.displayPath = displayPath;
    }

    public double distanceBetween(Node n1, Node n2) {
        int dx = n1.getX() - n2.getX();
        int dy = n1.getY() - n2.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    public double getPathLength(Path path) {
        return sliding(path.solution, 2)
                .mapToDouble(nodes1 -> distanceBetween(nodes1.get(0), nodes1.get(1)))
                .sum() + distanceBetween(path.solution.getLast(), path.solution.getFirst());
    }

    public static <T> Stream<List<T>> sliding(List<T> list, int size) {
        if (size > list.size())
            return Stream.empty();
        return IntStream.range(0, list.size() - size + 1)
                .mapToObj(start -> list.subList(start, start + size));
    }

    public String getPathString(Path path) {
        String solutionString = path.solution.stream()
                .map(node -> String.format("%d", nodes.indexOf(node)))
                .collect(Collectors.joining(", "));
        return "[" + solutionString + "] " + path.getLength();
    }
}
