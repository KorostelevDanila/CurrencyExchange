package models;

public class CurrencyModel {
    private int ID;
    private String code;
    private String fullName;
    private String sign;

    public CurrencyModel(int ID, String code, String fullName, String sign) {
        this.ID = ID;
        this.code = code;
        this.fullName = fullName;
        this.sign = sign;
    }

    public CurrencyModel(String code, String fullName, String sign) {
        this.code = code;
        this.fullName = fullName;
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "ID=" + ID +
                ", code='" + code + '\'' +
                ", fullName='" + fullName + '\'' +
                ", sign='" + sign + '\'' +
                '}';
    }

    public int getID() {
        return ID;
    }

    public String getCode() {
        return code;
    }

    public String getFullName() {
        return fullName;
    }

    public String getSign() {
        return sign;
    }
}
