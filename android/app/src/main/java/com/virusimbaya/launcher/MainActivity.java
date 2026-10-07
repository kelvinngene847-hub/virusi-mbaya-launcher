package com.virusimbaya.launcher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView clockLabel;
    private TextView greetingLabel;
    private EditText searchField;
    private GridView appGrid;
    private LinearLayout themeContainer;
    private Handler clockHandler;
    private Runnable clockRunnable;
    private ThemeColor currentTheme;
    private AppAdapter appAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clockLabel = findViewById(R.id.clockLabel);
        greetingLabel = findViewById(R.id.greetingLabel);
        searchField = findViewById(R.id.searchField);
        appGrid = findViewById(R.id.appGrid);
        themeContainer = findViewById(R.id.themeContainer);

        currentTheme = new ThemeColor("Neon Pulse",
                0xFF0C121C,
                0xFFFF6C82,
                0xFFF2F6FF,
                0xFF121628,
                0xFF1F2B3A);

        updateClock();
        setupAppGrid();
        setupThemeButtons();
        setupSearchFilter();
    }

    private void setupSearchFilter() {
        searchField.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                searchField.setHint("");
            }
        });

        searchField.setOnEditorActionListener((v, actionId, event) -> {
            String query = searchField.getText().toString().trim();
            if (!query.isEmpty()) {
                appAdapter.filter(query);
            }
            return true;
        });
    }

    private void updateClock() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String time = format.format(Calendar.getInstance().getTime());
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

        if (hour < 12) {
            greetingLabel.setText("Good morning");
        } else if (hour < 18) {
            greetingLabel.setText("Good afternoon");
        } else {
            greetingLabel.setText("Good evening");
        }

        clockLabel.setText(time + " • 24°C");
        clockHandler = new Handler(Looper.getMainLooper());
        clockRunnable = () -> {
            updateClock();
            clockHandler.postDelayed(clockRunnable, 1000);
        };
        clockHandler.postDelayed(clockRunnable, 1000);
    }

    private void setupAppGrid() {
        List<AppEntry> apps = new ArrayList<>();
        apps.add(new AppEntry("Messages", "✉", "msg"));
        apps.add(new AppEntry("Camera", "📷", "cam"));
        apps.add(new AppEntry("Music", "♫", "music"));
        apps.add(new AppEntry("Settings", "⚙", "settings"));
        apps.add(new AppEntry("Gallery", "🖼", "gallery"));
        apps.add(new AppEntry("Contacts", "☎", "contacts"));
        apps.add(new AppEntry("Weather", "☀", "weather"));
        apps.add(new AppEntry("Maps", "📍", "maps"));
        apps.add(new AppEntry("Banking", "💳", "banking"));
        apps.add(new AppEntry("Notes", "📝", "notes"));
        apps.add(new AppEntry("Calendar", "📅", "calendar"));
        apps.add(new AppEntry("Files", "📁", "files"));

        appAdapter = new AppAdapter(this, apps, currentTheme);
        appGrid.setAdapter(appAdapter);
        appGrid.setOnItemClickListener((parent, view, position, id) -> {
            AppEntry selected = (AppEntry) appAdapter.getItem(position);
            Toast.makeText(MainActivity.this, selected.getName() + " launched", Toast.LENGTH_SHORT).show();
        });
    }

    private void setupThemeButtons() {
        ThemeColor[] themes = {
                new ThemeColor("Neon Pulse", 0xFF0C121C, 0xFFFF6C82, 0xFFF2F6FF, 0xFF121628, 0xFF1F2B3A),
                new ThemeColor("Aurora Glow", 0xFF0C161C, 0xFF63FFC5, 0xFFF0F7FF, 0xFF122B2D, 0xFF153F46),
                new ThemeColor("Voltage Gold", 0xFF14100C, 0xFFFFBE5C, 0xFFFFF8E6, 0xFF2B1D14, 0xFF3B2A1F)
        };

        for (ThemeColor theme : themes) {
            Button button = new Button(this);
            button.setText(theme.getName());
            button.setTextColor(theme.getTextColor());
            button.setBackgroundColor(theme.getAccentColor());
            button.setPadding(20, 10, 20, 10);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(6, 0, 6, 0);
            button.setLayoutParams(params);
            button.setOnClickListener(v -> applyTheme(theme));
            themeContainer.addView(button);
        }
    }

    private void applyTheme(ThemeColor theme) {
        currentTheme = theme;
        View root = getWindow().getDecorView().getRootView();
        root.setBackgroundColor(theme.getBackgroundColor());
        appAdapter.updateTheme(theme);
        appGrid.invalidateViews();
    }

    public static class ThemeColor {
        private final String name;
        private final int backgroundColor;
        private final int accentColor;
        private final int textColor;
        private final int panelColor;
        private final int tileColor;

        public ThemeColor(String name, int backgroundColor, int accentColor, int textColor, int panelColor, int tileColor) {
            this.name = name;
            this.backgroundColor = backgroundColor;
            this.accentColor = accentColor;
            this.textColor = textColor;
            this.panelColor = panelColor;
            this.tileColor = tileColor;
        }

        public String getName() { return name; }
        public int getBackgroundColor() { return backgroundColor; }
        public int getAccentColor() { return accentColor; }
        public int getTextColor() { return textColor; }
        public int getPanelColor() { return panelColor; }
        public int getTileColor() { return tileColor; }
    }

    public static class AppEntry {
        private final String name;
        private final String icon;
        private final String key;

        public AppEntry(String name, String icon, String key) {
            this.name = name;
            this.icon = icon;
            this.key = key;
        }

        public String getName() { return name; }
        public String getIcon() { return icon; }
        public String getKey() { return key; }
    }
}
