package de.randomerror.fh.tsp;

import java.util.LinkedList;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Path {
    public LinkedList<Node> solution;

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    private double length;

    public void swap(int i1, int i2) {
        Node tmp = solution.get(i1);
        solution.set(i1, solution.get(i2));
        solution.set(i2, tmp);
    }

    public Path dulicate() {
        Path result = new Path();
        if(this.solution != null)
            result.solution = new LinkedList<>(this.solution);
        return result;
    }

    public String toString() {
        String solutionString = IntStream.range(0, solution.size())
                .mapToObj(index -> String.format("%d%s", index, solution.get(index)))
                .collect(Collectors.joining(", "));
        return "[" + solutionString + "] " + length;
    }
}
