import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class App extends JFrame {
	private JLabel velocidadgrafico;
	private JPanel contentPane;
	private JTextField textField;
    public String mensaje;
    public static JPanel cuadrocolor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App frame = new App();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
    public void conexion(int velocidad){
        try {
            System.out.println("velocidad: "+velocidad);
            cliente cliente = new cliente(velocidad,"192.168.0.19",cuadrocolor,velocidadgrafico);
            cliente.start();
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void reiniciar(){
        initialize();
    }

    public App() {
		initialize();
	}

    public void initialize(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		velocidadgrafico = new JLabel("0");
		velocidadgrafico.setBounds(330, 150, 116, 28);
		contentPane.add(velocidadgrafico);
		
		JButton btnNewButton = new JButton("COMPROBAR VELOCIDAD");
		btnNewButton.setBounds(45, 89, 177, 23);
		contentPane.add(btnNewButton);
		
		textField = new JTextField();
		textField.setBounds(56, 61, 149, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		cuadrocolor = new JPanel();
		cuadrocolor.setBackground(new Color(255, 255, 255));
		cuadrocolor.setBounds(290, 44, 89, 89);
		contentPane.add(cuadrocolor);

        //accion de pulsacion cuando se pulse el boton
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                mensaje=textField.getText();
                try {
                    conexion(Integer.parseInt(mensaje));
                } catch (Exception ee) {
                    // TODO: handle exception
                    System.out.println("Error al enviar el mensaje");
                }
            }
        });
    }

	

}
