package com.virusimbaya.launcher;

import java.util.List;

public class AppData {
    public static List<AppEntry> getApplications() {
        return List.of(
                new AppEntry("Messages", "✉"),
                new AppEntry("Camera", "📷"),
                new AppEntry("Music", "♫"),
                new AppEntry("Settings", "⚙"),
                new AppEntry("Gallery", "🖼"),
                new AppEntry("Contacts", "☎"),
                new AppEntry("Weather", "☀"),
                new AppEntry("Maps", "📍"),
                new AppEntry("Banking", "💳"),
                new AppEntry("Notes", "📝"),
                new AppEntry("Calendar", "📅"),
                new AppEntry("Files", "📁")
        );
    }

    public static class AppEntry {
        private final String name;
        private final String icon;

        public AppEntry(String name, String icon) {
            this.name = name;
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public String getIcon() {
            return icon;
        }
    }
}


