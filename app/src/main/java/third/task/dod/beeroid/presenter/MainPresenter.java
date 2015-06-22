package third.task.dod.beeroid.presenter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import third.task.dod.beeroid.model.Beer;
import third.task.dod.beeroid.view.MainView;
import third.task.dod.beeroid.R;
import third.task.dod.beeroid.interactor.SwipeableRecyclerViewTouchListener;
import third.task.dod.beeroid.adapter.BeerAdapter;
import third.task.dod.beeroid.interactor.MainInteractor;
import third.task.dod.beeroid.interactor.OnBeerActionListener;

public class MainPresenter implements IMainPresenter, OnBeerActionListener {

    private MainView mainView;
    private MainInteractor mainInteractor;
    LayoutInflater layoutInflater;
    OnBeerActionListener actionListener;
    Context context;
    BeerAdapter beerAdapter;
    RelativeLayout relativeLayout;
    ImageView beerImage;
    String uri;
    RecyclerView recyclerView;
    SwipeableRecyclerViewTouchListener swipeTouchListener;

    public MainPresenter(MainView mainView, Context context, LayoutInflater layoutInflater) {
        this.mainView = mainView;
        this.context = context;
        this.layoutInflater = layoutInflater;
        actionListener = this;
        mainInteractor = new MainInteractor();
    }

    @Override
    public void onResume(RecyclerView recyclerView) {
        mainInteractor.getAllBeers(this);
        this.recyclerView = recyclerView;
        setupSwipeableTouchListener();

    }

    @Override
    public void buildDialog(View view, AlertDialog.Builder alertDialogBuilder) {
        beerImage = (ImageView) view.findViewById(R.id.beer_photo);
        final EditText description = (EditText) view.findViewById(R.id.beer_description_edit_text);
        final EditText name = (EditText) view.findViewById(R.id.beer_name_edit_text);
        alertDialogBuilder.setView(view);

        alertDialogBuilder.setPositiveButton(context.getString(R.string.dialog_save_button), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

            }
        });
        alertDialogBuilder.setNegativeButton(context.getString(R.string.dialog_cancel_button), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });

        Glide.with(context)
                .load(R.drawable.beer_default)
                .centerCrop()
                .into(beerImage);
        final AlertDialog dialog = alertDialogBuilder.create();
        mainView.showAlert(dialog);

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.length() != 0 || description.length() != 0) {
                    Beer beer = new Beer();
                    beer.setName(name.getText() + "");
                    beer.setDescription(description.getText() + "");
                    beer.setImgURI(uri);
                    mainInteractor.saveBeer(actionListener, beer);
                    mainView.dismissDialog(dialog);
                    uri = null;
                } else {
                    mainView.showMessage(context.getString(R.string.empty_information_dialog));
                }
            }
        });


    }


    @Override
    public void photoSuccess(String filepath) {
        uri = filepath;
        mainView.replaceImages(relativeLayout, beerImage);
    }

    @Override
    public File createImageFile() throws IOException {
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File imagesFolder = new File(storageDir, "Beers");
        imagesFolder.mkdir();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        imagesFolder.mkdirs();
        File image = new File(imagesFolder, "beer_" + timeStamp + ".png");
        return image;

    }

    @Override
    public void onFindBeerSuccess(List<Beer> beerList) {
        beerAdapter = new BeerAdapter(beerList, context);
        mainView.showBeers(beerAdapter);
    }

    @Override
    public void onSaveBeerSuccess(Beer beer) {
        beerAdapter.addBear(beer);
        beerAdapter.notifyItemInserted(beerAdapter.beerList.size()-1);
    }

    @Override
    public void onDeleteBeerSuccess(int position) {
        beerAdapter.beerList.remove(position);
        beerAdapter.notifyDataSetChanged();
//        beer.delete();
    }

    @Override
    public void onUndoBeerSuccess(Beer copy, int position) {
        beerAdapter.beerList.add(position, copy);
        beerAdapter.notifyItemInserted(position);
    }

    private void setupSwipeableTouchListener() {

        swipeTouchListener = new SwipeableRecyclerViewTouchListener(recyclerView,
                new SwipeableRecyclerViewTouchListener.SwipeListener() {
                    @Override
                    public boolean canSwipe(int position) {
                        return true;
                    }

                    @Override
                    public void onDismissedBySwipeLeft(RecyclerView recyclerView, int[] reverseSortedPositions) {
                        for (final int position : reverseSortedPositions) {
                            removeBeer(position);
                        }
                    }

                    @Override
                    public void onDismissedBySwipeRight(RecyclerView recyclerView, int[] reverseSortedPositions) {
                        for (final int position : reverseSortedPositions) {
                            removeBeer(position);
                        }
                    }
                });

        recyclerView.addOnItemTouchListener(swipeTouchListener);

    }

    private void removeBeer(final int position) {
        final Beer beer = beerAdapter.beerList.get(position);
        final Beer copy = new Beer(beer);
        String information = beer.getName();
        mainInteractor.deleteBeer(actionListener, beer, position);
        Snackbar snackbar = Snackbar.make(recyclerView, information + context.getString(R.string.snackbar_removed), Snackbar.LENGTH_LONG)
                .setAction(R.string.snack_bar_undo, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mainInteractor.undoDeleteBeer(actionListener, copy, position);
                    }
                });
        mainView.showSnackbar(snackbar);
    }


}
