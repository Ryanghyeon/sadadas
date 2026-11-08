package bluehour;

public class App {
    public static void main(String[] args) {
        // 샘플 월드 로드
        World world = WorldLoader.loadSample();

        // 초기 활성 히어로를 0번으로 설정(필요 시 게임 내에서 1/2/3으로 바꿀 수 있음)
        // World의 구조상 heroes 리스트가 있다면 getActiveHero를 반환하도록 구성하는 편이 좋습니다.

        // 간단한 예: 월드의 맵을 기반으로 RayCaster를 생성하고 Engine 연결
        // 아래는 예시용으로, 실제 GameWindow/Panel 과 연결하는 부분은 GamePanel 내에서 처리하는 방식도 가능합니다.
        RayCaster rc = new RayCaster(world.map, world.getActiveViewport(), 800, 600);
        Engine engine = new Engine(world, rc);

        // UI 창 띄우기
        GameWindow window = new GameWindow(engine);
        window.show();

        // 루프 시작
        engine.start();
    }
}
