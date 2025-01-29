package models;

public class Currency {
    private int ID;
    private String code;
    private String fullName;
    private String sign;

    public Currency(int ID, String code, String fullName, String sign) {
        this.ID = ID;
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
}
