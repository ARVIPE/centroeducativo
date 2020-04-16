package parteGráfica;

import java.awt.BorderLayout;	
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;

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
import utils.CacheImagenes;
	
public class PanelGestionValoracionMateria extends JPanel {

	
	public static int SAVE = 1;
	private Dimension minimaDimensionJTextField = new Dimension(150, 20);
	JScrollPane jspane = new JScrollPane(new JPanel());

	private JComboBox<Materia> jcbMateria = new JComboBox<Materia>();
	private JComboBox<Profesor> jcbProfesor = new JComboBox<Profesor>();
	List<EstudianteJSpinner> spinners = new ArrayList<EstudianteJSpinner>();
	JButton jbtRefrescarAlumno = new JButton("Refrescar");
	Materia materia = null;
	Profesor profesor = null;


	public PanelGestionValoracionMateria() {
		//super();
		this.setLayout(new BorderLayout());
		this.add(getPanelGestion(), BorderLayout.CENTER);

	}

	private JPanel getPanelGestion() {
		JPanel panelGestion = new JPanel();
		panelGestion.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		List<Materia> materia = MateriaControlador.getInstancia().findAllMaterias();
		for (Materia mat : materia) {
			jcbMateria.addItem(mat);
		}

		c.gridx = 0;
		c.gridy = 1;
		c.anchor = GridBagConstraints.EAST;
		panelGestion.add(new JLabel("Materia: "), c);

		c.gridx = 1;
		c.anchor = GridBagConstraints.WEST;
		jcbMateria.setMinimumSize(minimaDimensionJTextField);
		panelGestion.add(jcbMateria, c);

		List<Profesor> profesores = ProfesorControlador.getInstancia().findAllProfesores();
		for (Profesor prof : profesores) {
			jcbProfesor.addItem(prof);
		}

		c.gridx = 0;
		c.gridy = 2;
		c.anchor = GridBagConstraints.EAST;
		panelGestion.add(new JLabel("Profesor: "), c);

		c.gridx = 1;
		c.anchor = GridBagConstraints.WEST;
		jcbProfesor.setMinimumSize(minimaDimensionJTextField);
		panelGestion.add(jcbProfesor, c);

		c.gridx = 1;
		c.gridy = 3;
		c.anchor = GridBagConstraints.WEST;
		panelGestion.add(jbtRefrescarAlumno, c);

		jbtRefrescarAlumno.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jspane.setViewportView(getPanelScroll());

			}
		});

		c.gridx = 1;
		c.gridy = 4;
		c.weighty = 1;
		c.anchor = GridBagConstraints.CENTER;
		jspane.setPreferredSize(new Dimension(250, 250));
		panelGestion.add(jspane, c);

		c.gridx = 1;
		c.gridy = 15;
		c.weighty = 0;
		c.anchor = GridBagConstraints.CENTER;
		JButton jbtGuardar = new JButton("Guardar");
		panelGestion.add(jbtGuardar, c);

		jbtGuardar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (spinners != null) {
					Profesor p = (Profesor) jcbProfesor.getSelectedItem();
					Materia m = (Materia) jcbMateria.getSelectedItem();
					for (EstudianteJSpinner spinner : spinners) {
						Valoracionmateria valoracion = ValoracionMateriaControlador.getInstancia()
								.findByEstudianteAndProfesorAndMateria(p, m, spinner.estudiante);
						if (valoracion != null) {
							valoracion.setValoracion(((Integer) spinner.getValue()).floatValue());
							ValoracionMateriaControlador.getInstancia().merge(valoracion);
						} else {
							Valoracionmateria v = new Valoracionmateria();
							v.setEstudiante(spinner.estudiante);
							v.setMateria(m);
							v.setProfesor(p);
							ValoracionMateriaControlador.getInstancia().persist(v);
						}

					}
				}

			}
		});

		return panelGestion;

	}

	public JPanel getPanelScroll() {
		JPanel pane = new JPanel();
		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		List<Estudiante> estudiantes = EstudianteControlador.getInstancia().findAllEstudiantes();
		int i = 2;
		this.spinners.clear();
		for (Estudiante e : estudiantes) {

			c.gridx = 0;
			c.gridy = 0 + i;
			c.insets = new Insets(2, 2, 2, 2);
			c.fill = GridBagConstraints.HORIZONTAL;
			pane.add(new JLabel(e.toString()), c);

			c.gridx = 1;
			c.gridy = 0 + i;
			c.anchor = GridBagConstraints.WEST;
			EstudianteJSpinner spinner = new EstudianteJSpinner(e);
			this.spinners.add(spinner);
			Valoracionmateria valoracion = ValoracionMateriaControlador.getInstancia()
					.findByEstudianteAndProfesorAndMateria((Profesor) this.jcbProfesor.getSelectedItem(),
							(Materia) this.jcbMateria.getSelectedItem(), e);
			if (valoracion != null) {
				spinner.setValue(new Integer((int) valoracion.getValoracion()));
			}
			pane.add(spinner, c);

			i++;
		}
		return pane;
	}

	private class EstudianteJSpinner extends JSpinner {
		Estudiante estudiante = null;
		public EstudianteJSpinner(Estudiante estudiante) {
			super();
			this.estudiante = estudiante;
		}

	}

}


