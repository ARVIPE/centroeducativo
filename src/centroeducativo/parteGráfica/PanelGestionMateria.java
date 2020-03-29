package centroeducativo.parteGráfica;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import centroeducativo.Curso;
import centroeducativo.Materia;
import centroeducativo.controladores.CursoControlador;
import centroeducativo.controladores.MateriaControlador;
import centroeducativo.utils.CacheImagenes;


public class PanelGestionMateria extends JPanel{
	
	public static int LOAD_FIRST = 0;
	public static int LOAD_PREV = 1;
	public static int LOAD_NEXT = 2;
	public static int LOAD_LAST = 3;
	public static int NEW = 4;
	public static int SAVE = 5;
	public static int REMOVE = 6;
	
	Materia actual = null;
	
	private JTextField jtfId = new JTextField(5);
	private JTextField jtfNombre = new JTextField(20);
	private JTextField jtfAcronimo = new JTextField(15);
	JComboBox<Curso> jcbCurso = new JComboBox<Curso>();
	

	
	public PanelGestionMateria() {
		
		actual = MateriaControlador.getInstancia().findFirst();
		cargarDatosActual();
		
		this.setLayout(new BorderLayout());
		this.add(getToolBar(), BorderLayout.NORTH);
		this.add(getPanelGestion(), BorderLayout.CENTER);
		
		
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
		jbt.setIcon(CacheImagenes.getCacheImagenes().getIcono(icono));
		jbt.addActionListener(new ActionListener () {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				Materia obtenido = null;
				if (funcion == LOAD_FIRST) 
					obtenido = MateriaControlador.getInstancia().findFirst();
				if (funcion == LOAD_PREV) 
					obtenido = MateriaControlador.getInstancia().findPrevious(actual);
				if (funcion == LOAD_NEXT) 
					obtenido = MateriaControlador.getInstancia().findNext(actual);
				if (funcion == LOAD_LAST) 
					obtenido = MateriaControlador.getInstancia().findLast();
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
		this.actual = new Materia();
		JOptionPane.showMessageDialog(this, "Por favor, introduzca los datos del nuevo registro");
	}
	
	/**
	 * 
	 */
	private void limpiarPantalla() {
		this.jtfId.setText("");
		this.jtfNombre.setText("");
		this.jtfAcronimo.setText("");
		this.jcbCurso.setSelectedIndex(0);
	}
	
	/**
	 * 
	 */
	private void guardar () {
		Materia nuevoRegistro = new Materia();
		
		if (this.jtfId.getText().trim().equals("")) 
			nuevoRegistro.setId(0);
		else 
			nuevoRegistro.setId(Integer.parseInt(this.jtfId.getText()));
		
		nuevoRegistro.setNombre(this.jtfNombre.getText());
		nuevoRegistro.setAcronimo(this.jtfAcronimo.getText());
		nuevoRegistro.setCurso((Curso)this.jcbCurso.getSelectedItem());	
		
		if (nuevoRegistro.getId() == 0) {
			MateriaControlador.getInstancia().persist(nuevoRegistro);
		}
		else {
			MateriaControlador.getInstancia().merge(nuevoRegistro);
		}
		
		this.jtfId.setText("" + nuevoRegistro.getId());
		JOptionPane.showMessageDialog(this, "Guardado correctamente");
		
		this.actual = nuevoRegistro;
	}
	
	/**
	 * 
	 * @return
	 */
	private Materia eliminar () {
		String respuestas[] = new String[] {"Sí", "No"};
		int opcionElegida = JOptionPane.showOptionDialog(null, 
				"¿Realmente desea eliminar el registro?", "Eliminación del registro", 
		        JOptionPane.OK_CANCEL_OPTION, 
		        JOptionPane.OK_CANCEL_OPTION, 
		        CacheImagenes.getCacheImagenes().getIcono("confirm.png"), 
		        respuestas, respuestas[1]);

	    if(opcionElegida == 0) {
	    	Materia nuevoAMostrar = MateriaControlador.getInstancia().findPrevious(actual);
	    	if (nuevoAMostrar == null) {
	    		nuevoAMostrar = MateriaControlador.getInstancia().findNext(actual);
	    	}
	    	MateriaControlador.getInstancia().remove(actual);
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
		panelGestion.add(new JLabel("Identificador: "), c);

		c.gridx = 1;
		c.gridy = 1;
		jtfId.setEnabled(false);
		c.anchor = GridBagConstraints.WEST;
		panelGestion.add(jtfId, c);

		// Inclusiï¿½n del campo "Nombre"
		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.EAST;
		panelGestion.add(new JLabel("Nombre: "), c);

		c.gridx = 1;
		c.gridy = 2;
		c.anchor = GridBagConstraints.WEST;
		panelGestion.add(jtfNombre, c);
		
		// Inclusiï¿½n del campo "Acrónimo"
		c.gridx = 0;
		c.gridy = 3;
		c.anchor = GridBagConstraints.EAST;
		panelGestion.add(new JLabel("Acronimo: "), c);

		c.gridx = 1;
		c.gridy = 3;
		c.anchor = GridBagConstraints.WEST;
		panelGestion.add(jtfAcronimo, c);
		
		// Inclusiï¿½n del campo "curso"
		c.gridx = 0;
		c.gridy = 4;
		c.anchor = GridBagConstraints.EAST;
		panelGestion.add(new JLabel("Curso: "), c);

		c.gridx = 1;
		c.gridy = 4;
		c.anchor = GridBagConstraints.WEST;
		panelGestion.add(jcbCurso, c);

		// Inclusiï¿½n del campo "IdFab"
		List<Curso> cursos = CursoControlador.getInstancia().findAllCursos();

		for (Curso cu : cursos) {
			jcbCurso.addItem(cu);
		}

		return panelGestion;
	}
	
	/**
	 * 
	 */
	private void cargarDatosActual () {
		if (this.actual != null) {
			this.jtfId.setText("" + this.actual.getId());
			this.jtfNombre.setText("" + this.actual.getNombre());
			this.jtfAcronimo.setText("" + this.actual.getAcronimo());
			this.jcbCurso.setSelectedItem(this.actual.getCurso());

		}
	}

}
