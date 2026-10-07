package com.virusimbaya.launcher;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LauncherApp extends JFrame {
    private final ThemeManager themeManager = new ThemeManager();
    private final GradientPanel rootPanel;
    private final JPanel appGrid;
    private final JPanel themeStrip;
    private final JLabel titleLabel;
    private final JLabel dateLabel;

    public LauncherApp() {
        super("Virusi Mbaya Launcher");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 760);
        setLocationRelativeTo(null);
        setResizable(false);

        rootPanel = new GradientPanel(themeManager.getCurrentTheme());
        rootPanel.setLayout(new BorderLayout(16, 16));
        rootPanel.setBorder(new EmptyBorder(24, 24, 24, 24));

        JPanel topBar = buildTopBar();
        rootPanel.add(topBar, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(18, 18));
        centerPanel.setOpaque(false);

        JPanel headingPanel = new JPanel(new BorderLayout(8, 8));
        headingPanel.setOpaque(false);

        titleLabel = new JLabel("Virusi Mbaya");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 34));
        titleLabel.setForeground(themeManager.getCurrentTheme().getText());

        dateLabel = new JLabel(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")) + " • 24°C");
        dateLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        dateLabel.setForeground(themeManager.getCurrentTheme().getMutedText());

        headingPanel.add(titleLabel, BorderLayout.WEST);
        headingPanel.add(dateLabel, BorderLayout.EAST);
        centerPanel.add(headingPanel, BorderLayout.NORTH);

        JTextField searchField = new JTextField("Search apps, contacts, music");
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        searchField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 80), 1, true),
                new EmptyBorder(12, 14, 12, 14)
        ));
        searchField.setBackground(new Color(12, 18, 28, 180));
        searchField.setForeground(themeManager.getCurrentTheme().getText());
        searchField.setCaretColor(themeManager.getCurrentTheme().getAccent());
        centerPanel.add(searchField, BorderLayout.CENTER);

        themeStrip = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        themeStrip.setOpaque(false);
        populateThemeButtons();
        centerPanel.add(themeStrip, BorderLayout.SOUTH);

        appGrid = new JPanel(new GridLayout(0, 4, 18, 18));
        appGrid.setOpaque(false);
        populateAppTiles();

        rootPanel.add(centerPanel, BorderLayout.CENTER);
        rootPanel.add(appGrid, BorderLayout.SOUTH);

        setContentPane(rootPanel);
    }

    private JPanel buildTopBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setOpaque(false);

        JPanel leftStatus = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        leftStatus.setOpaque(false);

        for (Color dotColor : List.of(new Color(255, 100, 100), new Color(255, 200, 94), new Color(72, 196, 116))) {
            JLabel dot = new JLabel("●");
            dot.setForeground(dotColor);
            dot.setFont(new Font("SansSerif", Font.BOLD, 18));
            leftStatus.add(dot);
        }

        JLabel signal = new JLabel("5G");
        signal.setForeground(themeManager.getCurrentTheme().getText());
        signal.setFont(new Font("SansSerif", Font.BOLD, 12));
        leftStatus.add(signal);

        bar.add(leftStatus, BorderLayout.WEST);

        JPanel rightStatus = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        rightStatus.setOpaque(false);

        JLabel battery = new JLabel("86% ▣");
        battery.setForeground(themeManager.getCurrentTheme().getText());
        battery.setFont(new Font("SansSerif", Font.BOLD, 12));
        rightStatus.add(battery);

        bar.add(rightStatus, BorderLayout.EAST);
        return bar;
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
        ThemeManager.AppTheme current = themeManager.getCurrentTheme();

        String[][] apps = {
                {"Messages", "✉"},
                {"Camera", "📷"},
                {"Music", "♫"},
                {"Settings", "⚙"},
                {"Gallery", "🖼"},
                {"Contacts", "☎"},
                {"Weather", "☀"},
                {"Maps", "📍"}
        };

        for (String[] app : apps) {
            appGrid.add(new AppTile(app[0], app[1], current.getAccent()));
        }

        appGrid.revalidate();
        appGrid.repaint();
    }

    private void applyTheme(ThemeManager.AppTheme theme) {
        themeManager.setCurrentTheme(theme);
        rootPanel.setTheme(theme);

        titleLabel.setForeground(theme.getText());
        dateLabel.setForeground(theme.getMutedText());
        searchFieldStyle();

        populateThemeButtons();
        populateAppTiles();
        rootPanel.revalidate();
        rootPanel.repaint();
    }

    private void searchFieldStyle() {
        Component[] components = rootPanel.getComponents();
        for (Component component : components) {
            if (component instanceof JPanel panel) {
                for (Component child : panel.getComponents()) {
                    if (child instanceof JTextField field) {
                        field.setForeground(themeManager.getCurrentTheme().getText());
                        field.setCaretColor(themeManager.getCurrentTheme().getAccent());
                        field.setBackground(new Color(12, 18, 28, 180));
                    }
                }
            }
        }
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
            setOpaque(false);
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
