ALTER DATABASE `app_db` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;

create table user
(
    user_id             int auto_increment
        primary key,
    user_name           text              null,
    user_email          text              not null,
    user_password       text              not null,
    user_weight         float             null,
    user_height         float             null,
    user_permissions    text              not null,
    user_date_of_birth  date              not null,
    user_gender         text              not null,
    user_exercise_level float default 1.2 not null
);

create table exercise
(
    exercise_id       int auto_increment
        primary key,
    exercise_name     text          not null,
    exercise_info     text          null,
    author            int           not null,
    exercise_category int           null,
    category          int           null,
    exercise_status   int default 2 not null,
    exercise_name_pl  text          null,
    exercise_info_pl  text          null,
    constraint Exercise_user__fk
        foreign key (author) references user (user_id)
);

create table exercise_category
(
    ec_id              int auto_increment
        primary key,
    ec_category        text not null,
    exercise_connected int  null,
    ec_category_pl     text null,
    constraint exercise_category_fk
        foreign key (exercise_connected) references exercise (exercise_id)
);

alter table exercise
    add constraint Exercise_ex_category_fk
        foreign key (category) references exercise_category (ec_id);

create table exercise_log
(
    exercise_log_id        int auto_increment
        primary key,
    exercise_log_date      date  not null,
    exercise_log_trainee   int   not null,
    exercise_log_weight    float not null,
    exercise_log_reps      int   null,
    exercise_log_reference int   not null,
    constraint exercise_log_exercise_exercise_id_fk
        foreign key (exercise_log_reference) references exercise (exercise_id),
    constraint exercise_log_user_fk
        foreign key (exercise_log_trainee) references user (user_id)
);

create table meal
(
    meal_id             int auto_increment
        primary key,
    meal_name           text          not null,
    meal_name_pl        text          null,
    meal_protein        float         not null,
    meal_carbs          float         not null,
    meal_fat            float         not null,
    meal_portion_weight float         not null,
    meal_user           int           not null,
    meal_status         int default 2 not null,
    meal_calories       float         not null,
    constraint meal_user_user_id_fk
        foreign key (meal_user) references user (user_id)
);

create table meal_log
(
    meal_log_id           int auto_increment
        primary key,
    meal_log_user         int   not null,
    meal_log_portionCount float not null,
    meal_log_reference    int   not null,
    meal_log_date         date  not null,
    constraint meal_log_meal_fk
        foreign key (meal_log_reference) references meal (meal_id),
    constraint meal_log_user_fk
        foreign key (meal_log_user) references user (user_id)
);

create table user_diet
(
    user_diet_id                 int auto_increment
        primary key,
    user_diet_protein_percentage int default 30 not null,
    user_diet_carbs_percentage   int default 50 not null,
    user_diet_fat_percentage     int default 20 not null,
    user_diet_user               int            not null,
    user_diet_calorie_diff       int default 0  not null,
    user_diet_total_calories     double         not null,
    constraint user_diet_user_fk
        foreign key (user_diet_user) references user (user_id)
);

create table user_preferences
(
    preference_id       int auto_increment
        primary key,
    preference_language text null,
    preference_layout   text null,
    preference_user     int  not null
);

create table weight_log
(
    weight_log_id             int auto_increment
        primary key,
    weight_log_current_weight float not null,
    weight_log_date           date  not null,
    weight_log_trainee        int   not null
);

create table workout
(
    workout_id             int auto_increment
        primary key,
    workout_name           text          not null,
    workout_info           text          not null,
    workout_author         int default 1 not null,
    workout_exerciseAmount int default 0 null,
    workout_status         int default 2 not null,
    workout_name_pl        text          null,
    workout_info_pl        text          null,
    constraint Workout_user__fk
        foreign key (workout_author) references user (user_id)
);

create table workout_exercise
(
    workout_id  int not null,
    exercise_id int not null,
    primary key (exercise_id, workout_id),
    constraint workout_exercise_fk1
        foreign key (workout_id) references workout (workout_id),
    constraint workout_exercise_fk2
        foreign key (exercise_id) references exercise (exercise_id)
);

INSERT INTO app_db.user (user_id, user_name, user_email, user_password, user_weight, user_height, user_permissions, user_date_of_birth, user_gender, user_exercise_level) VALUES (1, 'Jerzy Paslawski', 'jpas@gmail.com', 'admin', 71.5, 178, 'ROLE_ADMIN', '1998-04-30', 'Male', 1.375);
INSERT INTO app_db.user_diet (user_diet_id, user_diet_protein_percentage, user_diet_carbs_percentage, user_diet_fat_percentage, user_diet_user, user_diet_calorie_diff, user_diet_total_calories) VALUES (1, 35, 50, 15, 1, -200, 2464);


INSERT INTO app_db.exercise_category (ec_id, ec_category, exercise_connected, ec_category_pl) VALUES (1, 'Chest', null, 'Klatka piersiowa');
INSERT INTO app_db.exercise_category (ec_id, ec_category, exercise_connected, ec_category_pl) VALUES (2, 'Triceps', null, 'Triceps');
INSERT INTO app_db.exercise_category (ec_id, ec_category, exercise_connected, ec_category_pl) VALUES (3, 'Biceps', null, 'Biceps');
INSERT INTO app_db.exercise_category (ec_id, ec_category, exercise_connected, ec_category_pl) VALUES (4, 'Abs', null, N'Mięśnie brzucha');
INSERT INTO app_db.exercise_category (ec_id, ec_category, exercise_connected, ec_category_pl) VALUES (5, 'Back', null, 'Plecy');
INSERT INTO app_db.exercise_category (ec_id, ec_category, exercise_connected, ec_category_pl) VALUES (6, 'Calves', null, N'Łydki');
INSERT INTO app_db.exercise_category (ec_id, ec_category, exercise_connected, ec_category_pl) VALUES (7, 'Forearms', null, N'Przedramię');
INSERT INTO app_db.exercise_category (ec_id, ec_category, exercise_connected, ec_category_pl) VALUES (8, 'Glutes', null, N'Pośladki');
INSERT INTO app_db.exercise_category (ec_id, ec_category, exercise_connected, ec_category_pl) VALUES (9, 'Legs', null, 'Nogi');
INSERT INTO app_db.exercise_category (ec_id, ec_category, exercise_connected, ec_category_pl) VALUES (10, 'Shoulders', null, 'Barki');

INSERT INTO app_db.exercise (exercise_id, exercise_name, exercise_info, author, exercise_category, category, exercise_status, exercise_name_pl, exercise_info_pl) VALUES (1, 'Bench Press', 'An upper-body weight training exercise in which the trainee presses a weight upwards while lying on a weight training bench.', 1, null, 1, 0, N'Wyciskanie na ławce', '');
INSERT INTO app_db.exercise (exercise_id, exercise_name, exercise_info, author, exercise_category, category, exercise_status, exercise_name_pl, exercise_info_pl) VALUES (2, 'Shoulder Press', 'Sit up straight on the bench. Grab the barbell and lift off the track. Lowe it to your shoulders.', 1, null, 10, 0, '', '');
INSERT INTO app_db.exercise (exercise_id, exercise_name, exercise_info, author, exercise_category, category, exercise_status, exercise_name_pl, exercise_info_pl) VALUES (21, 'Squat', '', 1, null, 9, 0, 'Przysiad', '');
INSERT INTO app_db.exercise (exercise_id, exercise_name, exercise_info, author, exercise_category, category, exercise_status, exercise_name_pl, exercise_info_pl) VALUES (22, 'Incline Dumbell Curl', 'The incline dumbbell curl is a bicep curl variation that placed the lifter in a position that does not allow the shoulder to become involved.', 1, null, 3, 0, '', '');
INSERT INTO app_db.exercise (exercise_id, exercise_name, exercise_info, author, exercise_category, category, exercise_status, exercise_name_pl, exercise_info_pl) VALUES (24, 'Farmer walks', ' This move builds the wrist and finger flexors, as well as engaging just about every other muscle in your body.', 1, null, 7, 0, 'Spacer farmera', '');
INSERT INTO app_db.exercise (exercise_id, exercise_name, exercise_info, author, exercise_category, category, exercise_status, exercise_name_pl, exercise_info_pl) VALUES (25, 'Pull-up bar hang', 'This bodyweight exercise helps build not just your wrist and finger flexors, but it’s a great lead-in to tackling scapular pull-ups and other pull-up variations.', 1, null, 7, 0, '', '');
INSERT INTO app_db.exercise (exercise_id, exercise_name, exercise_info, author, exercise_category, category, exercise_status, exercise_name_pl, exercise_info_pl) VALUES (26, 'Russian Twist', 'Start seated on the floor balancing on tailbone with legs bent and elevated so shins are parallel to floor, with ankles crossed, hands clasped at chest and torso leaned back and rotated to left side.', 1, null, 4, 0, '', '');
INSERT INTO app_db.exercise (exercise_id, exercise_name, exercise_info, author, exercise_category, category, exercise_status, exercise_name_pl, exercise_info_pl) VALUES (27, 'Side Plank Dips', 'Start in a left side plank with left forearm on the floor, parallel to top of mat, elbow under shoulder, right hand on hips, and right leg stacked on top of left. Lower hips toward the ground a couple inches, then come back up to start.', 1, null, 4, 0, '', '');
INSERT INTO app_db.exercise (exercise_id, exercise_name, exercise_info, author, exercise_category, category, exercise_status, exercise_name_pl, exercise_info_pl) VALUES (28, 'Hip thrust', 'Lie faceup with knees bent and feet hip-width apart. Place hands on the floor directly under your shoulders, fingers facing away from your body. Squeeze glutes and lift hips into a tabletop position.', 1, null, 8, 0, '', '');
INSERT INTO app_db.exercise (exercise_id, exercise_name, exercise_info, author, exercise_category, category, exercise_status, exercise_name_pl, exercise_info_pl) VALUES (29, 'Glute bridge', 'Lie faceup with knees bent and feet shoulder-width apart. Raise hips straight up off the floor, engaging glutes and bracing core. Lower down slowly, creating your own resistance.', 1, null, 8, 0, '', '');
INSERT INTO app_db.exercise (exercise_id, exercise_name, exercise_info, author, exercise_category, category, exercise_status, exercise_name_pl, exercise_info_pl) VALUES (30, 'Zottman Curl', 'In this movement, you hold a dumbbell in each hand and have a palms-up (supinated) grip on the way up and a palms-down (pronated) grip as you lower the weight, so all of your elbow flexors get hit!', 1, null, 3, 0, '', '');
INSERT INTO app_db.exercise (exercise_id, exercise_name, exercise_info, author, exercise_category, category, exercise_status, exercise_name_pl, exercise_info_pl) VALUES (31, 'Dumbbell Biceps Curl', 'Stand holding a dumbbell in each hand with your arms hanging by your sides. Ensure your elbows are close to your torso and your palms facing forward.', 1, null, 3, 0, 'Uginanie hantli', N'Usiądź na ławce prostej w rozkroku. Weź hantel do ręki, oprzyj łokieć o wewnętrzną część kolana i całkowicie wyprostuj ramię, aby swobodnie zwisało. Drugą rękę oprzyj na przeciwnym kolanie. Zginając łokieć unieś hantel w kierunku barku, następnie wróć do pozycji wyjściowej całkowicie prostując ramię.');
INSERT INTO app_db.exercise (exercise_id, exercise_name, exercise_info, author, exercise_category, category, exercise_status, exercise_name_pl, exercise_info_pl) VALUES (32, 'Triceps Pushdown', 'The triceps pushdown is an effective single-joint exercise for targeting the triceps. This single-joint movement allows lifters to isolate the muscles while minimizing shoulder involvement and additional joint stress.', 1, null, 2, 0, '', '');
INSERT INTO app_db.exercise (exercise_id, exercise_name, exercise_info, author, exercise_category, category, exercise_status, exercise_name_pl, exercise_info_pl) VALUES (33, 'Skullcrushers', 'The skull crusher is a triceps exercise that can develop the long head of the triceps brachii. This exercise can be done with a bar, cable system, and other free weights.', 1, null, 2, 0, '', '');
INSERT INTO app_db.exercise (exercise_id, exercise_name, exercise_info, author, exercise_category, category, exercise_status, exercise_name_pl, exercise_info_pl) VALUES (34, 'Pull-Up', 'Grab the handles of the pull-up station with your palms facing away from you and your arms fully extended. Your hands should be around shoulder-width apart. Squeeze your shoulder blades together, exhale and drive your elbows towards your hips to bring your chin above the bar. Lower under control back to the start position.', 1, null, 5, 0, N'Podciąganie', '');
INSERT INTO app_db.exercise (exercise_id, exercise_name, exercise_info, author, exercise_category, category, exercise_status, exercise_name_pl, exercise_info_pl) VALUES (48, 'Towel pull-up hang', 'Drape two small workout towels, shoulder-width apart, over a pull-up bar. Reach up and grab a towel in each hand with a tight grip. Engage your core and lift your feet off the floor, hanging with your your ankles crossed behind you for as long as you can. Rest and repeat.', 1, null, 10, 0, '', '');
INSERT INTO app_db.exercise (exercise_id, exercise_name, exercise_info, author, exercise_category, category, exercise_status, exercise_name_pl, exercise_info_pl) VALUES (49, 'Kettlebell Swings', 'Place a kettlebell a couple of feet in front of you. Stand with your feet slightly wider than shoulder-width apart and bend your knees to lean forward and grab the handle with both hands. With your back flat, engage your lats to pull the weight between your legs then drive your hips forward and explosively pull the kettlebell up to shoulder height with your arms straight in front of you. Return to the start position and repeat without pauses.', 1, null, 5, 0, '', '');
INSERT INTO app_db.exercise (exercise_id, exercise_name, exercise_info, author, exercise_category, category, exercise_status, exercise_name_pl, exercise_info_pl) VALUES (61, 'Dumbbell Squat and Press', 'Squat down until the tops of your thighs are parallel to the floor. As you lower, imagine that you’re screwing your feet into the floor by actively pressing your ankles, lower legs, and thighs outward.
Push your body up from the squat as you press the dumbbells directly above your shoulders. Your biceps should be by your ears. Lower the weights and repeat.', 1, null, 4, 2, 'Wyciskanie hantli z przysiadu', N'Stań prosto, trzymając hantle w rękach wyprostowanych po bokach tułowia.
Dociśnij łokcie do korpusu, unieś hantle tak blisko barków, jak to możliwe. Natychmiast zrób przysiad – Twoje uda muszą znaleźć się w pozycji co najmniej równoległej do podłoża.
Wstań i wyciśnij hantle nad głowę. To jedno powtórzenie. Powróć do pozycji startowej i powtórz.');

INSERT INTO app_db.meal (meal_id, meal_name, meal_name_pl, meal_protein, meal_carbs, meal_fat, meal_portion_weight, meal_user, meal_status, meal_calories) VALUES (1, 'Lays Oven Baked', 'Lays Oven Baked', 5.3, 70, 14, 100, 1, 0, 440);
INSERT INTO app_db.meal (meal_id, meal_name, meal_name_pl, meal_protein, meal_carbs, meal_fat, meal_portion_weight, meal_user, meal_status, meal_calories) VALUES (2, 'Muller Riso', 'Muller Riso', 3.4, 19.8, 2.4, 100, 1, 0, 115);
INSERT INTO app_db.meal (meal_id, meal_name, meal_name_pl, meal_protein, meal_carbs, meal_fat, meal_portion_weight, meal_user, meal_status, meal_calories) VALUES (3, 'Chicken Breast', N'Pierś z kurczaka', 21.5, 0, 1.3, 100, 1, 0, 98);
INSERT INTO app_db.meal (meal_id, meal_name, meal_name_pl, meal_protein, meal_carbs, meal_fat, meal_portion_weight, meal_user, meal_status, meal_calories) VALUES (4, 'Sirloin', N'Polędwica wołowa', 20.1, 0, 3.5, 100, 1, 0, 112);
INSERT INTO app_db.meal (meal_id, meal_name, meal_name_pl, meal_protein, meal_carbs, meal_fat, meal_portion_weight, meal_user, meal_status, meal_calories) VALUES (5, 'Apple juice', N'Sok jabłkowy', 0.3, 12, 0.3, 100, 1, 0, 52);
INSERT INTO app_db.meal (meal_id, meal_name, meal_name_pl, meal_protein, meal_carbs, meal_fat, meal_portion_weight, meal_user, meal_status, meal_calories) VALUES (6, 'Bread', 'Chleb', 9.3, 51.6, 1.4, 100, 1, 0, 258);
INSERT INTO app_db.meal (meal_id, meal_name, meal_name_pl, meal_protein, meal_carbs, meal_fat, meal_portion_weight, meal_user, meal_status, meal_calories) VALUES (8, 'Milk', 'Mleko', 3.1, 4.7, 0.5, 100, 1, 0, 36);
INSERT INTO app_db.meal (meal_id, meal_name, meal_name_pl, meal_protein, meal_carbs, meal_fat, meal_portion_weight, meal_user, meal_status, meal_calories) VALUES (9, 'Wheat flour', N'Mąka pszenna', 12.3, 71.4, 1.3, 100, 1, 0, 347);
INSERT INTO app_db.meal (meal_id, meal_name, meal_name_pl, meal_protein, meal_carbs, meal_fat, meal_portion_weight, meal_user, meal_status, meal_calories) VALUES (10, 'Potatoes', 'Ziemniaki', 1.9, 16.8, 0.1, 100, 1, 0, 79);
INSERT INTO app_db.meal (meal_id, meal_name, meal_name_pl, meal_protein, meal_carbs, meal_fat, meal_portion_weight, meal_user, meal_status, meal_calories) VALUES (11, 'Cornflakes', N'Płatki kukurydziane', 7.4, 82.6, 1.4, 100, 1, 0, 381);
INSERT INTO app_db.meal (meal_id, meal_name, meal_name_pl, meal_protein, meal_carbs, meal_fat, meal_portion_weight, meal_user, meal_status, meal_calories) VALUES (12, 'Salad', N'Sałata', 1.4, 1.5, 0.2, 100, 1, 0, 14);
INSERT INTO app_db.meal (meal_id, meal_name, meal_name_pl, meal_protein, meal_carbs, meal_fat, meal_portion_weight, meal_user, meal_status, meal_calories) VALUES (15, 'Snickers', 'Batonik Snickers', 4.4, 31, 11.5, 51, 1, 1, 246);