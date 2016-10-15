CREATE TABLE graph(
	date DATETIME NOT NULL,
	model varchar(255) NOT NULL,
	watt DOUBLE NOT NULL,
	PRIMARY KEY (date, model)
);
