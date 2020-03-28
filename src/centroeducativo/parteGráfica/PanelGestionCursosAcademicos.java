package centroeducativo.parteGráfica;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.SimpleDateFormat;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import centroeducativo.Curso;
import centroeducativo.controladores.CursoControlador;

public class PanelGestionCursosAcademicos extends JPanel {

	public static int LOAD_FIRST = 0;
	public static int LOAD_PREV = 1;
	public static int LOAD_NEXT = 2;
	public static int LOAD_LAST = 3;
	public static int NEW = 4;
	public static int SAVE = 5;
	public static int REMOVE = 6;
	
	Curso actual = null;
	
	private JTextField jtfId = new JTextField(5);
	private JTextField jtfDescripcion = new JTextField(20);

	
	public PanelGestionCursosAcademicos() {
		super();
//		this.add(getToolBar(), BorderLayout.NORTH);
		this.add(getPanelGestion(), BorderLayout.CENTER);
		
		this.actual = CursoControlador.getInstancia().findFirst();
		cargarDatosActual();
	}
	
	private Component getToolBar() {
		return null;
	}

	private JPanel getPanelGestion() {
		JPanel panelGestion = new JPanel();
		panelGestion.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		// Inclusiï¿½n del campo "Identificador"
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.EAST;
		c.insets = new Insets(2, 2, 2, 2);
		this.add(new JLabel("Identificador: "), c);

		c.gridx = 1;
		c.gridy = 1;
		jtfId.setEnabled(false);
		c.anchor = GridBagConstraints.WEST;
		this.add(jtfId, c);

		// Inclusiï¿½n del campo "CIF"
		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.EAST;
		this.add(new JLabel("Descripción: "), c);

		c.gridx = 1;
		c.gridy = 2;
		c.anchor = GridBagConstraints.WEST;
		this.add(jtfDescripcion, c);
		
		return panelGestion;
	}
	
	/**
	 * 
	 */
	private void cargarDatosActual () {
		if (this.actual != null) {
			this.jtfId.setText("" + this.actual.getId());
			this.jtfDescripcion.setText("" + this.actual.getDescripcion());

		}
	}

	
}
