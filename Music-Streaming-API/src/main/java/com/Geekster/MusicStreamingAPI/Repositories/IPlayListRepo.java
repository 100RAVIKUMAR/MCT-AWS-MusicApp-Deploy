package com.Geekster.MusicStreamingAPI.Repositories;

import com.Geekster.MusicStreamingAPI.Models.PlayList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPlayListRepo extends JpaRepository<PlayList,Long> {
    PlayList findFirstByPlayListName(String playListName);
}
