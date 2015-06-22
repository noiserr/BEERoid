package third.task.dod.beeroid.view;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import third.task.dod.beeroid.adapter.BeerAdapter;

/**
 * Created by noiser on 21.06.15.
 */
public interface MainView {

    void showBeers(BeerAdapter beerList);

    void showAlert(AlertDialog alertDialog);

    void replaceImages(RelativeLayout relativeLayout, ImageView imageView);

    void dismissDialog(AlertDialog dialog);

    void showMessage(String message);

    void showSnackbar(Snackbar snackbar);

}
