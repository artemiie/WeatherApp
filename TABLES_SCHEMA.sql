CREATE TABLE WA_USER (
    USER_ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    USER_LOGIN VARCHAR(255) NOT NULL UNIQUE,
    USER_PASSWORD VARCHAR(255) NOT NULL,
    USER_NAME VARCHAR(255) NOT NULL,
    CREATION_DATETIME DATETIME NOT NULL
);

CREATE TABLE USER_SESSION (
    SESSION_ID VARCHAR(36) NOT NULL PRIMARY KEY,
    USER_ID INT NOT NULL,
    EXPIRY_DATETIME DATETIME NOT NULL,
    FOREIGN KEY (USER_ID) REFERENCES WA_USER (USER_ID)
);

CREATE TABLE USER_LOCATION (
	LOCATION_ID INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    USER_ID INT NOT NULL,
    LAT DOUBLE NOT NULL,
    LON DOUBLE NOT NULL,
    CITY VARCHAR(255) NOT NULL,
    COUNTRY VARCHAR(255) NOT NULL,
    STATE VARCHAR(255),
    FOREIGN KEY (USER_ID) REFERENCES WA_USER (USER_ID)
);