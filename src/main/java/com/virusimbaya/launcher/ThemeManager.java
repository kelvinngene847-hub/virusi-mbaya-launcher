package com.virusimbaya.launcher;

import java.awt.*;

public class ThemeManager {
    private AppTheme currentTheme;
    private final AppTheme neonTheme = new AppTheme(
            "Neon Pulse",
            new Color(9, 12, 20),
            new Color(30, 41, 59),
            new Color(18, 24, 38),
            new Color(255, 108, 130),
            new Color(242, 246, 255),
            new Color(165, 180, 252),
            new Color(31, 44, 72)
    );

    private final AppTheme auroraTheme = new AppTheme(
            "Aurora Glow",
            new Color(12, 22, 28),
            new Color(22, 91, 92),
            new Color(16, 35, 49),
            new Color(99, 255, 205),
            new Color(238, 247, 255),
            new Color(149, 241, 255),
            new Color(14, 26, 38)
    );

    private final AppTheme vintageTheme = new AppTheme(
            "Voltage Gold",
            new Color(20, 16, 12),
            new Color(104, 79, 42),
            new Color(52, 36, 20),
            new Color(255, 190, 92),
            new Color(255, 248, 230),
            new Color(255, 216, 125),
            new Color(66, 46, 24)
    );

    public ThemeManager() {
        this.currentTheme = neonTheme;
    }

    public AppTheme getCurrentTheme() {
        return currentTheme;
    }

    public void setCurrentTheme(AppTheme currentTheme) {
        this.currentTheme = currentTheme;
    }

    public AppTheme[] getThemes() {
        return new AppTheme[]{neonTheme, auroraTheme, vintageTheme};
    }

    public static class AppTheme {
        private final String name;
        private final Color backgroundStart;
        private final Color backgroundEnd;
        private final Color panel;
        private final Color accent;
        private final Color text;
        private final Color mutedText;
        private final Color tileBackground;

        public AppTheme(String name, Color backgroundStart, Color backgroundEnd, Color panel,
                        Color accent, Color text, Color mutedText, Color tileBackground) {
            this.name = name;
            this.backgroundStart = backgroundStart;
            this.backgroundEnd = backgroundEnd;
            this.panel = panel;
            this.accent = accent;
            this.text = text;
            this.mutedText = mutedText;
            this.tileBackground = tileBackground;
        }

        public String getName() {
            return name;
        }

        public Color getBackgroundStart() {
            return backgroundStart;
        }

        public Color getBackgroundEnd() {
            return backgroundEnd;
        }

        public Color getPanel() {
            return panel;
        }

        public Color getAccent() {
            return accent;
        }

        public Color getText() {
            return text;
        }

        public Color getMutedText() {
            return mutedText;
        }

        public Color getTileBackground() {
            return tileBackground;
        }
    }
}



