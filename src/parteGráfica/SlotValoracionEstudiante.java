package parteGráfica;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;

import model.Estudiante;
import model.Materia;
import model.Profesor;
import model.Valoracionmateria;
import model.controladores.ValoracionMateriaControlador;

public class SlotValoracionEstudiante extends JPanel{

	private JSpinner spinner;
	Estudiante estudiante = null;
	Materia materia = null;
	Profesor profesor = null;
	
	/**
	 * Create the panel
	 */
	public SlotValoracionEstudiante(Estudiante estudiante, Profesor profesor, Materia materia) {
		this.estudiante = estudiante;
		this.materia = materia;
		this.profesor = profesor;
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0,150};
		setLayout(gridBagLayout);
		
		JLabel lblNombre = new JLabel(estudiante.getNombre() + " " + estudiante.getApellido1() + " " + estudiante.getApellido2());
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.insets = new Insets(0, 0, 0, 5);
		gbc_lblNombre.anchor = GridBagConstraints.EAST;
		gbc_lblNombre.gridx = 0;
		gbc_lblNombre.gridy = 0;
		add(lblNombre, gbc_lblNombre);
		
		spinner = new JSpinner();
		this.cargarNotaDelEstudiante();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 1;
		gbc_textField.gridy = 0;
		add(spinner, gbc_textField);
		
	}
	
	private void cargarNotaDelEstudiante() {
		Valoracionmateria valoracionActual = ValoracionMateriaControlador.getInstancia().findByEstudianteAndProfesorAndMateria(profesor, materia, estudiante);
		if(valoracionActual != null) {
			this.spinner.setValue(valoracionActual.getValoracion() );
		}
	}
	
	public void guardarValoracion() {
		Valoracionmateria valoracionActual = ValoracionMateriaControlador.getInstancia().findByEstudianteAndProfesorAndMateria(this.profesor, this.materia, this.estudiante);
		if(valoracionActual == null) {
			valoracionActual = new Valoracionmateria();
			valoracionActual.setProfesor(this.profesor);
			valoracionActual.setMateria(this.materia);
			valoracionActual.setEstudiante(this.estudiante);
			
			if(this.spinner.getValue() instanceof Float) {
				valoracionActual.setValoracion(((Float) this.spinner.getValue()).floatValue());
			}
			else if(this.spinner.getValue() instanceof Integer) {
				valoracionActual.setValoracion(((Integer) this.spinner.getValue()).floatValue());
			}
			
			if(valoracionActual.getId() == 0) {
				ValoracionMateriaControlador.getInstancia().persist(valoracionActual);
			}
			else {
				ValoracionMateriaControlador.getInstancia().merge(valoracionActual);
			}
		}
	}
	
	
	
	
	
}
