import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.HashMap;
import java.util.Map;

public class CarAnimation extends Applet implements Runnable, ActionListener, ItemListener, AdjustmentListener {

    // Constants
    private static final int DEFAULT_WIDTH = 900;
    private static final int DEFAULT_HEIGHT = 650;
    private static final int ANIMATION_DELAY = 50;

    private static final Color SKY_TOP = new Color(135, 206, 250);
    private static final Color SKY_BOTTOM = new Color(176, 224, 230);
    private static final Color SKY_BACKGROUND = new Color(135, 206, 235);

    private static final Color ROAD_COLOR = new Color(70, 70, 70);
    private static final Color ROAD_LINE_COLOR = Color.YELLOW;

    private static final Color GRASS_TOP = new Color(34, 139, 34);
    private static final Color GRASS_BOTTOM = new Color(0, 100, 0);

    private static final int DEFAULT_CAR_X = 400;
    private static final int DEFAULT_CLOUD_X = 600;
    private static final int DEFAULT_SPEED = 5;
    private static final double DEFAULT_SCALE = 1.5;
    private static final Color DEFAULT_CAR_COLOR = new Color(180, 180, 190);

    private static final int CLOUD_SPEED = 5;
    private static final int SUN_ROTATION_SPEED = 2;
    private static final int WHEEL_ROTATION_MULTIPLIER = 3;

    // Animation
    private Thread animationThread;
    private volatile boolean isRunning = false;
    private int carX = DEFAULT_CAR_X;
    private int cloudX = DEFAULT_CLOUD_X;
    private int sunRotation = 0;
    private int wheelRotation = 0;

    // Double Buffering
    private Image offScreenImage;

    // Control
    private int speed = DEFAULT_SPEED;
    private Color carColor = DEFAULT_CAR_COLOR;
    private double carScale = DEFAULT_SCALE;
    private int direction = 1;
    private ControlPanel controlPanel;

    @Override
    public void init() {
        setupApplet();
        createControlPanel();
    }

    // Setup
    private void setupApplet() {
        setLayout(new BorderLayout());
        setBackground(SKY_BACKGROUND);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
    }

    // cr & con- control panel
    private void createControlPanel() {
        controlPanel = new ControlPanel();

        controlPanel.addButtonListeners(this);
        controlPanel.addItemListeners(this);
        controlPanel.addAdjustmentListener(this);

        add(controlPanel, BorderLayout.SOUTH);
    }

    // Drawing
    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = setupGraphics2D(g);

        int width = getWidth();
        int height = getHeight();
        int carY = calculateCarY(height);
        drawSky(g2d, width, height);
        drawTreeZone(g2d, width, height);
        drawSun(g2d, width - 150, 120);
        drawClouds(g2d);
        drawTrees(g2d, width, height);
        drawGrass(g2d, width, height);
        drawRoad(g2d, width, height);
        drawRoadGuardrail(g2d, width, height);
        drawCar(g2d, carX, carY);
    }

    // anti-aliasing
    private Graphics2D setupGraphics2D(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        return g2d;
    }

    // car Y position
    private int calculateCarY(int height) {
        return height - 220;
    }

    // sky background
    private void drawSky(Graphics2D g2d, int width, int height) {
        GradientPaint skyGradient = new GradientPaint(
                0, 0, SKY_TOP,
                0, height - 250, SKY_BOTTOM);
        g2d.setPaint(skyGradient);
        g2d.fill(new Rectangle2D.Double(0, 0, width, height - 250));
    }

    // rotating sun
    private void drawSun(Graphics2D g2d, int x, int y) {
        AffineTransform oldTransform = g2d.getTransform();

        g2d.rotate(Math.toRadians(sunRotation), x, y);

        drawSunBody(g2d, x, y);
        drawSunRays(g2d, x, y);

        g2d.setTransform(oldTransform);
    }

    // sun body
    private void drawSunBody(Graphics2D g2d, int x, int y) {
        GradientPaint sunGradient = new GradientPaint(
                x - 45, y - 45, Color.YELLOW,
                x + 45, y + 45, Color.ORANGE);
        g2d.setPaint(sunGradient);
        g2d.fill(new Ellipse2D.Double(x - 45, y - 45, 90, 90));
    }

    // sun rays
    private void drawSunRays(Graphics2D g2d, int x, int y) {
        g2d.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.setColor(Color.ORANGE);

        for (int i = 0; i < 12; i++) {
            double angle = Math.toRadians(i * 30);
            int x1 = (int) (x + Math.cos(angle) * 55);
            int y1 = (int) (y + Math.sin(angle) * 55);
            int x2 = (int) (x + Math.cos(angle) * 75);
            int y2 = (int) (y + Math.sin(angle) * 75);
            g2d.draw(new Line2D.Double(x1, y1, x2, y2));
        }
    }

    // moving clouds
    private static final int CLOUD_COUNT = 30;

    private void drawClouds(Graphics2D g2d) {
        for (int i = 0; i < CLOUD_COUNT; i++) {
            double scale = 0.8 + (i % 3) * 0.2;

            int x = cloudX - i * 250;
            int y = 70 + (i % 4) * 30;

            drawCloudScaled(g2d, x, y, scale);
        }
    }

    // single cloud
    private void drawCloudScaled(Graphics2D g2d, int x, int y, double scale) {
        AffineTransform old = g2d.getTransform();
        g2d.translate(x, y);
        g2d.scale(scale, scale);
        g2d.setColor(Color.WHITE);
        g2d.fill(new Ellipse2D.Double(0, 0, 70, 45));
        g2d.fill(new Ellipse2D.Double(35, -12, 80, 55));
        g2d.fill(new Ellipse2D.Double(70, 0, 70, 45));
        g2d.setColor(new Color(230, 230, 230));
        g2d.fill(new Ellipse2D.Double(5, 30, 60, 20));
        g2d.fill(new Ellipse2D.Double(75, 30, 60, 20));

        g2d.setTransform(old);
    }

    // Background behind trees
    private void drawTreeZone(Graphics2D g2d, int width, int height) {

        int roadTop = height - 250;
        int treeZoneHeight = 140;

        g2d.setColor(new Color(60, 120, 60));
        g2d.fillRect(0, roadTop - treeZoneHeight, width, treeZoneHeight);
    }

    // Trees along the road
    private void drawTrees(Graphics2D g2d, int width, int height) {

        int roadTop = height - 250;
        int trunkHeight = 100;
        int treeBaseY = roadTop - trunkHeight;
        int spacing = 120;
        int offset = 60;

        // left side
        for (int x = 0; x < width; x += spacing) {
            drawTree(g2d, x, treeBaseY);
        }

        // right side
        for (int x = spacing / 2; x < width; x += spacing) {
            drawTree(g2d, x, treeBaseY - offset);
        }
    }

    // Single Tree
    private void drawTree(Graphics2D g2d, int x, int y) {

        g2d.setColor(new Color(101, 67, 33));
        g2d.fillRect(x - 10, y, 20, 90);
        g2d.setColor(new Color(34, 139, 34));
        g2d.fillOval(x - 55, y - 40, 110, 90);
        g2d.setColor(new Color(0, 128, 0));
        g2d.fillOval(x - 45, y - 25, 90, 70);
        g2d.setColor(new Color(50, 150, 50));
        g2d.fillOval(x - 35, y - 10, 70, 55);
    }

    // Grass
    private void drawGrass(Graphics2D g2d, int width, int height) {
        GradientPaint grassGradient = new GradientPaint(
                0, height - 150, GRASS_TOP,
                0, height, GRASS_BOTTOM);

        g2d.setPaint(grassGradient);
        g2d.fillRect(0, height - 150, width, 150);
    }

    // Road
    private void drawRoad(Graphics2D g2d, int width, int height) {
        g2d.setColor(ROAD_COLOR);
        g2d.fillRect(0, height - 250, width, 100);

        g2d.setColor(ROAD_LINE_COLOR);
        g2d.setStroke(new BasicStroke(
                4, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                10.0f, new float[] { 20.0f, 20.0f }, 0.0f));
        g2d.drawLine(0, height - 200, width, height - 200);
    }

    // Side guardrail for the road
    private void drawRoadGuardrail(Graphics2D g2d, int width, int height) {

        int roadTop = height - 250;

        g2d.setColor(new Color(160, 160, 160));
        g2d.fillRect(0, roadTop - 15, width, 10);

        g2d.setColor(new Color(120, 120, 120));
        for (int x = 0; x < width; x += 40) {
            g2d.fillRect(x, roadTop - 15, 6, 25);
        }
    }

    // complete car
    private void drawCar(Graphics2D g2d, int x, int y) {
        AffineTransform oldTransform = g2d.getTransform();

        g2d.translate(x, y);
        g2d.scale(carScale * direction, carScale);
        drawCarBody(g2d);
        drawCarRoof(g2d);
        drawCarWindows(g2d);
        drawCarLights(g2d);
        drawCarDetails(g2d);
        drawCarBumpers(g2d);
        drawCarWheels(g2d);

        g2d.setTransform(oldTransform);
    }

    // car body
    private void drawCarBody(Graphics2D g2d) {
        GradientPaint bodyGradient = new GradientPaint(
                -70, -20, carColor,
                70, 20, carColor.darker());
        g2d.setPaint(bodyGradient);
        g2d.fill(new RoundRectangle2D.Double(-70, -20, 140, 40, 15, 15));
        g2d.setColor(new Color(255, 255, 255, 50));
        g2d.fill(new RoundRectangle2D.Double(-65, -18, 130, 15, 10, 10));
    }

    // car roof
    private void drawCarRoof(Graphics2D g2d) {
        GradientPaint roofGradient = new GradientPaint(
                -30, -50, carColor.brighter(),
                30, -20, carColor);
        g2d.setPaint(roofGradient);
        int[] roofX = { -40, -30, 30, 40 };
        int[] roofY = { -20, -50, -50, -20 };
        g2d.fillPolygon(roofX, roofY, 4);
        g2d.setColor(new Color(255, 255, 255, 60));
        int[] shineX = { -28, -25, 25, 28 };
        int[] shineY = { -20, -48, -48, -20 };
        g2d.fillPolygon(shineX, shineY, 4);
    }

    // car windows
    private void drawCarWindows(Graphics2D g2d) {
        g2d.setColor(new Color(120, 170, 220, 170));
        Polygon frontWindow = new Polygon(
                new int[] { 12, 34, 30, 8 },
                new int[] { -20, -20, -46, -48 },
                4);
        g2d.fill(frontWindow);
        Polygon backWindow = new Polygon(
                new int[] { -34, -12, -8, -30 },
                new int[] { -20, -20, -48, -46 },
                4);
        g2d.fill(backWindow);
        g2d.setColor(new Color(220, 235, 255, 90));
        g2d.fill(new Polygon(
                new int[] { 14, 22, 20, 12 },
                new int[] { -22, -22, -36, -40 },
                4));
        g2d.fill(new Polygon(
                new int[] { -30, -22, -24, -32 },
                new int[] { -22, -22, -40, -36 },
                4));

    }

    // headlights & taillights
    private void drawCarLights(Graphics2D g2d) {
        g2d.setColor(Color.YELLOW);
        g2d.fill(new Ellipse2D.Double(60, -10, 12, 8));
        g2d.fill(new Ellipse2D.Double(60, 2, 12, 8));

        g2d.setColor(Color.RED);
        g2d.fill(new Ellipse2D.Double(-72, -10, 10, 8));
        g2d.fill(new Ellipse2D.Double(-72, 2, 10, 8));
    }

    // door lines
    private void drawCarDetails(Graphics2D g2d) {
        g2d.setColor(carColor.darker().darker());
        g2d.setStroke(new BasicStroke(2));
        g2d.draw(new Line2D.Double(0, -20, 0, 20));
    }

    // car bumper
    private void drawCarBumpers(Graphics2D g2d) {
        g2d.setColor(Color.DARK_GRAY);
        g2d.fill(new RoundRectangle2D.Double(70, -15, 8, 30, 5, 5));
        g2d.fill(new RoundRectangle2D.Double(-78, -15, 8, 30, 5, 5));
    }

    // car wheel
    private void drawCarWheels(Graphics2D g2d) {
        drawWheel(g2d, -40, 20);
        drawWheel(g2d, 40, 20);
    }

    // single wheel with rotation
    private void drawWheel(Graphics2D g2d, int x, int y) {
        AffineTransform oldTransform = g2d.getTransform();

        // Outer tire
        g2d.setColor(Color.BLACK);
        g2d.fill(new Ellipse2D.Double(x - 15, y - 15, 30, 30));
        g2d.setColor(new Color(40, 40, 40));
        g2d.fill(new Ellipse2D.Double(x - 12, y - 12, 24, 24));
        g2d.setColor(new Color(200, 200, 200));
        g2d.fill(new Ellipse2D.Double(x - 10, y - 10, 20, 20));
        g2d.setColor(Color.DARK_GRAY);
        g2d.fill(new Ellipse2D.Double(x - 4, y - 4, 8, 8));

        drawWheelSpokes(g2d, x, y);

        g2d.setTransform(oldTransform);
    }

    // rotate wheel
    private void drawWheelSpokes(Graphics2D g2d, int x, int y) {
        g2d.translate(x, y);
        g2d.rotate(Math.toRadians(wheelRotation));

        g2d.setStroke(new BasicStroke(2));
        g2d.setColor(Color.DARK_GRAY);

        for (int i = 0; i < 5; i++) {
            double angle = Math.toRadians(i * 72);
            int x1 = (int) (Math.cos(angle) * 3);
            int y1 = (int) (Math.sin(angle) * 3);
            int x2 = (int) (Math.cos(angle) * 8);
            int y2 = (int) (Math.sin(angle) * 8);
            g2d.draw(new Line2D.Double(x1, y1, x2, y2));
        }
    }

    // Double Buffering
    @Override
    public void update(Graphics g) {
        int width = getWidth();
        int height = getHeight();

        // Create buffer image if needed
        if (offScreenImage == null ||
                offScreenImage.getWidth(null) != width ||
                offScreenImage.getHeight(null) != height) {

            offScreenImage = createImage(width, height);
        }

        // get Graphics EVERY FRAME
        Graphics2D bufferG = (Graphics2D) offScreenImage.getGraphics();

        bufferG.setColor(getBackground());
        bufferG.fillRect(0, 0, width, height);

        paint(bufferG);

        g.drawImage(offScreenImage, 0, 0, this);

        bufferG.dispose();
    }

    @Override
    public void run() {
        while (isRunning) {
            updateAnimationState();
            repaint();
            sleep();
        }
    }

    // animation state
    private void updateAnimationState() {
        updateCarPosition();
        updateCloudPosition();
        updateSunRotation();
        updateWheelRotation();
    }

    // car position
    private void updateCarPosition() {
        carX += speed * direction;

        if (carX > getWidth() + 150) {
            carX = -150;
        } else if (carX < -150) {
            carX = getWidth() + 150;
        }
    }

    // cloud position
    private void updateCloudPosition() {
        cloudX -= CLOUD_SPEED;
        if (cloudX < -300) {
            cloudX = getWidth() + 200;
        }
    }

    // sun rotation
    private void updateSunRotation() {
        sunRotation += SUN_ROTATION_SPEED;
        if (sunRotation >= 360) {
            sunRotation = 0;
        }
    }

    // wheel rotation
    private void updateWheelRotation() {
        wheelRotation += speed * WHEEL_ROTATION_MULTIPLIER * direction;

        if (wheelRotation >= 360) {
            wheelRotation = 0;
        } else
            wheelRotation = (wheelRotation + 360) % 360;

    }

    // Pause animation
    private void sleep() {
        try {
            Thread.sleep(ANIMATION_DELAY);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source == controlPanel.getStartBtn()) {
            handleStart();
        } else if (source == controlPanel.getStopBtn()) {
            handleStop();
        } else if (source == controlPanel.getResetBtn()) {
            handleReset();
        } else if (source == controlPanel.getScaleField()) {
            handleScaleChange();
        }
    }

    // Start animation
    private void handleStart() {
        if (!isRunning) {
            isRunning = true;
            animationThread = new Thread(this);
            animationThread.start();
            controlPanel.updateStatus(true);
        }
    }

    // Stop animation
    private void handleStop() {
        isRunning = false;
        controlPanel.updateStatus(false);
    }

    // Reset
    private void handleReset() {
        isRunning = false;

        carX = DEFAULT_CAR_X;
        cloudX = DEFAULT_CLOUD_X;
        sunRotation = 0;
        wheelRotation = 0;

        speed = DEFAULT_SPEED;
        carScale = DEFAULT_SCALE;
        direction = 1;

        controlPanel.resetToDefaults();

        controlPanel.getStatusLabel().setForeground(new Color(0, 100, 200));

        repaint();
    }

    // Handling scale
    private void handleScaleChange() {
        carScale = controlPanel.getScale();
        repaint();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        Object source = e.getSource();

        if (source == controlPanel.getColorChoice()) {
            handleColorChange();
        } else if (source == controlPanel.getReverseCheckbox()) {
            handleDirectionChange();
        }
    }

    // Handling color
    private void handleColorChange() {
        String colorName = controlPanel.getSelectedColor();
        carColor = getColorByName(colorName);
        repaint();
    }

    // color
    private static final Map<String, Color> COLOR_MAP = new HashMap<>();

    static {
        COLOR_MAP.put("Red", Color.RED);
        COLOR_MAP.put("Blue", Color.BLUE);
        COLOR_MAP.put("Green", new Color(0, 150, 0));
        COLOR_MAP.put("Yellow", new Color(255, 200, 0));
        COLOR_MAP.put("Black", Color.BLACK);
        COLOR_MAP.put("White", Color.WHITE);
        COLOR_MAP.put("Gray", Color.GRAY);
        COLOR_MAP.put("Orange", new Color(255, 140, 0));
        COLOR_MAP.put("Purple", new Color(128, 0, 128));
        COLOR_MAP.put("Pink", new Color(255, 105, 180));
        COLOR_MAP.put("Cyan", new Color(0, 180, 180));
        COLOR_MAP.put("Brown", new Color(139, 69, 19));
        COLOR_MAP.put("Silver", new Color(180, 180, 190));
        COLOR_MAP.put("Dark Blue", new Color(0, 60, 120));
    }

    private Color getColorByName(String colorName) {
        return COLOR_MAP.getOrDefault(colorName, DEFAULT_CAR_COLOR);
    }

    // Handle direction change
    private void handleDirectionChange() {
        direction = controlPanel.isReversed() ? -1 : 1;
    }

    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {
        if (e.getSource() == controlPanel.getSpeedScrollbar()) {
            speed = controlPanel.getSpeed();
        }
    }

    @Override
    public void stop() {
        isRunning = false;
        if (animationThread != null && animationThread.isAlive()) {
            try {
                animationThread.join(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        } // by x606
    } // by x606
} // by x606