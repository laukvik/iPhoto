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
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.Border;

public class PhotoIcon extends JLabel {

    private static final long serialVersionUID = -5992466907088133209L;
    public final static Dimension THUMBNAIL_SIZE = new Dimension(240, 160);
    public final static Dimension LABEL_SIZE = new Dimension(THUMBNAIL_SIZE.width, THUMBNAIL_SIZE.height + 20);
    private final Photo photo;
    private boolean selected;
    public final static Color PHOTO_BACKGROUND_COLOR = new Color(103, 103, 103);

    final static Border BORDER_SELECTION = BorderFactory.createLineBorder(new Color(246, 209, 96), 5);
    final static Border BORDER = BorderFactory.createLineBorder(PHOTO_BACKGROUND_COLOR, 5);

    public PhotoIcon(Photo photo) {
        super();
        this.photo = photo;
        setBackground(Color.RED);
        ImageIcon i = new ImageIcon(photo.getPath());
        setIcon(i);
        setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        setText(photo.getName());
        setToolTipText(photo.getDescription());
        setSize(LABEL_SIZE);
        setPreferredSize(LABEL_SIZE);
        setMinimumSize(LABEL_SIZE);
        setMaximumSize(LABEL_SIZE);
        Font font = getFont();
        setFont(new Font(font.getName(), Font.PLAIN, font.getSize() - 2));
        setVisible(true);
        setSelected(false);
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        setBorder(selected ? BORDER_SELECTION : BORDER);
        repaint();
    }

    public boolean isSelected() {
        return selected;
    }

}
