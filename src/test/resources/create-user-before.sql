delete from USER;

insert into USER
values (1, 'Max', '$2y$12$chQ6sAxPaGok/EN0bnL2SODdKmzy6lutYeTWM2naKo1voKrWeuq8S','$2y$12$chQ6sAxPaGok/EN0bnL2SODdKmzy6lutYeTWM2naKo1voKrWeuq8S'),
       (2, 'Alex', '$2y$12$AT5UkfuYgfWVLC4oabo9UOx7YEodesdbjigFV9.uQhr9fnhxZTEhK','$2y$12$AT5UkfuYgfWVLC4oabo9UOx7YEodesdbjigFV9.uQhr9fnhxZTEhK');

alter sequence hibernate_sequence restart with 3;
