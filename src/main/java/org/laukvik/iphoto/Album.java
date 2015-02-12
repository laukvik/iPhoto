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

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Album {

    private List<Photo> photos;
    private String name;
    private Node node;
    private Library library;
    private Node listOfMasterImages;
    private List<String> ids;
    private String albumID;

    public Album(Node node) {
        this.node = node;
        photos = new ArrayList<>();
        ids = new ArrayList<>();
        this.name = getProperty("AlbumName");
    }

    public Album(String name) {
        photos = new ArrayList<>();
        ids = new ArrayList<>();
        this.name = name;
    }

    public Album() {
    }

    public void setAlbumID(String albumID) {
        this.albumID = albumID;
    }

    public String getAlbumID() {
        return albumID;
    }

    public String getComments() {
        return getProperty("Comments");
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void add(Photo photo) {
        photo.setAlbum(this);
        photos.add(photo);
    }

    public void remove(Photo photo) {
        photos.remove(photo);
        photo.setAlbum(null);
    }

    public int size() {
        cache();
        return photos.size();
    }

    public Photo list(int index) {
        cache();
        return photos.get(index);
    }

    public void cache() {
        if (listOfMasterImages != null) {
            for (int n = 0; n < ids.size(); n++) {
                addPhoto((String) ids.get(n));
            }
        }
        listOfMasterImages = null;
    }

    public Photo[] list() {
        cache();

        Photo[] photoArr = new Photo[photos.size()];
        return photos.toArray(photoArr);
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public String getProperty(String key) {
        NodeList list = node.getChildNodes();
        for (int x = 0; x < list.getLength(); x++) {
            Node child = list.item(x);
            if (child.getNodeName().equalsIgnoreCase("key")) {
                if (child.getTextContent().equalsIgnoreCase(key)) {
                    return list.item(x + 2).getTextContent();
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }

    public void log(String message) {
        System.out.println(message);
    }

    public void addPhoto(String photoID) {
        NodeList list = listOfMasterImages.getChildNodes();
        for (int x = 0; x < list.getLength(); x++) {
            Node node = list.item(x);
            if (node.getNodeName().equalsIgnoreCase("key")) {
                if (node.getTextContent().equalsIgnoreCase(photoID)) {
                    Node imageDict = list.item(x + 2);
                    photos.add(new Photo(imageDict));
                }
            }
        }
    }

    public void addById(String photoID, Node listOfMasterImages) {
        this.listOfMasterImages = listOfMasterImages;
        ids.add(photoID);
    }
}
