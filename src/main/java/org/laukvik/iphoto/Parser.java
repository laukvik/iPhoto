/*
 * Copyright (C) 2015 Morten Laukvik <morten@laukvik.no>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package org.laukvik.iphoto;

import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Parser {

    private Library lib;

    public Parser() {
    }

    public List<File> findAvailableLibraries() {
        return null;
    }

    public File getLibraryFile2013() {
        File file = new File(System.getProperty("user.home"), "Pictures/iPhoto Library/AlbumData.xml");
        return file;
    }

    public File getLibraryFile2014() {
        File file = new File(System.getProperty("user.home"), "Pictures/iPhoto-bibliotek.photolibrary/AlbumData.xml");
        return file;
    }

    public File getDefaultLibraryFile() {
        return getLibraryFile2014();
    }

    public Library load() throws LibraryException {
        return load(getDefaultLibraryFile());
    }

    public Library load(File file) throws LibraryException {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document xml = builder.parse(file);
            Node plist = xml.getChildNodes().item(0);
            lib = new Library(plist);
            return lib;
        } catch (ParserConfigurationException ex) {
            throw new LibraryException(ex);
        } catch (SAXException ex) {
            throw new LibraryException(ex);
        } catch (IOException ex) {
            throw new LibraryException(ex);
        }
    }

    public void addAlbums(Node albumsUsingArrayNode) {
        NodeList list = albumsUsingArrayNode.getChildNodes();
        for (int x = 0; x < list.getLength(); x++) {
            Node node = list.item(x);
            if (node.getNodeName().equalsIgnoreCase("dict")) {
                addAlbum(node);
            }
        }
    }

    public void addAlbum(Node albumUsingDictNode) {
        NodeList list = albumUsingDictNode.getChildNodes();
        String key = "";
        String value = "";
        Album album = null;
        for (int x = 0; x < list.getLength(); x++) {
            Node node = list.item(x);
            if (node.getNodeName().equalsIgnoreCase("key")) {
                key = node.getTextContent();

            } else if (node.getNodeName().equalsIgnoreCase("string")) {
                value = node.getTextContent();

                if (key.equalsIgnoreCase("AlbumName")) {
                    album = new Album(value);
                    album.setNode(node);
                    lib.add(album);
                }
            } else if (node.getNodeName().equalsIgnoreCase("array")) {
                addPhotos(node, album);

            } else if (node.getNodeName().equalsIgnoreCase("integer")) {
                album.setAlbumID(node.getTextContent());
            }
        }
    }

    public void addPhotos(Node albumsUsingArrayNode, Album album) {
        NodeList list = albumsUsingArrayNode.getChildNodes();
        for (int x = 0; x < list.getLength(); x++) {
            Node node = list.item(x);
            if (node.getNodeName().equalsIgnoreCase("string")) {
                addPhoto(album, node.getTextContent());
            }
        }
    }

    public void addPhoto(Album album, String photoID) {
        album.addById(photoID, lib.getImagesNode());
    }

}
