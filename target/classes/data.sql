INSERT INTO puzzle (actif, colcluesjson, gridsize, niveau, rowcluesjson, solutionjson, title, max_erreurs, imageurl)
SELECT true, '[[1,1],[1,1],[1],[1,1],[1,1]]', 5, 'FACILE', '[[1,1],[1,1],[1],[1,1],[1,1]]', '[[1,0,0,0,1],[0,1,0,1,0],[0,0,1,0,0],[0,1,0,1,0],[1,0,0,0,1]]', 'X', 0, '/nonogram/static/images/croix.jpg'
WHERE NOT EXISTS (SELECT 1 FROM puzzle WHERE title = 'X');

INSERT INTO puzzle (actif, colcluesjson, gridsize, niveau, rowcluesjson, solutionjson, title, max_erreurs, imageurl)
SELECT true, '[[1],[3],[5],[7],[6,1],[6,1],[5],[4],[2],[0]]', 10, 'MOYEN', '[[2,2],[4,4],[9],[9],[7],[5],[3],[1],[0],[0]]', '[[0,1,1,0,0,0,1,1,0,0],[1,1,1,1,0,1,1,1,1,0],[1,1,1,1,1,1,1,1,1,0],[1,1,1,1,1,1,1,1,1,0],[0,1,1,1,1,1,1,1,0,0],[0,0,1,1,1,1,1,0,0,0],[0,0,0,1,1,1,0,0,0,0],[0,0,0,0,1,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0],[0,0,0,0,0,0,0,0,0,0]]', 'Coeur', 5, '/nonogram/static/images/coeur.jpg'
WHERE NOT EXISTS (SELECT 1 FROM puzzle WHERE title = 'Coeur');

INSERT INTO puzzle (actif, colcluesjson, gridsize, niveau, rowcluesjson, solutionjson, title, max_erreurs, imageurl)
SELECT true, '[[5],[6],[7],[9,3],[10,3],[10,3],[9],[9],[9],[10,3],[10,3],[9,3],[7],[6],[5]]', 15, 'DIFFICILE', '[[1],[3],[5],[7],[9],[11],[13],[15],[9],[9],[3,3],[3,3],[3,3],[3,3],[3,3]]', '[[0,0,0,0,0,0,0,1,0,0,0,0,0,0,0],[0,0,0,0,0,0,1,1,1,0,0,0,0,0,0],[0,0,0,0,0,1,1,1,1,1,0,0,0,0,0],[0,0,0,0,1,1,1,1,1,1,1,0,0,0,0],[0,0,0,1,1,1,1,1,1,1,1,1,0,0,0],[0,0,1,1,1,1,1,1,1,1,1,1,1,0,0],[0,1,1,1,1,1,1,1,1,1,1,1,1,1,0],[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],[0,0,0,1,1,1,1,1,1,1,1,1,0,0,0],[0,0,0,1,1,1,1,1,1,1,1,1,0,0,0],[0,0,0,1,1,1,0,0,0,1,1,1,0,0,0],[0,0,0,1,1,1,0,0,0,1,1,1,0,0,0],[0,0,0,1,1,1,0,0,0,1,1,1,0,0,0],[0,0,0,1,1,1,0,0,0,1,1,1,0,0,0],[0,0,0,1,1,1,0,0,0,1,1,1,0,0,0]]', 'Maison', 3, '/nonogram/static/images/maison.jpg'
WHERE NOT EXISTS (SELECT 1 FROM puzzle WHERE title = 'Maison');