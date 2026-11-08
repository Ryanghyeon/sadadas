package bluehour;

import java.awt.image.BufferedImage;

public class RayCaster {
    private final Map2D map;
    private Viewport viewport;
    private final int width;
    private final int height;

    public RayCaster(Map2D map, Viewport viewport, int width, int height) {
        this.map = map;
        this.viewport = viewport;
        this.width = width;
        this.height = height;
    }

    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }

    public BufferedImage renderFrame() {
        if (viewport == null) return new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // 배경
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                img.setRGB(x, y, y < height / 2 ? 0x87CEEB : 0x404040);
            }
        }

        for (int x = 0; x < width; x++) {
            double cameraX = 2.0 * x / width - 1.0;
            double rayDirX = viewport.getDirX() + viewport.getPlaneX() * cameraX;
            double rayDirY = viewport.getDirY() + viewport.getPlaneY() * cameraX;

            int mapX = (int) viewport.getX();
            int mapY = (int) viewport.getY();

            double deltaDistX = Math.abs(1.0 / rayDirX);
            double deltaDistY = Math.abs(1.0 / rayDirY);

            double sideDistX, sideDistY;
            int stepX, stepY;

            if (rayDirX < 0) {
                stepX = -1;
                sideDistX = (viewport.getX() - mapX) * deltaDistX;
            } else {
                stepX = 1;
                sideDistX = (mapX + 1.0 - viewport.getX()) * deltaDistX;
            }

            if (rayDirY < 0) {
                stepY = -1;
                sideDistY = (viewport.getY() - mapY) * deltaDistY;
            } else {
                stepY = 1;
                sideDistY = (mapY + 1.0 - viewport.getY()) * deltaDistY;
            }

            boolean hit = false;
            int side = 0;

            while (!hit) {
                if (sideDistX < sideDistY) {
                    sideDistX += deltaDistX;
                    mapX += stepX;
                    side = 0;
                } else {
                    sideDistY += deltaDistY;
                    mapY += stepY;
                    side = 1;
                }
                if (map.get(mapX, mapY) > 0) hit = true;
            }

            double perpWallDist;
            if (side == 0) perpWallDist = (mapX - viewport.getX() + (1 - stepX) / 2.0) / rayDirX;
            else perpWallDist = (mapY - viewport.getY() + (1 - stepY) / 2.0) / rayDirY;

            int lineHeight = (int) (height / (perpWallDist + 0.0001));
            int drawStart = -lineHeight / 2 + height / 2;
            int drawEnd = lineHeight / 2 + height / 2;
            int color = side == 0 ? 0xAAAAAA : 0x777777;
            int start = Math.max(0, drawStart);
            int end = Math.min(height - 1, drawEnd);
            for (int y = start; y <= end; y++) {
                img.setRGB(x, y, color);
            }
        }

        // 시각적 십자 포인터
        int cx = width / 2, cy = height / 2;
        for (int dy = -2; dy <= 2; dy++) img.setRGB(cx, cy + dy, 0xFFFF00);
        for (int dx = -2; dx <= 2; dx++) img.setRGB(cx + dx, cy, 0xFFFF00);

        return img;
    }
}
