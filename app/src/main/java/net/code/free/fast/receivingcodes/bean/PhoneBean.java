package net.code.free.fast.receivingcodes.bean;

import java.io.Serializable;

public class PhoneBean implements Serializable {
    private String number;
    private String countryName;
    private Boolean isChecked;

    public PhoneBean(String number, String countryName) {
        this.number = number;
        this.countryName = countryName;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
