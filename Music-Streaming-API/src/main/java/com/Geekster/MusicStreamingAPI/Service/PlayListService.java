package com.Geekster.MusicStreamingAPI.Service;

import com.Geekster.MusicStreamingAPI.Models.PlayList;
import com.Geekster.MusicStreamingAPI.Models.Song;
import com.Geekster.MusicStreamingAPI.Repositories.IPlayListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlayListService {
    @Autowired
    IPlayListRepo playListRepo;
    @Autowired
    SongService songService;
    public String addPlayList(PlayList playList) {
        playList.setPlayListCreationTimeStamp(LocalDateTime.now());
        playListRepo.save(playList);
        return "PlayList : "+playList.getPlayListName()+" created successfully!! Add your favourite songs to playList!!";
    }

    public PlayList findPlayList(String playListName) {
        return playListRepo.findFirstByPlayListName(playListName);
    }

    public List<PlayList> getAllPlayLists() {
        return playListRepo.findAll();
    }

    public PlayList getPlayList(Long pId) {
        return playListRepo.findById(pId).orElse(null);
    }

    public String addSongsToPlayList(PlayList playList,List<Song> existingSongs,LocalDateTime creationTime) {

        List<Song> newSongs = playList.getSongs();
        existingSongs.addAll(newSongs);
        playList.setSongs(existingSongs);
        playList.setPlayListCreationTimeStamp(creationTime);
        playListRepo.save(playList);
        return "Songs added to PlayList : " + playList.getPlayListName();
    }
    public PlayList findPlayListById(Long passedId) {
        return playListRepo.findById(passedId).orElse(null);
    }

    public String updatePlayList(PlayList playList,List<Song> existingSongs) {
        playList.setPlayListName(playList.getPlayListName());
        playList.setPlayListCreationTimeStamp(LocalDateTime.now());
        playList.setSongs(existingSongs);
        playListRepo.save(playList);
        return "playList  updated";
    }

    public String remove(PlayList playList) {
        playListRepo.delete(playList);
        return "PlayList deleted successfully";
    }

    public List<Song> getAllSongsByPlayListId(PlayList playList) {
        List<Song> songs = playList.getSongs();
        return songs;
    }

    public List<Song> getAllSongsByArtist(PlayList playList,String artist) {
        List<Song> songs = playList.getSongs();
        List<Song> artistSongs = new ArrayList<>();

        for(Song mySong : songs){
            if(mySong.getSongArtist().equals(artist))
                artistSongs.add(mySong);
        }
        return artistSongs;
    }
}
