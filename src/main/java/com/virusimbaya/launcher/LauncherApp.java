package com.virusimbaya.launcher;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LauncherApp extends JFrame {
    private final ThemeManager themeManager = new ThemeManager();
    private final GradientPanel rootPanel = new GradientPanel(themeManager.getCurrentTheme());
    private final JPanel appGrid = new JPanel(new GridLayout(0, 4, 18, 18));
    private final JPanel themeStrip = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
    private final JLabel greetingLabel = new JLabel("Good evening");
    private final JLabel clockLabel = new JLabel();
    private final JTextField searchField = new JTextField("Search apps, contacts, music");
    private final Timer clockTimer;

    public LauncherApp() {
        super("Virusi Mbaya Launcher");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 780);
        setLocationRelativeTo(null);
        setResizable(false);
        setUndecorated(false);

        rootPanel.setLayout(new BorderLayout(18, 18));
        rootPanel.setBorder(new EmptyBorder(18, 18, 18, 18));
        rootPanel.setOpaque(false);

        appGrid.setOpaque(false);
        themeStrip.setOpaque(false);

        JPanel statusBar = buildStatusBar();
        rootPanel.add(statusBar, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(18, 18));
        centerPanel.setOpaque(false);

        JPanel headingPanel = new JPanel(new BorderLayout(10, 10));
        headingPanel.setOpaque(false);

        greetingLabel.setFont(new Font("SansSerif", Font.BOLD, 34));
        greetingLabel.setForeground(themeManager.getCurrentTheme().getText());

        clockLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        clockLabel.setForeground(themeManager.getCurrentTheme().getMutedText());
        updateClock();

        headingPanel.add(greetingLabel, BorderLayout.WEST);
        headingPanel.add(clockLabel, BorderLayout.EAST);
        centerPanel.add(headingPanel, BorderLayout.NORTH);

        searchField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 80), 1, true),
                new EmptyBorder(12, 14, 12, 14)
        ));
        searchField.setBackground(new Color(12, 18, 28, 180));
        searchField.setForeground(themeManager.getCurrentTheme().getText());
        searchField.setCaretColor(themeManager.getCurrentTheme().getAccent());
        centerPanel.add(searchField, BorderLayout.CENTER);

        populateThemeButtons();
        centerPanel.add(themeStrip, BorderLayout.SOUTH);

        rootPanel.add(centerPanel, BorderLayout.CENTER);

        populateAppTiles();
        rootPanel.add(appGrid, BorderLayout.SOUTH);

        setContentPane(rootPanel);

        clockTimer = new Timer(1000, event -> updateClock());
        clockTimer.start();
    }

    private JPanel buildStatusBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setOpaque(false);

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        left.setOpaque(false);
        left.add(statusDot(new Color(255, 114, 118)));
        left.add(statusDot(new Color(255, 214, 102)));
        left.add(statusDot(new Color(88, 216, 133)));
        JLabel signal = new JLabel("5G");
        signal.setFont(new Font("SansSerif", Font.BOLD, 12));
        signal.setForeground(themeManager.getCurrentTheme().getText());
        left.add(signal);
        bar.add(left, BorderLayout.WEST);

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        right.setOpaque(false);
        JLabel battery = new JLabel("86% ▣");
        battery.setFont(new Font("SansSerif", Font.BOLD, 12));
        battery.setForeground(themeManager.getCurrentTheme().getText());
        right.add(battery);
        bar.add(right, BorderLayout.EAST);
        return bar;
    }

    private JLabel statusDot(Color color) {
        JLabel dot = new JLabel("●");
        dot.setForeground(color);
        dot.setFont(new Font("SansSerif", Font.BOLD, 17));
        return dot;
    }

    private void populateThemeButtons() {
        themeStrip.removeAll();
        for (ThemeManager.AppTheme theme : themeManager.getThemes()) {
            JButton button = new JButton(theme.getName());
            button.setFocusPainted(false);
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
            button.setOpaque(true);
            button.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(theme.getAccent(), 2, true),
                    new EmptyBorder(8, 14, 8, 14)
            ));
            button.setBackground(theme.getPanel());
            button.setForeground(theme.getText());
            button.setFont(new Font("SansSerif", Font.BOLD, 12));
            button.addActionListener(event -> applyTheme(theme));
            themeStrip.add(button);
        }
        themeStrip.revalidate();
        themeStrip.repaint();
    }

    private void populateAppTiles() {
        appGrid.removeAll();
        for (AppData.AppEntry app : AppData.getApplications()) {
            AppTile tile = new AppTile(
                    app.getName(),
                    app.getIcon(),
                    themeManager.getCurrentTheme().getAccent(),
                    () -> JOptionPane.showMessageDialog(this, app.getName() + " launched")
            );
            appGrid.add(tile);
        }
        appGrid.revalidate();
        appGrid.repaint();
    }

    private void applyTheme(ThemeManager.AppTheme theme) {
        themeManager.setCurrentTheme(theme);
        rootPanel.setTheme(theme);
        greetingLabel.setForeground(theme.getText());
        clockLabel.setForeground(theme.getMutedText());
        searchField.setForeground(theme.getText());
        searchField.setCaretColor(theme.getAccent());
        searchField.setBackground(new Color(12, 18, 28, 180));
        populateThemeButtons();
        populateAppTiles();
        repaint();
    }

    private void updateClock() {
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        clockLabel.setText(time + " • 24°C");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LauncherApp launcher = new LauncherApp();
            launcher.setVisible(true);
        });
    }

    private static class GradientPanel extends JPanel {
        private ThemeManager.AppTheme theme;

        public GradientPanel(ThemeManager.AppTheme theme) {
            this.theme = theme;
        }

        public void setTheme(ThemeManager.AppTheme theme) {
            this.theme = theme;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            GradientPaint gradient = new GradientPaint(
                    0, 0, theme.getBackgroundStart(),
                    getWidth(), getHeight(), theme.getBackgroundEnd()
            );
            g2.setPaint(gradient);
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.dispose();
        }
    }
}




