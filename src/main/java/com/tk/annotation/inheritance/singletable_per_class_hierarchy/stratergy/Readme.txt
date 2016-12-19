This package uses @Entity at all classes and doesn't use @MappedSuperclass and also class
is not abstract, saving using persist() fails and has to be use merge() here

http://docs.oracle.com/javaee/6/tutorial/doc/bnbqn.html