package com.trendyol.bootcamp.models;

import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class ArtistDetail {

    String name;
    String image;
    List<String> genres;
    String type;
    String description;
    SocialMedia socialMedia;
    String country;


}
