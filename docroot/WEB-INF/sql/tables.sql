create table lfvo_Item (
	uuid_ VARCHAR(75) null,
	itemId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	createDate DATE null,
	modifiedDate DATE null,
	publishDate DATE null,
	objectId VARCHAR(75) null,
	name VARCHAR(75) null,
	type_ VARCHAR(75) null,
	description VARCHAR(75) null,
	lat LONG,
	lng LONG
);

create table lfvo_LFImage (
	uuid_ VARCHAR(75) null,
	lfImageId LONG not null primary key,
	itemId LONG,
	image BLOB,
	createDate DATE null,
	modifiedDate DATE null
);