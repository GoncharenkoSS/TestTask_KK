CREATE TABLE workers
(
    id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    position VARCHAR(255) NOT NULL,
    avatar TEXT NOT NULL
);

CREATE TABLE tasks
(
    id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    time TIME NOT NULL,
    status VARCHAR(255) NOT NULL,
    performer int REFERENCES workers(id) ON DELETE SET NULL
);