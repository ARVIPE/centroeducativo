package parteGráfica;

import java.awt.BorderLayout;			
import java.awt.Dimension;	
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import utils.Apariencia;
import utils.CacheImagenes;







public class VentanaPrincipal extends JFrame {

	public static int ANCHO = 800;
	public static int ALTO = 600;
	public static String TITULO_APLICACION = "Centro educativo";

	private CacheImagenes cacheImagenes;
	public static BufferedImage iconoApp;
	
//	static JTabbedPane jTabbedPane = new JTabbedPaneGestion();

	// Establecer la apariencia típica de Windows
	static {
		Apariencia.setAparienciasOrdenadas(Apariencia.aparienciasOrdenadas);
	}
	
	
	
	public VentanaPrincipal () {
		super (TITULO_APLICACION);
		
		cacheImagenes = new CacheImagenes();
		iconoApp = cacheImagenes.getImagen("nave.gif");
		setIconImage(iconoApp);
		
		this.setExtendedState(JFrame.NORMAL);
		this.setMinimumSize(new Dimension(ANCHO, ALTO));
		
		agregarGestionCierreAplicacion();
		
		// Menú de la aplicación
		this.setJMenuBar(new Menu());

		// Construcción elementos básicos sobre el ContentPanel
		this.getContentPane().setLayout(new BorderLayout());
		this.add(new ToolBar(), BorderLayout.NORTH);
//		this.getContentPane().add(jTabbedPane, BorderLayout.CENTER);
		
	
		
	}
	

	
		
	/**
	 * 
	 */
	private void agregarGestionCierreAplicacion () {
		// Configuración del evento de cerrado
		// Para más información debes estudiar Javadoc WindowListener y WindowAdapter
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener (new WindowAdapter() {
			public void windowClosing (WindowEvent e) {
				String posiblesRespuestas[] = {"Sí","No"};
				// En esta opción se utiliza un showOptionDialog en el que personalizo el icono mostrado
				int opcionElegida = JOptionPane.showOptionDialog(null, "¿Realmente desea cerrar la aplicación?", TITULO_APLICACION, 
				        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, cacheImagenes.getIcono("confirm.png"), posiblesRespuestas, posiblesRespuestas[1]);
			    if(opcionElegida == 0) {
			      System.exit(0);          
			    }
			}
		});
	}
}
