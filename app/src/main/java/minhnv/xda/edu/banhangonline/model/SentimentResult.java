package minhnv.xda.edu.banhangonline.model;

/**
 * Created by MinhNguyen on 12/27/2017.
 */

public class SentimentResult {
    public String thic;
    public String get;
    public String giandu;

    public SentimentResult(String thic, String get, String giandu) {
        this.thic = thic;
        this.get = get;
        this.giandu = giandu;
    }

    public String getThic() {
        return thic;
    }

    public void setThic(String thic) {
        this.thic = thic;
    }

    public String getGet() {
        return get;
    }

    public void setGet(String get) {
        this.get = get;
    }

    public String getGiandu() {
        return giandu;
    }

    public void setGiandu(String giandu) {
        this.giandu = giandu;
    }

    @Override
    public String toString() {
        return "SentimentResult{" +
                "thic='" + thic + '\'' +
                ", get='" + get + '\'' +
                ", giandu='" + giandu + '\'' +
                '}';
    }
}
