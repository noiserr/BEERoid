package third.task.dod.beeroid.presenter;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.io.File;
import java.io.IOException;


public interface IMainPresenter {

    void onResume(RecyclerView recyclerView);

    void buildDialog(View view, AlertDialog.Builder builder);

    void photoSuccess(String uri);

    File createImageFile() throws IOException;


}
