package app;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class TestSwingPerformance {

    private static class SwingSpeedTestGUI extends JPanel {
        private TimerCallBack actionTimer = null;
        private Timer timer = null;

        // for debugging paint speed
        private static final int DURATION_BUFFER_LEN = 1500; // 30 sec
        private int paintDurationBufferIdx;
        private int[] paintDurationBuffer;

        // constructor
        SwingSpeedTestGUI(int timerTick_ms) {
            actionTimer = new TimerCallBack(this);
            timer = new Timer(timerTick_ms, actionTimer);

            paintDurationBuffer = new int[DURATION_BUFFER_LEN];
            paintDurationBufferIdx = 0;
        }

        void start() {
            timer.start();
        } // start paint timer

        public void rePaint() {
            repaint();
        } // repaint

        // paint method
        public void paintComponent(Graphics g) {
            long startTime = System.nanoTime();

            super.paintComponent(g);

            int cornerX = 10;
            int cornerY = 10;
            int x = cornerX;
            int y = cornerY;

            // draw bigger squares
            for (int i = 0; i < 40; i++, x += 60) {
                Color cSaved = g.getColor();
                g.setColor(Color.RED);
                g.drawRect(x, y, 34, 34);
                g.setColor(cSaved);

                if (x > 750) {
                    x = cornerX - 60;
                    y += 60;
                }
            }

            // draw smaller, filled squares
            x = cornerX + 800;
            y = cornerY;

            for (int i = 0; i < 20; i++, x -= 25, y += 25) {
                Color cSaved = g.getColor();
                g.setColor(Color.RED);
                g.drawRect(x, y, 7, 7);
                g.fillRect(x, y, 7, 7);
                g.setColor(cSaved);
            }

            // for debugging paint speed
            long endTime = System.nanoTime();
            if (paintDurationBufferIdx < DURATION_BUFFER_LEN) {
                paintDurationBuffer[paintDurationBufferIdx++] = (int) (endTime - startTime) / 1000;
                System.out.println("((int) (endTime - startTime) / 1000) = " + ((int) (endTime - startTime) / 1000));
            } else {
                long avg = 0;
                for (int ii = 0; ii < DURATION_BUFFER_LEN; ii++)
                    avg += paintDurationBuffer[ii];
                avg /= DURATION_BUFFER_LEN;
                System.out.println("Paint avg. duration: " + avg + " us");
                paintDurationBufferIdx = 0;
            }
        }

        private static class TimerCallBack implements ActionListener {
            private SwingSpeedTestGUI gui = null;

            // constructor
            TimerCallBack(SwingSpeedTestGUI gui) {
                this.gui = gui;
            }

            // timer callback method
            public void actionPerformed(ActionEvent evt) {
                gui.rePaint();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("TestSwingPerformance ...");
        JPanel container = new JPanel();
        container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
        SwingSpeedTestGUI gui = new SwingSpeedTestGUI(20);
        container.add(gui);

        JFrame window = new JFrame("SwingSpeedTestGUI");
        window.setContentPane(container);
        window.setSize(900, 700);
        window.setLocation(0, 0);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);

        gui.start();
    }
}