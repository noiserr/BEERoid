package third.task.dod.beeroid.interactor;

import java.util.List;

import third.task.dod.beeroid.model.Beer;

/**
 * Created by noiser on 21.06.15.
 */
public interface OnBeerActionListener {

    void onFindBeerSuccess(List<Beer> beerList);

    void onSaveBeerSuccess(Beer beer);

    void onDeleteBeerSuccess(int position);

    void onUndoBeerSuccess(Beer beer, int position);
}
