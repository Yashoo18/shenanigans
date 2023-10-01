package com.random;

import com.random.data.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestH2Repo extends JpaRepository<User,Long> {
}
