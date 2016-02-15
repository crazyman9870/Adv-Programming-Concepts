DROP TABLE IF EXISTS User;
DROP TABLE IF EXISTS Project;
DROP TABLE IF EXISTS Batch;
DROP TABLE IF EXISTS Fields;
DROP TABLE IF EXISTS Records;


CREATE TABLE User
(
	ID integer primary key autoincrement not null,
	UserName text not null unique,
	Password text not null,
	FirstName text not null,
	LastName text not null,
	Email text not null unique,
	RecordsIndexed integer not null default 0,
	BatchKey integer
);

CREATE TABLE Project
(
	ID integer primary key autoincrement not null,
	Title text not null unique,
	RecordsPerBatch integer not null,
	FirstYCoordinate integer not null,
	Height integer not null
);

CREATE TABLE Batch
(
	ID integer primary key autoincrement not null,
	ImagePath text not null unique,
	State integer not null default 0,
	ProjectKey integer not null
);

CREATE TABLE Fields
(
	ID integer primary key autoincrement not null,
	Title text not null,
	XCoordinate integer not null,
	Width integer not null,
	FieldHelpPath text not null,
	KnownDataPath text not null,
	ProjectKey integer not null
);

CREATE TABLE Records
(
	ID integer primary key autoincrement not null,
	RowNumber integer not null,
	Data text not  null,
	BatchKey integer not null,
	FieldKey integer not null
);
