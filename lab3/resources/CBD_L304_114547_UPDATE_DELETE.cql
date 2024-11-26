UPDATE cbd_football.tournament SET teams = teams + {'Ajax'} WHERE name = 'UEFA Champions League';
UPDATE cbd_football.match SET score['Ajax'] = 4 WHERE home_team = 'Ajax' AND away_team = 'PSV' AND date = '2023-10-01 20:00:00+0000';
UPDATE cbd_football.match_goals SET minutes = minutes + [87] WHERE match = 'Real Madrid vs Liverpool' AND player = 'Virgil van Dijk';
UPDATE cbd_football.match_goals SET minutes = [24, 78] WHERE match = 'Real Madrid vs Liverpool' AND player = 'Killian Mbappé';
UPDATE cbd_football.match_goals SET minutes = [12] + minutes WHERE match = 'Benfica vs Juventus' AND player = 'Fredrik Aursnes';

DELETE minutes[0] FROM cbd_football.match_goals WHERE match = 'Benfica vs Juventus' AND player = 'Fredrik Aursnes';
DELETE FROM cbd_football.match_goals WHERE match = 'Real Madrid vs Liverpool' AND player = 'Killian Mbappé';