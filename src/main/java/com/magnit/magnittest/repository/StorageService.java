package com.magnit.magnittest.repository;

import com.magnit.magnittest.entity.Entry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StorageService {
    private final EntityRepository entityRepository;

    @Transactional
    public void saveEntity(Entry entry) {
        entityRepository.save(entry);
    }

    public List<Entry> findAll() {
        return entityRepository.findAll();
    }

}