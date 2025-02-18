package com.betagames.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "error_messages")
public class ServiceMessages {

    @EmbeddedId
    private MessageID msgID;

    private String Message;

    public MessageID getMsgID() {
        return msgID;
    }

    public void setMsgID(MessageID msgID) {
        this.msgID = msgID;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

}
