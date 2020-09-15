package com.trendyol.bootcamp.common;


import com.trendyol.bootcamp.models.Albums;
import com.trendyol.bootcamp.models.Artist;
import com.trendyol.bootcamp.models.ArtistDetail;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
public class Mock {

    public static List<Artist> artistList = new ArrayList<>();

    public static void addMockData(){
        List<String> genrelist= new ArrayList();
        genrelist.add("rap");
        artistList.add(
                Artist.builder()
                        .id("testId")
                        .verified(true)
                        .tracks(new ArrayList<>())
                        .albums(new ArrayList<>())
                        .artistDetail(
                                ArtistDetail.builder()
                                        .type("solo")
                                        .image("resim yok")
                                        .name("TEST")
                                        .genres(genrelist)
                                        .country("TR")
                                        .build()
                        )
                        .build()
        );
    }

}
