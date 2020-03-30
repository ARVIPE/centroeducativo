package parteGráfica;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import model.Estudiante;
import model.controladores.EstudianteControlador;
import utils.CacheImagenes;

public class PanelGestionEstudiantes extends JPanel {

	public static int LOAD_FIRST = 0;
	public static int LOAD_PREV = 1;
	public static int LOAD_NEXT = 2;
	public static int LOAD_LAST = 3;
	public static int NEW = 4;
	public static int SAVE = 5;
	public static int REMOVE = 6;

	Estudiante actual = null;

	PanelGestionDatosPersonales panelDatosPersonales = new PanelGestionDatosPersonales();

	public PanelGestionEstudiantes() {
		
		actual = EstudianteControlador.getInstancia().findFirst();
		cargarDatosActual();
		
		this.setLayout(new BorderLayout());
		this.add(getToolBar(), BorderLayout.NORTH);
		this.add(panelDatosPersonales, BorderLayout.CENTER);
		
		
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
	private void asignarFuncion(JButton jbt, String icono, final int funcion) {
		jbt.setIcon(CacheImagenes.getCacheImagenes().getIcono(icono));
		jbt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				Estudiante obtenido = null;
				if (funcion == LOAD_FIRST)
					obtenido = EstudianteControlador.getInstancia().findFirst();
				if (funcion == LOAD_PREV)
					obtenido = EstudianteControlador.getInstancia().findPrevious(actual);
				if (funcion == LOAD_NEXT)
					obtenido = EstudianteControlador.getInstancia().findNext(actual);
				if (funcion == LOAD_LAST)
					obtenido = EstudianteControlador.getInstancia().findLast();
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
			}
		});
	}

	/**
	 * 
	 */
	private void nuevo() {
		this.panelDatosPersonales.limpiarPantalla();
		this.actual = new Estudiante();
		JOptionPane.showMessageDialog(this, "Introduzca los datos nuevos");
	}


	/**
	 * 
	 */
	private void guardar() {
		this.actual.setNombre(this.panelDatosPersonales.getNombre());
		this.actual.setApellido1(this.panelDatosPersonales.getPrimerApellido());
		this.actual.setApellido2(this.panelDatosPersonales.getPrimerApellido());
		this.actual.setDni(this.panelDatosPersonales.getDni());
		this.actual.setDireccion(this.panelDatosPersonales.getDireccion());
		this.actual.setTelefono(this.panelDatosPersonales.getTelefono());
		this.actual.setEmail(this.panelDatosPersonales.getEmail());
		this.actual.setTipologiasexo(this.panelDatosPersonales.getTipologiaSexo());
		this.actual.setColor(this.panelDatosPersonales.getColorElegido());
		
		if(actual.getId() == 0) {
			EstudianteControlador.getInstancia().persist(actual);
		}
		else {
			EstudianteControlador.getInstancia().merge(actual);
		}
		JOptionPane.showMessageDialog(this, "Guardado estupendamente");
	}

	/**
	 * 
	 * @return
	 */
	private Estudiante eliminar() {
		String respuestas[] = new String[] { "S�", "No" };
		int opcionElegida = JOptionPane.showOptionDialog(null, "�Realmente quiere eliminar el registro?",
				"Eliminar registro", JOptionPane.OK_CANCEL_OPTION, JOptionPane.OK_CANCEL_OPTION,
				CacheImagenes.getCacheImagenes().getIcono("confirm.png"), respuestas, respuestas[1]);

		if (opcionElegida == 0) {
			Estudiante nuevoAMostrar = EstudianteControlador.getInstancia().findFirst();
			if (nuevoAMostrar == null) {
				nuevoAMostrar = EstudianteControlador.getInstancia().findNext(actual);
			}
			EstudianteControlador.getInstancia().remove(actual);
			JOptionPane.showMessageDialog(this, "Eliminado correctamente");

			if (nuevoAMostrar != null) {
				actual = nuevoAMostrar;
			} else {
				this.panelDatosPersonales.limpiarPantalla();
			}
		}
		return actual;

	}

	/**
	 * 
	 */
	private void cargarDatosActual() {
		if (this.actual != null) {
			panelDatosPersonales.setNombre(this.actual.getNombre());
			panelDatosPersonales.setPrimerApellido(this.actual.getApellido1());
			panelDatosPersonales.setSegundoApellido(this.actual.getApellido2());
			panelDatosPersonales.setDni(this.actual.getDni());
			panelDatosPersonales.setDireccion(this.actual.getDireccion());
			panelDatosPersonales.setTelefono(this.actual.getTelefono());
			panelDatosPersonales.setEmail(this.actual.getEmail());
			panelDatosPersonales.setTipologiaSexo(this.actual.getTipologiasexo());
			panelDatosPersonales.setColorElegido(this.actual.getColor());
		}
	}

}
