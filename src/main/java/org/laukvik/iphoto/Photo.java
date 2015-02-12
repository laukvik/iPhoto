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

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Morten Laukvik <morten@laukvik.no>
 */
public class Photo {

    private String name;
    private String thumbnailPath;
    private String description;
    private Node node;
    private Album album;
    private String imagePath;

    public Photo(Node node) {
        this.node = node;
        this.name = getProperty("Caption");
        this.thumbnailPath = getProperty("ThumbPath");
        this.description = getProperty("Comment");
        this.imagePath = getProperty("ImagePath");
    }

    public Photo(String name, String path) {
        this.name = name;
        this.thumbnailPath = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return thumbnailPath;
    }

    public void setPath(String path) {
        this.thumbnailPath = path;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
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
        return "";
    }

    public String getIphoto() {
        return imagePath;
    }

    @Override
    public String toString() {
        return "Photo:" + imagePath;
    }

}
