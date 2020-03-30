package parteGráfica;

import java.awt.GridBagConstraints;	
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;

import java.awt.Color;
import java.awt.Dimension;

import model.Curso;
import model.Tipologiasexo;
import model.controladores.CursoControlador;
import model.controladores.TipologiaSexoControlador;
import utils.CacheImagenes;

public class PanelGestionDatosPersonales extends JPanel{

	JTextField jtfNombre = new JTextField(10);
	JTextField jtfPrimerApellido = new JTextField(10);
	JTextField jtfSegundoApellido = new JTextField(10);
	JTextField jtfDNI = new JTextField(10);
	JTextField jtfDireccion = new JTextField(10);
	JTextField jtfEMail = new JTextField(10);
	JTextField jtfTelefono = new JTextField(10);
	JComboBox<Tipologiasexo> jcbSexo = new JComboBox<Tipologiasexo>();
	JScrollPane jsp = new JScrollPane(new JLabel());
	byte[] imagen;
	JButton jbtCambiarImg;
	JTextField jtfColorPreferido = new JTextField(10);
	JButton jbtElegirColor = new JButton("Elija un color");
	JFileChooser jfileChooser = new JFileChooser();

    
	
	public PanelGestionDatosPersonales() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
	
		
		// a�adimos los campos para nombre
		c.fill = GridBagConstraints.NONE;
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.EAST;
		c.insets = new Insets(2, 2, 2, 2);
		this.add(new JLabel("Nombre: "), c);
		
		c.gridx = 1;
		c.gridy = 1;
		c.anchor = GridBagConstraints.WEST;
		this.add(jtfNombre, c);
		
		c.gridx = 2;
		c.gridy = 1;
		jtfDireccion.setEnabled(true);
		c.anchor = GridBagConstraints.WEST;
		c.insets = new Insets(0, 0, 5, 5);
		//Le damos unas dimensiones al scroll
		jsp.setPreferredSize( new Dimension(100, 100));
		this.add(jsp, c);

		jbtCambiarImg = new JButton("Elegir imagen");
		jbtCambiarImg.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				seleccionaFichero(getImagen());
			}
		});

		c.gridx = 2;
		c.gridy = 2;
		c.anchor = GridBagConstraints.WEST;
		this.add(jbtCambiarImg, c);
		
		// a�adimos los campos para primerApellido
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.EAST;
		this.add(new JLabel("Primer Apellido: "), c);
		
		c.gridx = 1;
		c.gridy = 2;
		jtfPrimerApellido.setEnabled(true);
		c.anchor = GridBagConstraints.WEST;
		this.add(jtfPrimerApellido, c);
		
		// a�adimos los campos para segundoApellido
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.EAST;
		this.add(new JLabel("Segundo Apellido: "), c);
		
		c.gridx = 1;
		c.gridy = 3;
		jtfSegundoApellido.setEnabled(true);
		c.anchor = GridBagConstraints.WEST;
		this.add(jtfSegundoApellido, c);

		// a�adimos los campos para Dni
		c.gridx = 0;
		c.gridy = 4;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.EAST;
		this.add(new JLabel("Dni: "), c);
		
		c.gridx = 1;
		c.gridy = 4;
		jtfDNI.setEnabled(true);
		c.anchor = GridBagConstraints.WEST;
		this.add(jtfDNI, c);
		
		// a�adimos los campos para Direcci�n
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.EAST;
		this.add(new JLabel("Direccion: "), c);
		
		c.gridx = 1;
		c.gridy = 5;
		c.gridwidth = 2;
		jtfDireccion.setEnabled(true);
		c.anchor = GridBagConstraints.WEST;
		this.add(jtfDireccion, c);
		
		// a�adimos los campos para Tel�fono
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 6;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.EAST;
		this.add(new JLabel("Telefono: "), c);
		
		c.gridx = 1;
		c.gridy = 6;
		jtfTelefono.setEnabled(true);
		c.anchor = GridBagConstraints.WEST;
		this.add(jtfTelefono, c);

		// a�adimos los campos para Email
		c.gridx = 0;
		c.gridy = 7;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.EAST;
		this.add(new JLabel("Email: "), c);
		
		c.gridx = 1;
		c.gridy = 7;
		jtfEMail.setEnabled(true);
		c.anchor = GridBagConstraints.WEST;
		this.add(jtfEMail, c);
		
		JLabel lblsexo = new JLabel("Sexo: ");
		GridBagConstraints gbc_lblsexo = new GridBagConstraints();
		gbc_lblsexo.gridx = 0;
		gbc_lblsexo.gridy = 8;
		gbc_lblsexo.gridwidth = 1;
		gbc_lblsexo.anchor = GridBagConstraints.EAST;
		add(lblsexo, gbc_lblsexo);

		c.gridx = 1;
		c.gridy = 8;
		c.anchor = GridBagConstraints.WEST;
		this.add(jcbSexo, c);
		
		List<Tipologiasexo> tipo = TipologiaSexoControlador.getInstancia().findAllTipologiasSexo();
		for (Tipologiasexo ti : tipo) {
			jcbSexo.addItem(ti);
		}
		
	
		// a�adimos los campos para Color
		c.gridx = 0;
		c.gridy = 9;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.EAST;
		this.add(new JLabel("Color: "), c);

		c.gridx = 1;
		c.gridy = 9;
		c.anchor = GridBagConstraints.WEST;
		this.add(jtfColorPreferido, c);

		c.gridx = 2;
		c.gridy = 9;
		c.anchor = GridBagConstraints.WEST;
		this.add(jbtElegirColor, c);
			
		jbtElegirColor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				seleccionarColor();

			}
		});
		
	
		
      
	}
	
		
	
	public void seleccionarColor() {
		Color color = (new JColorChooser().showDialog(null, "Elige un color", Color.gray));
		// cuando se elige un color, el color inicial deja de ser null
		if (color != null) {
			String strColor = "#" + Integer.toHexString(color.getRGB()).substring(2);
			this.jtfColorPreferido.setText(strColor);
			this.setColorElegido(strColor);
			this.setBackground(color);

		}
	}
	
	/**
	 * 
	 */

	protected JScrollPane scrollPane(String imagen) {

		JLabel jlb = new JLabel(CacheImagenes.getCacheImagenes().getIcono(imagen));

		JScrollPane jsp = new JScrollPane(jlb);
		return jsp;

	}

	/**
	 * 
	 * @return
	 */

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
		if (imagen != null && imagen.length > 0) {
			ImageIcon icono = new ImageIcon(this.imagen);
			JLabel jlblIcono = new JLabel(icono);
			jlblIcono.addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseClicked(e);
					this.mostrarMenu(e);
				}

				@Override
				public void mouseDragged(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseDragged(e);
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseEntered(e);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseExited(e);
				}

				@Override
				public void mouseMoved(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseMoved(e);
				}

				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mousePressed(e);
					this.mostrarMenu(e);
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					super.mouseReleased(e);
				}

				@Override
				public void mouseWheelMoved(MouseWheelEvent e) {
					// TODO Auto-generated method stub
					super.mouseWheelMoved(e);
				}

				private void mostrarMenu(MouseEvent e) {
					if (e.isPopupTrigger()) {
						JPopupMenu menu = new JPopupMenu();
						menu.add(new JMenuItem(icono.getIconWidth() + "x" + icono.getIconHeight() + "pixeles"));
						menu.addSeparator();
						JMenuItem miImagenSeleccionada = new JMenuItem("Seleccionar una imagen");
						miImagenSeleccionada.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								seleccionarImagen();

							}
						});
						menu.add(miImagenSeleccionada);
						menu.show(e.getComponent(), e.getX(), e.getY());
					}
				}

			});
			this.jsp.setViewportView(jlblIcono);
		} else {
			JLabel jlblIcono = new JLabel("No hay imagen");
			this.jsp.setViewportView(jlblIcono);
		}
	}
	
	/**
	 * @return 
	 * 
	 */

	private byte[] seleccionaFichero(byte[] imagenActual)  {
		this.jfileChooser = new JFileChooser();
		byte[] imagenSeleccionada = null;

		// Configurando el componente

		// Establecimiento de la carpeta de inicio
		this.jfileChooser.setCurrentDirectory(new File("C:\\"));

		// Tipo de selección que se hace en el diálogo
		this.jfileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY); // Sólo selecciona ficheros
		// this.jfileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); //
		// Sólo selecciona ficheros
		// this.jfileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		// // Selecciona ficheros y carpetas

		// Filtro del tipo de ficheros que puede abrir
		this.jfileChooser.setFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				return "Archivos de imagen *.jpg , .png , .jpeg o .gif";
			}

			@Override
			public boolean accept(File f) {
				if (f.isFile() && f.getAbsolutePath().endsWith(".jpg") || f.getAbsolutePath().endsWith(".png")
						|| f.getAbsolutePath().endsWith(".jpeg") || f.getAbsolutePath().endsWith(".gif"));
					
				return true;

			}
		});

		
		// Abro el diálogo para la elección del usuario
		int seleccionUsuario = jfileChooser.showOpenDialog(null);

		if (seleccionUsuario == JFileChooser.APPROVE_OPTION) {
			File fichero = this.jfileChooser.getSelectedFile();

			

			if (fichero.isFile()) {
				try {
					imagenSeleccionada = Files.readAllBytes(fichero.toPath());
					ImageIcon imagenProvisional = new ImageIcon(imagenSeleccionada);
					if (imagenProvisional.getIconWidth() > 800 || imagenProvisional.getIconHeight() > 800) {
						JOptionPane.showMessageDialog(null, "La imagen es demasiado grande");
						return imagenActual;
					}
					setImagen(imagenSeleccionada);
					return imagenSeleccionada;
					

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		imagenSeleccionada = imagenActual;
		
		return imagenActual;
	}

	public void seleccionarImagen() {
		this.jfileChooser = new JFileChooser();

	}
	
	
	/**
	 * 
	 */
	void limpiarPantalla() {
		this.jtfNombre.setText("");
		this.jtfPrimerApellido.setText("");
		this.jtfSegundoApellido.setText("");
		this.jtfDNI.setText("");
		this.jtfDireccion.setText("");
		this.jtfTelefono.setText("");
		this.jtfEMail.setText("");
		this.jcbSexo.setSelectedIndex(0);
		this.setBackground(Color.gray);
	}

	public String getNombre() {
		return jtfNombre.getText();
	}
	
	public void setNombre(String nombre) {
		this.jtfNombre.setText(nombre);
	}
	
	public String getPrimerApellido() {
		return jtfPrimerApellido.getText();
		
	}
	
	public void setPrimerApellido(String primerApellido) {
		this.jtfPrimerApellido.setText(primerApellido);
	}
	
	public String getSegundoApellido() {
		return jtfSegundoApellido.getText();
		
	}
	
	public byte[] getImagen() {
		return imagen;
	}
	
	public void setSegundoApellido(String segundoApellido) {
		this.jtfSegundoApellido.setText(segundoApellido);
	}
	
	public Tipologiasexo getTipologiaSexo() {
		return (Tipologiasexo) this.jcbSexo.getSelectedItem();
	}
	
	public void setTipologiaSexo(Tipologiasexo sexoEscogido) {
		if(sexoEscogido == null && this.jcbSexo.getItemCount() > 0) {
			this.jcbSexo.setSelectedIndex(0);
		}
		else {
			for (int i = 0; i < this.jcbSexo.getItemCount(); i++) {
				Tipologiasexo sexoEnJCombo = this.jcbSexo.getItemAt(i);
				if(sexoEscogido.getId() == sexoEnJCombo.getId()){
					this.jcbSexo.setSelectedIndex(i);
				}
			}
		}
	}
	
	public String getDni() {
		return jtfDNI.getText();
		
	}
	
	public void setDni(String Dni) {
		this.jtfDNI.setText(Dni);
	}
	
	public String getDireccion() {
		return jtfDireccion.getText();
		
	}
	
	public void setDireccion(String direccion) {
		this.jtfDireccion.setText(direccion);
	}
	
	public String getTelefono() {
		return jtfTelefono.getText();
		
	}
	
	public void setTelefono(String telefono) {
		this.jtfTelefono.setText(telefono);
	}
	
	public String getEmail() {
		return jtfEMail.getText();
		
	}
	
	public void setEmail(String email) {
		this.jtfEMail.setText(email);
	}
	

	public JTextField getJtfTelefono() {
		return jtfTelefono;
	}
	

	
	public void setColorElegido(String colorElegido) {
		this.jtfColorPreferido.setText(colorElegido);
		try {
			this.setBackground(Color.decode(colorElegido));
		} catch (Exception e) {
			System.out.println("No cambio");
			this.setBackground(Color.GRAY);
		}
	}
	
	public String getColorElegido() {
		return jtfColorPreferido.getText();
	}



	
	
	
	
	
	
}
