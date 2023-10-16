package ru.practicum.main.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.main.model.Compilations;

import java.util.List;

public interface CompilationMainServiceRepository extends JpaRepository<Compilations, Long> {


    @Query("select com " +
            "from Compilations com " +
            "where com.pinned = :pinned or (com.pinned = false or com.pinned = true)")
    List<Compilations> findAllByPinned(@Param("pinned") Boolean pinned, Pageable pageable);

}
