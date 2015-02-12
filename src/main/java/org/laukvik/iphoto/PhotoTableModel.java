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
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

public class PhotoTableModel implements TableModel {

    private static final long serialVersionUID = -3023063609335267709L;
    private Album album;
    private List<TableModelListener> listeners;

    public PhotoTableModel() {
        listeners = new ArrayList<>();
        album = new Album();
    }

    public void addTableModelListener(TableModelListener l) {
        listeners.add(l);
    }

    public void removeTableModelListener(TableModelListener l) {
        listeners.remove(l);
    }

    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return ImageIcon.class;
            case 1:
                return String.class;
            case 2:
                return String.class;
            default:
                return String.class;
        }
    }

    public int getColumnCount() {
        return 3;
    }

    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Thumbnail";
            case 1:
                return "Name";
            case 2:
                return "Description";
            default:
                return null;
        }
    }

    public int getRowCount() {
        return album.size();
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Photo p = album.list(rowIndex);
        switch (columnIndex) {
            case 0:
                return new ImageIcon(p.getPath());
            case 1:
                return p.getName();
            case 2:
                return p.getDescription();
            default:
                return null;
        }
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album, JTable table) {
        this.album = album;
        table.tableChanged(new TableModelEvent(this));
    }

}
