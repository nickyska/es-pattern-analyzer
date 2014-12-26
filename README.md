es-pattern-analyzer
===================

This pluging for ES extends lucine PatternTokenizer.
Instead of indexing TERMS it will index patterns.

Very usefull when one doesn't need to do a free text querry search but does need to figure out how many
of the terms were found int the stream.

Example:

Text arrives with bunch of different account numbers, credit cards or SSN.
Instead of indexing the terms it will idnex the Regex patterns of each word

analyzer is a nice place to hide ssn : 123-74-7894 and also acocunt number 123456

If we use regex: [0-9]{3}-[0-9]{2}-[0-9]{4} for "SSN" and Regex [0-9]{6} for "Acount number" then the output is

Index in ES : SSN and Acount number.






