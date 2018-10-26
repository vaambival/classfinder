package com.simbirsoft.classfinder;

import com.simbirsoft.classfinder.dto.ClassName;
import com.simbirsoft.classfinder.rule.*;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ClassFinderUT {
    private final ClassName fooBarBaz = new ClassName("a.b.FooBarBaz");
    private final ClassName fooBar = new ClassName("c.d.FooBar");
    private final ClassName myClass = new ClassName("e.f.MyClass");
    private final ClassName fooFoaBar = new ClassName("FooFoaBar");
    private final ClassName inputStreamReader = new ClassName("InputStreamReader");
    private final ClassName ignoreAllErrorHandler = new ClassName("IgnoreAllErrorHandler");
    private final ClassName javaDotUtilDotList = new ClassName("java.util.List");
    private final ClassName javaxDotSwingDotAbstractAction = new ClassName("javax.swing.AbstractAction");
    private final ClassName rearer = new ClassName("Rearer");

    @Test
    public void testSpaceAtTheEndHandlerWithWildcard() {
        assertTrue(ClassFinder.match(inputStreamReader,
                new SpaceAtTheEndHandler(new CamelCaseRule("SR*der* "))));
        assertTrue(ClassFinder.match(rearer,
                new SpaceAtTheEndHandler(new CamelCaseRule("Re*re*r "))));
        assertTrue(ClassFinder.match(rearer,
                new SpaceAtTheEndHandler(new CaseInsensitiveRule("re*re*r "))));
        assertTrue(ClassFinder.match(inputStreamReader,
                new SpaceAtTheEndHandler(new CaseInsensitiveRule("sr*der* "))));
    }

    @Test
    public void testSpaceAtTheEndHandler() {
        assertTrue(ClassFinder.match(fooBarBaz,
                new SpaceAtTheEndHandler(new CamelCaseRule("FBBaz "))));
        assertTrue(ClassFinder.match(fooBar,
                new SpaceAtTheEndHandler(new CaseInsensitiveRule("fbar "))));
        assertTrue(ClassFinder.match(fooFoaBar,
                new SpaceAtTheEndHandler(new CaseInsensitiveRule("fbar "))));

        assertFalse(ClassFinder.match(fooBarBaz,
                new SpaceAtTheEndHandler(new CaseInsensitiveRule("fbar "))));
    }

    @Test
    public void testPackageNameHandling() {
        assertTrue(ClassFinder.match(javaDotUtilDotList,
                new PackageNameHandler(new CamelCaseRule(), "ja.ut.List")));

        assertTrue(ClassFinder.match(javaxDotSwingDotAbstractAction,
                new PackageNameHandler(new CamelCaseRule(), "jav*x.sw*g.Abst")));
        assertTrue(ClassFinder.match(javaxDotSwingDotAbstractAction,
                new PackageNameHandler(new CamelCaseRule(), "jav*ax.sw*g.Abst")));
        assertTrue(ClassFinder.match(javaxDotSwingDotAbstractAction,
                new PackageNameHandler(new CamelCaseRule(), "jav*ax.sw*g.Abst")));

        assertFalse(ClassFinder.match(javaDotUtilDotList,
                new PackageNameHandler(new CamelCaseRule(), "jaa.ut.List")));
        assertFalse(ClassFinder.match(javaDotUtilDotList,
                new PackageNameHandler(new CamelCaseRule(), "jav*x.sw*tg.Abst")));
    }

    @Test
    public void testIgnoreAllErrorHandlerCaseInsensitive() {
        assertTrue(ClassFinder.match(ignoreAllErrorHandler, new CaseInsensitiveRule("reaer")));
    }

    @Test
    public void testInputStreamReaderCamelCase() {
        assertTrue(ClassFinder.match(inputStreamReader, new CamelCaseRule("npuStreamReader")));
    }

    @Test
    public void testInputStreamReaderCaseInsensitive() {
        assertTrue(ClassFinder.match(inputStreamReader, new CaseInsensitiveRule("isr")));
        assertTrue(ClassFinder.match(inputStreamReader, new CaseInsensitiveRule("inputstreamreader")));
        assertTrue(ClassFinder.match(inputStreamReader, new CaseInsensitiveRule("putstreamreader")));
        assertTrue(ClassFinder.match(inputStreamReader, new CaseInsensitiveRule("putsrea")));
    }

    @Test
    public void classFinderMatchShouldFindFoa() {
        SearchRule rule = new CamelCaseRule("Foa");

        assertTrue(ClassFinder.match(fooFoaBar, rule));
    }

    @Test
    public void classFinderMatchShouldFindFB() {
        SearchRule rule = new CamelCaseRule("FB");

        assertTrue(ClassFinder.match(fooBarBaz, rule));
        assertTrue(ClassFinder.match(fooBar, rule));
        assertFalse(ClassFinder.match(myClass, rule));
    }

    @Test
    public void classFinderMatchShouldFindFoBa() {
        SearchRule rule = new CamelCaseRule("FoBa");

        assertTrue(ClassFinder.match(fooBarBaz, rule));
        assertTrue(ClassFinder.match(fooBar, rule));
        assertFalse(ClassFinder.match(myClass, rule));
    }

    @Test
    public void classFinderMatchShouldFindFBar() {
        SearchRule rule = new CamelCaseRule("FBar");

        assertTrue(ClassFinder.match(fooBarBaz, rule));
        assertTrue(ClassFinder.match(fooBar, rule));
        assertFalse(ClassFinder.match(myClass, rule));
    }

    @Test
    public void classFinderMatchShouldNotFindBF() {
        SearchRule rule = new CamelCaseRule("BF");

        assertFalse(ClassFinder.match(fooBarBaz, rule));
        assertFalse(ClassFinder.match(fooBar, rule));
        assertFalse(ClassFinder.match(myClass, rule));
    }

    @Test
    public void classFinderMatchShouldFindfbb() {
        SearchRule rule = new CaseInsensitiveRule("fbb");

        assertTrue(ClassFinder.match(fooBarBaz, rule));
        assertFalse(ClassFinder.match(fooBar, rule));
        assertFalse(ClassFinder.match(myClass, rule));
    }

    @Test
    public void classFinderMatchShouldNotFindfBb() {
        SearchRule rule = new CamelCaseRule("fBb");

        assertFalse(ClassFinder.match(fooBarBaz, rule));
        assertFalse(ClassFinder.match(fooBar, rule));
        assertFalse(ClassFinder.match(myClass, rule));
    }

    @Test
    public void classFinderMatchShouldFindFBarWithSpace() {
        SearchRule rule = new SpaceAtTheEndHandler(new CamelCaseRule("FBar "));

        assertFalse(ClassFinder.match(fooBarBaz, rule));
        assertTrue(ClassFinder.match(fooBar, rule));
        assertFalse(ClassFinder.match(myClass, rule));
    }

    @Test
    public void classFinderMatchShouldFindBrBazWithWildcard() {
        SearchRule rule = new CamelCaseRule("B*rBaz");

        assertTrue(ClassFinder.match(fooBarBaz, rule));
        assertFalse(ClassFinder.match(fooBar, rule));
        assertFalse(ClassFinder.match(myClass, rule));
    }

    @Test
    public void classFinderMatchShouldNotFindBrBaz() {
        SearchRule rule = new CamelCaseRule("BrBaz");

        assertFalse(ClassFinder.match(fooBarBaz, rule));
        assertFalse(ClassFinder.match(fooBar, rule));
        assertFalse(ClassFinder.match(myClass, rule));
    }
}
