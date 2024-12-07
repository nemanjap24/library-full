-- Drop and recreate "users" table
DROP TABLE IF EXISTS "users";
CREATE TABLE "users" (
                         "user_id" INTEGER NOT NULL UNIQUE ,
                         "username" TEXT NOT NULL UNIQUE,
                         "password" TEXT NOT NULL,
                         "role_id" INTEGER NOT NULL,
                         "email" TEXT NOT NULL UNIQUE,
                         PRIMARY KEY("user_id" AUTOINCREMENT)
);

-- Drop and recreate "books" table
DROP TABLE IF EXISTS "books";
CREATE TABLE "books" (
                         "book_id" INTEGER NOT NULL,
                         "title" TEXT NOT NULL,
                         "author" TEXT NOT NULL,
                         "isbn" TEXT NOT NULL,
                         "available_copies" INTEGER NOT NULL,
                         PRIMARY KEY("book_id" AUTOINCREMENT)
);

-- Drop and recreate "roles" table
DROP TABLE IF EXISTS "roles";
CREATE TABLE "roles" (
                         "roles_id" INTEGER NOT NULL,
                         "role_name" TEXT NOT NULL UNIQUE,
                         PRIMARY KEY("roles_id" AUTOINCREMENT)
);

-- Drop and recreate "transaction" table
DROP TABLE IF EXISTS "transaction";
CREATE TABLE "transaction" (
                               "transaction_id" INTEGER NOT NULL,
                               "user_id" INTEGER NOT NULL,
                               "book_id" INTEGER NOT NULL,
                               "action" TEXT NOT NULL,
                               "date" TEXT NOT NULL,
                               PRIMARY KEY("transaction_id" AUTOINCREMENT),
                               FOREIGN KEY("book_id") REFERENCES "books"("book_id"),
                               FOREIGN KEY("user_id") REFERENCES "users"("user_id")
);
