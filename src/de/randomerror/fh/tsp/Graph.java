package de.randomerror.fh.tsp;

import java.util.List;

public class Graph {

    private List<Node> nodes;
    private List<Node> path;

    public List<Node> getPath() {
        return path;
    }

    public void setPath(List<Node> path) {
        this.path = path;
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
}
