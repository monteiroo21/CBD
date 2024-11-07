CREATE KEYSPACE cbd_videos WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

use cbd_videos;

CREATE TABLE IF NOT EXISTS user (
    id UUID PRIMARY KEY,
    username TEXT,
    email TEXT,
    registration_timestamp TIMESTAMP
);

CREATE TABLE IF NOT EXISTS video (
    id UUID PRIMARY KEY,
    share_author TEXT,
    name TEXT,
    description TEXT,
    tag SET<TEXT>,
    upload_timestamp TIMESTAMP
);

CREATE TABLE IF NOT EXISTS comments (
    id UUID PRIMARY KEY,
    video TEXT,
    ts TIMESTAMP,
    author TEXT,
    comment TEXT
);

CREATE TABLE IF NOT EXISTS followers (
    video TEXT PRIMARY KEY,
    followers SET<TEXT>
);

CREATE TABLE IF NOT EXISTS events (
    video TEXT,
    user TEXT,
    type TEXT,
    timestamp_event TIMESTAMP,
    moment_event INT,

    PRIMARY KEY(video, user)
);

CREATE TABLE IF NOT EXISTS ratings (
    id UUID PRIMARY KEY,
    rating INT,
    video TEXT
);