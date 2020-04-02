package parteGráfica;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
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
import java.util.ArrayList;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;

public class PanelGestionValoracionMateria extends JPanel{
	
	JScrollPane pnlValoraciones = new JScrollPane();
	Valoracionmateria actual = null;
	JComboBox<Profesor> jcbProfesor = new JComboBox<Profesor>();
	JComboBox<Materia> jcbMateria = new JComboBox<Materia>();
	JButton jbtRefrescarAlumno = new JButton("Botón refrescar alumno");
	List<SlotValoracionEstudiante> panelesValoracionEstudiante = new ArrayList<SlotValoracionEstudiante>();
	
	public PanelGestionValoracionMateria() {
		
		actual = ValoracionMateriaControlador.getInstancia().findFirst();
		cargarDatosActual();
		
		this.setLayout(new BorderLayout());
		this.add(getPanelGestion(), BorderLayout.NORTH);
	
		
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
				System.out.println("ha pulsado el boton");

			}
		});
		
		c.gridx = 0;
		c.gridy = 5;
		panelGestion.add(pnlValoraciones, c);
		pnlValoraciones.setViewportView(getPanelGestionAlumnos());

		return panelGestion;
	}
	
	private JPanel getPanelGestionAlumnos() {
	JPanel panelGestionAlumnos = new JPanel();
	panelGestionAlumnos.setLayout(new GridBagLayout());
	GridBagConstraints c = new GridBagConstraints();
	
	
	int i = 2;
	for (SlotValoracionEstudiante s : panelesValoracionEstudiante) {
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0 + i;
		panelGestionAlumnos.add(new JLabel(s.toString() + ":"), c);
		i++;
	}
	
	
	
	return panelGestionAlumnos;
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




