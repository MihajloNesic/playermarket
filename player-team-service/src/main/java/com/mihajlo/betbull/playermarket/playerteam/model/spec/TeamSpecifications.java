package com.mihajlo.betbull.playermarket.playerteam.model.spec;

import com.mihajlo.betbull.playermarket.playerteam.entity.Team;
import com.mihajlo.betbull.playermarket.playerteam.entity.domain.TeamStatus;
import org.springframework.data.jpa.domain.Specification;

public class TeamSpecifications {

    public static Specification<Team> isActive() {
        return (root, query, cb) -> cb.equal(root.get("status"), TeamStatus.ACTIVE);
    }
}
