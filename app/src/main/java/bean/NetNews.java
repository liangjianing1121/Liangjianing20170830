package bean;

/**
 * Created by DELL on 2017/9/7.
 */

public class NetNews {

    private  String result;
    private String type;

    public NetNews(String result, String type) {
        this.result = result;
        this.type = type;
    }

    public NetNews() {
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "NetNews{" +
                "result='" + result + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
