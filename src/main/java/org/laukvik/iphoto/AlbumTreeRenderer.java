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

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultTreeCellRenderer;

public class AlbumTreeRenderer extends DefaultTreeCellRenderer {

    private static final long serialVersionUID = -167539240567732372L;
    public final static Icon collection = getIcon("collection.png");
    public final static Icon albumOpen = getIcon("album.png");
    public final static Icon albumClosed = getIcon("album.png");
    public final static Icon roll = getIcon("roll.png");
    public final static Icon library = getIcon("library.png");
    public final static Icon keywords = getIcon("keywords.png");

    public Color NORMAL = new Color(244, 244, 247);
    public Color SELECTED = UIManager.getColor("Tree.selectionBackground");

    public AlbumTreeRenderer() {
        super();
        setOpaque(true);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setClosedIcon(albumClosed);
        setLeafIcon(new javax.swing.ImageIcon("icons/album.png"));
        setOpenIcon(albumOpen);
        setEnabled(true);
        setBackground(new Color(244, 244, 247));
    }

    public static Icon getIcon(String filename) {
        return new javax.swing.ImageIcon("icons/" + filename);
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value,
            boolean sel, boolean expanded, boolean leaf, int row,
            boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
        if (selected) {
            setBackground(SELECTED);
        } else {
            setBackground(NORMAL);
        }
//        if (leaf) {
//            if (expanded) {
//                setIcon(albumOpen);
//            } else {
//                setIcon(albumClosed);
//            }
//        } else {
//            setIcon(albumOpen);
//        }
        return this;
    }

}
