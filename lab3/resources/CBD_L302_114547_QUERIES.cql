// 7. Permitir a pesquisa de todos os videos de determinado autor;
SELECT JSON * FROM cbd_videos.video_author WHERE share_author = 'eduardo_b';

// 8. Permitir a pesquisa de comentarios por utilizador, ordenado inversamente pela data;
SELECT JSON * FROM cbd_videos.comments_author WHERE author = 'pedro_p';

// 9. Permitir a pesquisa de comentarios por videos, ordenado inversamente pela data;
SELECT JSON * FROM cbd_videos.comments WHERE video = 'Top 10 filmes de ação';

// 10. Permitir a pesquisa do rating medio de um video e quantas vezes foi votado;
SELECT JSON AVG(rating) AS "avg_rating", COUNT(rating) AS "votes" FROM cbd_videos.ratings WHERE video = 'Top 10 filmes de ação';


// 1. Os ultimos 3 comentarios introduzidos para um video;
SELECT JSON * FROM cbd_videos.comments WHERE video = 'Top 10 filmes de ação' LIMIT 3;

// 2. Lista das tags de determinado video;UA.DETI.CBD- 2023/24 15
SELECT JSON tag FROM cbd_videos.video WHERE name = 'Tour pela minha casa';

// 3. Todos os videos com a tag Vlogs;
SELECT JSON name FROM cbd_videos.video_by_tag WHERE tag = 'Vlogs';

// 4.a. Os últimos 5 eventos de determinado vídeo realizados por um utilizador;
SELECT JSON * FROM cbd_videos.events WHERE video = 'Tour pela minha casa' AND user = 'paulo_m' LIMIT 5;

// 4.b. Todos os eventos de determinado utilizador;
SELECT JSON * FROM cbd_videos.events WHERE user = 'paulo_m';

// 4.c. Todos os eventos de determinado utilizador to tipo "pause"
SELECT JSON * FROM cbd_videos.events WHERE user = 'paulo_m' AND type = 'pause';

// 5. Videos partilhados por determinado utilizador (maria1987, por exemplo) num determinado periodo de tempo (Agosto de 2017, por exemplo);
SELECT JSON * FROM cbd_videos.video_author WHERE share_author = 'paulo_m' AND upload_timestamp >= '2017-08-01' AND upload_timestamp <= '2017-08-31';

// 6. Os ultimos 10 videos, ordenado inversamente pela data da partilhada;

// 7. Todos os seguidores (followers) de determinado video;
SELECT JSON followers FROM cbd_videos.followers WHERE video = 'Top 10 filmes de ação';

// 8. Todos os comentarios (dos videos) que determinado utilizador esta a seguir (following);


// 9. Os 5 videos com maior rating;

// 10. Uma query que retorne todos os videos e que mostre claramente a forma pela qual estao ordenados;
SELECT JSON share_author, upload_timestamp FROM cbd_videos.video_author;

// 11. Lista com as Tags existentes e o numero de videos catalogados com cada uma delas;
SELECT JSON tag, COUNT(name) AS "videos" FROM cbd_videos.video_by_tag GROUP BY tag;

// 12.

// 13.

// 14.