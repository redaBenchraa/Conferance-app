CREATE TABLE  IF NOT EXISTS SESSIONS (
    session_id   INTEGER      NOT NULL AUTO_INCREMENT,
    session_name VARCHAR(128) NOT NULL,
    session_description VARCHAR(128) NOT NULL,
    session_length INT(11) NOT NULL,
    PRIMARY KEY (session_id)
);

CREATE TABLE  IF NOT EXISTS SPEAKERS (
    speaker_id   INTEGER      NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(128) NOT NULL,
    last_name VARCHAR(128) NOT NULL,
    title VARCHAR(128) NOT NULL,
    company VARCHAR(128) NOT NULL,
    speaker_bio VARCHAR(128) NOT NULL,
    PRIMARY KEY (speaker_id)
);


CREATE TABLE  IF NOT EXISTS SESSION_SPEAKERS (
    session_id INT(11) NOT NULL REFERENCES SESSIONS(session_id),
    speaker_id INT(11) NOT NULL REFERENCES SPEAKERS(speaker_id)
);