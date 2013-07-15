kiama-sequence-diagrams
=======================

This project uses the Kiama framework to build a parser and a semantic analysis for a sequence diagram DSL.
You can declare simple sequence diagrams with the following syntax:

diagram test
classes:
    class A:
        method foo;
        method bar;
    class B:
        method foobar;
objects:
    A a;
    B b;
interactions:
    a -> b.foobar;
    b -> a.foo;
    
The semantic analysis checks for the correct declaration of classes, objects and interactions.
