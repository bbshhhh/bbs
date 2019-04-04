package com.ccnu.bbs.repository;


import com.ccnu.bbs.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface LikeRepository extends JpaRepository<Like, BigInteger> {
}
