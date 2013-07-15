kiama-sequence-diagrams
=======================

This project uses the Kiama framework to build a parser and a semantic analysis for a sequence diagram DSL.
You can declare simple sequence diagrams with the following syntax:

<pre><code>diagram Name
classes:
    class TypA:
        method foo;
        method bar;
    class TypB:
        method foobar;
objects:
    TypA a;
    TypB b;
interactions:
    a -> b.foobar;
    b -> a.foo;
<code/></pre>

The semantic analysis checks for the correct declaration of classes, objects and interactions and prints an error message if something is not correct.
