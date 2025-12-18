import java.awt.*;
import java.awt.event.*;

public class ControlPanel extends Panel {
    // Constants
    private static final Color PANEL_BG_COLOR = new Color(255, 255, 224);
    private static final Color STATUS_RUNNING_COLOR = new Color(0, 150, 0);
    private static final Color STATUS_STOPPED_COLOR = new Color(200, 0, 0);
    private static final Font LARGE_FONT = new Font("Arial", Font.BOLD, 16);
    private static final Font STATUS_FONT = new Font("Arial", Font.BOLD, 18);
    private static final int SPEED_MIN = 1;
    private static final int SPEED_MAX = 21;
    private static final int SPEED_DEFAULT = 5;
    private static final double SCALE_MIN = 1.0;
    private static final double SCALE_MAX = 2.5;
    private static final double SCALE_DEFAULT = 1.5;

    // UI Components
    private Button startBtn, stopBtn, resetBtn;
    private Label speedLabel, statusLabel, colorLabel;
    private Scrollbar speedScrollbar;
    private Choice colorChoice;
    private Checkbox reverseCheckbox;
    private TextField scaleField;
    private Panel topPanel;
    private Panel middlePanel;
    private Panel bottomPanel;

    public ControlPanel() {
        setLayout(new BorderLayout());
        initializeComponents();
    }

    // components
    private void initializeComponents() {
        topPanel = createTopPanel();
        middlePanel = createMiddlePanel();
        bottomPanel = createBottomPanel();

        Panel centerContainer = new Panel(new BorderLayout());
        centerContainer.add(middlePanel, BorderLayout.NORTH);
        centerContainer.add(bottomPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(centerContainer, BorderLayout.CENTER);
    }

    private Panel createTopPanel() {
        Panel panel = new Panel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panel.setBackground(PANEL_BG_COLOR);

        addControlButtons(panel);
        addSeparator(panel);
        addColorSelector(panel);

        return panel;
    }

    // direction, scale, status
    private Panel createMiddlePanel() {
        Panel panel = new Panel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        panel.setBackground(PANEL_BG_COLOR);

        addDirectionControl(panel);
        addSeparator(panel);
        addScaleControl(panel);
        addSeparator(panel);
        addStatusLabel(panel);

        return panel;
    }

    // bottom panel
    private Panel createBottomPanel() {
        Panel panel = new Panel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel.setBackground(PANEL_BG_COLOR);

        addSpeedControls(panel);

        return panel;
    }

    // control buttons (Start, Stop, Reset)
    private void addControlButtons(Panel panel) {

        startBtn = createButton("Start", LARGE_FONT);
        startBtn.setBackground(new Color(0, 160, 0));
        startBtn.setForeground(Color.WHITE);

        stopBtn = createButton("Stop", LARGE_FONT);
        stopBtn.setBackground(new Color(200, 0, 0));
        stopBtn.setForeground(Color.WHITE);

        resetBtn = createButton("Reset", LARGE_FONT);
        resetBtn.setBackground(new Color(255, 140, 0));
        resetBtn.setForeground(Color.BLACK);

        panel.add(startBtn);
        panel.add(stopBtn);
        panel.add(resetBtn);
    }

    // speed controls
    private void addSpeedControls(Panel panel) {

        speedLabel = new Label("Speed: " + SPEED_DEFAULT, Label.CENTER);
        speedLabel.setFont(new Font("Arial", Font.BOLD, 25));

        speedScrollbar = new Scrollbar(
                Scrollbar.HORIZONTAL,
                SPEED_DEFAULT, 1,
                SPEED_MIN, SPEED_MAX);
        speedScrollbar.setPreferredSize(new Dimension(300, 20));

        speedScrollbar.addAdjustmentListener(e -> {
            int value = e.getValue();
            speedLabel.setText("Speed: " + value);
            if (value <= 7) {
                speedLabel.setForeground(new Color(0, 150, 0));
            } else if (value <= 14) {
                speedLabel.setForeground(new Color(255, 140, 0));
            } else {
                speedLabel.setForeground(new Color(200, 0, 0));
            }
        });

        panel.add(speedLabel);
        panel.add(speedScrollbar);
    }

    // color selector
    private void addColorSelector(Panel panel) {
        colorLabel = new Label("Car Color:");
        colorLabel.setFont(LARGE_FONT);
        colorChoice = new Choice();
        colorChoice.setFont(LARGE_FONT);
        String[] colors = {
                "Silver", "Blue", "Green", "Yellow",
                "Black", "White", "Gray", "Orange",
                "Purple", "Pink", "Cyan", "Brown",
                "Red", "Dark Blue"
        };

        for (String color : colors) {
            colorChoice.add(color);
        }

        panel.add(colorLabel);
        panel.add(colorChoice);
    }

    // direction control
    private void addDirectionControl(Panel panel) {
        reverseCheckbox = new Checkbox("Reverse Direction");
        reverseCheckbox.setFont(LARGE_FONT);

        reverseCheckbox.setForeground(new Color(0, 150, 0));

        reverseCheckbox.addItemListener(e -> {
            if (reverseCheckbox.getState()) {
                reverseCheckbox.setForeground(new Color(200, 0, 0));
            } else {
                reverseCheckbox.setForeground(new Color(0, 150, 0));
            }
        });

        panel.add(reverseCheckbox);
    }

    // scale control
    private void addScaleControl(Panel panel) {
        Label scaleLabel = new Label("Scale (" + SCALE_MIN + "-" + SCALE_MAX + "):");
        scaleLabel.setFont(LARGE_FONT);
        scaleField = new TextField(String.valueOf(SCALE_DEFAULT), 6);
        scaleField.setFont(LARGE_FONT);
        panel.add(scaleLabel);
        panel.add(scaleField);
    }

    // status label
    private void addStatusLabel(Panel panel) {
        statusLabel = new Label("Status: Stopped");
        statusLabel.setFont(STATUS_FONT);
        statusLabel.setForeground(STATUS_STOPPED_COLOR);
        panel.add(statusLabel);
    }

    // Add separator
    private void addSeparator(Panel panel) {
        panel.add(new Label(" | "));
    }

    private Button createButton(String label, Font font) {
        Button button = new Button(label);
        button.setFont(font);
        return button;
    }

    // listeners for buttons
    public void addButtonListeners(ActionListener listener) {
        startBtn.addActionListener(listener);
        stopBtn.addActionListener(listener);
        resetBtn.addActionListener(listener);
        scaleField.addActionListener(listener);
    }

    // listener for items
    public void addItemListeners(ItemListener listener) {
        colorChoice.addItemListener(listener);
        reverseCheckbox.addItemListener(listener);
    }

    // listener for speed scrollbar
    public void addAdjustmentListener(AdjustmentListener listener) {
        speedScrollbar.addAdjustmentListener(listener);
    }

    // application status
    public void updateStatus(boolean isRunning) {
        if (isRunning) {
            statusLabel.setText("Status: Running");
            statusLabel.setForeground(STATUS_RUNNING_COLOR);
        } else {
            statusLabel.setText("Status: Stopped");
            statusLabel.setForeground(STATUS_STOPPED_COLOR);
        }
    }

    // speed value
    public int getSpeed() {
        return speedScrollbar.getValue();
    }

    // selected color
    public String getSelectedColor() {
        return colorChoice.getSelectedItem();
    }

    // Check if reverse direction is enabled
    public boolean isReversed() {
        return reverseCheckbox.getState();
    }

    // scale value
    public double getScale() {
        try {
            double scale = Double.parseDouble(scaleField.getText());
            if (scale < SCALE_MIN || scale > SCALE_MAX) {
                scaleField.setText(String.valueOf(SCALE_DEFAULT));
                return SCALE_DEFAULT;
            }
            return scale;
        } catch (NumberFormatException e) {
            scaleField.setText(String.valueOf(SCALE_DEFAULT));
            return SCALE_DEFAULT;
        }
    }

    // Reset to default values
    public void resetToDefaults() {
        speedScrollbar.setValue(SPEED_DEFAULT);
        speedLabel.setText("Speed: " + SPEED_DEFAULT);

        colorChoice.select(0);
        reverseCheckbox.setState(false);
        scaleField.setText(String.valueOf(SCALE_DEFAULT));
        updateStatus(false);
    }

    // by x606
    public void setControlsEnabled(boolean enabled) {
        speedScrollbar.setEnabled(enabled);
        startBtn.setEnabled(enabled);
        stopBtn.setEnabled(enabled);
    }

    public Button getStartBtn() {
        return startBtn;
    }

    public Button getStopBtn() {
        return stopBtn;
    }

    public Button getResetBtn() {
        return resetBtn;
    }

    public TextField getScaleField() {
        return scaleField;
    }

    public Scrollbar getSpeedScrollbar() {
        return speedScrollbar;
    }

    public Choice getColorChoice() {
        return colorChoice;
    }

    public Checkbox getReverseCheckbox() {
        return reverseCheckbox;
    }

    public Label getStatusLabel() {
        return statusLabel;
    }
}
// by x606