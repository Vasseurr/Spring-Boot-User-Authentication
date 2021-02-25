use userlogindb;
INSERT INTO user(email, password, firstName, lastName)VALUES("jackmic@gmail.com", "jack123", "Jack", "Mic");
INSERT INTO user(email, password, firstName, lastName)VALUES("Harrison45@gmail.com", "harrison12", "Harrison", "Michael");
INSERT INTO user(email, password, firstName, lastName)VALUES("AlexRob@gmail.com", "alexrobb", "Alex", "Robby");

SELECT * FROM userlogindb.user;