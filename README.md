# MCT-AWS-MusicApp-Deploy

## Language And Framework used:
#### --> Java and SpringBoot Respectively

## Models
#### --> User
#### --> Admin
#### --> AuthenticationToken
#### --> Song
#### --> Playlist
#### --> Enums
      -->Gender
      -->Genre

## Controllers
#### --> Admin Controller
       -->AdminController EndPoints 
          @PostMapping("/SignUp")- for registering an user.
          @PostMapping("/SignIn")- for signing in registered user.
          @DeleteMapping("/SignOut")- for signingout registered user.
          @PostMapping("/addsong")-for adding a song intodatabase.
          @GetMapping("/getsong/{songId}")-getting a song by its id.
          @PutMapping("/updatesong/artistby/{songId}/{artist}")-updating artist of the song using songId.
          @DeleteMapping("/deletesong/{songId}")-deleting a song using songId.
          ***<only regitered admin users can perform above operations>***

#### --> User Controller
        -->UserController Endpoints.
           @PostMapping("/SignUp")-for registering an user.
           @PostMapping("/SignIn")-for signing in registered user.
           @DeleteMapping("/SignOut")-for signing out registered user.
           @PostMapping("/addplayList")-adding playlist to user.
           @GetMapping("/fetchplayLists")-getting all the playlist.
           @PostMapping("/addsongstoplaylist")-adding songs to playlist.
           @GetMapping("fetchsongs/fromplayListby/{playListId}")-getting list of songs from playlist using playlistId.
           @PutMapping("/Update")-for updating a playlist.
           @DeleteMapping("deleteplayList/{playListId}")-for deleting a playlist with playlistId.
           ***<only regitered users can perform above operations>***


## Services
#### --> Admin Service
#### --> User Service
#### --> AuthenticationToken Service
#### --> PlayList Service
#### --> Song Service

## Repositeries
#### --> IAdmin Repo
#### --> IUser Repo
#### --> IAuthentication Repo
#### --> IPlayList Repo
#### --> ISong Repo

## DataBase used:
#### --> MySql

## Mapping used:
#### --> ManyToOne
#### --> OneToMany
#### --> ManyToMany

## Deployment Board
#### -->AWS
      -->Created two instances one for project to run and other for database.Connected two instances using applicationproperties and IP of the respective instances.

## Project Summary
##### -->The project is a basic web application built using Java and the Spring framework. It allows users to sign up, sign in, and manage their profile information. Admin can also create and songs. The application uses authentication tokens to secure user data and ensure that only authenticated users can access certain features. The API endpoints include user signup, signin, and update details, post creation and retrieval, and authentication token creation.
