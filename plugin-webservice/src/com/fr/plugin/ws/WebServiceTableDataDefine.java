package com.fr.plugin.ws;

import com.fr.base.TableData;
import com.fr.design.data.tabledata.tabledatapane.AbstractTableDataPane;
import com.fr.design.fun.ServerTableDataDefineProvider;
import com.fr.design.fun.impl.AbstractTableDataDefineProvider;
import com.fr.plugin.ws.data.WebServiceTableData;
import com.fr.plugin.ws.ui.WebServiceTableDataPane;

public class WebServiceTableDataDefine extends AbstractTableDataDefineProvider implements ServerTableDataDefineProvider {

    @Override
    public Class<? extends TableData> classForTableData() {
        return WebServiceTableData.class;
    }

    @Override
    public Class<? extends TableData> classForInitTableData() {
        return WebServiceTableData.class;
    }

    @Override
    public Class<? extends AbstractTableDataPane> appearanceForTableData() {
        return WebServiceTableDataPane.class;
    }

    @Override
    public String nameForTableData() {
        return "WebService数据集";
    }

    @Override
    public String prefixForTableData() {
        return "ws";
    }

    @Override
    public String iconPathForTableData() {
        return "com/fr/plugin/ws/images/ws.png";
    }
}
