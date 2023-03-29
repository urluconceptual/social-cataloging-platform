# social-cataloging-platform
## Java project for Advanced OOP course. Implemented an app that works like a social cataloging platform for books, emulating Goodreads' functionalities.

1st stage:

## Defining the system  
### Types of objects:  
1. User <- extended by Reader, Author, Librarian  
2. Shelf <- extended by PersonalShelf, SharedShelf  
3. Book  
4. Edition  
5. Review  
6. Connection  
7. ReadingChallenge  
8. BookClub  
9. Message 
### Functionalities:  
1. A reader has by default 3 lists of books that they cannot remove: want-to-read, reading, read. They can add or remove books from these lists.
2. A reader can create new personal shelves, that act as custom lists of books. The shelves are either public or private, and their visibility can be changed at any time.
3. A reader can collaborate with their friends on shared shelves, where all the collaborators can edit the shelf.
4. A reader can set their reading goals as a Reading Challenge and see how much of it they completed, as the status will be updated every time they add a new book to the "read" list.
5. A reader can add reviews to the books they read, and their average rating will be visible on their profile, as well as a list of their top-rated books.
6. A reader can also select the edition of the book they read but it is not mandatory.
7. A reader can join a BookClub and leave messages there.
8. An author can use the platform to promote their books by adding them to their profile.
9. An author has an average rating the users left for their books and it is visible on their page.
10. A librarian can create curated lists of recommendations and book clubs. They manage the contents of the lists, the members of the bookclubs, and the messages on their bookclubs' chats.
11. Two users can have a connection that can be either: mutual(friends), or one user following the other. A reader can follow any type of user, but can only befriend other readers.
12. Two users that have a mutual connection can compare books to see what they have in common.