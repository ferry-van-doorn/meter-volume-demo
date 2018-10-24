create sequence if not exists hibernate_sequence;

create table if not exists profile
(
	id bigint not null
		constraint profile_pkey
			primary key,
	profile varchar(255) not null
		constraint profile_profile_unique
			unique
);

create table if not exists profile_fraction
(
	profile_id bigint not null
		constraint profile_fraction_profile_fkey
			references profile(id),
	fraction numeric(19,2) not null,
	month varchar(255) not null,
	constraint profile_fraction_pkey
		primary key (profile_id, month)
);

create index if not exists profile_fraction_profile_id_idx ON profile_fraction USING btree (profile_id);

create table if not exists meter_reading
(
	id bigint not null
		constraint meter_reading_pkey
			primary key,
	meter_id varchar(255) not null,
	profile_id bigint not null
	  constraint meter_reading_profile_fkey
			references profile(id),
	year integer not null
);

create unique index if not exists meter_reading_meter_id_year_unique on meter_reading using btree(meter_id, year);

create table if not exists meter_reading_reading
(
	meter_reading_id bigint not null
		constraint meter_reading_reading_meter_reading_fkey
			references meter_reading(id),
	reading numeric(19,2) not null,
	month varchar(255) not null,
	constraint meter_reading_reading_pkey
		primary key (meter_reading_id, month)
);

create index if not exists meter_reading_reading_meter_reading_id_idx ON meter_reading_reading USING btree (meter_reading_id);
