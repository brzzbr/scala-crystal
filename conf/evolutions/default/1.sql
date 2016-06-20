# --- !Ups

create table "COMPETITOR" (
  "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL PRIMARY KEY,
  "NAME" VARCHAR NOT NULL,
  "URL" VARCHAR NOT NULL,
  "LAST_CRAWL_START" TIMESTAMP NULL,
  "LAST_CRAWL_FINISH" TIMESTAMP NULL);

create table "REVIEW" (
  "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL PRIMARY KEY,
  "COMPETITOR_ID" BIGINT NOT NULL,
  "AUTHOR" VARCHAR NOT NULL,
  "TEXT" VARCHAR NOT NULL,
  "DATE" DATE NOT NULL,
  FOREIGN KEY (COMPETITOR_ID) REFERENCES COMPETITOR(ID));

CREATE INDEX REVIEW_DATE_INDEX ON REVIEW (DATE);

create table "GOOD" (
  "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL PRIMARY KEY,
  "COMPETITOR_ID" BIGINT NOT NULL,
  "EXT_ID" BIGINT NOT NULL,
  "NAME" VARCHAR NOT NULL,
  "PRICE" FLOAT NOT NULL,
  "IMG_URL" VARCHAR NOT NULL,
  "URL" VARCHAR NOT NULL,
  "DATE" DATE NOT NULL,
  FOREIGN KEY (COMPETITOR_ID) REFERENCES COMPETITOR(ID));

CREATE INDEX GOOD_DATE_INDEX ON GOOD (DATE);
CREATE INDEX GOOD_EXT_ID_INDEX ON GOOD (EXT_ID);

create table "CHART" (
  "ID" BIGINT GENERATED BY DEFAULT AS IDENTITY(START WITH 1) NOT NULL PRIMARY KEY,
  "COMPETITOR_ID" BIGINT NOT NULL,
  "AMOUNT" INT NOT NULL,
  "DATE" DATE NOT NULL,
  FOREIGN KEY (COMPETITOR_ID) REFERENCES COMPETITOR(ID));

CREATE INDEX CHART_DATE_INDEX ON CHART (DATE);

# --- !Downs
drop table "CHART";
drop table "GOOD";
drop table "REVIEW";
drop table "COMPETITOR";


