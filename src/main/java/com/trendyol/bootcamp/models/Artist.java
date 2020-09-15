package com.trendyol.bootcamp.models;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;


@Data
@Builder
public class Artist {

    String id;
    ArtistDetail artistDetail;
    List<Albums> albums;
    List<Tracks> tracks;
    int followers;
    Long createdDate;
    boolean verified;
    int totalStream;


}
