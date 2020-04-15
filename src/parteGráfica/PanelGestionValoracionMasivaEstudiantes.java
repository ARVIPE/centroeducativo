package parteGráfica;

import java.awt.BorderLayout;		
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
	
public class PanelGestionValoracionMasivaEstudiantes extends JPanel {

	public static int SAVE = 1;
	private Dimension minimaDimensionJTextField = new Dimension(150, 20);
	JScrollPane jspane = new JScrollPane(new JPanel());
	JScrollPane jspane2 = new JScrollPane(new JPanel());

	private JComboBox<Materia> jcbMateria = new JComboBox<Materia>();
	private JComboBox<Profesor> jcbProfesor = new JComboBox<Profesor>();
	List<EstudianteJSpinner> spinners = new ArrayList<EstudianteJSpinner>();
	JButton jbtRefrescarAlumno = new JButton("Botón actualizar alumnado");
	JButton jbtTodosIzquierda = new JButton("<<");
	JButton jbtIzquierda = new JButton("<");
	JButton jbtDerecha = new JButton(">");
	JButton jbtTodosDerecha = new JButton(">>");
	Materia materia = null;
	Profesor profesor = null;
	JSlider slider = null;
	JLabel label = new JLabel();
	private static final int MAX = 10;
	private static final int MIN = 0;
	private static final int DEFAULT = 5;
	
	public PanelGestionValoracionMasivaEstudiantes() {
		super();
		this.setLayout(new BorderLayout());
		this.add(getPanelGestion(), BorderLayout.CENTER);
		this.slider.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				label.setText(String.valueOf(slider.getValue()));
			}
		});

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
		
		// Incluimos el Slider

		slider = new JSlider(MIN, MAX, DEFAULT);
		slider.setMinorTickSpacing(MIN);
		slider.setMajorTickSpacing(MAX);
		slider.setPaintTicks(true);
		slider.setPaintLabels(true);

		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.EAST;
		c.insets = new Insets(2, 2, 2, 2);
		panelGestion.add(new JLabel("Nota: "), c);

		
		c.gridx = 1;
		c.gridy = 3;
		jcbProfesor.setEnabled(true);
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(2, 2, 2, 2);
		panelGestion.add(slider, c);
		
		//Incluimos el JLabel de nota
		
		c.fill = GridBagConstraints.CENTER;
		c.gridx = 2;
		c.gridy = 3;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(2, 2, 2, 2);
		panelGestion.add(new JLabel("Nota: "), c);
		
		
		c.gridx = 2;
		c.gridy = 3;
		c.anchor = GridBagConstraints.EAST;
		c.insets = new Insets(2, 2, 2, 2);
		panelGestion.add(label, c);
		
		// Inserto el formattedtextfield para fechas personalizadas
		
		c.gridx = 0;
		c.gridy = 4;
		c.anchor = GridBagConstraints.SOUTHEAST;
		panelGestion.add(new JLabel("Fecha"), c);

		c.gridx = 1;
		c.gridy = 4;
		c.anchor = GridBagConstraints.SOUTHWEST; 
		panelGestion.add(getJFormattedTextFieldDatePersonalizado(), c);
		
		c.gridx = 1;
		c.gridy = 5;
		c.anchor = GridBagConstraints.WEST;
		panelGestion.add(jbtRefrescarAlumno, c);

		jbtRefrescarAlumno.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				jspane.setViewportView(getPanelScroll());

			}
		});

		c.gridx = 0;
		c.gridy = 9;
		c.weighty = 1;
		c.anchor = GridBagConstraints.CENTER;
		jspane.setPreferredSize(new Dimension(250, 250));
		panelGestion.add(jspane, c);
		
		c.gridx = 2;
		c.gridy = 9;
		c.weighty = 1;
		c.anchor = GridBagConstraints.CENTER;
		jspane2.setPreferredSize(new Dimension(250, 250));
		panelGestion.add(jspane2, c);
		
		c.gridx = 1;
		c.gridy = 9;
		c.weighty = 1;
		c.anchor = GridBagConstraints.CENTER;
		panelGestion.add(getjpBotones(),c);
		
		
		c.gridx = 1;
		c.gridy = 12;
		c.weighty = 0;
		c.anchor = GridBagConstraints.CENTER;
		JButton jbtGuardar = new JButton("Guardar las notas de todos los alumnos seleccionados");
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
	
	public JPanel getjpBotones() {
		
		JPanel panelBotones = new JPanel();
		panelBotones.setLayout(new GridBagLayout());
		GridBagConstraints b = new GridBagConstraints();
		b.gridx = 0;
		b.gridy = 0;
		b.anchor = GridBagConstraints.CENTER;
		panelBotones.add(jbtTodosIzquierda, b);
		
		b.gridx = 0;
		b.gridy = 1;
		b.anchor = GridBagConstraints.CENTER;
		panelBotones.add(jbtIzquierda, b);
		
		b.gridx = 0;
		b.gridy = 2;
		b.anchor = GridBagConstraints.CENTER;
		panelBotones.add(jbtDerecha, b);
		
		b.gridx = 0;
		b.gridy = 3;
		b.anchor = GridBagConstraints.CENTER;
		panelBotones.add(jbtTodosDerecha, b);
		
		return panelBotones;
	}
	
	/**
	 * 
	 * @return
	 */
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
	
	/**
	 * 
	 * @return
	 */
	private JFormattedTextField getJFormattedTextFieldDatePersonalizado() {
		JFormattedTextField jftf = new JFormattedTextField(new JFormattedTextField.AbstractFormatter() {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

			@Override
			public String valueToString(Object value) throws ParseException {
				if (value != null && value instanceof Date) {
					return sdf.format(((Date) value));
				}
				return "";
			}

			@Override
			public Object stringToValue(String text) throws ParseException {
				try {
					return sdf.parse(text);
				} catch (Exception e) {
					return null;
				}
			}
		});
		jftf.setColumns(20);
		jftf.setValue(new Date());
		return jftf;
	}



}
