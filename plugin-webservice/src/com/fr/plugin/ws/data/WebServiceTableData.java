package com.fr.plugin.ws.data;

import com.fr.data.AbstractTableData;
import com.fr.general.data.TableDataException;

public class WebServiceTableData extends AbstractTableData {
    @Override
    public int getColumnCount() throws TableDataException {
        return 0;
    }

    @Override
    public String getColumnName(int i) throws TableDataException {
        return null;
    }

    @Override
    public int getRowCount() throws TableDataException {
        return 0;
    }

    @Override
    public Object getValueAt(int i, int i1) {
        return null;
    }
}
