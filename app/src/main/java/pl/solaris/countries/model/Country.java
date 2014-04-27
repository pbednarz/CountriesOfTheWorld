package pl.solaris.countries.model;

import java.io.Serializable;

/**
 * Created by solaris on 2014-04-27.
 */
public class Country implements Serializable {

    private String areaInSqKm;
    private String capital;
    private String continent;
    private String continentName;
    private String countryCode;
    private String countryName;
    private String currencyCode;
    private double east;
    private String fipsCode;
    private long geonameId;
    private String isoAlpha3;
    private String isoNumeric;
    private String languages;
    private double north;
    private String population;
    private double south;
    private double west;

    public Country() {
    }

    public Country(String areaInSqKm, String capital, String continent, String continentName, String countryCode, String countryName, String currencyCode, long east, String fipsCode, long geonameId, String isoAlpha3, String isoNumeric, String languages, long north, String population, long south, long west) {
        this.areaInSqKm = areaInSqKm;
        this.capital = capital;
        this.continent = continent;
        this.continentName = continentName;
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.currencyCode = currencyCode;
        this.east = east;
        this.fipsCode = fipsCode;
        this.geonameId = geonameId;
        this.isoAlpha3 = isoAlpha3;
        this.isoNumeric = isoNumeric;
        this.languages = languages;
        this.north = north;
        this.population = population;
        this.south = south;
        this.west = west;
    }

    public String getAreaInSqKm() {
        return areaInSqKm;
    }

    public void setAreaInSqKm(String areaInSqKm) {
        this.areaInSqKm = areaInSqKm;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getContinentName() {
        return continentName;
    }

    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public double getEast() {
        return east;
    }

    public void setEast(long east) {
        this.east = east;
    }

    public String getFipsCode() {
        return fipsCode;
    }

    public void setFipsCode(String fipsCode) {
        this.fipsCode = fipsCode;
    }

    public long getGeonameId() {
        return geonameId;
    }

    public void setGeonameId(long geonameId) {
        this.geonameId = geonameId;
    }

    public String getIsoAlpha3() {
        return isoAlpha3;
    }

    public void setIsoAlpha3(String isoAlpha3) {
        this.isoAlpha3 = isoAlpha3;
    }

    public String getIsoNumeric() {
        return isoNumeric;
    }

    public void setIsoNumeric(String isoNumeric) {
        this.isoNumeric = isoNumeric;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public double getNorth() {
        return north;
    }

    public void setNorth(long north) {
        this.north = north;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public double getSouth() {
        return south;
    }

    public void setSouth(long south) {
        this.south = south;
    }

    public double getWest() {
        return west;
    }

    public void setWest(double west) {
        this.west = west;
    }
}
