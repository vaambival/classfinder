# Solution

## Constraints

Solution is wrote with Java 8.

File with classes must be correct, otherwise the correct operation of the program is not guaranteed.
Correct means that file don't contain any symbols except English letters, dots as package delimiter and spaces at the ends or/and at the start.

Pattern can contains only letters, dots, spaces and asterisks with some constraints.
 
Dots cannot be last non-space symbol.

Empty pattern string is not valid.

Space cannot be inside of pattern. Pattern can start with space(s) or end with space(s).

## How to run class-finder

Before you'll run class-finder you should make sure that the program has rights to create a directory.

Please use next command:

```
$ ./class-finder <filename> '<pattern>'
```

where

`$` - prompt for input (it may be '>>' or other),

`./class-finder` - name of file with bash script, it is in root directory of project. Please change current directory to the root directory,

`<filename>` - you should change it on your file's path, which contains the list of files,

`<pattern>` - some pattern for search relevant classes. You should follow the rules. You may find it in constraints section higher. 
If you want use space-char, you should separate the pattern in quotation marks. For example:
```./class-finder classes.txt "FBar "```

## Tests

There are several tests for developer. To run the tests, you need the installed maven.

Use next command to run tests:

```mvn clean test```

# Codeborne homework

The goal of this homework is to implement class finder functionality
in a similar way to the Intellij IDEA Ctrl+N search.

You can use any language of your choice for this task.

You have to implement a program that can be executed from the command line:

```
./class-finder <filename> '<pattern>'
```

where `<filename>` refers to the file containing class names separated by line breaks, e.g.:

```
a.b.FooBarBaz
c.d.FooBar
```

Search pattern `<pattern>` must include class name camelcase upper case letters
in the right order and it may contain lower case letters to narrow down the search results,
for example `'FB'`, `'FoBa'` and `'FBar'` searches must all match
`a.b.FooBarBaz` and `c.d.FooBar` classes.

Upper case letters written in the wrong order will not find any results, for example
`'BF'` will not find `c.d.FooBar`.

If the search pattern consists of only lower case characters then the search becomes
case insensitive (`'fbb'` finds `FooBarBaz` but `'fBb'` will not).

If the search pattern ends with a space `' '` then the last word in the pattern must
also be the last word of the found class name (`'FBar '` finds `FooBar` but not `FooBarBaz`).

The search pattern may include wildcard characters `'*'` which match missing letters
(`'B*rBaz'` finds `FooBarBaz`i but `BrBaz` does not).

The found class names must be sorted in alphabetical order ignoring package names
(package names must still be included in the output).

Solution limitations:
- Regexp must not be used.
- Use of other libraries (other than the language itself) is prohibited.

Solution requirements:
- Unit tests must be present. For unit tests you are allowed to use libraries
(such as JUnit, Rspec, Jasmine and equivalent)

It is good enough if the solution implements the functionality described in this file.
To get familiar with the Intellij IDEA class finder functionality you can try it
out in the IDE but be informed that the real search has some extra complexity
not described here.

Included with this homework are primitive examples of command-line programs in different languages.
You do not have to follow their style or use them at all.

```
./class-finder-<language> classes.txt 'FooBar'
```
