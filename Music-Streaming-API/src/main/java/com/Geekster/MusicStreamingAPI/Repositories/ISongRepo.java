package com.Geekster.MusicStreamingAPI.Repositories;

import com.Geekster.MusicStreamingAPI.Models.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISongRepo extends JpaRepository<Song,Long> {
    Song findFirstBySongTitle(String songTitle);
}
