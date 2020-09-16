package com.trendyol.bootcamp.controllers;


import com.trendyol.bootcamp.common.SearchType;
import com.trendyol.bootcamp.common.exceptions.*;
import com.trendyol.bootcamp.models.*;
import com.trendyol.bootcamp.services.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.ErrorManager;


@RestController
@RequestMapping("/artists")
public class ArtistsController {

    @Autowired
    ArtistService artistService;

    @PostMapping
    public ResponseEntity createArtists(@RequestBody ArtistDetail artistDetail) {
        URI location = null;
        try {
            location = artistService.createArtist(artistDetail);
        } catch (ArtistNotCreated e) {
            return new ResponseEntity(
                    createError("Artist not created", "URI problem"),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        } catch (NullParameter e) {
            return new ResponseEntity(
                    createError("Artist not created", "Null body"),
                    HttpStatus.BAD_REQUEST
            );
        }
        return ResponseEntity.created(location).build();
    }


    @GetMapping
    public ResponseEntity<List<Artist>> getAllArtists() {
        List<Artist> allArtist = artistService.getAllArtists();

        if (allArtist == null) {
            return new ResponseEntity(
                    createError("Artists not found", "Something went wrong"),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }

        return ResponseEntity.ok(allArtist);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artist> getArtist(@PathVariable String id) {
        Artist artist = null;
        try {
            artist = artistService.getArtist(id);
        } catch (ArtistsNotFound e) {
            return new ResponseEntity(
                    createError("Artist not found", "Nothing belonging to this id found"),
                    HttpStatus.NOT_FOUND
            );
        } catch (NullParameter e) {
            return new ResponseEntity(
                    createError("Null parameter", "Id parameter is null"),
                    HttpStatus.BAD_REQUEST
            );
        }

        return ResponseEntity.ok(artist);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteArtist(@PathVariable String id) {
        try {
            artistService.deleteArtist(id);
        } catch (ArtistsNotFound e) {
            return new ResponseEntity(
                    createError("Artist not found", "Nothing belonging to this id found"),
                    HttpStatus.NOT_FOUND
            );
        } catch (NullParameter e) {
            return new ResponseEntity(
                    createError("Null parameter", "Id parameter is null"),
                    HttpStatus.BAD_REQUEST
            );
        }

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity updateArtist(@PathVariable String id,
                                       @RequestBody ArtistDetail artistDetail) {
        URI location = null;

        try {
            location = artistService.updateArtist(id, artistDetail);
        } catch (ArtistNotUpdated e) {
            return new ResponseEntity(
                    createError("Artist not created", "URI problem"),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        } catch (ArtistsNotFound e) {
            return new ResponseEntity(
                    createError("Artist not found", "Nothing belonging to this id found"),
                    HttpStatus.NOT_FOUND
            );
        } catch (NullParameter e) {
            return new ResponseEntity(
                    createError("Null parameter", "Id  or artistDetail parameter are null"),
                    HttpStatus.BAD_REQUEST
            );
        }

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}/albums")
    public ResponseEntity<List<Albums>> getAlbums(@PathVariable String id) {
        List<Albums> albums = null;
        try {
            albums = artistService.getAlbums(id);
        } catch (ArtistsNotFound e) {
            return new ResponseEntity(
                    createError("Artist not found", "Nothing belonging to this id found"),
                    HttpStatus.NOT_FOUND
            );
        } catch (NullParameter e) {
            return new ResponseEntity(
                    createError("Null parameter", "Id parameter is null"),
                    HttpStatus.BAD_REQUEST
            );
        }
        return ResponseEntity.ok(albums);
    }

    @GetMapping("/{id}/tracks")
    public ResponseEntity<List<Tracks>> getTracks(@PathVariable String id) {
        List<Tracks> tracks = null;
        try {
            tracks = artistService.getTracks(id);
        } catch (ArtistsNotFound e) {
            return new ResponseEntity(
                    createError("Artist not found", "Nothing belonging to this id found"),
                    HttpStatus.NOT_FOUND
            );
        } catch (NullParameter e) {
            return new ResponseEntity(
                    createError("Null parameter", "Id parameter is null"),
                    HttpStatus.BAD_REQUEST
            );
        }
        return ResponseEntity.ok(tracks);
    }


    @RequestMapping(value = "filter", method = RequestMethod.GET)
    public ResponseEntity<List<Artist>> getFilterArtist(@RequestParam("search-type") SearchType searchType,
                                                        @RequestParam String search) {
        List<Artist> artists = null;
        try {
            artists = artistService.filterAllArtists(searchType, search);
        } catch (NullParameter e) {
            return new ResponseEntity(
                    createError("Null parameter", ""),
                    HttpStatus.BAD_REQUEST
            );
        }
        return ResponseEntity.ok(artists);
    }

    public List<ErrorModel> createError(String message, String messageDetail) {
        List<ErrorModel> errorModels = new ArrayList<>();
        ErrorModel errorModel = new ErrorModel(message,messageDetail);
        errorModels.add(errorModel);
        return errorModels;
    }

}
