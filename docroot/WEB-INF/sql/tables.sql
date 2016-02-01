create table lfvo_Item (
	uuid_ VARCHAR(75) null,
	itemId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	objectId VARCHAR(75) null,
	name VARCHAR(75) null,
	type_ VARCHAR(75) null,
	description VARCHAR(75) null,
	lat LONG,
	lng LONG,
	field2 BOOLEAN,
	field3 INTEGER,
	field4 DATE null,
	field5 VARCHAR(75) null
);