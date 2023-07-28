package com.Geekster.MusicStreamingAPI.Service;

import com.Geekster.MusicStreamingAPI.Models.AuthenticationToken;
import com.Geekster.MusicStreamingAPI.Models.Dtos.SignInInput;
import com.Geekster.MusicStreamingAPI.Models.Dtos.SignUpOutput;
import com.Geekster.MusicStreamingAPI.Models.PlayList;
import com.Geekster.MusicStreamingAPI.Models.Song;
import com.Geekster.MusicStreamingAPI.Models.User;
import com.Geekster.MusicStreamingAPI.Repositories.IUserRepo;
import com.Geekster.MusicStreamingAPI.Service.EmailUtitlity.EmailHandler;
import com.Geekster.MusicStreamingAPI.Service.HasingUtility.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {
    @Autowired
    IUserRepo userRepo;
    @Autowired
    AuthenticationTokenService authenticationTokenService;
    @Autowired
    PlayListService playListService;
    public SignUpOutput userSignUp(User user) {
        String signUpOutputMessage = null;
        Boolean signUpOutputStatus = true;
        String newEmail = user.getUserEmail();
        System.out.println(user);
        // check user email is null
        if(newEmail == null){
            signUpOutputStatus = false;
            signUpOutputMessage = "User email should not be null";
            return new SignUpOutput(signUpOutputStatus,signUpOutputMessage);
        }
        // check user already exists or not ??
        User existingUser = userRepo.findFirstByUserEmail(newEmail);
        if(existingUser != null){
            signUpOutputStatus = false;
            signUpOutputMessage = "User email already exists !!! try with signIn";
            return new SignUpOutput(signUpOutputStatus,signUpOutputMessage);

        }
        // encrypt the password by hashing
        try {
            //encrypt the actual password
            String encryptedPassword = PasswordEncrypter.encryptPassword(user.getUserPassword());
            // set the encrypted password and save the encrypted password;
            user.setUserPassword(encryptedPassword);
            userRepo.save(user);
            signUpOutputMessage = "User registered successfully!!!";
            return new SignUpOutput(signUpOutputStatus,signUpOutputMessage);
        }catch (Exception e){
            signUpOutputStatus = false;
            signUpOutputMessage = "error occurred during signUp!!!";
            return new SignUpOutput(signUpOutputStatus,signUpOutputMessage);
        }
    }

    public String userSignIn(SignInInput signInInput) {
        String signInInputMessage = null;
        String userEmail = signInInput.getEmail();
        // check the email is null
        if(userEmail == null){
            signInInputMessage = "User email should not be null";
            return signInInputMessage;
        }
        //check user exist or not ??
        User existingUser = userRepo.findFirstByUserEmail(userEmail);
        if(existingUser == null){
            signInInputMessage = "No such user exist!!! try with signUp";
            return signInInputMessage;
        }
        // if exist...hash the password && check the password matches with original password
        try{
            String encryptedPassword = PasswordEncrypter.encryptPassword(signInInput.getPassword());
            if(encryptedPassword.equals(existingUser.getUserPassword())){
                AuthenticationToken authenticationToken = new AuthenticationToken(existingUser);
                authenticationTokenService.save(authenticationToken);
                EmailHandler.sendEmail(userEmail,"Java testing mail",authenticationToken.getTokenValue());
                signInInputMessage = "Token sent to your registered mail";
            }else{
                signInInputMessage = "Invalid credentials";
            }
            return signInInputMessage;
        }catch (Exception e){
            return "Internal error occurred during signIn";
        }
    }

    public String userSignOut(String email) {
        User user = userRepo.findFirstByUserEmail(email);
        AuthenticationToken authenticationToken = authenticationTokenService.findFirstByUser(user);
        authenticationTokenService.remove(authenticationToken);
        return "User signed out successfully!!";
    }

    public String addPlayList(PlayList playList,String email) {
        PlayList existingPlayList = playListService.findPlayList(playList.getPlayListName());
        if(existingPlayList == null){
            playList.setPlayListOwner(userRepo.findFirstByUserEmail(email));
            return playListService.addPlayList(playList);
        }else{
            return "PlayList with name : "+playList.getPlayListName()+" already exists!! try again";
        }
    }

    public List<PlayList> getAllPlayLists() {
        return playListService.getAllPlayLists();
    }
    private boolean checkuser(User playListOwner, String email) {
        String ownerEmail = playListOwner.getUserEmail();
        return ownerEmail.equals(email);
    }
    public String addSongsToPlayList(PlayList playList, String email) {
        Long passedId = playList.getPlayListId();
        PlayList existingPlayList = playListService.findPlayListById(passedId);
        if(existingPlayList != null){
            if(checkuser(existingPlayList.getPlayListOwner(),email)){
                List<Song> existingSongs = existingPlayList.getSongs();
                LocalDateTime creationTime = existingPlayList.getPlayListCreationTimeStamp();
                playList.setPlayListOwner(userRepo.findFirstByUserEmail(email));
                return playListService.addSongsToPlayList(playList,existingSongs,creationTime);
            }else{
                return "no such playlist exist!!!";
            }
        }else{
            return "No such PlayList : "+existingPlayList.getPlayListId()+" exists!! try adding songs to existing PlayList!!";
        }
    }


    public String updatePlayList(PlayList playList, String email) {
        Long passedId = playList.getPlayListId();
        PlayList existingPlayList = playListService.findPlayListById(passedId);
        if(existingPlayList != null){
            if(checkuser(existingPlayList.getPlayListOwner(),email)){
                List<Song> existingSongs = existingPlayList.getSongs();
                return playListService.updatePlayList(playList,existingSongs);
            }else{
                return "Playlist doesnt exist!!";
            }
        }else{
            return "No such PlayList : "+existingPlayList.getPlayListId()+" exists!! try adding songs to existing PlayList!!";
        }
    }
    private boolean authorizeDelete(User user, String email) {
        String userEmail = user.getUserEmail();
        return userEmail.equals(email);
    }
    public String deletePlayListById(Long playListId,String email) {
        PlayList playList = playListService.findPlayListById(playListId);
        if(playList != null) {
            User user = playList.getPlayListOwner();
            if (authorizeDelete(user, email)) {
                return playListService.remove(playList);
            } else {
                return "UnAuthorised user Activity";
            }
        }else{
            return "No such PlayList Exists!!!!";
        }
    }


    public List<Song> getAllSongsByPlayListId(Long playListId) {
        PlayList playList = playListService.findPlayListById(playListId);
        if(playList != null){
            return playListService.getAllSongsByPlayListId(playList);
        }else{
            return null;
        }
    }

    public List<Song> getAllSongsByArtist(Long playListId ,String artist) {
        PlayList playList = playListService.getPlayList(playListId);
        return playListService.getAllSongsByArtist(playList,artist);
    }
}
