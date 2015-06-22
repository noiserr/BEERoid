package third.task.dod.beeroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import third.task.dod.beeroid.model.Beer;
import third.task.dod.beeroid.R;

/**
 * Created by noiser on 21.06.15.
 */
public class BeerAdapter extends RecyclerView.Adapter<BeerAdapter.BeerViewHolder>{

    public List<Beer> beerList = new ArrayList<>();
    Context context;

    public BeerAdapter(List<Beer> beerList, Context context) {
        this.beerList = beerList;
        this.context = context;
    }

    public void addBear(Beer beer) {
        beerList.add(beer);
    }

    @Override
    public BeerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.beer_cardview, parent, false);
        return new BeerViewHolder(viewItem);
    }

    @Override
    public void onBindViewHolder(BeerViewHolder holder, int position) {
        Beer beer = beerList.get(position);
        holder.beerName.setText(beer.getName());
        holder.beerDescription.setText(beer.getDescription());

        if (beer.getImgURI() == null) {
            Glide.with(context)
                    .load(R.drawable.beer_default)
                    .centerCrop()
                    .into(holder.beerIcon);
        }else{
            Glide.with(context)
                    .load(beer.getImgURI())
                    .centerCrop()
                    .into(holder.beerIcon);
        }

    }

    @Override
    public int getItemCount() {
        return beerList.size();
    }

    class BeerViewHolder extends RecyclerView.ViewHolder {

        @InjectView(R.id.beer_description)
        TextView beerDescription;
        @InjectView(R.id.beer_name)
        TextView beerName;
        @InjectView(R.id.beer_icon)
        ImageView beerIcon;

        public BeerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);

        }
    }

}
