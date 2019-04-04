package com.ccnu.bbs.repository;


import com.ccnu.bbs.entity.Extract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface ExtractRepository extends JpaRepository<Extract, BigInteger> {
}
