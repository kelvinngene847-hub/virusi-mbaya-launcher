package com.virusimbaya.launcher;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.graphics.Color;
import android.util.TypedValue;

import java.util.List;

public class AppAdapter extends BaseAdapter {

    private Context context;
    private List<MainActivity.AppEntry> apps;
    private MainActivity.ThemeColor theme;

    public AppAdapter(Context context, List<MainActivity.AppEntry> apps, MainActivity.ThemeColor theme) {
        this.context = context;
        this.apps = apps;
        this.theme = theme;
    }

    @Override
    public int getCount() {
        return apps.size();
    }

    @Override
    public Object getItem(int position) {
        return apps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MainActivity.AppEntry app = apps.get(position);

        LinearLayout tile = new LinearLayout(context);
        tile.setOrientation(LinearLayout.VERTICAL);
        tile.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200));
        tile.setPadding(8, 8, 8, 8);
        tile.setBackgroundColor(theme.getBackgroundColor());
        tile.setForeground(context.getDrawable(android.R.drawable.list_selector_background));
        tile.setGravity(android.view.Gravity.CENTER);

        TextView icon = new TextView(context);
        icon.setText(app.getIcon());
        icon.setTextSize(TypedValue.COMPLEX_UNIT_SP, 36);
        icon.setTextColor(theme.getAccentColor());
        tile.addView(icon);

        TextView label = new TextView(context);
        label.setText(app.getName());
        label.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        label.setTextColor(theme.getTextColor());
        label.setTypeface(android.graphics.Typeface.DEFAULT_BOLD);
        label.setGravity(android.view.Gravity.CENTER);
        tile.addView(label);

        return tile;
    }

    public void updateTheme(MainActivity.ThemeColor newTheme) {
        this.theme = newTheme;
        notifyDataSetChanged();
    }
}
