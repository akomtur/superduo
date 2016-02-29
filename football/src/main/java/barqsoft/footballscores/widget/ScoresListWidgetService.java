package barqsoft.footballscores.widget;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.List;

import barqsoft.footballscores.DatabaseContract;
import barqsoft.footballscores.R;

/**
 * Created by kaubisch on 29.02.16.
 */
public class ScoresListWidgetService extends RemoteViewsService {
    public static final String INTENT_LIST_DATA = "listData";
    public static final String INTENT_SEARCH_DATE = "searchDate";
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        String date = intent.getStringExtra(INTENT_SEARCH_DATE);
        return new ListProvider(getApplicationContext(), getMatchesForDate(getContentResolver(), date));
    }

    private List<Match> getMatchesForDate(final ContentResolver contentResolver, final String date) {
        List<Match> matches = new ArrayList<>();
        Cursor cursor = contentResolver.query(DatabaseContract.scores_table.buildScoreWithDate(), null, null, new String[] {date}, null);
        try {
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                do {
                    String homeTeam = cursor.getString(cursor.getColumnIndex(DatabaseContract.scores_table.HOME_COL));
                    String guestTeam = cursor.getString(cursor.getColumnIndex(DatabaseContract.scores_table.AWAY_COL));
                    int homeScore = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DatabaseContract.scores_table.HOME_GOALS_COL)));
                    int guestScore = Integer.parseInt(cursor.getString(cursor.getColumnIndex(DatabaseContract.scores_table.AWAY_GOALS_COL)));
                    matches.add(new Match(homeTeam, guestTeam, homeScore, guestScore));
                } while (cursor.moveToNext());
            }
        } finally {
            if(cursor != null) {
                cursor.close();
            }
        }

        return matches;
    }

    class ListProvider implements RemoteViewsFactory {

        private Context context;
        private List<Match> matches;

        public ListProvider(final Context context, List<Match> matches) {
            this.context = context;
            this.matches = matches;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return matches.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            Match match = matches.get(position);
            RemoteViews listItem = new RemoteViews(context.getPackageName(), R.layout.widget_scores_list_item);
            listItem.setTextViewText(R.id.home_name, match.homeTeam);
            listItem.setTextViewText(R.id.guest_name, match.guestTeam);
            listItem.setTextViewText(R.id.score_home, match.homeScore == -1 ? "" : String.valueOf(match.homeScore));
            listItem.setTextViewText(R.id.score_guest, match.guestScore == -1 ? "" : String.valueOf(match.guestScore));
            return listItem;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
