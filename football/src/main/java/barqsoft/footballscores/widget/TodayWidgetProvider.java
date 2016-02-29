package barqsoft.footballscores.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;

/**
 * Created by kaubisch on 25.02.16.
 */
public class TodayWidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int widgetId : appWidgetIds) {
            Intent updateIntent = new Intent(context, UpdateWidgetService.class);
            updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
            context.startService(updateIntent);
        }
    }
}
