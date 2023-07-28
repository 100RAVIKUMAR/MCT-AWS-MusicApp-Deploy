package com.Geekster.MusicStreamingAPI.Service;

import com.Geekster.MusicStreamingAPI.Models.Admin;
import com.Geekster.MusicStreamingAPI.Models.AuthenticationToken;
import com.Geekster.MusicStreamingAPI.Models.Dtos.AdminSignInInput;
import com.Geekster.MusicStreamingAPI.Models.Dtos.AdminSignUpOutput;
import com.Geekster.MusicStreamingAPI.Models.Song;
import com.Geekster.MusicStreamingAPI.Repositories.IAdminRepo;
import com.Geekster.MusicStreamingAPI.Service.EmailUtitlity.EmailHandler;
import com.Geekster.MusicStreamingAPI.Service.HasingUtility.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    IAdminRepo adminRepo;
    @Autowired
    AuthenticationTokenService authenticationTokenService;
    @Autowired
    SongService songService;
    public AdminSignUpOutput adminSignUp(Admin admin) {
        boolean signUpStatus = true;
        String signUpStatusMessage = null;
        String newEmail = admin.getAdminEmail();
        if(newEmail == null){
            signUpStatus = false;
            signUpStatusMessage = "The SoundFusion admin email should not be null";
            return new AdminSignUpOutput(signUpStatus,signUpStatusMessage);
        }

        Admin existingAdmin = adminRepo.findFirstByAdminEmail(newEmail);
        if(existingAdmin != null){
            signUpStatus = false;
            signUpStatusMessage = "The Instagram User already exists!! Try with signIn";
            return new AdminSignUpOutput(signUpStatus,signUpStatusMessage);
        }

        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(admin.getAdminPassword());

            admin.setAdminPassword(encryptedPassword);
            adminRepo.save(admin);
            return new AdminSignUpOutput(signUpStatus,"User registered Successfully!!");
        }catch(Exception e){
            signUpStatus = false;
            signUpStatusMessage = "Internal error occurred during sign up";
            return new AdminSignUpOutput(signUpStatus,signUpStatusMessage);
        }
    }

    public String adminSignIn(AdminSignInInput adminSignInInput) {
        String signInInputMessage = null;
        String adminEmail = adminSignInInput.getEmail();
        if(adminEmail == null){
            signInInputMessage = "User email should not be null";
            return signInInputMessage;
        }
        Admin existingAdmin = adminRepo.findFirstByAdminEmail(adminEmail);
        if(existingAdmin == null){
            signInInputMessage = "No such user exist!!! try with signUp";
            return signInInputMessage;
        }
        try{
            String encryptedPassword = PasswordEncrypter.encryptPassword(adminSignInInput.getPassword());
            if(encryptedPassword.equals(existingAdmin.getAdminPassword())){
                AuthenticationToken authenticationToken = new AuthenticationToken(existingAdmin);
                authenticationTokenService.save(authenticationToken);
                EmailHandler.sendEmail(adminEmail,"Java testing mail",authenticationToken.getTokenValue());
                signInInputMessage = "Token sent to your registered mail";
            }else{
                signInInputMessage = "Invalid credentials";
            }
            return signInInputMessage;
        }catch (Exception e){
            return "Internal error occurred during signIn";
        }
    }

    public String adminSignOut(String email) {
        Admin admin = adminRepo.findFirstByAdminEmail(email);
        AuthenticationToken authenticationToken = authenticationTokenService.findFirstByAdmin(admin);
        authenticationTokenService.removeAdminToken(authenticationToken);
        return "User signed out successfully!!";
    }

    public String addSong(Song song) {

        Song existingSong = songService.findFirstBySongTitle(song.getSongTitle());
        if(existingSong == null){
            return songService.addSong(song);
        }else{
            return "Song with Title" + " : " +song.getSongTitle() +" already exists!!! try with different title!!";
        }
    }

    public Song getSongById(Long sId) {
        Song song = songService.getSongById(sId);
        return song;
    }

    public Iterable<Song> getAllSongs() {
        return songService.getAllSongs();
    }

    public String UpdateSongById(Long songId, String artist) {
        return songService.UpdateSongById(songId,artist);
    }

    public String deleteSongById(Long sId) {
        Song existingSong = songService.getSongById(sId);
        if(existingSong != null){
            songService.removeSong(existingSong);
            return "Song : "+sId+" deleted successfully!!!";
        }else{
            return "No such song : "+sId+" exists";
        }
    }
}
