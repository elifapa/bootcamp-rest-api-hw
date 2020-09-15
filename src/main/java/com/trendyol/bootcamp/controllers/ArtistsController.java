package com.trendyol.bootcamp.controllers;


import com.trendyol.bootcamp.common.SearchType;
import com.trendyol.bootcamp.common.exceptions.*;
import com.trendyol.bootcamp.models.Albums;
import com.trendyol.bootcamp.models.Artist;
import com.trendyol.bootcamp.models.ArtistDetail;
import com.trendyol.bootcamp.models.Tracks;
import com.trendyol.bootcamp.services.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/artists")
public class ArtistsController {

    @Autowired
    ArtistService artistService;

    @PostMapping
    public  ResponseEntity createArtists(@RequestBody ArtistDetail artistDetail){
        URI location = null;
        try{
            location = artistService.createArtist(artistDetail);
        }catch(ArtistNotCreated e){
            return ResponseEntity.status(500).build();
        }catch (NullParameter e){
            return ResponseEntity.badRequest().body("Artist detail is null.");
        }
        return ResponseEntity.created(location).build();
    }



    @GetMapping
    public  ResponseEntity<List<Artist>> getAllArtists(){
        List<Artist> allArtist= artistService.getAllArtists();

        if (allArtist==null){
            return  ResponseEntity.status(500).build();
        }

        return ResponseEntity.ok(allArtist);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<Artist> getArtist(@PathVariable String id){
        Artist artist= null;
        try{
            artist =  artistService.getArtist(id);
        }catch(ArtistsNotFound e){
            return ResponseEntity.notFound().build();
        }catch(NullParameter e){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(artist);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity deleteArtist(@PathVariable String id){
        try{
            artistService.deleteArtist(id);
        }catch(ArtistsNotFound e){
            return ResponseEntity.notFound().build();
        }catch(NullParameter e){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public  ResponseEntity updateArtist(@PathVariable String id,
                                        @RequestBody ArtistDetail artistDetail){
         URI location =  null;

         try {
           location=  artistService.updateArtist(id,artistDetail);
         }catch (ArtistNotUpdated e){
             return  ResponseEntity.status(500).build();
         }catch (ArtistsNotFound e){
             return ResponseEntity.notFound().build();
         }catch(NullParameter e){
             return ResponseEntity.badRequest().build();
         }
         
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}/albums")
    public  ResponseEntity<List<Albums>> getAlbums(@PathVariable String id){
        List<Albums> albums= null;
        try{
             albums = artistService.getAlbums(id);
        }catch(ArtistsNotFound e){
            return ResponseEntity.notFound().build();
        }catch(NullParameter e){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(albums);
    }
    
    @GetMapping("/{id}/tracks")
    public  ResponseEntity<List<Tracks>> getTracks(@PathVariable String id){
        List<Tracks> tracks = null;
        try{
            tracks= artistService.getTracks(id);
        }catch(ArtistsNotFound e){
            return ResponseEntity.notFound().build();
        }catch(NullParameter e){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(tracks);
    }


    @RequestMapping(value="filter", method = RequestMethod.GET)
    public ResponseEntity<List<Artist>> getFilterArtist(@RequestParam("search-type") SearchType searchType,
                                                        @RequestParam String search){
        List<Artist> artists = null;
        try{
            artists=artistService.filterAllArtists(searchType,search);
        }catch(NullParameter e){
            return ResponseEntity.badRequest().build();
        }
        return  ResponseEntity.ok(artists);
    }

}
