package barqsoft.footballscores.widget;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Date;

import barqsoft.footballscores.MainActivity;
import barqsoft.footballscores.R;

/**
 * Created by kaubisch on 25.02.16.
 */
public class UpdateWidgetService extends IntentService {

    public static final String WIDGET_ID_KEY = "widgetId";

    public UpdateWidgetService() {
        super("updateWidgetService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        AppWidgetManager widgetManager = AppWidgetManager.getInstance(this);
        int widgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        if(widgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
            updateWidget(widgetManager, widgetId);
        }

    }

    private void updateWidget(AppWidgetManager widgetManager, int widgetId) {
        RemoteViews remoteViews = new RemoteViews(
                getPackageName()
                , R.layout.widget_today
        );
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        remoteViews.setOnClickPendingIntent(R.id.widget, pendingIntent);
        fillListView(remoteViews);
        widgetManager.updateAppWidget(widgetId, remoteViews);
    }

    private String getFormattedDateForToday() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    private void fillListView(RemoteViews remoteView) {
        Intent listService = new Intent(this, ScoresListWidgetService.class);
        listService.putExtra(ScoresListWidgetService.INTENT_SEARCH_DATE, getFormattedDateForToday());
        remoteView.setRemoteAdapter(R.id.widget_scores_list, listService);
        remoteView.setEmptyView(R.id.widget_scores_list, R.id.widget_empty_list);
    }
}
