package it.jaschke.alexandria;

import android.database.Cursor;
import android.view.View;
import android.widget.TextView;

import it.jaschke.alexandria.data.AlexandriaContract;

/**
 * Created by kaubisch on 01.03.16.
 */
public class BookDetailViewHolder extends AddBookViewHolder {
    public final TextView description;

    public BookDetailViewHolder(View rootView) {
        super(rootView);
        description = (TextView) rootView.findViewById(R.id.fullBookDesc);
    }

    @Override
    public void fillViewWithData(Cursor data) {
        super.fillViewWithData(data);
        String desc = data.getString(data.getColumnIndex(AlexandriaContract.BookEntry.DESC));
        if (desc != null) {
            description.setText(desc);
        }

    }
}
