package com.trendyol.bootcamp.respository;

import com.trendyol.bootcamp.common.SearchType;
import com.trendyol.bootcamp.models.Albums;
import com.trendyol.bootcamp.models.Artist;
import com.trendyol.bootcamp.models.ArtistDetail;
import com.trendyol.bootcamp.models.Tracks;

import java.net.URI;
import java.util.List;

public interface ArtistRepository {
    URI createArtist(ArtistDetail artistDetail);
    List<Artist> getAllArtists();
    Artist getArtist(String id);
    void deleteArtist(String id);
    URI updateArtist(String id, ArtistDetail artistDetail);
    List<Albums> getAlbums(String id);
    List<Tracks> getTracks(String id);
    List<Artist> filterAllArtists(SearchType searchType, String search);
}
