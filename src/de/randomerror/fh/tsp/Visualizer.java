package de.randomerror.fh.tsp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.util.List;

public class Visualizer extends JFrame {
    private Graph graph;
    private Trainer trainer;

    public Visualizer(Graph graph, Trainer trainer, KeyListener l) {
        super("TSP");
        this.graph = graph;
        this.trainer = trainer;
        VCan contentPane = new VCan();
        addKeyListener(l);
        this.setContentPane(contentPane);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


    }

    class VCan extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g;
            List<Node> nodes = graph.getNodes();
            for (int i = 0; i < nodes.size(); i++) {
                Node node = nodes.get(i);
                g2d.setStroke(new BasicStroke(2f));
                g2d.drawArc(node.getX() - 3, node.getY() - 3, 6, 6, 0, 360);
             //   g2d.drawString("" + i, node.getX() - 15 + 10, node.getY() - 15 + 18);

            }

            List<Node> renderPath = graph.getDisplayPath().dulicate().solution;
            if(renderPath == null)
                return;
            renderPath.add(renderPath.get(0));
            if (renderPath.size() - 1 != graph.getNodes().size())
                return;

            for (int i = 1; i < renderPath.size(); i++) {
                Node n0 = renderPath.get(i - 1);
                Node n1 = renderPath.get(i);

                g2d.drawLine(n0.getX(), n0.getY(), n1.getX(), n1.getY());
            }

            g2d.drawString("Length: " + graph.getDisplayPath().getLength() + " Gen: " + trainer.getGeneration(), 5, 20);
        }
    }
}
