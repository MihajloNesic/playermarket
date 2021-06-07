CREATE TABLE transfer
(
    id                     BIGSERIAL     NOT NULL,
    player_id              BIGINT        NOT NULL,
    team_id                BIGINT        NOT NULL,
    transfer_date          DATE          NOT NULL,
    transfer_fee           NUMERIC(12,2) NOT NULL,
    commission_percentage  NUMERIC(3,2)  NOT NULL,
    team_commission        NUMERIC(12,2) NOT NULL,
    contract_fee           NUMERIC(12,2) NOT NULL,
    currency               VARCHAR(3)    NOT NULL,
    end_date               DATE          NOT NULL,
    status                 VARCHAR(127)  NOT NULL,
    CONSTRAINT pk_transfer PRIMARY KEY (id)
);
