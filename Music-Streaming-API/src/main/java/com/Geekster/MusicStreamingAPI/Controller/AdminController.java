package com.Geekster.MusicStreamingAPI.Controller;

import com.Geekster.MusicStreamingAPI.Models.Admin;
import com.Geekster.MusicStreamingAPI.Models.Dtos.AdminSignInInput;
import com.Geekster.MusicStreamingAPI.Models.Dtos.AdminSignUpOutput;
import com.Geekster.MusicStreamingAPI.Models.Song;
import com.Geekster.MusicStreamingAPI.Service.AdminService;
import com.Geekster.MusicStreamingAPI.Service.AuthenticationTokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/admincontroller")
public class AdminController {
    @Autowired
    AdminService adminService;

    @Autowired
    AuthenticationTokenService authenticationTokenService;


    @PostMapping("/SignUp")
    public AdminSignUpOutput adminSignUp(@RequestBody Admin admin){
        return adminService.adminSignUp(admin);
    }

    @PostMapping("/SignIn")
    public String adminSignIn(@RequestBody AdminSignInInput adminSignInInput){
        return adminService.adminSignIn(adminSignInInput);
    }

    @DeleteMapping("/SignOut")
    public String adminSignOut(@RequestParam @Valid String email, @RequestParam String tokenValue){
        if(authenticationTokenService.authenticateAdmin(email,tokenValue)){
            return adminService.adminSignOut(email);
        }else{
            return "Invalid User Credentials!!!";
        }
    }


    @PostMapping("/addsong")
    public String addSong(@RequestBody Song song, @RequestParam String email, @RequestParam String tokenValue){
        if(authenticationTokenService.authenticateAdmin(email,tokenValue)){
            return adminService.addSong(song);
        }else{
            return "Invalid User Credentials!!!";
        }
    }


    @GetMapping("/getsong/{songId}")
    public Object getSongById(@PathVariable Long songId,@RequestParam String email,@RequestParam String tokenValue){
        if(authenticationTokenService.authenticateAdmin(email,tokenValue)){
            return adminService.getSongById(songId);
        }else{
            return "Invalid User Credentials!!!";
        }
    }


    @PutMapping("/updatesong/artistby/{songId}/{artist}")
    public String UpdateSongById(@PathVariable Long songId,@PathVariable String artist,@RequestParam String email,@RequestParam String tokenValue){
        if(authenticationTokenService.authenticateAdmin(email,tokenValue)){
            return adminService.UpdateSongById(songId,artist);
        }else{
            return "Invalid User Credentials!!!";
        }
    }
    @DeleteMapping("/deletesong/{songId}")
    public String deleteSongById(@PathVariable Long songId,@RequestParam String email,@RequestParam String tokenValue){
        if(authenticationTokenService.authenticateAdmin(email,tokenValue)){
            return adminService.deleteSongById(songId);
        }else{
            return "Invalid User Credentials!!!";
        }
    }
}
