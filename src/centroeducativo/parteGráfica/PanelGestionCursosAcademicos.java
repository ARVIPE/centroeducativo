package centroeducativo.parteGráfica;

import java.awt.BorderLayout;		
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import centroeducativo.Curso;
import centroeducativo.controladores.CursoControlador;
import centroeducativo.utils.CacheImagenes;

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
		this.add(getToolBar(), BorderLayout.NORTH);
		this.add(getPanelGestion(), BorderLayout.CENTER);
		
		this.actual = CursoControlador.getInstancia().findFirst();
		cargarDatosActual();
	}
	
	private JToolBar getToolBar() {
		JToolBar toolBar = new JToolBar();
		
		JButton jbtPrimero = new JButton();
		asignarFuncion(jbtPrimero, "gotostart.png", LOAD_FIRST);
		toolBar.add(jbtPrimero);
		
		JButton jbtPrevio = new JButton();
		asignarFuncion(jbtPrevio, "previous.png", LOAD_PREV);
		toolBar.add(jbtPrevio);
		
		JButton jbtSiguiente = new JButton();
		asignarFuncion(jbtSiguiente, "next.png", LOAD_NEXT);
		toolBar.add(jbtSiguiente);
		
		JButton jbtUltimo = new JButton();
		asignarFuncion(jbtUltimo, "gotoend.png", LOAD_LAST);
		toolBar.add(jbtUltimo);
		
		JButton jbtNuevo = new JButton();
		asignarFuncion(jbtNuevo, "nuevo.png", NEW);
		toolBar.add(jbtNuevo);
		
		JButton jbtGuardar = new JButton();
		asignarFuncion(jbtGuardar, "guardar.png", SAVE);
		toolBar.add(jbtGuardar);
		
		JButton jbtEliminar = new JButton();
		asignarFuncion(jbtEliminar, "eliminar.png", REMOVE);
		toolBar.add(jbtEliminar);
		
		return toolBar;
		
	}
	
	/**
	 * 
	 * @param jbt
	 * @param icono
	 * @param funcion
	 */
	private void asignarFuncion (JButton jbt, String icono, final int funcion) {
		jbt.addActionListener(new ActionListener () {
			public void actionPerformed(ActionEvent arg0) {
				
				Curso obtenido = null;
				if (funcion == LOAD_FIRST) 
					obtenido = CursoControlador.getInstancia().findFirst();
				if (funcion == LOAD_PREV) 
					obtenido = CursoControlador.getInstancia().findPrevious(actual);
				if (funcion == LOAD_NEXT) 
					obtenido = CursoControlador.getInstancia().findNext(actual);
				if (funcion == LOAD_LAST) 
					obtenido = CursoControlador.getInstancia().findLast();
				if (funcion == NEW) 
					nuevo();
				if (funcion == SAVE)
					guardar();
				if (funcion == REMOVE) 
					obtenido = eliminar();
				
				if (obtenido != null) {
					actual = obtenido;
					cargarDatosActual();
				}
			}});
	}
	
	
	
	/**
	 * 
	 */
	private void nuevo () {
		limpiarPantalla();
		this.actual = new Curso();
		JOptionPane.showMessageDialog(this, "Por favor, introduzca los datos del nuevo registro");
	}
	
	/**
	 * 
	 */
	private void limpiarPantalla() {
		this.jtfId.setText("");
		this.jtfDescripcion.setText("");
	}
	
	/**
	 * 
	 */
	private void guardar () {
		Curso nuevoRegistro = new Curso();
		
		if (this.jtfId.getText().trim().equals("")) 
			nuevoRegistro.setId(0);
		else 
			nuevoRegistro.setId(Integer.parseInt(this.jtfId.getText()));
		
		nuevoRegistro.setDescripcion(this.jtfDescripcion.getText());
		
		if (nuevoRegistro.getId() == 0) {
			CursoControlador.getInstancia().persist(nuevoRegistro);
		}
		else {
			CursoControlador.getInstancia().merge(nuevoRegistro);
		}
		
		this.jtfId.setText("" + nuevoRegistro.getId());
		JOptionPane.showMessageDialog(this, "Guardado correctamente");
		
		this.actual = nuevoRegistro;
	}
	
	/**
	 * 
	 * @return
	 */
	private Curso eliminar () {
		String respuestas[] = new String[] {"Sí", "No"};
		int opcionElegida = JOptionPane.showOptionDialog(null, 
				"¿Realmente desea eliminar el registro?", "Eliminación del registro", 
		        JOptionPane.OK_CANCEL_OPTION, 
		        JOptionPane.OK_CANCEL_OPTION, 
		        CacheImagenes.getCacheImagenes().getIcono("confirm.png"), 
		        respuestas, respuestas[1]);

	    if(opcionElegida == 0) {
	    	Curso nuevoAMostrar = CursoControlador.getInstancia().findPrevious(actual);
	    	if (nuevoAMostrar == null) {
	    		nuevoAMostrar = CursoControlador.getInstancia().findNext(actual);
	    	}
	    	CursoControlador.getInstancia().remove(actual);
			JOptionPane.showMessageDialog(this, "Eliminación correcta");

	    	if (nuevoAMostrar != null) {
	    		actual = nuevoAMostrar;
	    	}
	    	else {
	    		limpiarPantalla();
	    	} 
	    }
	    return actual;
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
