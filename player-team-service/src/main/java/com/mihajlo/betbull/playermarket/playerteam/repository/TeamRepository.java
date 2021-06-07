package com.mihajlo.betbull.playermarket.playerteam.repository;

import com.mihajlo.betbull.playermarket.playerteam.entity.Team;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends PagingAndSortingRepository<Team, Long>, JpaSpecificationExecutor<Team> {

    @Query("select t from Team t where t.status = 'ACTIVE' order by t.name")
    List<Team> findAllActive();
}
