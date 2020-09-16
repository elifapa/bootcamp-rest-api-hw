package com.trendyol.bootcamp.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorModel {
    String message;
    String messageDetail;

    public ErrorModel(String message, String messageDetail) {
        this.message = message;
        this.messageDetail = messageDetail;
    }

}
