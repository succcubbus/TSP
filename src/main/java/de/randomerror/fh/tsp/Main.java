package de.randomerror.fh.tsp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    private static Timer autoEvolutionTimer;
    private static Random r = new Random();
    private static Graph graph = new Graph();
    private static Trainer trainer = new Trainer(graph);
    private static final int GRAPH_SIZE =100;
    private static final  int windowSizeX=1400;
    private static final  int windowSizeY=700;
    private static Visualizer visualizer = new Visualizer(graph, trainer, new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() == 'r') {
                synchronized (Main.class) {
                    SwingUtilities.invokeLater(Main::init);
                    trainer.setGeneration(0);
                }
            } else if (e.getKeyChar() == 't') {
                trainer.train();
                visualizer.repaint();
            } else if (e.getKeyChar()=='z'){
                graph.moveNodes(windowSizeX,windowSizeY);
                visualizer.repaint();
            } else if(e.getKeyChar() == 'a') {
                new Thread(() -> {
                    while(true) {
                        if (Math.random() < 0.01) {
                            visualizer.repaint();
                            try {
                                Thread.sleep(5);
                            } catch (Throwable t) {
                            }
                        } else {
                            trainer.train();
                        }
                    }
                }).start();
                // if(autoEvolutionTimer.isRunning()) {
                //     autoEvolutionTimer.stop();
                // } else {
                //     autoEvolutionTimer.start();
                // }
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
        graph.setNodes(IntStream.range(0, 5 + (r.nextInt(GRAPH_SIZE)))
                               .parallel()
                               .boxed()
                               .map(l -> new Node(r.nextInt(windowSizeX) + 100, r.nextInt(windowSizeY) + 100))
                               .collect(Collectors.toList()));
        trainer.initPopulation();
        visualizer.repaint();
        ActionListener timerTask = e -> {
        };

        autoEvolutionTimer = new Timer(1, timerTask);
        // autoEvolutionTimer.setRepeats(true);
    }

    public static void main(String[] args) {
        init();
        visualizer.setSize(1600, 900);
        visualizer.setVisible(true);
    }

}
