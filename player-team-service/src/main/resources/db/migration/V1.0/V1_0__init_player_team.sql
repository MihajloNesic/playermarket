CREATE TABLE team
(
    id                     BIGSERIAL    NOT NULL,
    name                   VARCHAR(127) NOT NULL,
    commission_percentage  NUMERIC(3,2) NOT NULL,
    currency               VARCHAR(3)   NOT NULL DEFAULT 'USD',
    status                 VARCHAR(127) NOT NULL,
    CONSTRAINT pk_team PRIMARY KEY (id)
);


CREATE TABLE player
(
    id                 BIGSERIAL    NOT NULL,
    first_name         VARCHAR(127) NOT NULL,
    last_name          VARCHAR(255) NOT NULL,
    birth_date         DATE         NOT NULL,
    current_team_id    BIGINT,
    status             VARCHAR(127) NOT NULL,
    CONSTRAINT pk_player PRIMARY KEY (id)
);

ALTER TABLE player
    ADD CONSTRAINT fk_player_current_team
        FOREIGN KEY (current_team_id)
            REFERENCES team (id)
            ON UPDATE RESTRICT ON DELETE RESTRICT;
