/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.laukvik.iphoto;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.Test;
import org.xml.sax.SAXException;

/**
 *
 * @author Morten Laukvik <morten@laukvik.no>
 */
public class LibraryTest {

    public LibraryTest() {
    }

    @Test
    public void testSomeMethod() throws ParserConfigurationException, SAXException, IOException {
        Parser parser = new Parser();
        parser.load();

    }

}
