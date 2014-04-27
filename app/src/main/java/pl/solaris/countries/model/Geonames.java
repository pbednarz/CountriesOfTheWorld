package pl.solaris.countries.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by solaris on 2014-04-27.
 */
public class Geonames implements Serializable {
    private List<Country> geonames;

    public Geonames(List<Country> geonames) {
        this.geonames = geonames;
    }

    public Geonames() {
    }

    public List<Country> getGeonames() {
        return geonames;
    }

    public void setGeonames(List<Country> geonames) {
        this.geonames = geonames;
    }
}
