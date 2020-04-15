package parteGráfica;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import utils.CacheImagenes;





public class ToolBar extends JToolBar {

	private static final long serialVersionUID = 1L;
	

	public ToolBar() {
		this.add(crearBoton(0, "Curso", "conectado.png", "Ir a curso", new PanelGestionCursosAcademicos()));
		this.add(crearBoton(1, "Materia", "conectado.png", "Ir a materia", new PanelGestionMateria()));
		this.add(crearBoton(2, "Estudiante", "conectado.png", "Ir a estudiante", new PanelGestionEstudiantes()));
		this.add(crearBoton(3, "Profesor", "conectado.png", "Ir profesor", new PanelGestionProfesores()));
		this.add(crearBoton(4, "Valoración Materia", "conectado.png", "Ir al materia", new PanelGestionValoracionMateria()));
		this.add(crearBoton(5, "Valoracion masiva Materia", "conectado.png", "Ir a la valoracion", new PanelGestionValoracionMasivaEstudiantes()));
	}

	private JButton crearBoton(int num, String titulo, String icono, String toolTip, JPanel panel) {
		JButton jbt = new JButton(titulo);
		
		jbt.setText(titulo);
		jbt.setToolTipText(toolTip);
		
		jbt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog dialogo = new JDialog();
				dialogo.setResizable(true);
				dialogo.setTitle(titulo);
				dialogo.setContentPane(panel);
				dialogo.pack();
				dialogo.setModal(true);
				dialogo.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width)/2 - dialogo.getWidth()/2,
						(Toolkit.getDefaultToolkit().getScreenSize().height)/2 - dialogo.getHeight()/2);
				dialogo.setVisible(true);
			}
			
		});
		
		try {
			jbt.setIcon(CacheImagenes.getCacheImagenes().getIcono(icono));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return jbt;
	}
	

}

