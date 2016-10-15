create table dataentry(
	date text,
	time bigint,
	model text,
	type text,
	amp double,
	voltage double,
	primary key (date, time, model))
	with CLUSTERING ORDER BY (time ASC);
