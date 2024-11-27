INSERT INTO cbd_football.player (id, name, country, position, age) VALUES (uuid(), 'Kylian Mbappé', 'France', 'Forward', 25);
INSERT INTO cbd_football.player (id, name, country, position, age) VALUES (uuid(), 'Virgil van Dijk', 'Netherlands', 'Defender', 33);
INSERT INTO cbd_football.player (id, name, country, position, age) VALUES (uuid(), 'Robert Lewandowski', 'Poland', 'Forward', 35);
INSERT INTO cbd_football.player (id, name, country, position, age) VALUES (uuid(), 'Manuel Neuer', 'Germany', 'Goalkeeper', 38);
INSERT INTO cbd_football.player (id, name, country, position, age) VALUES (uuid(), 'Mo Salah', 'Egypt', 'Forward', 30);
INSERT INTO cbd_football.player (id, name, country, position, age) VALUES (uuid(), 'Kerem Akturkoglou', 'Turkey', 'Midfielder', 25);
INSERT INTO cbd_football.player (id, name, country, position, age) VALUES (uuid(), 'Fredrik Aursnes', 'Norway', 'Midfielder', 28);
INSERT INTO cbd_football.player (id, name, country, position, age) VALUES (uuid(), 'Trent Alexander-Arnold', 'England', 'Defender', 25);
INSERT INTO cbd_football.player (id, name, country, position, age) VALUES (uuid(), 'João Félix', 'Portugal', 'Forward', 23);
INSERT INTO cbd_football.player (id, name, country, position, age) VALUES (uuid(), 'Joshua Kimmich', 'Germany', 'Midfielder', 28);
INSERT INTO cbd_football.player (id, name, country, position, age) VALUES (uuid(), 'Rafael Leão', 'Portugal', 'Forward', 24);
INSERT INTO cbd_football.player (id, name, country, position, age) VALUES (uuid(), 'Erling Haaland', 'Norway', 'Forward', 23);

INSERT INTO cbd_football.team (id, name, country, foundation_year) VALUES (uuid(), 'Paris Saint-Germain', 'France', 1970);
INSERT INTO cbd_football.team (id, name, country, foundation_year) VALUES (uuid(), 'Liverpool', 'England', 1892);
INSERT INTO cbd_football.team (id, name, country, foundation_year) VALUES (uuid(), 'Bayern Munich', 'Germany', 1900);
INSERT INTO cbd_football.team (id, name, country, foundation_year) VALUES (uuid(), 'Real Madrid', 'Spain', 1902);
INSERT INTO cbd_football.team (id, name, country, foundation_year) VALUES (uuid(), 'Barcelona', 'Spain', 1899);
INSERT INTO cbd_football.team (id, name, country, foundation_year) VALUES (uuid(), 'Manchester City', 'England', 1880);
INSERT INTO cbd_football.team (id, name, country, foundation_year) VALUES (uuid(), 'Juventus', 'Italy', 1897);
INSERT INTO cbd_football.team (id, name, country, foundation_year) VALUES (uuid(), 'Borussia Dortmund', 'Germany', 1909);
INSERT INTO cbd_football.team (id, name, country, foundation_year) VALUES (uuid(), 'Benfica', 'Portugal', 1904);
INSERT INTO cbd_football.team (id, name, country, foundation_year) VALUES (uuid(), 'Chelsea', 'England', 1921);
INSERT INTO cbd_football.team (id, name, country, foundation_year) VALUES (uuid(), 'Manchester United', 'England', 1878);
INSERT INTO cbd_football.team (id, name, country, foundation_year) VALUES (uuid(), 'AC Milan', 'Italy', 1903);
INSERT INTO cbd_football.team (id, name, country, foundation_year) VALUES (uuid(), 'Ajax', 'Netherlands', 1903);
INSERT INTO cbd_football.team (id, name, country, foundation_year) VALUES (uuid(), 'PSV', 'Netherlands', 1903);
INSERT INTO cbd_football.team (id, name, country, foundation_year) VALUES (uuid(), 'Vitória SC', 'Portugal', 1911);
INSERT INTO cbd_football.team (id, name, country, foundation_year) VALUES (uuid(), 'Dinamo Kyiv', 'Ukraine', 1923);
INSERT INTO cbd_football.team (id, name, country, foundation_year) VALUES (uuid(), 'Dinamo Zagreb', 'Croatia', 1907);
INSERT INTO cbd_football.team (id, name, country, foundation_year) VALUES (uuid(), 'Hadjuk Split', 'Croatia', 1904);


INSERT INTO cbd_football.tournament (name, country, teams) VALUES ('UEFA Champions League', 'Europe', {'Paris Saint-Germain', 'Liverpool', 'Bayern Munich', 'Real Madrid', 'Barcelona', 'Benfica', 'Manchester City', 'Juventus', 'Borussia Dortmund', 'AC Milan'});
INSERT INTO cbd_football.tournament (name, country, teams) VALUES ('UEFA Europa League', 'Europe', {'Chelsea', 'Manchester United', 'Borussia Dortmund'});
INSERT INTO cbd_football.tournament (name, country, teams) VALUES ('Premier League', 'England', {'Liverpool', 'Manchester City', 'Chelsea', 'Manchester United'});
INSERT INTO cbd_football.tournament (name, country, teams) VALUES ('La Liga', 'Spain', {'Real Madrid', 'Barcelona'});
INSERT INTO cbd_football.tournament (name, country, teams) VALUES ('Serie A', 'Italy', {'Juventus', 'AC Milan'});
INSERT INTO cbd_football.tournament (name, country, teams) VALUES ('Bundesliga', 'Germany', {'Bayern Munich', 'Borussia Dortmund'});
INSERT INTO cbd_football.tournament (name, country, teams) VALUES ('Primeira Liga', 'Portugal', {'Benfica', 'Vitória SC'});
INSERT INTO cbd_football.tournament (name, country, teams) VALUES ('Ligue 1', 'France', {'Paris Saint-Germain'});
INSERT INTO cbd_football.tournament (name, country, teams) VALUES ('Eredivisie', 'Netherlands', {'Ajax', 'PSV'});
INSERT INTO cbd_football.tournament (name, country, teams) VALUES ('Ukrainian Premier League', 'Ukraine', {'Dinamo Kyiv'});
INSERT INTO cbd_football.tournament (name, country, teams) VALUES ('Croatian First Football League', 'Croatia', {'Dinamo Zagreb', 'Hadjuk Split'});


INSERT INTO cbd_football.player_team (player, team) VALUES ('Kylian Mbappé', 'Real Madrid');
INSERT INTO cbd_football.player_team (player, team) VALUES ('Kylian Mbappé', 'Paris Saint-Germain');
INSERT INTO cbd_football.player_team (player, team) VALUES ('Virgil van Dijk', 'Liverpool');
INSERT INTO cbd_football.player_team (player, team) VALUES ('Robert Lewandowski', 'Barcelona');
INSERT INTO cbd_football.player_team (player, team) VALUES ('Robert Lewandowski', 'Bayern Munich');
INSERT INTO cbd_football.player_team (player, team) VALUES ('Manuel Neuer', 'Bayern Munich');
INSERT INTO cbd_football.player_team (player, team) VALUES ('Mo Salah', 'Liverpool');
INSERT INTO cbd_football.player_team (player, team) VALUES ('Mo Salah', 'Chelsea');
INSERT INTO cbd_football.player_team (player, team) VALUES ('Kerem Akturkoglou', 'Benfica');
INSERT INTO cbd_football.player_team (player, team) VALUES ('Fredrik Aursnes', 'Benfica');
INSERT INTO cbd_football.player_team (player, team) VALUES ('Trent Alexander-Arnold', 'Liverpool');
INSERT INTO cbd_football.player_team (player, team) VALUES ('João Félix', 'Chelsea');
INSERT INTO cbd_football.player_team (player, team) VALUES ('Joshua Kimmich', 'Bayern Munich');
INSERT INTO cbd_football.player_team (player, team) VALUES ('Rafael Leão', 'AC Milan');
INSERT INTO cbd_football.player_team (player, team) VALUES ('Erling Haaland', 'Manchester City');


INSERT INTO cbd_football.match (home_team, away_team, date, tournament, score) VALUES ('Real Madrid', 'Liverpool', '2023-10-01 20:00:00', 'UEFA Champions League', {'Real Madrid': 3, 'Liverpool': 1});
INSERT INTO cbd_football.match (home_team, away_team, date, tournament, score) VALUES ('Bayern Munich', 'Barcelona', '2020-10-01 20:00:00', 'UEFA Champions League', {'Bayern Munich': 8, 'Barcelona': 2});
INSERT INTO cbd_football.match (home_team, away_team, date, tournament, score) VALUES ('Benfica', 'Juventus', '2021-10-01 20:00:00', 'UEFA Champions League', {'Benfica': 4, 'Juventus': 3});
INSERT INTO cbd_football.match (home_team, away_team, date, tournament, score) VALUES ('Liverpool', 'Barcelona', '2022-10-01 20:00:00', 'UEFA Champions League', {'Liverpool': 4, 'Barcelona': 0});
INSERT INTO cbd_football.match (home_team, away_team, date, tournament, score) VALUES ('Paris Saint-Germain', 'Benfica', '2023-10-01 20:00:00', 'UEFA Champions League', {'Paris Saint-Germain': 1, 'Benfica': 1});
INSERT INTO cbd_football.match (home_team, away_team, date, tournament, score) VALUES ('Chelsea', 'Manchester United', '2023-10-01 20:00:00', 'Premier League', {'Chelsea': 2, 'Manchester United': 1});
INSERT INTO cbd_football.match (home_team, away_team, date, tournament, score) VALUES ('Liverpool', 'Manchester United', '2023-10-01 20:00:00', 'Premier League', {'Liverpool': 7, 'Manchester United': 0});
INSERT INTO cbd_football.match (home_team, away_team, date, tournament, score) VALUES ('Real Madrid', 'Barcelona', '2023-10-01 20:00:00', 'La Liga', {'Real Madrid': 2, 'Barcelona': 1});
INSERT INTO cbd_football.match (home_team, away_team, date, tournament, score) VALUES ('Juventus', 'AC Milan', '2023-10-01 20:00:00', 'Serie A', {'Juventus': 3, 'AC Milan': 1});
INSERT INTO cbd_football.match (home_team, away_team, date, tournament, score) VALUES ('Bayern Munich', 'Borussia Dortmund', '2023-10-01 20:00:00', 'Bundesliga', {'Bayern Munich': 2, 'Borussia Dortmund': 1});
INSERT INTO cbd_football.match (home_team, away_team, date, tournament, score) VALUES ('Benfica', 'Vitória SC', '2023-10-01 20:00:00', 'Primeira Liga', {'Benfica': 3, 'Vitória SC': 1});
INSERT INTO cbd_football.match (home_team, away_team, date, tournament, score) VALUES ('Ajax', 'PSV', '2023-10-01 20:00:00', 'Eredivisie', {'Ajax': 2, 'PSV': 2});
INSERT INTO cbd_football.match (home_team, away_team, date, tournament, score) VALUES ('Hadjuk Split', 'Dinamo Zagreb', '2024-10-01 20:00:00', 'Croatian First Football League', {'Hadjuk Split': 1, 'Dinamo Zagreb': 0});
INSERT INTO cbd_football.match (home_team, away_team, date, tournament, score) VALUES ('Hadjuk Split', 'Dinamo Zagreb', '2023-10-01 20:00:00', 'Croatian First Football League', {'Hadjuk Split': 2, 'Dinamo Zagreb': 1});


INSERT INTO cbd_football.match_players (match, player, team) VALUES ('Real Madrid vs Liverpool', 'Kylian Mbappé', 'Real Madrid');
INSERT INTO cbd_football.match_players (match, player, team) VALUES ('Real Madrid vs Liverpool', 'Virgil van Dijk', 'Liverpool');
INSERT INTO cbd_football.match_players (match, player, team) VALUES ('Bayern Munich vs Barcelona', 'Robert Lewandowski', 'Bayern Munich');
INSERT INTO cbd_football.match_players (match, player, team) VALUES ('Bayern Munich vs Barcelona', 'Manuel Neuer', 'Bayern Munich');
INSERT INTO cbd_football.match_players (match, player, team) VALUES ('Benfica vs Juventus', 'Kerem Akturkoglou', 'Benfica');
INSERT INTO cbd_football.match_players (match, player, team) VALUES ('Benfica vs Juventus', 'Fredrik Aursnes', 'Benfica');
INSERT INTO cbd_football.match_players (match, player, team) VALUES ('Liverpool vs Manchester United', 'Trent Alexander-Arnold', 'Liverpool');
INSERT INTO cbd_football.match_players (match, player, team) VALUES ('Chelsea vs Manchester United', 'João Félix', 'Chelsea');
INSERT INTO cbd_football.match_players (match, player, team) VALUES ('Bayern Munich vs Barcelona', 'Joshua Kimmich', 'Bayern Munich');
INSERT INTO cbd_football.match_players (match, player, team) VALUES ('Juventus vs AC Milan', 'Rafael Leão', 'AC Milan');
INSERT INTO cbd_football.match_players (match, player, team) VALUES ('Paris Saint-Germain vs Manchester City', 'Erling Haaland', 'Manchester City');
INSERT INTO cbd_football.match_players (match, player, team) VALUES ('Liverpool vs Manchester United', 'Mo Salah', 'Liverpool');
INSERT INTO cbd_football.match_players (match, player, team) VALUES ('Real Madrid vs Barcelona', 'Kylian Mbappé', 'Real Madrid');


INSERT INTO cbd_football.match_goals (match, player, team, minutes) VALUES ('Real Madrid vs Liverpool', 'Kylian Mbappé', 'Real Madrid', [10, 20, 30]);
INSERT INTO cbd_football.match_goals (match, player, team, minutes) VALUES ('Real Madrid vs Liverpool', 'Virgil van Dijk', 'Liverpool', [40]);
INSERT INTO cbd_football.match_goals (match, player, team, minutes) VALUES ('Bayern Munich vs Barcelona', 'Robert Lewandowski', 'Bayern Munich', [10, 20, 50, 80]);
INSERT INTO cbd_football.match_goals (match, player, team, minutes) VALUES ('Bayern Munich vs Barcelona', 'Manuel Neuer', 'Bayern Munich', [90]);
INSERT INTO cbd_football.match_goals (match, player, team, minutes) VALUES ('Benfica vs Juventus', 'Kerem Akturkoglou', 'Benfica', [10, 20]);
INSERT INTO cbd_football.match_goals (match, player, team, minutes) VALUES ('Benfica vs Juventus', 'Fredrik Aursnes', 'Benfica', [30, 40]);
INSERT INTO cbd_football.match_goals (match, player, team, minutes) VALUES ('Liverpool vs Manchester United', 'Trent Alexander-Arnold', 'Liverpool', [10, 20, 30]);
INSERT INTO cbd_football.match_goals (match, player, team, minutes) VALUES ('Chelsea vs Manchester United', 'João Félix', 'Chelsea', [10, 20, 30]);
INSERT INTO cbd_football.match_goals (match, player, team, minutes) VALUES ('Bayern Munich vs Barcelona', 'Joshua Kimmich', 'Bayern Munich', [10, 20, 30]);
INSERT INTO cbd_football.match_goals (match, player, team, minutes) VALUES ('Juventus vs AC Milan', 'Rafael Leão', 'AC Milan', [10, 20, 30]);
INSERT INTO cbd_football.match_goals (match, player, team, minutes) VALUES ('Paris Saint-Germain vs Manchester City', 'Erling Haaland', 'Manchester City', [10, 20, 30]);
INSERT INTO cbd_football.match_goals (match, player, team, minutes) VALUES ('Liverpool vs Manchester United', 'Mo Salah', 'Liverpool', [10, 20, 30]);
INSERT INTO cbd_football.match_goals (match, player, team, minutes) VALUES ('Real Madrid vs Barcelona', 'Kylian Mbappé', 'Real Madrid', [10, 20, 30]);


SELECT JSON * FROM cbd_football.player;
SELECT JSON * FROM cbd_football.team;
SELECT JSON * FROM cbd_football.tournament;
SELECT JSON * FROM cbd_football.player_team;
SELECT JSON * FROM cbd_football.match;
SELECT JSON * FROM cbd_football.match_players;
SELECT JSON * FROM cbd_football.match_goals;



