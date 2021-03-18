package com.softlynx.bs.common.base;

import org.zkoss.zhtml.Table;
import org.zkoss.zk.ui.Path;
import org.zkoss.zul.Button;
import org.zkoss.zul.Center;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Imagemap;
import org.zkoss.zul.Include;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Menubar;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Panel;
import org.zkoss.zul.Radiogroup;
import org.zkoss.zul.Row;
import org.zkoss.zul.Style;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;
import org.zkoss.zul.Vbox;
import org.zkoss.zul.Window;

public class BaseWindow {

	// Available Positions
	public static final String TOP_LEFT = "top_left";
	public static final String TOP_CENTER = "top_center";
	public static final String TOP_RIGHT = "top_right";
	public static final String MIDDLE_LEFT = "middle_left";
	public static final String MIDDLE_CENTER = "middle_center";
	public static final String MIDDLE_RIGHT = "middle_right";
	public static final String BOTTOM_LEFT = "bottom_left";
	public static final String BOTTOM_CENTER = "bottom_center";
	public static final String BOTTOM_RIGHT = "bottom_right";

	Window myWin = null;

	public BaseWindow(Window objWin) {
		this.myWin = objWin;
	}

	public Window getMyWin() {
		return myWin;
	}

	public Include getInclude(String fieldname) {
		return (Include) myWin.getFellow(fieldname);
	}

	public Include getIncludeByPath(String path) {
		return (Include) Path.getComponent(path);
	}

	public Tree getTree(String fieldname) {
		return (Tree) myWin.getFellow(fieldname);
	}

	public Tabbox getTabbox(String fieldname) {
		return (Tabbox) myWin.getFellow(fieldname);
	}

	public Tab getTab(String fieldname) {
		return (Tab) myWin.getFellow(fieldname);
	}

	public Listbox getListbox(String fieldname) {
		return (Listbox) myWin.getFellow(fieldname);
	}

	public Label getLabel(String fieldname) {
		return (Label) myWin.getFellow(fieldname);
	}

	public Textbox getTextbox(String fieldname) {
		return (Textbox) myWin.getFellow(fieldname);
	}

	public Decimalbox getDecimalbox(String fieldname) {
		return (Decimalbox) myWin.getFellow(fieldname);
	}

	public Intbox getIntbox(String fieldname) {
		return (Intbox) myWin.getFellow(fieldname);
	}

	public Button getButton(String fieldname) {
		return (Button) myWin.getFellow(fieldname);
	}

	public Window getWindow(String fieldname) {
		return (Window) myWin.getFellow(fieldname);
	}

	public Combobox getCombobox(String fieldname) {
		return (Combobox) myWin.getFellow(fieldname);
	}

	public Table getTable(String fieldname) {
		return (Table) myWin.getFellow(fieldname);
	}

	public Menubar getMenubar(String fieldname) {
		return (Menubar) myWin.getFellow(fieldname);
	}

	public Datebox getDatebox(String fieldname) {
		return (Datebox) myWin.getFellow(fieldname);
	}

	public Radiogroup getRadiogroup(String fieldname) {
		return (Radiogroup) myWin.getFellow(fieldname);
	}

	public Grid getGrid(String fieldname) {
		return (Grid) myWin.getFellow(fieldname);
	}

	public Row getGridRow(String fieldname) {
		return (Row) myWin.getFellow(fieldname);
	}

	public Imagemap getImagemap(String fieldname) {
		return (Imagemap) myWin.getFellow(fieldname);
	}

	public Style getStyleByPath(String path) {
		return (Style) Path.getComponent(path);
	}

	public Vbox getVbox(String fieldname) {
		return (Vbox) myWin.getFellow(fieldname);
	}

	public Groupbox getGroupbox(String fieldname) {
		return (Groupbox) myWin.getFellow(fieldname);
	}

	public Paging getPaging(String fieldname) {
		return (Paging) myWin.getFellow(fieldname);
	}

	public Iframe getIfram(String fieldname) {
		return (Iframe) myWin.getFellow(fieldname);
	}

	public Center getCenterlayout(String fieldname) {
		return (Center) myWin.getFellow(fieldname);
	}

	public Panel getPanel(String fieldname) {
		return (Panel) myWin.getFellow(fieldname);
	}
}