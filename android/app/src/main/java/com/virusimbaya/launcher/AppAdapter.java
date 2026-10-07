package com.virusimbaya.launcher;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AppAdapter extends BaseAdapter {

    private final Context context;
    private final List<MainActivity.AppEntry> originalApps;
    private final List<MainActivity.AppEntry> filteredApps;
    private MainActivity.ThemeColor theme;

    public AppAdapter(Context context, List<MainActivity.AppEntry> apps, MainActivity.ThemeColor theme) {
        this.context = context;
        this.originalApps = new ArrayList<>(apps);
        this.filteredApps = new ArrayList<>(apps);
        this.theme = theme;
    }

    public void updateTheme(MainActivity.ThemeColor newTheme) {
        this.theme = newTheme;
        notifyDataSetChanged();
    }

    public void filter(String query) {
        filteredApps.clear();
        if (query == null || query.trim().isEmpty()) {
            filteredApps.addAll(originalApps);
        } else {
            String lowered = query.toLowerCase();
            for (MainActivity.AppEntry app : originalApps) {
                if (app.getName().toLowerCase().contains(lowered) || app.getKey().toLowerCase().contains(lowered)) {
                    filteredApps.add(app);
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return filteredApps.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredApps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MainActivity.AppEntry app = filteredApps.get(position);

        LinearLayout tile = new LinearLayout(context);
        tile.setOrientation(LinearLayout.VERTICAL);
        tile.setGravity(Gravity.CENTER);
        tile.setPadding(18, 18, 18, 18);
        tile.setBackgroundColor(theme.getTileColor());

        int width = (int) (parent.getWidth() / 3.3);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, 180);
        tile.setLayoutParams(params);

        TextView icon = new TextView(context);
        icon.setText(app.getIcon());
        icon.setTextSize(32f);
        icon.setTextColor(theme.getAccentColor());
        icon.setGravity(Gravity.CENTER);
        icon.setTypeface(Typeface.DEFAULT_BOLD);

        TextView label = new TextView(context);
        label.setText(app.getName());
        label.setTextSize(15f);
        label.setTextColor(theme.getTextColor());
        label.setGravity(Gravity.CENTER);
        label.setTypeface(Typeface.DEFAULT_BOLD);

        tile.addView(icon);
        tile.addView(label);
        return tile;
    }
}
