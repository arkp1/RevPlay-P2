//package com.revature.Revplay.repository;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import com.revature.Revplay.entity.Favorite;
//import com.revature.Revplay.entity.compositekey.FavoriteId;
//
//public interface FavoriteRepository 
//    extends JpaRepository<Favorite, FavoriteId> {
//}

package com.revature.Revplay.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.revature.Revplay.entity.Favorite;
import com.revature.Revplay.entity.compositekey.FavoriteId;

public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteId> {

    List<Favorite> findByUser_UserId(Long userId);
    

    boolean existsByUser_UserIdAndSong_SongId(Long userId, Long songId);
}


