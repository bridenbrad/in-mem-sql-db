In-memory columnar SQL OLAP DB
======

Features:
* BNF + ANTLR SQL Compiler
* Abstract Syntax Tree 
* Data Flow Runtime Graph
* On-Heap & Off-Heap Allocators
* Snappy Compression
* Template based Code Generation for Primitive Types
* BitMap, MinMax Indexing
* Tokenization, Paging, Append-Only

Written back in 2013/2014. Worked on Amazon EC2 with 60 million rows at millisecond latency.

Some packages of interest:
* tirion-db-store/src/main/java/com/tirion/db/store/page
* tirion-db-sql-exec/src/main/java/com/tirion/db/sql/exec/operator/physical
* tirion-db-sql-ast/src/main/java/com/tirion/db/sql/ast





