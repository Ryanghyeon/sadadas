package bluehour;

import java.awt.image.BufferedImage;

public class Engine implements Runnable {
    private final World world;
    private final RayCaster renderer;
    private volatile boolean running = false;
    private Thread thread;

    public Engine(World world, RayCaster renderer) {
        this.world = world;
        this.renderer = renderer;
    }

    public World getWorld() {
        return world;
    }

    public RayCaster getRenderer() {
        return renderer;
    }

    public void start() {
        if (running) return;
        running = true;
        thread = new Thread(this, "Engine");
        thread.start();
    }

    public void stop() {
        running = false;
        if (thread != null) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    public void run() {
        long last = System.nanoTime();
        while (running) {
            long now = System.nanoTime();
            double dt = (now - last) / 1_000_000_000.0;
            last = now;

            world.update(dt);
            // 렌더링은 GamePanel이 renderFrame()으로 요청할 때 수행

            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public BufferedImage renderFrame() {
        return renderer.renderFrame();
    }
}
