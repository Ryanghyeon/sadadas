package bluehour;

public class App {
    public static void main(String[] args) {
        World world = WorldLoader.loadSample();
        bluehour.heroes.Hero active = (world.heroes != null && !world.heroes.isEmpty()) ? world.heroes.get(0) : null;
        int w = 800, h = 600;
        RayCaster rc = new RayCaster(world.map, active, w, h);
        Engine engine = new Engine(world, rc);
        GameWindow window = new GameWindow(engine);
        window.show();
        engine.start();
    }
}
