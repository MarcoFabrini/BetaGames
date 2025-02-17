
    create table authors (
        id integer not null auto_increment,
        biography varchar(255),
        country varchar(255),
        lastname varchar(255),
        name varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table authors_games (
        id_authors integer not null,
        id_games integer not null
    ) engine=InnoDB;

    create table carts (
        id integer not null auto_increment,
        id_users integer,
        created_at datetime(6),
        updated_at datetime(6),
        primary key (id)
    ) engine=InnoDB;

    create table categories (
        id integer not null auto_increment,
        name varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table category_games (
        id_category integer not null,
        id_games integer not null
    ) engine=InnoDB;

    create table details_cart (
        id integer not null auto_increment,
        id_carts integer,
        id_games integer,
        quantity integer not null,
        primary key (id)
    ) engine=InnoDB;

    create table details_order (
        id integer not null auto_increment,
        id_games integer,
        id_orders integer,
        price_at_time float(53),
        quantity integer,
        primary key (id)
    ) engine=InnoDB;

    create table editors (
        id integer not null auto_increment,
        name varchar(255) not null,
        website varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table games (
        id integer not null auto_increment,
        id_editors integer,
        max_game_time integer,
        max_player_number integer,
        min_age integer,
        min_game_time integer,
        min_player_number integer,
        price float(53) not null,
        stock_quantity integer not null,
        pubblication_date datetime(6),
        description varchar(255),
        name varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table orders (
        id integer not null auto_increment,
        id_pay_cards integer,
        id_users integer,
        total_ammount float(53),
        created_at datetime(6),
        updated_at datetime(6),
        order_status varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table pay_cards (
        active bit not null,
        cvv integer,
        id integer not null auto_increment,
        id_users integer,
        created_at datetime(6),
        expiration_date datetime(6),
        updated_at datetime(6),
        billing_address varchar(255),
        card_holder_name varchar(255),
        card_number varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table reviews (
        id integer not null auto_increment,
        id_games integer,
        id_users integer,
        score integer,
        created_at datetime(6),
        description varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table roles (
        id integer not null auto_increment,
        name varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table users (
        active bit not null,
        id integer not null auto_increment,
        id_role integer,
        email varchar(255) not null,
        pwd varchar(255) not null,
        username varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    alter table carts 
       add constraint UK1eyx4jkn3tmk5cc9itor35q84 unique (id_users);

    alter table authors_games 
       add constraint FKqgd6l1vsxbw40b80frhpds3a7 
       foreign key (id_games) 
       references games (id);

    alter table authors_games 
       add constraint FKfsl8vf60rqqg7anjj5bsp7nm7 
       foreign key (id_authors) 
       references authors (id);

    alter table carts 
       add constraint FKjgsx53mn11qckeaot8tedfo5q 
       foreign key (id_users) 
       references users (id);

    alter table category_games 
       add constraint FKd8lpt2nsj7aklcagslgceq7mv 
       foreign key (id_games) 
       references games (id);

    alter table category_games 
       add constraint FKjow4gv74b6mf6q9y82u8adw6x 
       foreign key (id_category) 
       references categories (id);

    alter table details_cart 
       add constraint FKe5au5adhpsien8khafffev9co 
       foreign key (id_carts) 
       references carts (id);

    alter table details_cart 
       add constraint FK4pmb9omoi4mx9no0ycpduwl0q 
       foreign key (id_games) 
       references games (id);

    alter table details_order 
       add constraint FKkbafp62gsltnf9n44pjagx8w5 
       foreign key (id_games) 
       references games (id);

    alter table details_order 
       add constraint FKbvxfkfdne3bp5je3swrwrlfrn 
       foreign key (id_orders) 
       references orders (id);

    alter table games 
       add constraint FKonisfsdq4l9lupdi6dd8th74s 
       foreign key (id_editors) 
       references editors (id);

    alter table orders 
       add constraint FKrlv1mu9c3hmlvlxmfeu1gg57t 
       foreign key (id_pay_cards) 
       references pay_cards (id);

    alter table orders 
       add constraint FKhur7mikdwaqa7j9rnu62s4bp 
       foreign key (id_users) 
       references users (id);

    alter table pay_cards 
       add constraint FK1w26uiumo01kcx0hnck9ykary 
       foreign key (id_users) 
       references users (id);

    alter table reviews 
       add constraint FKa5wp6deva7rmqoahsk7kuq8ov 
       foreign key (id_games) 
       references games (id);

    alter table reviews 
       add constraint FKhw3ftxf3l8wgn9uo2j3fjdpph 
       foreign key (id_users) 
       references users (id);

    alter table users 
       add constraint FKt92dgi4412ywy3u8tm9jwdya5 
       foreign key (id_role) 
       references roles (id);
