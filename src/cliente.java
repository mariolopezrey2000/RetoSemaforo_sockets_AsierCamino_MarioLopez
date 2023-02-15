
import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class cliente  extends Thread{
    int velocidad;
    String ip;
    Boolean enviando=false;
    String respuesta="";
    String [] cadena;
    respuesta respuestaserver = new respuesta();
    JPanel cuadrocolor;
    JLabel velocidadgrafico;

    public cliente(int velocidad, String ip,JPanel cuadrocolor,JLabel velocidadgrafico) {
        this.velocidad = velocidad;
        this.ip = ip;
        this.cuadrocolor=cuadrocolor;
        this.velocidadgrafico=velocidadgrafico;
    }

    //metodo que envia al servidor mediante socket
    public void run(){
        try {
            sender(velocidad,ip);
        } catch (Exception e) {
            // TODO: handle exception
        }
    
    }
    //metodo que recibe respuesta del servidor
    public void recibir(){
        try(ServerSocket serversocket = new ServerSocket(12345)) {
            while(respuesta.equals("")){
                Socket socket = serversocket.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                respuesta= in.readLine();
                in.close();
                socket.close();
                cadena=respuesta.split(",");
                respuestaserver.setColor(cadena[0]);
                respuestaserver.setVelocidadnueva(Integer.parseInt(cadena[1]));
            }
            cambiarcolor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //metodo que cambia el color del jpanel
    public void cambiarcolor() throws InterruptedException{
        if(respuestaserver.getColor().equals("verde")){
            //cambiar color a verde
            cuadrocolor.setBackground(Color.GREEN);
            velocidadgrafico.setText("100");
        }else if(respuestaserver.getColor().equals("amarillo")){
            cuadrocolor.setBackground(Color.YELLOW);
            velocidadgrafico.setText("150");
            //cambiar color a amarillo
        }else if(respuestaserver.getColor().equals("rojo")){
            //cambiar color a rojo
            velocidadgrafico.setText("200");
            cuadrocolor.setBackground(Color.RED);
        }else if(respuestaserver.getColor().equals("blanco")){
            //cambiar color a blanco
            cuadrocolor.setBackground(Color.WHITE);
        }
        int vel=respuestaserver.getVelocidadnueva();
        Thread.sleep(2000);
        velocidadgrafico.setText(vel+"");
        cuadrocolor.setBackground(Color.GREEN);
        System.out.println("velocidad nueva: "+vel);
    }

    public  void sender(int velocidad,String ip) throws UnknownHostException,IOException{
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress("192.168.13.28",9090),5000);
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            out.println(velocidad);
            out.close();
            socket.close();
            Thread.sleep(1000);
            recibir();
        } catch (Exception e) {
        }
    }
}
