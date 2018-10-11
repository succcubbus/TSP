package de.randomerror.fh.tsp;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    private static Random r = new Random(1337);
    private static Graph graph = new Graph();
    private static Trainer trainer = new Trainer(graph);
    private static Visualizer visualizer = new Visualizer(graph, trainer, new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() == 'r') {
                SwingUtilities.invokeLater(Main::init);
            } else if(e.getKeyChar() == 't') {
                trainer.train();
                visualizer.repaint();
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


        trainer.initPopulation();
        visualizer.repaint();
    }

    public static void main(String[] args) {
        init();
        visualizer.setSize(1600, 900);
        visualizer.setVisible(true);
    }
}
