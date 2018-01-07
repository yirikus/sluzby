package cz.yiri.kus.sluzby.view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;

/**
 * @author jiri.kus
 */
public class ComponentTree {
	private JButton processButton;
	private JButton saveButton;
	private JButton loadButton;
	private JButton addButton;
	private JButton createButton;
	private JButton writeButton;
	private JTextField doctorNameField;
	private JButton prevCalendar;
	private JButton nextCalendar;
	private JFrame frame;
	private JTable oldTable;
	private JTable countTable;
	private JTable youngTable;
	private JTable mainTable;
	private JTabbedPane tabbedPane;
	private JToolBar toolBar;
	private DateComponent dateField;

	public JButton getProcessButton() {
		return processButton;
	}

	public void setProcessButton(JButton processButton) {
		this.processButton = processButton;
	}

	public JButton getSaveButton() {
		return saveButton;
	}

	public void setSaveButton(JButton saveButton) {
		this.saveButton = saveButton;
	}

	public JButton getLoadButton() {
		return loadButton;
	}

	public void setLoadButton(JButton loadButton) {
		this.loadButton = loadButton;
	}

	public JButton getAddButton() {
		return addButton;
	}

	public void setAddButton(JButton addButton) {
		this.addButton = addButton;
	}

	public JButton getCreateButton() {
		return createButton;
	}

	public void setCreateButton(JButton createButton) {
		this.createButton = createButton;
	}

	public JButton getWriteButton() {
		return writeButton;
	}

	public void setWriteButton(JButton writeButton) {
		this.writeButton = writeButton;
	}

	public JTextField getDoctorNameField() {
		return doctorNameField;
	}

	public void setDoctorNameField(JTextField doctorNameField) {
		this.doctorNameField = doctorNameField;
	}

	public JButton getPrevCalendar() {
		return prevCalendar;
	}

	public void setPrevCalendar(JButton prevCalendar) {
		this.prevCalendar = prevCalendar;
	}

	public JButton getNextCalendar() {
		return nextCalendar;
	}

	public void setNextCalendar(JButton nextCalendar) {
		this.nextCalendar = nextCalendar;
	}

	public JFrame getFrame() {
		return frame;
	}

	public void setFrame(JFrame frame) {
		this.frame = frame;
	}

	public void setOldTable(JTable oldTable) {
		this.oldTable = oldTable;
	}

	public JTable getOldTable() {
		return oldTable;
	}

	public void setCountTable(JTable countTable) {
		this.countTable = countTable;
	}

	public JTable getCountTable() {
		return countTable;
	}

	public void setYoungTable(JTable youngTable) {
		this.youngTable = youngTable;
	}

	public JTable getYoungTable() {
		return youngTable;
	}

	public void setMainTable(JTable mainTable) {
		this.mainTable = mainTable;
	}

	public JTable getMainTable() {
		return mainTable;
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}

	public void setTabbedPane(JTabbedPane tabbedPane) {
		this.tabbedPane = tabbedPane;
	}

	public void setToolBar(JToolBar toolBar) {
		this.toolBar = toolBar;
	}

	public JToolBar getToolBar() {
		return toolBar;
	}

	public void setDateField(DateComponent dateField) {
		this.dateField = dateField;
	}

	public DateComponent getDateField() {
		return dateField;
	}
}
