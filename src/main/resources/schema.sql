CREATE TABLE IF NOT EXISTS joueurs (
    id BIGSERIAL PRIMARY KEY,
    pseudo VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    meilleurscore INTEGER DEFAULT 0,
    totalparties INTEGER DEFAULT 0
);

CREATE TABLE IF NOT EXISTS puzzle (
    id BIGSERIAL PRIMARY KEY,
    actif BOOLEAN NOT NULL,
    colcluesjson TEXT,
    gridsize INTEGER,
    niveau VARCHAR(255) CHECK (niveau IN ('FACILE', 'MOYEN', 'DIFFICILE')),
    rowcluesjson TEXT,
    solutionjson TEXT,
    title TEXT,
    max_erreurs INTEGER DEFAULT 0,
    imageurl TEXT
);

CREATE TABLE IF NOT EXISTS partie (
    id BIGSERIAL PRIMARY KEY,
    joueur_id BIGINT NOT NULL REFERENCES joueurs(id),
    puzzle_id BIGINT NOT NULL REFERENCES puzzle(id),
    etat VARCHAR(255),
    grillecourante TEXT,
    scoreactuel INTEGER DEFAULT 0,
    scorefinal INTEGER DEFAULT 0,
    nberreurs INTEGER DEFAULT 0,
    tempsecoule INTEGER DEFAULT 0,
    estgagnee BOOLEAN DEFAULT false,
    datedebut TIMESTAMP,
    datesauvegarde TIMESTAMP,
    datefin TIMESTAMP
);