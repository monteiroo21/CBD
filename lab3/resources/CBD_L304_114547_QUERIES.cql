// 1. Média de idades de uma equipa;
SELECT JSON name, average_age(ages) AS "average_age" FROM cbd_football.team GROUP BY name;

// 2. Lista das tags de determinado video;UA.DETI.CBD- 2023/24 15

// 3. Todos os videos com a tag Vlogs;

// 4. Os últimos 5 eventos de determinado vídeo realizados por um utilizador;

// 5. Videos partilhados por determinado utilizador (maria1987, por exemplo) num determinado periodo de tempo (Agosto de 2017, por exemplo);

// 6. Os ultimos 10 videos, ordenado inversamente pela data da partilhada;

// 7. Todos os seguidores (followers) de determinado video;

// 8. Todos os comentarios (dos videos) que determinado utilizador esta a seguir (following);

// 9. Os 5 videos com maior rating;

// 10. Uma query que retorne todos os videos e que mostre claramente a forma pela qual estao ordenados;