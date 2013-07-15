kiama-sequence-diagrams
=======================

This project uses the Kiama framework to build a parser and a semantic analysis for a sequence diagram DSL.<br/>
The syntax for the DSL is as follows:

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

The semantic analysis checks for the correct declaration of classes, objects and interactions and prints a corresponding error message if something is not correct.
