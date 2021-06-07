package com.mihajlo.betbull.playermarket.playerteam.model.spec;

import com.mihajlo.betbull.playermarket.playerteam.entity.Player;
import com.mihajlo.betbull.playermarket.playerteam.entity.domain.PlayerStatus;
import org.springframework.data.jpa.domain.Specification;

public class PlayerSpecifications {

    public static Specification<Player> isActive() {
        return (root, query, cb) -> cb.equal(root.get("status"), PlayerStatus.ACTIVE);
    }
}
