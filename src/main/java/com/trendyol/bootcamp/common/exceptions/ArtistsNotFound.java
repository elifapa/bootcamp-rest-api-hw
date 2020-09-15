package com.trendyol.bootcamp.common.exceptions;

public class ArtistsNotFound  extends  RuntimeException{
    public ArtistsNotFound(String message) {
        super(message);
    }
}
