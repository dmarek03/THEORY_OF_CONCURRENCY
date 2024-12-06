import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import javax.swing.JFrame;

public class Mandelbrot extends JFrame {

    private  static final int MAX_ITER = 3000;
    private static final double ZOOM = 150;
    private  BufferedImage I;


    public Mandelbrot() {
        System.out.println("newFixedThreadPool:\n");
        List<Integer> iterationsNum = List.of(100, 200, 500, 1000, 1500, 2000,3000);
        List<Double> executionTime = new ArrayList<>();

        for(int iterNum : iterationsNum) {
            ExecutorService executor = Executors.newFixedThreadPool(12);
            double startTime = System.nanoTime();
            runSimulation(executor, iterNum);
            double endTime = System.nanoTime();
            executionTime.add((endTime - startTime) / 1_000_000.0);
        }
        for(double execTime: executionTime) {
            System.out.println(execTime);
            //System.out.println("Number of threads: "+iterationsNum.get(executionTime.indexOf(execTime)) + " time: "+ execTime);
        }


    }


    public void runSimulation(ExecutorService executor, int iterationNum) {

        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

        List<Future<PixelResult>> futures = new ArrayList<>();

        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                Callable<PixelResult> task = new PixelOperator(x, y, ZOOM, iterationNum);
                futures.add(executor.submit(task));

            }
        }

        for (Future<PixelResult> future : futures) {
            try {
                PixelResult result = future.get();
                I.setRGB(result.x(), result.y(), result.color());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        executor.shutdown();
    }


    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }

    public static void main(String[] args) {
        new Mandelbrot().setVisible(true);
    }
}