BEGIN TRANSACTION;

INSERT INTO `roles` (role_name) VALUES
                                    ('admin'),
                                    ('librarian'),
                                    ('member');

INSERT INTO `users` (username, password, role_id, email) VALUES
                                                             ('admin_user', 'password123', 1, 'admin@example.com'),
                                                             ('librarian_john', 'libpass456', 2, 'john.librarian@example.com'),
                                                             ('member_smith', 'member789', 3, 'smith.member@example.com'),
                                                             ('member_jane', 'securepass123', 3, 'jane.doe@example.com');

INSERT INTO `books` (title, author, isbn, available_copies) VALUES
                                                                ('To Kill a Mockingbird', 'Harper Lee', '9780061120084', 5),
                                                                ('1984', 'George Orwell', '9780451524935', 3),
                                                                ('The Great Gatsby', 'F. Scott Fitzgerald', '9780743273565', 4),
                                                                ('The Catcher in the Rye', 'J.D. Salinger', '9780316769488', 2);

INSERT INTO `transaction` (user_id, book_id, action, date) VALUES
                                                               (3, 1, 'borrow', '2024-12-01'),
                                                               (3, 2, 'borrow', '2024-12-03'),
                                                               (4, 3, 'borrow', '2024-12-05'),
                                                               (3, 1, 'return', '2024-12-06'),
                                                               (4, 3, 'return', '2024-12-07');

COMMIT;
