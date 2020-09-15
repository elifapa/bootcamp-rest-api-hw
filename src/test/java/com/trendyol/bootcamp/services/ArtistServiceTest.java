package com.trendyol.bootcamp.services;

import com.trendyol.bootcamp.common.Mock;
import com.trendyol.bootcamp.common.SearchType;
import com.trendyol.bootcamp.common.exceptions.ArtistsNotFound;
import com.trendyol.bootcamp.common.exceptions.NullParameter;
import com.trendyol.bootcamp.models.Albums;
import com.trendyol.bootcamp.models.Artist;
import com.trendyol.bootcamp.models.ArtistDetail;

import com.trendyol.bootcamp.models.Tracks;
import org.junit.Test;

import java.net.URI;
import java.util.List;

import static org.junit.Assert.*;

public class ArtistServiceTest {


    ArtistService artistService = new ArtistService();

    @Test(expected = NullParameter.class)
    public  void it_should_catch_null_parameter_exception_for_created_artist(){
        ArtistDetail artistDetail=null;
        artistService.createArtist(artistDetail);
    }

    @Test
    public  void it_should_create_location_for_created_artist(){
        ArtistDetail artistDetail= ArtistDetail.builder()
                .name("TEST")
                .country("TR")
                .image("http://image")
                .type("solo")
                .build();

        URI location = artistService.createArtist(artistDetail);
        boolean flag = location.toString().startsWith("/artist/");
        assertTrue(flag);
    }


    @Test(expected = NullParameter.class)
    public  void it_should_catch_null_parameter_exception_for_get_artist(){
        String id =null;
        artistService.getArtist(id);
    }

    @Test(expected = ArtistsNotFound.class)
    public  void it_should_catch_artist_not_found_exception_for_get_artist(){
        String id ="1234";
        artistService.getArtist(id);
    }

    @Test
    public  void it_should_return_artist_not_null_for_get_artist(){
        Mock.addMockData();
        String id ="testId";
        Artist artist= artistService.getArtist(id);
        assertNotNull(artist);
    }

    @Test(expected = NullParameter.class)
    public  void it_should_catch_null_parameter_exception_for_delete_artist(){
        String id =null;
        artistService.deleteArtist(id);
    }

    @Test(expected = ArtistsNotFound.class)
    public  void it_should_catch_artist_not_found_exception_for_delete_artist(){
        String id ="1234";
        artistService.deleteArtist(id);
    }

    @Test
    public  void it_should_nothing_for_delete_artist(){
        Mock.addMockData();
        String id ="testId";
        artistService.deleteArtist(id);
    }


    @Test(expected = NullParameter.class)
    public  void it_should_catch_null_parameter_exception_for_update_artist(){
        String id ="1234";
        ArtistDetail artistDetail=null;
        artistService.updateArtist(id,artistDetail);
    }

    @Test(expected = ArtistsNotFound.class)
    public  void it_should_catch_artist_not_found_exception_for_update_artist(){
        String id ="1234";
        ArtistDetail artistDetail = ArtistDetail.builder().name("TEST").build();
        artistService.updateArtist(id,artistDetail);
    }

    @Test
    public  void it_should_return_location_for_update_artist(){
        //Given
        Mock.addMockData();
        String id ="testId";
        ArtistDetail artistDetail= ArtistDetail.builder()
                .name("TEST")
                .country("TR")
                .image("http://image")
                .type("solo")
                .build();

        //when
        URI location = artistService.updateArtist(id,artistDetail);
        boolean flag = location.toString().startsWith("/artist/");

        //then
        assertTrue(flag);
    }

    @Test(expected = NullParameter.class)
    public  void it_should_catch_null_parameter_exception_for_get_albums(){
        String id =null;
        artistService.getAlbums(id);
    }

    @Test(expected = ArtistsNotFound.class)
    public  void it_should_catch_artist_not_found_exception_for_get_albums(){
        String id ="1234";
        artistService.getAlbums(id);
    }

    @Test
    public  void it_should_return_album_list_for_get_albums(){
        Mock.addMockData();
        String id ="testId";
        List<Albums> albums= artistService.getAlbums(id);
        assertNotNull(albums);
    }

    @Test(expected = NullParameter.class)
    public  void it_should_catch_null_parameter_exception_for_get_tracks(){
        String id =null;
        artistService.getTracks(id);
    }

    @Test(expected = ArtistsNotFound.class)
    public  void it_should_catch_artist_not_found_exception_for_get_tracks(){
        String id ="1234";
        artistService.getTracks(id);
    }

    @Test
    public  void it_should_return_track_list_for_get_tracks(){
        Mock.addMockData();
        String id ="testId";
        List<Tracks> tracks= artistService.getTracks(id);
        assertNotNull(tracks);
    }

    @Test(expected = NullParameter.class)
    public  void it_should_catch_null_parameter_exception_for_filter_artist(){
        artistService.filterAllArtists(SearchType.country,null);
    }

    @Test
    public  void it_should_return_filter_name_for_filter_all_artists(){
        Mock.addMockData();
        List<Artist> artist = artistService.filterAllArtists(SearchType.name,"TES");
        assertNotNull(artist);
    }
    @Test
    public  void it_should_return_filter_type_for_filter_all_artists(){
        Mock.addMockData();
        List<Artist> artist = artistService.filterAllArtists(SearchType.type,"solo");
        assertNotNull(artist);
    }
    @Test
    public  void it_should_return_filter_country_for_filter_all_artists(){
        Mock.addMockData();
        List<Artist> artist = artistService.filterAllArtists(SearchType.country,"R");
        assertNotNull(artist);
    }
    @Test
    public  void it_should_return_filter_genre_for_filter_all_artists(){
        Mock.addMockData();
        List<Artist> artist = artistService.filterAllArtists(SearchType.genre,"rap");
        assertNotNull(artist);
    }



}
