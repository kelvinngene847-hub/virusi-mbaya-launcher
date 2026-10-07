package com.virusimbaya.launcher;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AppTile extends JButton {
    public AppTile(String label, String icon, Color accent, Runnable action) {
        super();
        setLayout(new BorderLayout(8, 8));
        setOpaque(true);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setFocusPainted(false);
        setBorder(createTileBorder(accent));
        setBackground(new Color(18, 24, 38, 180));
        setForeground(new Color(245, 247, 255));

        JLabel iconLabel = new JLabel(icon, SwingConstants.CENTER);
        iconLabel.setFont(new Font("SansSerif", Font.PLAIN, 28));
        iconLabel.setForeground(accent);

        JLabel titleLabel = new JLabel(label, SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        titleLabel.setForeground(new Color(245, 247, 255));

        add(iconLabel, BorderLayout.CENTER);
        add(titleLabel, BorderLayout.SOUTH);
        setPreferredSize(new Dimension(190, 140));

        addActionListener(event -> {
            if (action != null) {
                action.run();
            }
        });
    }

    private Border createTileBorder(Color accent) {
        Border lineBorder = BorderFactory.createLineBorder(accent, 2, true);
        Border padding = new EmptyBorder(12, 12, 10, 12);
        return new CompoundBorder(lineBorder, padding);
    }
}


