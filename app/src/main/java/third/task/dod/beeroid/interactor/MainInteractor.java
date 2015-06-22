package third.task.dod.beeroid.interactor;

import com.activeandroid.query.Select;

import java.util.List;

import third.task.dod.beeroid.model.Beer;

/**
 * Created by noiser on 21.06.15.
 */
public class MainInteractor implements IMainInteractor {

    @Override
    public void getAllBeers(OnBeerActionListener listener) {
        List<Beer> list = new Select().from(Beer.class).execute();
        listener.onFindBeerSuccess(list);
    }

    @Override
    public void saveBeer(OnBeerActionListener listener, Beer beer) {
        beer.save();
        listener.onSaveBeerSuccess(beer);
    }

    @Override
    public void undoDeleteBeer(OnBeerActionListener listener, Beer beer, int position) {
        beer.save();
        listener.onUndoBeerSuccess(beer, position);
    }

    @Override
    public void deleteBeer(OnBeerActionListener listener, Beer beer, int position) {
        beer.delete();
        listener.onDeleteBeerSuccess(position);

    }


}
