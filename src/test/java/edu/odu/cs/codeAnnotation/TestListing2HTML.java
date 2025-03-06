package edu.odu.cs.codeAnnotation;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


public class TestListing2HTML {
    


    @Test
    public void testDots() throws Exception {
    	assertEquals("abc&#x22ee;def", Listing2HTML.test("abc/*...*/def"));
    	assertEquals("abc&#x22ee;def", Listing2HTML.test("abc//...def"));
    }

    @Test
    public void testCallOuts() throws Exception {
    	assertEquals("abc&#10102;def", Listing2HTML.test("abc/*co1*/def"));
    	assertEquals("abc&#10103;def", Listing2HTML.test("abc/**co2*/def"));
    	assertEquals("abc&#10104;def", Listing2HTML.test("abc/*co3*/def"));
    	assertEquals("abc&#10110;def", Listing2HTML.test("abc/*co9*/def"));
    }

    @Test
    public void singleLineHighlight() throws Exception {
    	assertEquals("abc<span class='highlighted1'>def</span>ghi", Listing2HTML.test("abc/*+*/def/*-*/ghi"));
    	assertEquals("abc<span class='highlighted1'>def</span>ghi", Listing2HTML.test("abc/*+1*/def/*-1*/ghi"));
    	assertEquals("abc<span class='highlighted2'>def</span>ghi", Listing2HTML.test("abc/*+2*/def/*-2*/ghi"));
    	assertEquals("abc<span class='highlighted3'>def</span>ghi", Listing2HTML.test("abc/*+3*/def/*-3*/ghi"));
    }

    @Test
    public void multiLineHighlight() throws Exception {
    	assertEquals("abc<span class='highlighted1'>d<br/>\nef</span>ghi", Listing2HTML.test("abc/*+*/d\nef/*-*/ghi"));
    }

    
}
