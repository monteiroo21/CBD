CREATE KEYSPACE cbd_football WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

use cbd_football;

CREATE TABLE IF NOT EXISTS player (
    id UUID,
    name TEXT,
    country TEXT,
    position TEXT,
    age INT,
    PRIMARY KEY (name, id)
);

CREATE TABLE IF NOT EXISTS team (
    id UUID,
    name TEXT,
    country TEXT,
    foundation_year INT,
    PRIMARY KEY (name, id)
);

CREATE TABLE IF NOT EXISTS tournament (
    name TEXT,
    country TEXT,
    teams SET<TEXT>,
    PRIMARY KEY (name)
);

CREATE TABLE IF NOT EXISTS player_team (
    player TEXT,
    team TEXT,
    PRIMARY KEY (player, team)
);

CREATE TABLE IF NOT EXISTS match (
    home_team TEXT,
    away_team TEXT,
    date TIMESTAMP,
    tournament TEXT,
    score MAP<TEXT, INT>,
    PRIMARY KEY ((home_team, away_team), date)
) WITH CLUSTERING ORDER BY (date DESC);

CREATE TABLE IF NOT EXISTS match_players (
    match TEXT,
    player TEXT,
    team TEXT,
    PRIMARY KEY (match, player)
);

CREATE TABLE IF NOT EXISTS match_goals (
    match TEXT,
    player TEXT,
    team TEXT,
    minutes LIST<INT>,
    PRIMARY KEY (match, player)
);

