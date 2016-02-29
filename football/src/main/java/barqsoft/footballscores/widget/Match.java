package barqsoft.footballscores.widget;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kaubisch on 29.02.16.
 */
public class Match {
    public final String homeTeam;
    public final String guestTeam;
    public final int homeScore;
    public final int guestScore;

    public Match(String homeTeam, String guestTeam, int homeScore, int guestScore) {
        this.homeTeam = homeTeam;
        this.guestTeam = guestTeam;
        this.homeScore = homeScore;
        this.guestScore = guestScore;
    }
}
