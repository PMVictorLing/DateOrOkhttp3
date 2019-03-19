package com.example.mayn.myfisrtapp.bean;

/**
 * @author lwc
 * 2019/3/18
 * 描述：$des$
 */
public class PresentBean {

    /**
     * additionalAmt : 0
     * amt : 10
     * bitmap : true
     * ccy : HKD
     * errorCode : 0
     * errorMessage : no error
     * extendParam : null
     * gwErrorCode : null
     * gwErrorMessage : null
     * gwRefCode : null
     * lang : zh-HK
     * qrcode :
     * rrn : S01012019030510243531
     * storeCode : S01
     * tillId : 01
     * timeout : 60000
     * txDateTime : 2019-03-05T10:24:35+0800
     * type : WXPAY
     */

    private int additionalAmt;
    private int amt;
    private boolean bitmap;
    private String ccy;
    private String errorCode;
    private String errorMessage;
    private Object extendParam;
    private Object gwErrorCode;
    private Object gwErrorMessage;
    private Object gwRefCode;
    private String lang;
    private String qrcode;
    private String rrn;
    private String storeCode;
    private String tillId;
    private int timeout;
    private String txDateTime;
    private String type;

    public int getAdditionalAmt() {
        return additionalAmt;
    }

    public void setAdditionalAmt(int additionalAmt) {
        this.additionalAmt = additionalAmt;
    }

    public int getAmt() {
        return amt;
    }

    public void setAmt(int amt) {
        this.amt = amt;
    }

    public boolean isBitmap() {
        return bitmap;
    }

    public void setBitmap(boolean bitmap) {
        this.bitmap = bitmap;
    }

    public String getCcy() {
        return ccy;
    }

    public void setCcy(String ccy) {
        this.ccy = ccy;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Object getExtendParam() {
        return extendParam;
    }

    public void setExtendParam(Object extendParam) {
        this.extendParam = extendParam;
    }

    public Object getGwErrorCode() {
        return gwErrorCode;
    }

    public void setGwErrorCode(Object gwErrorCode) {
        this.gwErrorCode = gwErrorCode;
    }

    public Object getGwErrorMessage() {
        return gwErrorMessage;
    }

    public void setGwErrorMessage(Object gwErrorMessage) {
        this.gwErrorMessage = gwErrorMessage;
    }

    public Object getGwRefCode() {
        return gwRefCode;
    }

    public void setGwRefCode(Object gwRefCode) {
        this.gwRefCode = gwRefCode;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getRrn() {
        return rrn;
    }

    public void setRrn(String rrn) {
        this.rrn = rrn;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }

    public String getTillId() {
        return tillId;
    }

    public void setTillId(String tillId) {
        this.tillId = tillId;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getTxDateTime() {
        return txDateTime;
    }

    public void setTxDateTime(String txDateTime) {
        this.txDateTime = txDateTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
