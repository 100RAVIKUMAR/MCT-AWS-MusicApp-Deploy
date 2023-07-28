package com.Geekster.MusicStreamingAPI.Controller;

import com.Geekster.MusicStreamingAPI.Models.Dtos.SignInInput;
import com.Geekster.MusicStreamingAPI.Models.Dtos.SignUpOutput;
import com.Geekster.MusicStreamingAPI.Models.PlayList;
import com.Geekster.MusicStreamingAPI.Models.Song;
import com.Geekster.MusicStreamingAPI.Models.User;
import com.Geekster.MusicStreamingAPI.Service.AuthenticationTokenService;
import com.Geekster.MusicStreamingAPI.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/usercontroller")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationTokenService authenticationTokenService;


    @PostMapping("/SignUp")
    public SignUpOutput userSignUp(@RequestBody User user){
        return userService.userSignUp(user);
    }

    @PostMapping("/SignIn")
    public String userSignIn(@RequestBody SignInInput signInInput){
        return userService.userSignIn(signInInput);
    }

    @DeleteMapping("/SignOut")
    public String userSignOut(@RequestParam String email, @RequestParam String tokenValue){
        if(authenticationTokenService.authenticateUser(email,tokenValue)){
            return userService.userSignOut(email);
        }else{
            return "Invalid User Credentials!!!";
        }
    }


    @PostMapping("/addplayList")
    public String addPlayList(@RequestBody PlayList playList, @RequestParam String email, @RequestParam String tokenValue){
        if(authenticationTokenService.authenticateUser(email,tokenValue)){
            return userService.addPlayList(playList,email);
        }else{
            return "Invalid User Credentials!!!";
        }
    }


    @GetMapping("/fetchplayLists")
    public List<PlayList> getAllPlayLists(@RequestParam String email, @RequestParam String tokenValue){
        List<PlayList> playListList = null;
        if(authenticationTokenService.authenticateUser(email,tokenValue)){
           return userService.getAllPlayLists();
        }
        throw new IllegalStateException("Invalid User Credentials!!!");
    }

    @PostMapping("/addsongstoplaylist")
    public String addSongsToPlayList(@RequestBody PlayList playList,@RequestParam String email, @RequestParam String tokenValue){
        if(authenticationTokenService.authenticateUser(email,tokenValue)){
            return  userService.addSongsToPlayList(playList,email);
        }else{
            return "Invalid User Credentials!!!";
        }
    }

    @GetMapping("fetchsongs/fromplayListby/{playListId}")

    public List<Song> getAllSongsByPlayListId(@PathVariable Long playListId, @RequestParam String email, @RequestParam String tokenValue){
        List<Song> songs = null;
        if(authenticationTokenService.authenticateUser(email,tokenValue)){
           return userService.getAllSongsByPlayListId(playListId);
        }
        throw new IllegalStateException("Invalid User Credentials!!!");
    }

    @GetMapping("fetchsongs/fromplayListby/{playListId}/{artist}")
    public List<Song> getAllSongsByArtist(@PathVariable Long playListId,@PathVariable String artist, @RequestParam String email, @RequestParam String tokenValue){
        List<Song> songs = null;
        if(authenticationTokenService.authenticateUser(email,tokenValue)){
           return userService.getAllSongsByArtist(playListId,artist);
        }
       throw new IllegalStateException("Invalid User Credentials!!!");
    }

    @PutMapping("/Update")
    public String updatePlayList(@RequestBody PlayList playList,@RequestParam String email, @RequestParam String tokenValue){
        if(authenticationTokenService.authenticateUser(email,tokenValue)){
            return  userService.updatePlayList(playList,email);
        }else{
            return "Invalid User Credentials!!!";
        }
    }

    @DeleteMapping("deleteplayList/{playListId}")
    public String deletePlayListById(@PathVariable Long playListId,@RequestParam String email,@RequestParam String tokenValue){
        if(authenticationTokenService.authenticateUser(email,tokenValue)){
            return userService.deletePlayListById(playListId,email);
        }else{
            return "Invalid User Credentials!!!";
        }
    }
}
