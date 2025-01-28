
    create table authors (
        id integer not null auto_increment,
        biography varchar(255),
        country varchar(255),
        lastname varchar(255),
        name varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table authors_games (
        authors_id integer not null,
        games_id integer not null
    ) engine=InnoDB;

    create table games (
        id integer not null auto_increment,
        max_game_time integer,
        max_player_number integer,
        min_age integer,
        min_game_time integer,
        min_player_number integer,
        pubblication_date datetime(6),
        description varchar(255),
        name varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    alter table authors_games 
       add constraint FKswhsqt2qiwgsks0okr1q4iqxk 
       foreign key (games_id) 
       references games (id);

    alter table authors_games 
       add constraint FKox0eblesmfvttqpuko4alnwc0 
       foreign key (authors_id) 
       references authors (id);
