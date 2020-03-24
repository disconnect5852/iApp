create sequence hibernate_sequence start with 1 increment by 1
create table email (id bigint not null, email varchar(255) not null, user_id bigint, primary key (id))
create table user (id bigint not null, birth_date date not null, name varchar(255) not null, primary key (id))
alter table email add constraint UK_ek29mh30yo2rxnsy4svwbgogh unique (email)
alter table user add constraint UK_gj2fy3dcix7ph7k8684gka40c unique (name)
alter table email add constraint FK4qxwfk0jqc0au545318wfiqxx foreign key (user_id) references user
