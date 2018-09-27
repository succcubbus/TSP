package de.randomerror.fh.tsp;

import javafx.util.Pair;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    private static Random r = new Random(1337);
    private static Graph graph = new Graph();
    private static Visualizer visualizer = new Visualizer(graph, new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() == 'r') {
                SwingUtilities.invokeLater(Main::init);
            }
        }

        @Override
        public void keyPressed(KeyEvent e) {

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    });

    private static void init() {
        graph.setNodes(IntStream.range(0, 5+(r.nextInt(25)))
                               .boxed()
                               .map(l -> new Node(r.nextInt(1400) + 100, r.nextInt(700) + 100))
                               .collect(Collectors.toList()));

        graph.setPath(findPath());
        visualizer.repaint();
    }

    public static void main(String[] args) {
        init();
        visualizer.setSize(1600, 900);
        visualizer.setVisible(true);
    }

    private static List<Node> findPath() {
        LinkedList<Node> path = new LinkedList<>();

        Node start = graph.getNodes().get(0);
        path.add(start);

        for (Node _node : graph.getNodes()) {
            graph.getNodes().stream()
                    .filter(o -> !path.contains(o))
                    .filter(node -> node != path.getLast())
                    .map(node -> new Pair<>(node, graph.distanceBetween(node, path.getLast())))
                    .min(Comparator.comparingDouble(Pair::getValue))
                    .map(Pair::getKey)
                    .ifPresent(path::add);
        }

        path.add(start);

        return path;
    }
}
