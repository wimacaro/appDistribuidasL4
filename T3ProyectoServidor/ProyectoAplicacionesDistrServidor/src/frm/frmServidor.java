package frm;

import java.awt.EventQueue;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.joda.time.DateTime;

import entidades.entMaeCli;
import negocio.negCliente;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class frmServidor extends JFrame implements Runnable{
	
	static ServerSocket servidor = null;
	Socket cli = null;
	
	
	static entMaeCli mlv=null,mc=null;
	Thread hilo;
	/**
	 * 
	 */
	private static final long serialVersionUID = -4787352196010705642L;
	boolean banderaV = true;
	boolean banderaM = false;
	private JPanel contentPane;
	private JTextArea txtContenedor;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmServidor frame = new frmServidor();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public frmServidor() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 334);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 414, 239);
		contentPane.add(scrollPane);
		
		txtContenedor = new JTextArea();
		scrollPane.setViewportView(txtContenedor);
		hilo = new Thread(this);
		hilo.start();
	}

	@Override
	public void run() {
		try {
			while (banderaV) {
				servidor = new ServerSocket(5003);
				try {

					ObjectOutputStream datoflujo;
					ObjectInputStream flujo;
					
					cli = servidor.accept();
					flujo = new ObjectInputStream(cli.getInputStream());
					mc = (entMaeCli) flujo.readObject();
					System.out.println("entMaeCli mc: "+mc);	
				
					txtContenedor.append("\n"+"=========================");	
					txtContenedor.append("\n"+mc.getNumeroTarjeta());
					txtContenedor.append("\n"+mc.getClave());
					txtContenedor.append("\n"+"=========================");
					
										
					mlv = negCliente.Instancia().verificarAccesoCliente(mc.getNumeroTarjeta(),mc.getClave());
					System.out.println("mlv: "+mlv);			
					
					if(mlv.getId()!=0){

						datoflujo = new ObjectOutputStream(cli.getOutputStream());
						datoflujo.writeObject(mlv);						

						System.out.println(mlv);
						
						txtContenedor.append("\n"+"=========================");
						txtContenedor.append("\n Bienvenido :"+mlv.getNombre());	
						txtContenedor.append("\n"+"Saldo : "+mlv.getSaldo());
						txtContenedor.append("\n"+"=========================");
						banderaV = false;
						banderaM = true;
						flujo.close();
						datoflujo.close();
						cli.close();
						servidor.close();
					}
					else{

						System.out.println(mlv);
						datoflujo = new ObjectOutputStream(cli.getOutputStream());
						datoflujo.writeObject(new entMaeCli());							
						flujo.close();
						datoflujo.close();
						cli.close();
						servidor.close();
					}					
					
				}
				catch (Exception e) {
					System.out.println("Error en  frm VerificarAcceso : "+e.getMessage());
					cli.close();
					servidor.close();					
				}
			}
			while(banderaM){

				servidor = new ServerSocket(9090);
				try {

					ObjectOutputStream datoflujo;
					ObjectInputStream flujo;
					
					cli = servidor.accept();
					flujo = new ObjectInputStream(cli.getInputStream());
					entMaeCli mc = (entMaeCli) flujo.readObject();
					System.out.println(mc.toString());
					
					
					entMaeCli entNuevo = new entMaeCli();
	
					entNuevo.setUltMovMonto(mc.getUltMovMonto());
					System.out.println(mc.getUltMovMonto());
					entNuevo.setFechaUlt(mc.getFechaUlt());
					System.out.println(mc.getFechaUlt());
					entNuevo.setHoraUlt(mc.getHoraUlt());
					System.out.println(mc.getHoraUlt());
					entNuevo.setId(mlv.getId());
					System.out.println(mlv.getId());
					entNuevo.setNombre(mlv.getNombre());
					System.out.println(mlv.getNombre());
					entNuevo.setNumeroTarjeta(mlv.getNumeroTarjeta());
					System.out.println(mlv.getNumeroTarjeta());
					entNuevo.setSaldo(mlv.getSaldo()-mc.getUltMovMonto());
					System.out.println(mlv.getSaldo()-mc.getUltMovMonto());
					
					
					System.out.println(entNuevo.toString());
					
					try {
						
						boolean x = negCliente.Instancia().editarMaeCliente(entNuevo);
						if(x){
						File archivo = new File("c:\\log\\data_atm.log");
						BufferedWriter Fescribe = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivo,true),"utf-8"));
						Fescribe.write(entNuevo+"\r\n");
						Fescribe.close();	
						
						if(entNuevo.getUltMovMonto()>=500){
							
							Properties props = new Properties();
							// Nombre del host de correo, es smtp.gmail.com
							 props.setProperty("mail.smtp.host", "smtp.gmail.com");

							 // TLS si est� disponible
							 props.setProperty("mail.smtp.starttls.enable", "true");

							 // Puerto de gmail para envio de correos
							 props.setProperty("mail.smtp.port","25");

							 // Nombre del usuario
							 props.setProperty("mail.smtp.user","aplidistribuidas@gmail.com");

							 // Si requiere o no usuario y password para conectarse.
							 props.setProperty("mail.smtp.auth", "true");
						        
						        Session session = Session.getInstance(props);
						       // session.setDebug(true);
						        
						        try {
						 
						            Message message = new MimeMessage(session);
						            // Quien envia el correo
						            message.setFrom(new InternetAddress("aplidistribuidas@gmail.com"));
						            //Destinatario Principal
						            String correo="";
						            message.addRecipient(Message.RecipientType.TO, new InternetAddress());
						          //Estableciendo el destino de la copia (CC)
						            message.addRecipient(Message.RecipientType.CC, new InternetAddress("aplidistribuidas@gmail.com"));

						            //Estableciendo el destino de la copia oculta (BCC)
						            message.addRecipient(Message.RecipientType.BCC, new InternetAddress("manuel.992260598@gmail.com"));

						            
						            message.setSubject("Prueba");
						            message.setText("Mensaje de retiro de "+entNuevo.getUltMovMonto());
						 
						            Transport t = session.getTransport("smtp");
									t.connect("aplidistribuidas@gmail.com", "p12345678");
									t.sendMessage(message, message.getAllRecipients());
									t.close();
						            System.out.println("Su mensaje ha sido enviado");
						 
						        } catch (MessagingException e) {
						            throw new RuntimeException(e);
						        }
				
							}
						
						
						
						
						}
					} catch (Exception e) {					
						hilo.resume();
						System.out.println("Error al grabar en el log : "+e.getMessage());
					}
					
					datoflujo = new ObjectOutputStream(cli.getOutputStream());
					datoflujo.writeObject(entNuevo);
					datoflujo.close();
					flujo.close();
				} catch (Exception e) {
					   System.out.println("Error 2: "+e.getMessage());
				}
			}
			
		} catch (Exception e) {
			   System.out.println("Error Run: "+e.getMessage());
		}
	}
}
