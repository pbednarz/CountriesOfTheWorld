package pl.solaris.countries.model;

/**
 * Created by solaris on 2014-04-27.
 */
public class CountryItem {
    private String a2;
    private String a3;
    private String continent;
    private String continent_name;
    private String fifa;
    private String id;
    private String ioc;
    private String itu;
    private String name;
    private long num;

    public CountryItem() {
    }

    public CountryItem(String a2, String a3, String continent, String continent_name, String fifa, String id, String ioc, String itu, String name, long num) {
        this.a2 = a2;
        this.a3 = a3;
        this.continent = continent;
        this.continent_name = continent_name;
        this.fifa = fifa;
        this.id = id;
        this.ioc = ioc;
        this.itu = itu;
        this.name = name;
        this.num = num;
    }

    public String getA2() {
        return a2;
    }

    public void setA2(String a2) {
        this.a2 = a2;
    }

    public String getA3() {
        return a3;
    }

    public void setA3(String a3) {
        this.a3 = a3;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getContinent_name() {
        return continent_name;
    }

    public void setContinent_name(String continent_name) {
        this.continent_name = continent_name;
    }

    public String getFifa() {
        return fifa;
    }

    public void setFifa(String fifa) {
        this.fifa = fifa;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIoc() {
        return ioc;
    }

    public void setIoc(String ioc) {
        this.ioc = ioc;
    }

    public String getItu() {
        return itu;
    }

    public void setItu(String itu) {
        this.itu = itu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getNum() {
        return num;
    }

    public void setNum(long num) {
        this.num = num;
    }
}
