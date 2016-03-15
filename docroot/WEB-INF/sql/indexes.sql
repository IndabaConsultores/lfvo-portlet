create index IX_BC3C2805 on lfvo_Item (groupId);
create index IX_A3B864F9 on lfvo_Item (uuid_[$COLUMN_LENGTH:75$], companyId);
create unique index IX_73D7D5BB on lfvo_Item (uuid_[$COLUMN_LENGTH:75$], groupId);

create index IX_44ACC87D on lfvo_LFImage (itemId);
create index IX_30C5959 on lfvo_LFImage (uuid_[$COLUMN_LENGTH:75$]);