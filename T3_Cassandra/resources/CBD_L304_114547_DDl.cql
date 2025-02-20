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

CREATE INDEX IF NOT EXISTS ON cbd_football.player(position);

CREATE TABLE IF NOT EXISTS team (
    id UUID,
    name TEXT,
    country TEXT,
    foundation_year INT,
    PRIMARY KEY (name, id)
);

CREATE INDEX IF NOT EXISTS ON cbd_football.team(country);

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

CREATE INDEX IF NOT EXISTS ON cbd_football.match(tournament);
CREATE INDEX IF NOT EXISTS ON cbd_football.match(home_team);

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


CREATE OR REPLACE FUNCTION average_goal_minute(minutes list<int>)
RETURNS NULL ON NULL INPUT
RETURNS double
LANGUAGE java AS '
    int totalMinutes = 0;
    int count = 0;
    if (minutes != null) {
        for (Integer minute : minutes) {
            if (minute != null) {
                totalMinutes += minute;
                count++;
            }
        }
        if (count == 0) {
            return null;
        }
    }
    return totalMinutes / (double) count;
';

CREATE OR REPLACE FUNCTION list_size(minutes list<int>)
RETURNS NULL ON NULL INPUT
RETURNS int
LANGUAGE java AS '
    return minutes == null ? 0 : minutes.size();
';

CREATE OR REPLACE FUNCTION set_size(teams set<text>)
RETURNS NULL ON NULL INPUT
RETURNS int
LANGUAGE java AS '
    return teams == null ? 0 : teams.size();
';


CREATE OR REPLACE FUNCTION sum_goals(state int, score map<text, int>)  
RETURNS NULL ON NULL INPUT
RETURNS INT
LANGUAGE java AS '
    int totalGoals = state;
    if (score != null) {
        for (Integer goals : score.values()) {
            if (goals != null) {
                totalGoals += goals;
            }
        }
    }
    return totalGoals;
';

CREATE OR REPLACE AGGREGATE total_goals(map<text, int>)
SFUNC sum_goals
STYPE int
INITCOND 0;
