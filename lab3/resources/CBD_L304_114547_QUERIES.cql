// 1. Lista de avançados;
SELECT name, age 
FROM cbd_football.player
WHERE position = 'Forward';

// 2. Obter os últimos três jogos;
SELECT home_team, away_team, date 
FROM cbd_football.match 
LIMIT 3;

// 3. Localização de cada torneio;
SELECT name, country 
FROM cbd_football.tournament;

// 4. Lista de equipas que 'Mo Salah' já representou;
SELECT team 
FROM cbd_football.player_team 
WHERE player = 'Mo Salah';

// 5. Número de equipas por torneio;
SELECT name, set_size(teams) AS "number_of_teams" 
FROM cbd_football.tournament;

// 6. Número de golos marcados por 'Mo Salah' no 'Liverpool vs Manchester United';
SELECT list_size(minutes) AS total_goals 
FROM cbd_football.match_goals
WHERE match = 'Liverpool vs Manchester United' AND player = 'Mo Salah';

// 7. Média de minutos de golos por jogador;
SELECT JSON match, player, average_goal_minute(minutes) AS "average_goal_minute" 
FROM cbd_football.match_goals 
GROUP BY match, player;

// 8. Número de golos na 'Champions League';
SELECT total_goals(score) AS total_goals 
FROM cbd_football.match 
WHERE tournament = 'UEFA Champions League';

// 9. Lista de clubes de Inglaterra;
SELECT name 
FROM cbd_football.team 
WHERE country = 'England';

// 10. Número de golos marcados em jogos na casa do Benfica;
SELECT total_goals(score) AS total_goals 
FROM cbd_football.match 
WHERE home_team = 'Benfica';


