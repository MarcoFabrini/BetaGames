package com.betagames.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class MessageID {

    private String lang;
    private String code;

    public MessageID() {
        super();
    }

    public MessageID(String lang, String code) {
        super();
        this.lang = lang;
        this.code = code;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
