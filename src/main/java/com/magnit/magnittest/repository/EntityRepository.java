package com.magnit.magnittest.repository;

import com.magnit.magnittest.entity.Entry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityRepository extends JpaRepository<Entry, Long> {

}