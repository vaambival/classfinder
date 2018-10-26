package com.simbirsoft.classfinder;

import com.simbirsoft.classfinder.dto.ClassName;
import com.simbirsoft.classfinder.factory.RuleFactory;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ClassFinderIT {
    private final ClassName arrayList = new ClassName("java.util.ArrayList");
    private final ClassName concurrentHashMap = new ClassName("java.util.concurrent.ConcurrentHashMap");
    private final ClassName object = new ClassName("java.lang.Object");
    private final ClassName math = new ClassName("Math");
    private final ClassName jFrame = new ClassName("JFrame");
    private final ClassName jFrameFractal = new ClassName("JFrameFractal");
    private final ClassName cowSubList = new ClassName("COWSubList");
    private final ClassName radioButtonBorder = new ClassName("javax.swing.RadioButtonBorder");
    private final ClassName renderer = new ClassName("Renderer");

    @Test
    public void checkSimpleCamelCasePattern() {
        assertTrue(ClassFinder.match(arrayList, RuleFactory.createRule("AL")));
        assertTrue(ClassFinder.match(arrayList, RuleFactory.createRule("ArLi")));
        assertTrue(ClassFinder.match(concurrentHashMap, RuleFactory.createRule("CHM")));
        assertTrue(ClassFinder.match(concurrentHashMap, RuleFactory.createRule("rentHaM")));
        assertTrue(ClassFinder.match(object, RuleFactory.createRule("Obj")));
        assertTrue(ClassFinder.match(math, RuleFactory.createRule("Math")));
        assertTrue(ClassFinder.match(jFrame, RuleFactory.createRule("JFra")));
        assertTrue(ClassFinder.match(jFrameFractal, RuleFactory.createRule("JFrFr")));
        assertTrue(ClassFinder.match(jFrameFractal, RuleFactory.createRule("JFrac")));
        assertTrue(ClassFinder.match(cowSubList, RuleFactory.createRule("CL")));
        assertTrue(ClassFinder.match(cowSubList, RuleFactory.createRule("COLi")));
        assertTrue(ClassFinder.match(radioButtonBorder, RuleFactory.createRule("RBB")));
        assertTrue(ClassFinder.match(radioButtonBorder, RuleFactory.createRule("BuBo")));
        assertTrue(ClassFinder.match(renderer, RuleFactory.createRule("Rend")));

        assertFalse(ClassFinder.match(arrayList, RuleFactory.createRule("ALT")));
        assertFalse(ClassFinder.match(concurrentHashMap, RuleFactory.createRule("ChM")));
        assertFalse(ClassFinder.match(jFrameFractal, RuleFactory.createRule("JFraeF")));
        assertFalse(ClassFinder.match(cowSubList, RuleFactory.createRule("CoWSub")));
        assertFalse(ClassFinder.match(radioButtonBorder, RuleFactory.createRule("RBoBu")));
        assertFalse(ClassFinder.match(renderer, RuleFactory.createRule("ReRe")));
    }

    @Test
    public void checkSimpleCaseInsensitivePattern() {
        assertTrue(ClassFinder.match(arrayList, RuleFactory.createRule("al")));
        assertTrue(ClassFinder.match(arrayList, RuleFactory.createRule("arli")));
        assertTrue(ClassFinder.match(concurrentHashMap, RuleFactory.createRule("chm")));
        assertTrue(ClassFinder.match(concurrentHashMap, RuleFactory.createRule("rentham")));
        assertTrue(ClassFinder.match(object, RuleFactory.createRule("obj")));
        assertTrue(ClassFinder.match(math, RuleFactory.createRule("math")));
        assertTrue(ClassFinder.match(jFrame, RuleFactory.createRule("jfra")));
        assertTrue(ClassFinder.match(jFrameFractal, RuleFactory.createRule("jfrfr")));
        assertTrue(ClassFinder.match(jFrameFractal, RuleFactory.createRule("jfrac")));
        assertTrue(ClassFinder.match(cowSubList, RuleFactory.createRule("cl")));
        assertTrue(ClassFinder.match(cowSubList, RuleFactory.createRule("coli")));
        assertTrue(ClassFinder.match(radioButtonBorder, RuleFactory.createRule("rbb")));
        assertTrue(ClassFinder.match(radioButtonBorder, RuleFactory.createRule("bubo")));
        assertTrue(ClassFinder.match(renderer, RuleFactory.createRule("rend")));

        assertFalse(ClassFinder.match(arrayList, RuleFactory.createRule("alt")));
        assertFalse(ClassFinder.match(concurrentHashMap, RuleFactory.createRule("chshm")));
        assertFalse(ClassFinder.match(jFrameFractal, RuleFactory.createRule("jfraef")));
        assertFalse(ClassFinder.match(cowSubList, RuleFactory.createRule("cowsulst")));
        assertFalse(ClassFinder.match(radioButtonBorder, RuleFactory.createRule("rbobuu")));
        assertFalse(ClassFinder.match(renderer, RuleFactory.createRule("rere")));
    }

    @Test
    public void checkCamelCaseWithPackageNamePattern() {
        assertTrue(ClassFinder.match(arrayList, RuleFactory.createRule("java.util.AL")));
        assertTrue(ClassFinder.match(arrayList, RuleFactory.createRule("jaVa.Ut.ArLi")));
        assertTrue(ClassFinder.match(concurrentHashMap, RuleFactory.createRule("java.ut.con.CHM")));
        assertTrue(ClassFinder.match(object, RuleFactory.createRule("java.lang.Obj")));
        assertTrue(ClassFinder.match(radioButtonBorder, RuleFactory.createRule("j.s.RBB")));

        assertFalse(ClassFinder.match(arrayList, RuleFactory.createRule("jaa.util.AL")));
        assertFalse(ClassFinder.match(arrayList, RuleFactory.createRule("jaVa.ui.ArLi")));
        assertFalse(ClassFinder.match(concurrentHashMap, RuleFactory.createRule("ut.con.CHM")));
        assertFalse(ClassFinder.match(object, RuleFactory.createRule("java.lg.Obj")));
        assertFalse(ClassFinder.match(radioButtonBorder, RuleFactory.createRule("jx.s.RBB")));
    }

    @Test
    public void checkInsensitiveCaseWithPackageNamePattern() {
        assertTrue(ClassFinder.match(arrayList, RuleFactory.createRule("java.util.al")));
        assertTrue(ClassFinder.match(arrayList, RuleFactory.createRule("java.ut.arli")));
        assertTrue(ClassFinder.match(concurrentHashMap, RuleFactory.createRule("java.ut.con.chm")));
        assertTrue(ClassFinder.match(object, RuleFactory.createRule("java.lang.obj")));
        assertTrue(ClassFinder.match(radioButtonBorder, RuleFactory.createRule("j.s.rbb")));

        assertFalse(ClassFinder.match(arrayList, RuleFactory.createRule("jaa.util.al")));
        assertFalse(ClassFinder.match(arrayList, RuleFactory.createRule("java.ui.arli")));
        assertFalse(ClassFinder.match(concurrentHashMap, RuleFactory.createRule("ut.con.chm")));
        assertFalse(ClassFinder.match(object, RuleFactory.createRule("java.lg.bbj")));
        assertFalse(ClassFinder.match(radioButtonBorder, RuleFactory.createRule("jx.s.rbb")));
    }

    @Test
    public void checkCamelCaseWithEndingSpacePattern() {
        assertTrue(ClassFinder.match(arrayList, RuleFactory.createRule("AList ")));
        assertTrue(ClassFinder.match(arrayList, RuleFactory.createRule("ArList ")));
        assertTrue(ClassFinder.match(concurrentHashMap, RuleFactory.createRule("CHMap ")));
        assertTrue(ClassFinder.match(concurrentHashMap, RuleFactory.createRule("rentHaMap ")));
        assertTrue(ClassFinder.match(math, RuleFactory.createRule("Math ")));
        assertTrue(ClassFinder.match(jFrame, RuleFactory.createRule("JFrame ")));
        assertTrue(ClassFinder.match(jFrameFractal, RuleFactory.createRule("JFrFractal ")));
        assertTrue(ClassFinder.match(jFrameFractal, RuleFactory.createRule("JFractal ")));
        assertTrue(ClassFinder.match(cowSubList, RuleFactory.createRule("CList ")));
        assertTrue(ClassFinder.match(cowSubList, RuleFactory.createRule("COList ")));
        assertTrue(ClassFinder.match(radioButtonBorder, RuleFactory.createRule("RBBorder ")));
        assertTrue(ClassFinder.match(radioButtonBorder, RuleFactory.createRule("BuBorder ")));

        assertFalse(ClassFinder.match(object, RuleFactory.createRule("Obj ")));
        assertFalse(ClassFinder.match(arrayList, RuleFactory.createRule("ALi ")));
        assertFalse(ClassFinder.match(concurrentHashMap, RuleFactory.createRule("rentHaM ")));
        assertFalse(ClassFinder.match(jFrame, RuleFactory.createRule("JFra ")));
        assertFalse(ClassFinder.match(jFrameFractal, RuleFactory.createRule("JFrFr ")));
        assertFalse(ClassFinder.match(jFrameFractal, RuleFactory.createRule("JFrac ")));
        assertFalse(ClassFinder.match(cowSubList, RuleFactory.createRule("CL ")));
        assertFalse(ClassFinder.match(cowSubList, RuleFactory.createRule("COLi ")));
        assertFalse(ClassFinder.match(renderer, RuleFactory.createRule("Render ")));
    }

    @Test
    public void checkCaseInsensitiveWithEndingSpacePattern() {
        assertTrue(ClassFinder.match(arrayList, RuleFactory.createRule("alist ")));
        assertTrue(ClassFinder.match(arrayList, RuleFactory.createRule("arlist ")));
        assertTrue(ClassFinder.match(concurrentHashMap, RuleFactory.createRule("chmap ")));
        assertTrue(ClassFinder.match(concurrentHashMap, RuleFactory.createRule("renthamap ")));
        assertTrue(ClassFinder.match(math, RuleFactory.createRule("math ")));
        assertTrue(ClassFinder.match(jFrame, RuleFactory.createRule("jframe ")));
        assertTrue(ClassFinder.match(jFrameFractal, RuleFactory.createRule("jfrfractal ")));
        assertTrue(ClassFinder.match(jFrameFractal, RuleFactory.createRule("jfractal ")));
        assertTrue(ClassFinder.match(cowSubList, RuleFactory.createRule("clist ")));
        assertTrue(ClassFinder.match(cowSubList, RuleFactory.createRule("colist ")));
        assertTrue(ClassFinder.match(radioButtonBorder, RuleFactory.createRule("rbborder ")));
        assertTrue(ClassFinder.match(radioButtonBorder, RuleFactory.createRule("buborder ")));
        assertTrue(ClassFinder.match(renderer, RuleFactory.createRule("derer ")));

        assertFalse(ClassFinder.match(object, RuleFactory.createRule("obj ")));
        assertFalse(ClassFinder.match(arrayList, RuleFactory.createRule("ali ")));
        assertFalse(ClassFinder.match(concurrentHashMap, RuleFactory.createRule("rentham ")));
        assertFalse(ClassFinder.match(jFrame, RuleFactory.createRule("jfra ")));
        assertFalse(ClassFinder.match(jFrameFractal, RuleFactory.createRule("jfrfr ")));
        assertFalse(ClassFinder.match(jFrameFractal, RuleFactory.createRule("jfrac ")));
        assertFalse(ClassFinder.match(cowSubList, RuleFactory.createRule("cl ")));
        assertFalse(ClassFinder.match(cowSubList, RuleFactory.createRule("coli ")));
        assertFalse(ClassFinder.match(renderer, RuleFactory.createRule("render ")));
    }

    @Test
    public void checkCamelCaseWithWildCardsPattern() {
        assertTrue(ClassFinder.match(arrayList, RuleFactory.createRule("A*L")));
        assertTrue(ClassFinder.match(arrayList, RuleFactory.createRule("*Ar*Li")));
        assertTrue(ClassFinder.match(concurrentHashMap, RuleFactory.createRule("C*M")));
        assertTrue(ClassFinder.match(concurrentHashMap, RuleFactory.createRule("rentH*hM")));
        assertTrue(ClassFinder.match(object, RuleFactory.createRule("Obj*")));
        assertTrue(ClassFinder.match(math, RuleFactory.createRule("M**h")));
        assertTrue(ClassFinder.match(jFrame, RuleFactory.createRule("JFra*e")));
        assertTrue(ClassFinder.match(jFrameFractal, RuleFactory.createRule("JFrFr*al")));
        assertTrue(ClassFinder.match(jFrameFractal, RuleFactory.createRule("JFrac*al")));
        assertTrue(ClassFinder.match(cowSubList, RuleFactory.createRule("C*L")));
        assertTrue(ClassFinder.match(cowSubList, RuleFactory.createRule("COL*t")));
        assertTrue(ClassFinder.match(radioButtonBorder, RuleFactory.createRule("R*BB")));
        assertTrue(ClassFinder.match(radioButtonBorder, RuleFactory.createRule("B*Bo")));
        assertTrue(ClassFinder.match(renderer, RuleFactory.createRule("**Rend*r")));

        assertFalse(ClassFinder.match(arrayList, RuleFactory.createRule("AL*T")));
        assertFalse(ClassFinder.match(concurrentHashMap, RuleFactory.createRule("Ch*M")));
        assertFalse(ClassFinder.match(jFrameFractal, RuleFactory.createRule("JFr*mF*cl")));
        assertFalse(ClassFinder.match(cowSubList, RuleFactory.createRule("C*WO*Sub")));
        assertFalse(ClassFinder.match(radioButtonBorder, RuleFactory.createRule("R*B*uBu")));
        assertFalse(ClassFinder.match(renderer, RuleFactory.createRule("Re*R*")));
    }

    @Test
    public void checkCaseInsensitiveWithWildCardPattern() {
        assertTrue(ClassFinder.match(arrayList, RuleFactory.createRule("a*l*")));
        assertTrue(ClassFinder.match(arrayList, RuleFactory.createRule("ar*yli")));
        assertTrue(ClassFinder.match(concurrentHashMap, RuleFactory.createRule("ch*hm")));
        assertTrue(ClassFinder.match(concurrentHashMap, RuleFactory.createRule("c*rentham")));
        assertTrue(ClassFinder.match(object, RuleFactory.createRule("obj*ct")));
        assertTrue(ClassFinder.match(math, RuleFactory.createRule("m***th")));
        assertTrue(ClassFinder.match(jFrame, RuleFactory.createRule("j*fra*e")));
        assertTrue(ClassFinder.match(jFrameFractal, RuleFactory.createRule("jfr*efr*l")));
        assertTrue(ClassFinder.match(jFrameFractal, RuleFactory.createRule("jfrac*l***")));
        assertTrue(ClassFinder.match(cowSubList, RuleFactory.createRule("c*l")));
        assertTrue(ClassFinder.match(cowSubList, RuleFactory.createRule("****c*l*")));
        assertTrue(ClassFinder.match(radioButtonBorder, RuleFactory.createRule("r*obb")));
        assertTrue(ClassFinder.match(radioButtonBorder, RuleFactory.createRule("bu*nbo*r")));
        assertTrue(ClassFinder.match(renderer, RuleFactory.createRule("*re*re*r")));

        assertFalse(ClassFinder.match(arrayList, RuleFactory.createRule("a*lt*")));
        assertFalse(ClassFinder.match(concurrentHashMap, RuleFactory.createRule("chs*hm")));
        assertFalse(ClassFinder.match(jFrameFractal, RuleFactory.createRule("jfraef")));
        assertFalse(ClassFinder.match(cowSubList, RuleFactory.createRule("c*ows*bls*t")));
        assertFalse(ClassFinder.match(radioButtonBorder, RuleFactory.createRule("r*b*obu")));
        assertFalse(ClassFinder.match(renderer, RuleFactory.createRule("re*rek")));
    }

    @Test
    public void checkCamelCaseWithPackageNameAndSpacePattern() {
        assertTrue(ClassFinder.match(arrayList, RuleFactory.createRule("jaVa.ut.AList ")));
        assertTrue(ClassFinder.match(concurrentHashMap, RuleFactory.createRule("java.til.c.CHashMap ")));

        assertFalse(ClassFinder.match(arrayList, RuleFactory.createRule("jaa.ut.AList ")));
        assertFalse(ClassFinder.match(arrayList, RuleFactory.createRule("java.ut.ALi ")));
        assertFalse(ClassFinder.match(concurrentHashMap, RuleFactory.createRule("java.til.CHashMap ")));
        assertFalse(ClassFinder.match(concurrentHashMap, RuleFactory.createRule("java.til.rent.CHashMa ")));
    }

    @Test
    public void checkCaseInsensitiveWithPackageNameAndSpacePattern() {
        assertTrue(ClassFinder.match(arrayList, RuleFactory.createRule("jaVa.ut.alist ")));
        assertTrue(ClassFinder.match(concurrentHashMap, RuleFactory.createRule("java.til.c.chashmap ")));

        assertFalse(ClassFinder.match(arrayList, RuleFactory.createRule("jaa.ut.alist ")));
        assertFalse(ClassFinder.match(arrayList, RuleFactory.createRule("java.ut.ali ")));
        assertFalse(ClassFinder.match(concurrentHashMap, RuleFactory.createRule("java.til.chashmap ")));
        assertFalse(ClassFinder.match(concurrentHashMap, RuleFactory.createRule("java.til.rent.chashma ")));
    }

    @Test
    public void checkCamelCaseWithPackageNameAndWildCardPattern() {
        assertTrue(ClassFinder.match(radioButtonBorder, RuleFactory.createRule("ja*x*.s*ng.Bu*nBo*r")));
        assertTrue(ClassFinder.match(radioButtonBorder, RuleFactory.createRule("ja*x*.*.Bu*nBo*r")));
        assertTrue(ClassFinder.match(concurrentHashMap, RuleFactory.createRule("**j*va.t*l.c*t.CHashM*p")));
        assertTrue(ClassFinder.match(concurrentHashMap, RuleFactory.createRule("**j*va.t**l.c**t.CHashM****p**")));

        assertFalse(ClassFinder.match(radioButtonBorder, RuleFactory.createRule("jav*ux.*.Bu*nBo*r")));
        assertFalse(ClassFinder.match(concurrentHashMap, RuleFactory.createRule("java.*.rent.CHashMa*t")));
    }

    @Test
    public void checkCaseInsensitiveWithPackageNameAndWildCardPattern() {
        assertTrue(ClassFinder.match(radioButtonBorder, RuleFactory.createRule("ja*x*.s*ng.bu*nbo*r")));
        assertTrue(ClassFinder.match(radioButtonBorder, RuleFactory.createRule("ja*x*.*.bu*nbo*r")));
        assertTrue(ClassFinder.match(concurrentHashMap, RuleFactory.createRule("**j*va.t*l.c*t.chashm*p")));
        assertTrue(ClassFinder.match(concurrentHashMap, RuleFactory.createRule("*****j****va.t****l.c****t.chashm****p***")));

        assertFalse(ClassFinder.match(radioButtonBorder, RuleFactory.createRule("jav*ux.*.bu*nbo*r")));
        assertFalse(ClassFinder.match(concurrentHashMap, RuleFactory.createRule("java.*.rent.chashma*t")));
    }

    @Test
    public void checkCamelCaseWithSpaceAtTheEndAndWildCard() {
        assertTrue(ClassFinder.match(cowSubList, RuleFactory.createRule("CO*bList ")));
        assertTrue(ClassFinder.match(radioButtonBorder, RuleFactory.createRule("RBB*r*r ")));

        assertFalse(ClassFinder.match(cowSubList, RuleFactory.createRule("CO*sList ")));
        assertFalse(ClassFinder.match(renderer, RuleFactory.createRule("R*re ")));
    }

    @Test
    public void checkCaseInsensitiveWithSpaceAtTheEndAndWildCard() {
        assertTrue(ClassFinder.match(cowSubList, RuleFactory.createRule("co*blist ")));
        assertTrue(ClassFinder.match(radioButtonBorder, RuleFactory.createRule("rbb*r*r ")));

        assertFalse(ClassFinder.match(cowSubList, RuleFactory.createRule("co*swlist ")));
        assertFalse(ClassFinder.match(renderer, RuleFactory.createRule("r*re ")));
    }

    @Test
    public void checkCamelCaseWithPackageNameAndSpaceAtTheEndAndWildCardPattern() {
        assertTrue(ClassFinder.match(radioButtonBorder, RuleFactory.createRule("ja*x*.s*ng.Bu*nBo*r ")));
        assertTrue(ClassFinder.match(radioButtonBorder, RuleFactory.createRule("ja*x*.*.Bu*nBo*r ")));
        assertTrue(ClassFinder.match(concurrentHashMap, RuleFactory.createRule("**j*va.t*l.c*t.CHashM*p ")));
        assertTrue(ClassFinder.match(concurrentHashMap, RuleFactory.createRule("**j***va.t***l.c****t.CHashM****p*** ")));

        assertFalse(ClassFinder.match(radioButtonBorder, RuleFactory.createRule("jav*ux.*.Bu*nBo*r ")));
        assertFalse(ClassFinder.match(radioButtonBorder, RuleFactory.createRule("jav*x.*.Bu*nBo*e ")));
        assertFalse(ClassFinder.match(concurrentHashMap, RuleFactory.createRule("java.*.rent.CH*hMa ")));
    }

    @Test
    public void checkCaseInsensitiveWithPackageNameAndSpaceAtTheEndAndWildCardPattern() {
        assertTrue(ClassFinder.match(radioButtonBorder, RuleFactory.createRule("ja*x*.s*ng.bu*nbo*r ")));
        assertTrue(ClassFinder.match(radioButtonBorder, RuleFactory.createRule("ja*x*.*.bu*nbo*r ")));
        assertTrue(ClassFinder.match(concurrentHashMap, RuleFactory.createRule("**j*va.t*l.c*t.chashm*p ")));
        assertTrue(ClassFinder.match(radioButtonBorder, RuleFactory.createRule("jav*x.*.bu*nbo*e")));
        assertTrue(ClassFinder.match(radioButtonBorder, RuleFactory.createRule("**jav***x.***.bu***nbo***e")));

        assertFalse(ClassFinder.match(radioButtonBorder, RuleFactory.createRule("jav*ux.*.bu*nbo*r ")));
        assertFalse(ClassFinder.match(radioButtonBorder, RuleFactory.createRule("jav*x.*.bu*nbo*e ")));
        assertFalse(ClassFinder.match(concurrentHashMap, RuleFactory.createRule("java.*.rent.ch*hma ")));
        assertFalse(ClassFinder.match(radioButtonBorder, RuleFactory.createRule("jav*x.*.*.bu*nbo*e")));
    }

    @Test(expected = RuntimeException.class)
    public void checkRuleFactoryWithInvalidPattern() {
        RuleFactory.createRule("....");
        RuleFactory.createRule("     ");
        RuleFactory.createRule("...   ....");
        RuleFactory.createRule("    ....   ");
        RuleFactory.createRule(",,,,");
        RuleFactory.createRule("list.");
        RuleFactory.createRule("list   ");
        RuleFactory.createRule("array list");
        RuleFactory.createRule("array#list");
    }
}
