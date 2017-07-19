package frm;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;

import entidades.entMaeCli;
import util.validaciones;

import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Font;

public class frmCuenta extends JFrame{
	
	
	static ResourceBundle msj = ResourceBundle.getBundle("mensajes");
	static Socket cli=null;
	static ObjectOutputStream flujoEnvio = null;
	static ObjectInputStream flujoEntrada = null;
	static entMaeCli entMae = null;
	private validaciones v = null;
	
	private entMaeCli mc=null;
	private boolean bandera = true;

	private static final long serialVersionUID = -2778620051956465611L;
	private JPanel contentPane;
	private JTextField txtNroTargeta;
	private JPasswordField txtClave;
	private JButton btnCancelar;
	private JPanel panel;
	private JLabel lblNewLabel;
	private JButton btnIngresar;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmCuenta frame = new frmCuenta();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public frmCuenta() {
		gui();
		eventos();
		v.validar(txtNroTargeta, 8);
		v.validar(txtClave, 4);
	}

	private void gui() {
		
		setResizable(false);
		setBackground(new Color(153, 204, 153));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 455, 206);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(153, 204, 153));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setBorder(new LineBorder(new Color(0, 102, 102)));
		btnCancelar.setForeground(new Color(0, 102, 102));
		btnCancelar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCancelar.setBounds(353, 147, 89, 23);
		contentPane.add(btnCancelar);
		
		panel = new JPanel();
		panel.setBackground(new Color(153, 204, 153));
		panel.setBorder(new LineBorder(new Color(0, 102, 102)));
		panel.setBounds(156, 11, 286, 125);
		contentPane.add(panel);
		panel.setLayout(null);
		
		txtNroTargeta = new JTextField();		
		txtNroTargeta.setFont(new Font("Tahoma", Font.BOLD, 14));
		txtNroTargeta.setForeground(new Color(255, 255, 255));
		txtNroTargeta.setBackground(new Color(0, 102, 102));
		txtNroTargeta.setBounds(127, 32, 150, 20);
		panel.add(txtNroTargeta);
		txtNroTargeta.setColumns(10);
		
		txtClave = new JPasswordField();
		txtClave.setFont(new Font("Tahoma", Font.PLAIN, 14));
		txtClave.setForeground(new Color(255, 255, 255));
		txtClave.setBackground(new Color(0, 102, 102));
		txtClave.setBounds(127, 70, 150, 20);
		panel.add(txtClave);
		
		JLabel lblNroTarjeta = new JLabel("Nro Tarjeta:");
		lblNroTarjeta.setForeground(new Color(0, 102, 102));
		lblNroTarjeta.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNroTarjeta.setBounds(10, 35, 107, 14);
		panel.add(lblNroTarjeta);
		
		JLabel lblClave = new JLabel("Clave:");
		lblClave.setForeground(new Color(0, 102, 102));
		lblClave.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblClave.setBounds(10, 73, 107, 14);
		panel.add(lblClave);
		
		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(frmCuenta.class.getResource("/recursos/1446422649_Login.png")));
		lblNewLabel.setBounds(10, 11, 136, 125);
		contentPane.add(lblNewLabel);
		
		btnIngresar = new JButton("Ingresar");
		btnIngresar.setBorder(new LineBorder(new Color(0, 102, 102)));
		btnIngresar.setForeground(new Color(0, 102, 102));
		btnIngresar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnIngresar.setBounds(254, 147, 89, 23);
		contentPane.add(btnIngresar);
		//centrar formulario
		this.setLocationRelativeTo(null);
		
		
	}

	private void eventos() {
	
		txtNroTargeta.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode()==KeyEvent.VK_ENTER){
					validarCampos();
				}
			}
		});
		
		txtClave.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER){ 
						validarCampos();					
					} 
			}
		});
		
		btnIngresar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
								
				validarCampos();	

			}
		});
		
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {	
				System.exit(0);
			}
		});
		
	}
	
	void ingresar(){
		try {
				
				
				
				//Creacion del Objeto
				mc = new entMaeCli();
				mc.setNumeroTarjeta(Integer.parseInt(txtNroTargeta.getText()));
				mc.setClave(Integer.parseInt(txtClave.getText().toString()));
				
						while(!(txtNroTargeta.getText().equals("") && txtClave.getText().equals(""))){
						//Inicializacion del Socket del cliente
						cli = new Socket("127.0.0.1",5003);
						
						//Envio del Objeto al Servidor
						flujoEnvio = new ObjectOutputStream(cli.getOutputStream());
						flujoEnvio.writeObject(mc);
						System.out.println("Envio del parametros: tarjeta:"+mc.getNumeroTarjeta()+" Clave:"+mc.getClave());	
						
						//Recepcion del Objeto desde el Servidor
						flujoEntrada = new ObjectInputStream(cli.getInputStream()); 
						//llenamos en una variable global y estatica el objeto para poder transitar parametros entre formularios;
						entMae = (entMaeCli)flujoEntrada.readObject();
						System.out.println("Recibo del parametros: Saldo : "+entMae.getSaldo()+
								" Nombre :  "+entMae.getNombre());	
						
						
						//Validamos si los datos son validos
						if(entMae.getId()!=0){
							dispose();
							JOptionPane.showMessageDialog(this, "Bienvenido "+entMae.getNombre()+".","Cuenta",JOptionPane.INFORMATION_MESSAGE);
							frmMenu frm = new frmMenu();
							frm.show();
							
							//cerramos las conexiones
							flujoEnvio.close();
							flujoEntrada.close();
							cli.close();
			
							//como buena Practica imprimimos en consola el avance
							System.out.println("Exito en el frmCuenta");
							bandera=false;
							return;
						}else{
							txtNroTargeta.setText("");
							txtClave.setText("");
							txtNroTargeta.requestFocus();
							
							JOptionPane.showMessageDialog(this,msj.getString("msjfrmCuentaUsuarioOPasswordNoValido"),msj.getString("cabeceraMensajeCuenta") ,JOptionPane.INFORMATION_MESSAGE);
							
							//cerramos las conexiones
							flujoEnvio.close();
							flujoEntrada.close();
							cli.close();
							
							//como buena Practica imprimimos en consola el avance
							System.out.println("Sin exito para ingresar al frmCuenta");
							mc=null;
						}	
				}
			
		}
		catch (IOException io) {								
			io.printStackTrace();
			System.out.println("Error: "+io.getMessage());
		} 
		catch (Exception e) {				
			e.printStackTrace();
			System.out.println("Error: "+e.getMessage());
		}finally{
			
		}
	}
	
	
	private void validarCampos(){
		if(txtNroTargeta.getText().equals("") && txtClave.getText().equals("")){
			JOptionPane.showMessageDialog(this, msj.getString("msjfrmCuentaSinCampos"),msj.getString("cabeceraMensajeCuenta"),JOptionPane.INFORMATION_MESSAGE);
			txtNroTargeta.requestFocus();
			return;
		}
		if(txtNroTargeta.getText().equals("")){
			JOptionPane.showMessageDialog(this,msj.getString("msjfrmCuentaSinNumeroCuenta"),msj.getString("cabeceraMensajeCuenta"),JOptionPane.INFORMATION_MESSAGE);
			txtNroTargeta.requestFocus();
			return;
		}
		if(txtNroTargeta.getText().length()<8){
			JOptionPane.showMessageDialog(this, msj.getString("msjfrmCuentaTarjetaOchoDigitos"),msj.getString("cabeceraMensajeCuenta"),JOptionPane.INFORMATION_MESSAGE);
			txtNroTargeta.requestFocus();
			return;
		}
		if(txtClave.getText().equals("")){
			JOptionPane.showMessageDialog(this, msj.getString("msjfrmCuentaSinClave"),msj.getString("cabeceraMensajeCuenta"),JOptionPane.INFORMATION_MESSAGE);
			txtClave.requestFocus();
			return;
		}
		if(txtClave.getText().length()<4){
			JOptionPane.showMessageDialog(this, msj.getString("msjfrmCuentaClaveCuatroDigitos"),msj.getString("cabeceraMensajeCuenta"),JOptionPane.INFORMATION_MESSAGE);
			txtClave.requestFocus();
			return;
		}
		else{
			ingresar();
		}
	}
	
}
