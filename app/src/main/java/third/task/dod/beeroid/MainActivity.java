package third.task.dod.beeroid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import third.task.dod.beeroid.adapter.BeerAdapter;
import third.task.dod.beeroid.presenter.IMainPresenter;
import third.task.dod.beeroid.presenter.MainPresenter;
import third.task.dod.beeroid.view.MainView;

public class MainActivity extends AppCompatActivity implements MainView {

    private static final int CAMERA_PIC_REQUEST = 1111;

    @InjectView(R.id.toolbar)
    Toolbar toolbar;
    @InjectView(R.id.fab)
    FloatingActionButton fab;
    @InjectView(R.id.beer_recyclerview)
    RecyclerView recyclerView;

    File photoFile = null;
    BeerAdapter beerAdapter;

    IMainPresenter mainPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupRecyclerview();
        mainPresenter = new MainPresenter(this, getApplicationContext(), getLayoutInflater());


    }

    private void setupRecyclerview() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainPresenter.onResume(recyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void buildDialog(View view) {
        View promptView = getLayoutInflater().inflate(R.layout.addbeer_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
        mainPresenter.buildDialog(promptView, alertDialogBuilder);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            mainPresenter.photoSuccess(Uri.fromFile(photoFile)+"");

        }
    }


    @Override
    public void showBeers(BeerAdapter adapter) {
//        beerAdapter = new BeerAdapter(beerList, getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showAlert(AlertDialog alertDialog) {
        alertDialog.show();
    }

    @Override
    public void replaceImages(RelativeLayout relativeLayout, ImageView beerImage) {
        Uri uri = Uri.fromFile(photoFile);
        Glide.with(this)
                .load(uri)
                .fitCenter()
                .into(beerImage);
//        relativeLayout.setVisibility(View.INVISIBLE);
//        beerImage.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissDialog(AlertDialog dialog) {

        dialog.dismiss();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSnackbar(Snackbar snackbar) {
        snackbar.show();
    }


    public void showCamera(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            try {
                photoFile = mainPresenter.createImageFile();
            } catch (IOException ex) {
            }
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, 1);
            }
        }

    }
}
