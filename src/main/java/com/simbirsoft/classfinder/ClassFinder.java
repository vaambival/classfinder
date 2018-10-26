package com.simbirsoft.classfinder;

import com.simbirsoft.classfinder.dto.ClassName;
import com.simbirsoft.classfinder.factory.RuleFactory;
import com.simbirsoft.classfinder.rule.SearchRule;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

class ClassFinder {
    public static void main(String[] args) {
        if (args.length != 2) {
            help();
            System.exit(1);
        }
        try (Stream<String> stream = Files.lines(Paths.get(args[0]))) {
            SearchRule rule = RuleFactory.createRule(args[1]);
            stream
                    .filter(word -> !word.isEmpty())
                    .map(ClassName::new)
                    .filter(className -> match(className, rule))
                    .sorted()
                    .forEach(System.out::println);
        } catch (IOException exc) {
            System.out.println("Input error. Please check path to file.");
        } catch (SecurityException exc) {
            System.out.println("Access denied error. Please check rights for reading file.");
        } catch (RuntimeException exc) {
            System.out.println("Your pattern is not correct. Reason:\n" + exc.getMessage());
        }
    }

    public static boolean match(ClassName className, SearchRule rule) {
        return rule.test(className);
    }

    private static void help() {
        System.out.println(HELP_MESSAGE);
    }

    private static final String HELP_MESSAGE = "Please use next command:\n" +
            "$ ./class-finder <filename> '<pattern>'\n" +
            "where\n$ - prompt for input (it may be '>>' or other),\n" +
            "./class-finder - name of file with bash script, it is in root directory of project. " +
            "Please change current directory to the root directory,\n" +
            "<filename> - you should change it on your file's path, which contains the list of files,\n" +
            "<pattern> - some pattern for search relevant classes. You should follow the rules. You may find it in " +
            "Readme file. If you want use space-char, you should separate the pattern in quotation marks.\n" +
            "For example:\n" +
            "./class-finder classes.txt \"FBar \"\n";
}
