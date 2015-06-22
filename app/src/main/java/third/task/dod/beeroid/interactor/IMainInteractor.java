package third.task.dod.beeroid.interactor;

import third.task.dod.beeroid.model.Beer;

public interface IMainInteractor {

    void getAllBeers(OnBeerActionListener listener);

    void saveBeer(OnBeerActionListener listener, Beer beer);

    void undoDeleteBeer(OnBeerActionListener listener, Beer beer, int position);

    void deleteBeer(OnBeerActionListener listener, Beer beer, int position);

}
