# Social Cataloging Platform :closed_book:

Social cataloging platform application running in CLI, implemented using Java 19, with similar functionality to
Goodreads: a place for people to review books and connect with other readers and authors.

## Table of Contents :bookmark_tabs:

* [Stage 1](#Stage-one)
* [Stage 2](#Stage-two)
* [Class Hierarchy](#Class-Hierarchy-bar_chart)
* [Functionality](#Functionality)
* [Screenshots](#Screenshots-camera)  


## Stage :one:

### Defining and Implementing the System :book:

Creating a list of 10 actions possible in the system and at least 8 kinds of objects.
The application features:

* Simple classes featuring private/protected fields with accessor methods (getters/setters);
* Collections capable of managing the aforementioned object, with at least one such collection being sorted;
* Use of inheritance for creating additional classes and for use inside the Collections
* Service classes to expose the system's functionality
* Service calls from the Menu classes, representing the application's interface for the user.

### Class Hierarchy :bar_chart:

#### 1. Abstract Classes:

* AbstractEntity
* Shelf
* User

#### 2. Regular Classes:

* Author - derived from User
* Librarian - derived from User
* Reader - derived from User
* Book
* Connection
* Message
* PersonalShelf - derived from Shelf
* SharedShelf - derived from Shelf
* Review
* ReadingChallenge

#### 3. Records:

* Message

#### 4. Enums:

* BookGenre
* ShelfType
* UserType

## Functionality

### 0. General Functionality :grey_exclamation:

You can log in or register as a new user in the command-line interface.
Afterwards, you'll be prompted by an interactive menu with the options at your disposal.

### 1. Reader Functionality :page_with_curl:

As a _reader_, you can:

* Connect with others users: readers or authors;
* Add or remove books from one of your shelves;
* Shelves are either personal or shared, between you and multiple other readers _(there's no limit on how many users can
  share a shelf, there's always room for more!)_;
* Set reading challenges;
* Check top books, ranked by own rating.

### 2. Author Functionality :black_nib:

As an _author_, you can:

* Use the platform to promote your books;
* Completely remove your book from the platform;
* Check your current followers;
* Check your average rating, determined as the mean of the ratings that your books have gotten.

### 3. Librarian Functionality :black_nib:

As a _librarian_, you can:

* Use the platform to recommend books;
* Check your current followers;
* Add posts on your personal book club, to share your book knowledge with the readers.

## Stage :two:

### Extending the System :book:

The application now features:

* A template for the menus, using the template method pattern;

* A database connection, using JDBC;
* CRUD operations on the database, using the repository pattern;
* Singleton generics for these operations;

* A listener functionality, to store the actions performed in a log file;
* A logger functionality, that notifies the user that their actions have been logged;

## Screenshots :camera:

To do! Add screenshots.
