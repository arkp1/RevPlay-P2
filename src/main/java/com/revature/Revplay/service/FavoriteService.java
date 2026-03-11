package com.revature.Revplay.service;

import java.util.List;
import com.revature.Revplay.entity.Favorite;

public interface FavoriteService {

    Favorite addFavorite(Long userId, Long songId);

    void removeFavorite(Long userId, Long songId);

    List<Favorite> getUserFavorites(Long userId);
}
