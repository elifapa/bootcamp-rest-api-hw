package com.trendyol.bootcamp.services;


import com.trendyol.bootcamp.common.Mock;
import com.trendyol.bootcamp.common.SearchType;
import com.trendyol.bootcamp.common.exceptions.*;
import com.trendyol.bootcamp.models.*;
import com.trendyol.bootcamp.respository.ArtistRepository;
import org.springframework.stereotype.Service;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ArtistService implements ArtistRepository {

    @Override
    public URI createArtist(ArtistDetail artistDetail) {
        if (artistDetail==null){
            throw new NullParameter("Artist detail is missing.");
        }

        String id = UUID.randomUUID().toString();
        URI location = null;

        Artist artist = Artist.builder()
                .id(id)
                .artistDetail(artistDetail)
                .albums(new ArrayList<>())
                .tracks(new ArrayList<>())
                .verified(false)
                .createdDate(new Date().getTime())
                .build();

        Mock.artistList.add(artist);

        try {
            location = new URI(String.format("/artist/%s", id));
        } catch (URISyntaxException e) {
            throw new ArtistNotCreated("User not created.");
        }

        return location;
    }


    @Override
    public List<Artist> getAllArtists() {
        return Mock.artistList;
    }

    @Override
    public Artist getArtist(String id) {
        if(id==null) throw new NullParameter("Id not found.");
        Artist artist= Mock.artistList.stream()
                .filter(element -> id.equals(element.getId()))
                .findFirst().orElse(null);

        if(artist == null){
            throw new ArtistsNotFound("Artist not found");
        }
        return artist;
    }

    @Override
    public void deleteArtist(String id) {
        if(id==null) throw new NullParameter("Id not found.");
        boolean flag = Mock.artistList.removeIf(
                artist -> artist.getId().equals(id)
        );
        if(!flag){
            throw new ArtistsNotFound("Artist not found");
        }
    }


    @Override
    public URI updateArtist(String id, ArtistDetail artistDetail) {
        if(id==null) throw new NullParameter("Id not found.");
        if(artistDetail==null) throw new NullParameter("Artist detail not found.");
        boolean flag=false;
        URI location = null;

        for(Artist artist: Mock.artistList){
            if (artist.getId().equals(id)){
                flag=true;
                ArtistDetail temp = artist.getArtistDetail();
                artist.getArtistDetail().setName((artistDetail.getName()==null) ? temp.getName(): artistDetail.getName());
                artist.getArtistDetail().setCountry((artistDetail.getCountry()==null) ? temp.getCountry(): artistDetail.getCountry());
                artist.getArtistDetail().setGenres((artistDetail.getGenres()==null) ? temp.getGenres(): artistDetail.getGenres());
                artist.getArtistDetail().setImage((artistDetail.getImage()==null) ? temp.getImage(): artistDetail.getImage());
                artist.getArtistDetail().setType((artistDetail.getType()==null) ? temp.getType(): artistDetail.getType());
                artist.getArtistDetail().setDescription((artistDetail.getDescription()==null) ? temp.getDescription(): artistDetail.getDescription());
                artist.getArtistDetail().setSocialMedia((artistDetail.getSocialMedia()==null) ? temp.getSocialMedia(): artistDetail.getSocialMedia());
                artist.getArtistDetail().setCountry((artistDetail.getCountry()==null) ? temp.getCountry(): artistDetail.getCountry());
                break;
            }
        }
        if (!flag) throw  new ArtistsNotFound("Artist not found");


        try {
            location = new URI(String.format("/artist/%s", id));
        } catch (URISyntaxException e) {
            throw new ArtistNotUpdated("User not updated..");
        }
        return location;
    }

    @Override
    public List<Albums> getAlbums(String id) {
        if(id==null) throw new NullParameter("Id not found.");
        Artist artist = Mock.artistList.stream()
                .filter(element -> id.equals(element.getId()))
                .findFirst().orElse(null);
        if (artist == null) throw new ArtistsNotFound("Artist not found.");

        return artist.getAlbums();
    }

    @Override
    public List<Tracks> getTracks(String id) {
        if(id==null) throw new NullParameter("Id not found.");
        Artist artist = Mock.artistList.stream()
                .filter(element -> id.equals(element.getId()))
                .findFirst().orElse(null);

        if (artist == null) throw new ArtistsNotFound("Artist not found.");

        return artist.getTracks();
    }

    @Override
    public List<Artist> filterAllArtists(SearchType searchType, String search) {
         if (search==null) throw new NullParameter("Search parameter is null.");
         if (searchType == null) throw new NullParameter("Search parameter is null.");

        switch (searchType){
            case name:
                return Mock.artistList.stream().filter(
                        p -> p.getArtistDetail().getName().toLowerCase().contains(search.toLowerCase())
                ).collect(Collectors.toList());
            case genre:
                return Mock.artistList.stream().filter(
                        p -> p.getArtistDetail().getGenres().contains(search)
                ).collect(Collectors.toList());
            case country:
                return Mock.artistList.stream().filter(
                        p -> p.getArtistDetail().getCountry().toLowerCase().contains(search.toLowerCase())
                ).collect(Collectors.toList());
            case type:
                return Mock.artistList.stream().filter(
                        p -> p.getArtistDetail().getType().toLowerCase().contains(search.toLowerCase())
                ).collect(Collectors.toList());
        }

        return null;
    }


}
