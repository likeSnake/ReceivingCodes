package net.code.free.fast.receivingcodes.bean;

public class SmsBean {

    /**
     * number : 7944528766
     * content : test  123124drmOWjeCYeL
     * smsFrom :  18339950042
     * timeStamp : 1690797619000
     */

    private String number;
    private String content;
    private String smsFrom;
    private long timeStamp;

    private Boolean isChecked = false;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSmsFrom() {
        return smsFrom;
    }

    public void setSmsFrom(String smsFrom) {
        this.smsFrom = smsFrom;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
}
