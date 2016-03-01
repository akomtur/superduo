package it.jaschke.alexandria;

import android.database.Cursor;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import it.jaschke.alexandria.data.AlexandriaContract;
import it.jaschke.alexandria.services.DownloadImage;

/**
 * Created by kaubisch on 01.03.16.
 */
public class AddBookViewHolder {
    private final TextView titleView;
    private final TextView subTitleView;
    private final TextView categoriesView;
    private final TextView authorsView;
    private final ImageView imageView;

    public AddBookViewHolder(final View rootView) {
        titleView = (TextView) rootView.findViewById(R.id.bookTitle);
        subTitleView = (TextView) rootView.findViewById(R.id.bookSubTitle);
        categoriesView = (TextView) rootView.findViewById(R.id.categories);
        authorsView = (TextView) rootView.findViewById(R.id.authors);
        imageView = (ImageView) rootView.findViewById(R.id.bookCover);
    }

    public void fillViewWithData(final Cursor data) {
        fillTitle(data);
        fillSubtitle(data);
        fillCategories(data);
        fillAuthors(data);
        fillImage(data);
    }

    private void fillCategories(Cursor data) {
        String categories = data.getString(data.getColumnIndex(AlexandriaContract.CategoryEntry.CATEGORY));
        if (categories != null) {
            categoriesView.setText(categories);
        }
    }

    private void fillImage(Cursor data) {
        String imgUrl = data.getString(data.getColumnIndex(AlexandriaContract.BookEntry.IMAGE_URL));
        if(Patterns.WEB_URL.matcher(imgUrl).matches()){
            new DownloadImage(imageView).execute(imgUrl);
            imageView.setVisibility(View.VISIBLE);
        }
    }

    private void fillTitle(Cursor data) {
        String bookTitle = data.getString(data.getColumnIndex(AlexandriaContract.BookEntry.TITLE));
        if (bookTitle != null) {
            titleView.setText(bookTitle);
        }
    }

    private void fillSubtitle(Cursor data) {
        String bookSubTitle = data.getString(data.getColumnIndex(AlexandriaContract.BookEntry.SUBTITLE));
        if(bookSubTitle != null) {
            subTitleView.setText(bookSubTitle);
        }
    }

    private void fillAuthors(Cursor data) {
        String authors = data.getString(data.getColumnIndex(AlexandriaContract.AuthorEntry.AUTHOR));
        if(authors != null) {
            String[] authorsArr = authors.split(",");
            authorsView.setLines(authorsArr.length);
            authorsView.setText(authors.replace(",","\n"));
        } else {
            authorsView.setText("");
        }
    }
}
