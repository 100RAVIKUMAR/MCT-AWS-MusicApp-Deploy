package com.Geekster.MusicStreamingAPI.Repositories;

import com.Geekster.MusicStreamingAPI.Models.Admin;
import com.Geekster.MusicStreamingAPI.Models.AuthenticationToken;
import com.Geekster.MusicStreamingAPI.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAuthenticationRepo extends JpaRepository<AuthenticationToken,Long> {
    AuthenticationToken findFirstByTokenValue(String tokenValue);

    AuthenticationToken findFirstByUser(User user);

    AuthenticationToken findFirstByAdmin(Admin admin);
}
