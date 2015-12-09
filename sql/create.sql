CREATE TABLE project (
  id NUMERIC default UNIQUEKEY('project'),
  name varchar(255) NOT NULL,
  description text,
  price double,
  startdate date,
  enddate date,
  UNIQUE (id),
  PRIMARY KEY (id)
);

CREATE TABLE task (
  id NUMERIC default UNIQUEKEY('task'),
  project_id int(4) NOT NULL,
  name varchar(255) NOT NULL,
  comment text,
  effort double,
  UNIQUE (id),
  PRIMARY KEY (id)
);

ALTER TABLE task ADD CONSTRAINT fk_project FOREIGN KEY (project_id) REFERENCES project (id) DEFERRABLE;
