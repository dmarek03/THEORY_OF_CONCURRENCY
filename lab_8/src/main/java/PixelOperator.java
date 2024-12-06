import java.util.concurrent.Callable;

public class PixelOperator implements Callable<PixelResult>  {
    private final int x, y;
    private final double  ZOOM;
    private final int MAX_ITER;


    public PixelOperator(int x, int y , double ZOOM, int MAX_ITER) {
        this.x = x;
        this.y = y;
        this.ZOOM = ZOOM;
        this.MAX_ITER = MAX_ITER;
    }


    @Override
    public PixelResult call() {
        double zx, cX, cY, zy;
        zx = zy = 0;
        cX = (x - 400) / ZOOM;
        cY = (y - 300) / ZOOM;
        int iter = MAX_ITER;
        while (zx * zx + zy * zy < 4 && iter > 0) {
            double tmp = zx * zx - zy * zy + cX;
            zy = 2.0 * zx * zy + cY;
            zx = tmp;
            iter--;
        }
        int color = iter | (iter << 8);
        return new PixelResult(this.x, this.y, color);
    }
}
