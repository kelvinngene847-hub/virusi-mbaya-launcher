package com.virusimbaya.launcher;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;
import android.view.LayoutInflater;
import android.view.View;
import android.graphics.Color;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView clockLabel;
    private GridView appGrid;
    private LinearLayout themeContainer;
    private ThemeColor currentTheme;
    private AppAdapter appAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        clockLabel = findViewById(R.id.clockLabel);
        appGrid = findViewById(R.id.appGrid);
        themeContainer = findViewById(R.id.themeContainer);

        currentTheme = new ThemeColor("Neon Pulse",
                Color.parseColor("#0C121C"),
                Color.parseColor("#FF6C82"),
                Color.parseColor("#F2F6FF"));

        updateClock();
        setupAppGrid();
        setupThemeButtons();
    }

    private void updateClock() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String time = timeFormat.format(calendar.getTime());
        clockLabel.setText(time + " • 24°C");
        clockLabel.postDelayed(this::updateClock, 1000);
    }

    private void setupAppGrid() {
        List<AppEntry> apps = new ArrayList<>();
        apps.add(new AppEntry("Messages", "✉"));
        apps.add(new AppEntry("Camera", "📷"));
        apps.add(new AppEntry("Music", "♫"));
        apps.add(new AppEntry("Settings", "⚙"));
        apps.add(new AppEntry("Gallery", "🖼"));
        apps.add(new AppEntry("Contacts", "☎"));
        apps.add(new AppEntry("Weather", "☀"));
        apps.add(new AppEntry("Maps", "📍"));
        apps.add(new AppEntry("Banking", "💳"));

        appAdapter = new AppAdapter(this, apps, currentTheme);
        appGrid.setAdapter(appAdapter);
        appGrid.setOnItemClickListener((parent, view, position, id) ->
                Toast.makeText(MainActivity.this,
                        ((AppEntry) appAdapter.getItem(position)).getName() + " launched",
                        Toast.LENGTH_SHORT).show());
    }

    private void setupThemeButtons() {
        ThemeColor[] themes = {
                new ThemeColor("Neon Pulse", Color.parseColor("#0C121C"), Color.parseColor("#FF6C82"), Color.parseColor("#F2F6FF")),
                new ThemeColor("Aurora Glow", Color.parseColor("#0C161C"), Color.parseColor("#63FFC5"), Color.parseColor("#F0F7FF")),
                new ThemeColor("Voltage Gold", Color.parseColor("#14100C"), Color.parseColor("#FFBE5C"), Color.parseColor("#FFF8E6"))
        };

        for (ThemeColor theme : themes) {
            Button button = new Button(this);
            button.setText(theme.getName());
            button.setTextColor(theme.getTextColor());
            button.setBackgroundColor(theme.getAccentColor());
            button.setPadding(20, 10, 20, 10);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(4, 0, 4, 0);
            button.setLayoutParams(params);
            button.setOnClickListener(v -> applyTheme(theme));
            themeContainer.addView(button);
        }
    }

    private void applyTheme(ThemeColor theme) {
        currentTheme = theme;
        View rootView = getWindow().getDecorView().getRootView();
        rootView.setBackgroundColor(theme.getBackgroundColor());
        appAdapter.updateTheme(theme);
        appGrid.invalidateViews();
    }

    public static class ThemeColor {
        private String name;
        private int backgroundColor;
        private int accentColor;
        private int textColor;

        public ThemeColor(String name, int backgroundColor, int accentColor, int textColor) {
            this.name = name;
            this.backgroundColor = backgroundColor;
            this.accentColor = accentColor;
            this.textColor = textColor;
        }

        public String getName() { return name; }
        public int getBackgroundColor() { return backgroundColor; }
        public int getAccentColor() { return accentColor; }
        public int getTextColor() { return textColor; }
    }

    public static class AppEntry {
        private String name;
        private String icon;

        public AppEntry(String name, String icon) {
            this.name = name;
            this.icon = icon;
        }

        public String getName() { return name; }
        public String getIcon() { return icon; }
    }
}
