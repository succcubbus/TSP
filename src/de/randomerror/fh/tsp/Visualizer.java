package de.randomerror.fh.tsp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.util.List;

public class Visualizer extends JFrame {
    private Graph graph;

    public Visualizer(Graph graph, KeyListener l) {
        super("TSP");
        this.graph = graph;
        VCan contentPane = new VCan();
        addKeyListener(l);
        this.setContentPane(contentPane);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


    }

    class VCan extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (graph.getPath().size() - 1 != graph.getNodes().size())
                return;

            Graphics2D g2d = (Graphics2D) g;
            List<Node> nodes = graph.getNodes();
            for (int i = 0; i < nodes.size(); i++) {
                Node node = nodes.get(i);
                g2d.setStroke(new BasicStroke(2f));
                g2d.drawArc(node.getX() - 15, node.getY() - 15, 30, 30, 0, 360);
                g2d.drawString("" + i, node.getX() - 15 + 10, node.getY() - 15 + 18);

            }

            for (int i = 1; i < graph.getPath().size(); i++) {
                Node n0 = graph.getPath().get(i - 1);
                Node n1 = graph.getPath().get(i);

                g2d.drawLine(n0.getX(), n0.getY(), n1.getX(), n1.getY());
            }
        }
    }
}
