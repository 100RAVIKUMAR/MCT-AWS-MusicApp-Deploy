package com.Geekster.MusicStreamingAPI.Models;

import com.Geekster.MusicStreamingAPI.Models.Enums.Gender;
import com.Geekster.MusicStreamingAPI.Models.Enums.Genre;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class,scope=Song.class,property="songId")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long songId;

    @Column(unique = true)
    private String songTitle;

    private String songArtist;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    private String songDuration;
}
