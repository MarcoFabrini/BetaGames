
    alter table authors_games 
       drop 
       foreign key FKqgd6l1vsxbw40b80frhpds3a7;

    alter table authors_games 
       drop 
       foreign key FKfsl8vf60rqqg7anjj5bsp7nm7;

    alter table carts 
       drop 
       foreign key FKjgsx53mn11qckeaot8tedfo5q;

    alter table category_games 
       drop 
       foreign key FKd8lpt2nsj7aklcagslgceq7mv;

    alter table category_games 
       drop 
       foreign key FKjow4gv74b6mf6q9y82u8adw6x;

    alter table details_cart 
       drop 
       foreign key FKe5au5adhpsien8khafffev9co;

    alter table details_cart 
       drop 
       foreign key FK4pmb9omoi4mx9no0ycpduwl0q;

    alter table details_order 
       drop 
       foreign key FKkbafp62gsltnf9n44pjagx8w5;

    alter table details_order 
       drop 
       foreign key FKbvxfkfdne3bp5je3swrwrlfrn;

    alter table games 
       drop 
       foreign key FKonisfsdq4l9lupdi6dd8th74s;

    alter table orders 
       drop 
       foreign key FKrlv1mu9c3hmlvlxmfeu1gg57t;

    alter table orders 
       drop 
       foreign key FKhur7mikdwaqa7j9rnu62s4bp;

    alter table pay_cards 
       drop 
       foreign key FK1w26uiumo01kcx0hnck9ykary;

    alter table reviews 
       drop 
       foreign key FKa5wp6deva7rmqoahsk7kuq8ov;

    alter table reviews 
       drop 
       foreign key FKhw3ftxf3l8wgn9uo2j3fjdpph;

    alter table users 
       drop 
       foreign key FKt92dgi4412ywy3u8tm9jwdya5;

    drop table if exists authors;

    drop table if exists authors_games;

    drop table if exists carts;

    drop table if exists categories;

    drop table if exists category_games;

    drop table if exists details_cart;

    drop table if exists details_order;

    drop table if exists editors;

    drop table if exists games;

    drop table if exists orders;

    drop table if exists pay_cards;

    drop table if exists reviews;

    drop table if exists roles;

    drop table if exists users;
