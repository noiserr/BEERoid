package third.task.dod.beeroid.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by noiser on 21.06.15.
 */
@Table(name = "beer")
public class Beer extends Model {

    @Column(name = "beer_name")
    private String name;
    @Column(name = "beer_desription")
    private String description;
    @Column(name = "beer_image_uri")
    private String imgURI;

    public Beer() {

    }

    public Beer(Beer beer) {
        this.name = beer.getName();
        this.description = beer.getDescription();
        this.imgURI = beer.imgURI;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgURI() {
        return imgURI;
    }

    public void setImgURI(String imgURI) {
        this.imgURI = imgURI;
    }
}
