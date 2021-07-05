# Assignment: Library Project

A website for a library where you can login as a librarian a patron who can checkout and return books. Utilizes JDBC, Servlets, JSPs, and Maven.
By Nikita Tran, David Knittel, Austin Brooks for Cognixia Full Stack Java course

## Completed Requirements for Patron User 
- [x] Sign up for an account via form

When logged in, patron can:
- [x] View all books in the library and check out the available ones
- [x] View all previously checked out books
- [x] View all currently checked out books
- [x] Return checked out books

## Completed Extensions
Patron
- [x] Update patron's name, username, and password
- [ ] Frozen patron account upon first creation

Librarian
  - [ ] Add in new books
  - [ ] Update book title and description
  - [ ] Approve accounts for patrons
  - [ ] Update librarian username and password

## Extra features 
- User input verification on forms (error banner when bad input is submitted)
  - Bad input = empty fields, user/pass nonexistant in database (login page); empty fields or less than 3 characters (create/update account page)
- 404 error page
- Landing page with slideshow/carousel upon log-in
