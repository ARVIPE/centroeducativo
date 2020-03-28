package centroeducativo.parteGráfica;

import java.awt.Dimension;		
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import centroeducativo.utils.CacheImagenes;




public class ToolBar extends JToolBar {

	private static final long serialVersionUID = 1L;
	
	public ToolBar () {
        this.add(creaBoton(0, "", "ruedadentada.png", "rueda"));
	}

	
	/**
	 * 
	 * @param titulo
	 * @param icono
	 * @return
	 */
	private JButton creaBoton(int num, String titulo, String icono, String toolTip) {
        JButton jbt = new JButton();
        
        jbt.setText(titulo);
        jbt.setToolTipText(toolTip);
        
        jbt.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
//            	VentanaPrincipal.jTabbedPane.setSelectedIndex(num);
            	System.out.println("Has hecho clic en el botón: \"" + toolTip + "\"");
            }
        });
        
        try {
        	jbt.setIcon(CacheImagenes.getCacheImagenes().getIcono(icono));  
          } catch (Exception ex) {
        	  ex.printStackTrace();
          }
        return jbt;
	}
	
}
