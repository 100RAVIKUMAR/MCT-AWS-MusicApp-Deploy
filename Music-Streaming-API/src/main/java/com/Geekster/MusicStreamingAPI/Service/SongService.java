package com.Geekster.MusicStreamingAPI.Service;

import com.Geekster.MusicStreamingAPI.Models.Song;
import com.Geekster.MusicStreamingAPI.Repositories.ISongRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SongService {
    @Autowired
    ISongRepo songRepo;

    public Song findFirstBySongTitle(String songTitle) {
        return  songRepo.findFirstBySongTitle(songTitle);
    }

    public String addSong(Song song) {
        songRepo.save(song);
        return "Song"+" : " + song.getSongTitle() +" added successfully";
    }

    public Song getSongById(Long sId) {
        return songRepo.findById(sId).orElse(null);
    }

    public Iterable<Song> getAllSongs() {
        return songRepo.findAll();
    }

    public String UpdateSongById(Long songId, String artist) {
        Song song = getSongById(songId);
        if(song != null) {
            song.setSongArtist(artist);
            songRepo.save(song);
            return "artist of the song updated Successfully...";
        }else{
            return "song with songId= " + songId+" doesn't exist!!";
        }
    }
    public void removeSong(Song song) {
        songRepo.delete(song);
    }

    public boolean isExist(Long songId) {
        return songRepo.existsById(songId);
    }
}
