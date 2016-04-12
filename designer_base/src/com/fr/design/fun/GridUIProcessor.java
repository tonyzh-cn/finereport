package com.fr.design.fun;

import com.fr.stable.fun.Level;

import javax.swing.plaf.ComponentUI;

/**
 * 自定义gridui接口
 *
 * Created by Administrator on 2016/4/11/0011.
 */
public interface GridUIProcessor extends Level {

    String MARK_STRING = "GridUIProcessor";

    int CURRENT_LEVEL = 1;

    /**
     * 获取自定义的gridui
     *
     * @return 自定义的格子界面
     */
    ComponentUI appearanceForGrid(int resolution);

}
