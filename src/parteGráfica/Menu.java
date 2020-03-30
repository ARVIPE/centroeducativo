package parteGráfica;

import java.awt.Toolkit;					
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import javax.swing.JTabbedPane;



public class Menu extends JMenuBar {

	public Menu() {
		
		//Menú Archivo de la aplicación
		JMenu menu = new JMenu("Entidades");
		
		menu.add(crearMenuLanzamiento("Gestión de cursos académicos", new PanelGestionCursosAcademicos()));
		menu.add(crearMenuLanzamiento("Gestión de estudiantes", new PanelGestionEstudiantes()));
		menu.add(crearMenuLanzamiento("Gestión de materia", new PanelGestionMateria()));

		this.add(menu);
	}
	
	private JMenuItem crearMenuLanzamiento(String titulo, JPanel panel) {
		JMenuItem mi = new JMenuItem(titulo);
		mi.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JDialog dialogo = new JDialog();
				//El usuario no puede redimensionar el dialogo
				dialogo.setResizable(true);
				//titulo del dialogo
				dialogo.setTitle(titulo);
				// Introducimos el panel creado sobre el dialogo
				dialogo.setContentPane(panel);
				//Empaquetar el diálogo hace que todos los componentes ocupen el espacio que deben y el lugar adecuado
				dialogo.pack();
				//El usuario no puede hacer clic sobre la ventana padre, si el dialogo es modal
				dialogo.setModal(true);
				//Centro el diálogo en pantalla
				dialogo.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width)/2 - dialogo.getWidth()/2,
						(Toolkit.getDefaultToolkit().getScreenSize().height)/2 - dialogo.getHeight()/2);
				//Muestro el diálogo en pantalla
				dialogo.setVisible(true);
			}
		});
		return mi;
	}
}
