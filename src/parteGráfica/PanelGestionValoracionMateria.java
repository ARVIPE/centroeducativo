package parteGráfica;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Curso;
import model.Estudiante;
import model.Materia;
import model.Profesor;
import model.Valoracionmateria;
import model.controladores.CursoControlador;
import model.controladores.EstudianteControlador;
import model.controladores.MateriaControlador;
import model.controladores.ProfesorControlador;
import model.controladores.ValoracionMateriaControlador;

import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;

public class PanelGestionValoracionMateria extends JPanel{
	
	public static int LOAD_FIRST = 0;
	public static int LOAD_PREV = 1;
	public static int LOAD_NEXT = 2;
	public static int LOAD_LAST = 3;
	public static int NEW = 4;
	public static int SAVE = 5;
	public static int REMOVE = 6;
	
	Valoracionmateria actual = null;

	JComboBox<Profesor> jcbProfesor = new JComboBox<Profesor>();
	JComboBox<Materia> jcbMateria = new JComboBox<Materia>();
	JButton jbtRefrescarAlumno = new JButton("Botón refrescar alumno");
	
	
	public PanelGestionValoracionMateria() {
		
		actual = ValoracionMateriaControlador.getInstancia().findFirst();
		cargarDatosActual();
		
		this.setLayout(new BorderLayout());
//		this.add(getToolBar(), BorderLayout.NORTH);
		this.add(getPanelGestion(), BorderLayout.NORTH);
		this.add(getPanelGestionAlumnos(), BorderLayout.CENTER);
		
		
	}
	
	private JPanel getPanelGestionAlumnos() {
		JPanel panelGestionAlumnos = new JPanel();
		panelGestionAlumnos.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		
		int i = 2;
		List<Estudiante> est = EstudianteControlador.getInstancia().findAllEstudiantes();
		for (Estudiante e : est) {
			c.fill = GridBagConstraints.BOTH;
			c.gridx = 0;
			c.gridy = 0 + i;
			panelGestionAlumnos.add(new JLabel(e.toString() + ":"), c);
			i++;
		}
		
		
		
		return panelGestionAlumnos;
	}

	private JPanel getPanelGestion() {
		JPanel panelGestion = new JPanel();
		panelGestion.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

        //Color JPanel
        panelGestion.setBackground(Color.green);
		
		c.gridx = 0;
		c.gridy = 1;
		c.anchor = GridBagConstraints.EAST;
		panelGestion.add(new JLabel("Materia: "), c);

		c.gridx = 1;
		c.gridy = 1;
		c.anchor = GridBagConstraints.WEST;
		panelGestion.add(jcbMateria, c);

		List<Materia> materias = MateriaControlador.getInstancia().findAllMaterias();

		for (Materia ma : materias) {
			jcbMateria.addItem(ma);
		}
		
		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.EAST;
		panelGestion.add(new JLabel("Profesor: "), c);

		c.gridx = 1;
		c.gridy = 2;
		c.anchor = GridBagConstraints.WEST;
		panelGestion.add(jcbProfesor, c);

		// Inclusiï¿½n del campo "IdFab"
		List<Profesor> profesores = ProfesorControlador.getInstancia().findAllProfesores();

		for (Profesor pr : profesores) {
			jcbProfesor.addItem(pr);
		}

		c.gridx = 2;
		c.gridy = 9;
		c.anchor = GridBagConstraints.WEST;
		panelGestion.add(jbtRefrescarAlumno, c);
			
		jbtRefrescarAlumno.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				 System.out.println("Pulsaste el botón");

			}
		});

		return panelGestion;
	}
	
	
	/**
	 * 
	 */
	private void cargarDatosActual () {
		if (this.actual != null) {
			this.jcbProfesor.setSelectedItem(this.actual.getProfesor());
			this.jcbMateria.setSelectedItem(this.actual.getMateria());
		}
	}
}




