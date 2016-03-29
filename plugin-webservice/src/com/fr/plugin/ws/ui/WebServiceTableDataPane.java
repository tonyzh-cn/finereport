package com.fr.plugin.ws.ui;

import com.fr.design.data.tabledata.tabledatapane.AbstractTableDataPane;
import com.fr.plugin.ws.data.WebServiceTableData;

public class WebServiceTableDataPane extends AbstractTableDataPane<WebServiceTableData>{
    @Override
    public void populateBean(WebServiceTableData ob) {

    }

    @Override
    public WebServiceTableData updateBean() {
        return new WebServiceTableData();
    }

    @Override
    protected String title4PopupWindow() {
        return "WebService";
    }
}
