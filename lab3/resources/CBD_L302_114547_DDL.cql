CREATE KEYSPACE cbd_videos WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

use cbd_videos;

CREATE TABLE IF NOT EXISTS user (
    id UUID PRIMARY KEY,
    username TEXT,
    email TEXT,
    registration_timestamp TIMESTAMP
);

CREATE TABLE IF NOT EXISTS video (
    id UUID,
    share_author TEXT,
    name TEXT,
    description TEXT,
    tag SET<TEXT>,
    upload_timestamp TIMESTAMP,
    PRIMARY KEY(name, upload_timestamp)
) WITH CLUSTERING ORDER BY (upload_timestamp DESC);

CREATE TABLE IF NOT EXISTS video_author (
    id UUID,
    share_author TEXT,
    name TEXT,
    description TEXT,
    tag SET<TEXT>,
    upload_timestamp TIMESTAMP,
    PRIMARY KEY(share_author, upload_timestamp)
) WITH CLUSTERING ORDER BY (upload_timestamp DESC);

CREATE TABLE IF NOT EXISTS video_by_tag (
    name TEXT,
    tag TEXT,
    PRIMARY KEY(tag, name)
) WITH CLUSTERING ORDER BY (upload_timestamp DESC);

CREATE TABLE IF NOT EXISTS comments (
    id UUID,
    video TEXT,
    ts TIMESTAMP,
    author TEXT,
    comment TEXT,
    PRIMARY KEY(video, ts)
) WITH CLUSTERING ORDER BY (ts DESC);

CREATE TABLE IF NOT EXISTS comments_author (
    id UUID,
    video TEXT,
    ts TIMESTAMP,
    author TEXT,
    comment TEXT,
    PRIMARY KEY(author, ts)
) WITH CLUSTERING ORDER BY (ts DESC);

CREATE TABLE IF NOT EXISTS comments_follower (
    follower TEXT,
    video TEXT,
    comment TEXT,
    ts TIMESTAMP,
    PRIMARY KEY(follower, ts)
) WITH CLUSTERING ORDER BY (ts DESC);

CREATE TABLE IF NOT EXISTS followers (
    video TEXT,
    followers SET<TEXT>,
    PRIMARY KEY(video)
);

CREATE TABLE IF NOT EXISTS events (
    video TEXT,
    user TEXT,
    type TEXT,
    timestamp_event TIMESTAMP,
    moment_event INT,

    PRIMARY KEY(user, video, timestamp_event)
);

CREATE INDEX ON events(type);

CREATE TABLE IF NOT EXISTS ratings (
    rating INT,
    video TEXT,
    PRIMARY KEY(video, rating)
);
