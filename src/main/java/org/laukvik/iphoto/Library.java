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

public class Library {

    private List<Album> albums;
    private Node node;
    private Node albumsNode = null;
    private Node rollsNode = null;
    private Node imagesNode = null;

    public Library() {
        albums = new ArrayList<>();
    }

    public Library(Node plist) {
        this();
        this.node = plist;
        Node dict = plist.getChildNodes().item(1);
        NodeList list = dict.getChildNodes();
        for (int x = 0; x < list.getLength(); x++) {
            Node node = list.item(x);
            String nodeName = list.item(x).getNodeName();
            if (nodeName.equalsIgnoreCase("array")) {
                Node prev = list.item(x - 2);
                if (prev.getTextContent().equalsIgnoreCase("List of Albums")) {
                    setAlbumsNode(node);
                } else if (prev.getTextContent().equalsIgnoreCase("List of Rolls")) {
                    setRollsNode(node);
                }
            } else if (nodeName.equalsIgnoreCase("dict")) {
                String prevKey = list.item(x - 2).getTextContent();
                if (prevKey.equalsIgnoreCase("Master Image List")) {
                    setImagesNode(node);
                }
            }
        }
        addAlbumsFromNode();
    }

    private void addAlbumsFromNode() {
        NodeList list = albumsNode.getChildNodes();
        for (int x = 0; x < list.getLength(); x++) {
            Node node = list.item(x);
            if (node.getNodeName().equalsIgnoreCase("dict")) {
                addAlbum(node);
            }
        }
    }

    private void addAlbum(Node albumUsingDictNode) {
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
                    add(album);
                }
            } else if (node.getNodeName().equalsIgnoreCase("array")) {
                addPhotos(node, album);
            }
        }
    }

    private void addPhotos(Node albumsUsingArrayNode, Album album) {
        NodeList list = albumsUsingArrayNode.getChildNodes();
        for (int x = 0; x < list.getLength(); x++) {
            Node node = list.item(x);
            if (node.getNodeName().equalsIgnoreCase("string")) {
                addPhoto(album, node.getTextContent());
            }
        }
    }

    private void addPhoto(Album album, String photoID) {
        album.addById(photoID, getImagesNode());
    }

    public void add(Album album) {
        album.setLibrary(this);
        albums.add(album);
    }

    public void remove(Album album) {
        albums.remove(album);
        album.setLibrary(null);
    }

    public Album[] list() {
        Album[] photoArr = new Album[albums.size()];
        return albums.toArray(photoArr);
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Node getAlbumsNode() {
        return albumsNode;
    }

    public void setAlbumsNode(Node albumsNode) {
        this.albumsNode = albumsNode;
    }

    public Node getImagesNode() {
        return imagesNode;
    }

    public void setImagesNode(Node imagesNode) {
        this.imagesNode = imagesNode;
    }

    public Node getRollsNode() {
        return rollsNode;
    }

    public void setRollsNode(Node rollsNode) {
        this.rollsNode = rollsNode;
    }

}
