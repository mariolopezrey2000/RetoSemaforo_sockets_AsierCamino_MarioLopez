import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Servidor {
    public static int velocidadnueva;
    public static String color;
    public static int velocidadantigua;
    public static String mensaje="";
    public static void main(String[] args) throws Exception {
        //el ciente escucha infinito que el cliente mande el mensaje
            try (ServerSocket server = new ServerSocket(9090)){
                System.out.println("Servidor iniciado");
                while(true){
                    Socket socket = server.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    mensaje = in.readLine();
                    if(!mensaje.equals("")){
                        velocidadantigua=Integer.parseInt(mensaje);
                        System.out.println("Mensaje recibido: "+mensaje);
                        cambiarcolorvelocidad();
                    }
                    in.close();
                    socket.close();
                }
            } catch (Exception e) {
                // TODO: handle exception
            }

    }
    public static void cambiarcolorvelocidad() throws UnknownHostException, IOException{
        if(velocidadantigua==100){
            color="verde";
            velocidadnueva=100;
        }else if(velocidadantigua==150){
            color="amarillo";
            velocidadnueva=100;
        }else if(velocidadantigua==200){
            color="rojo";
            velocidadnueva=100;
        }else{
            color="blanco";
            velocidadnueva=velocidadantigua;
        }
        respuesta r=new respuesta();
        r.setColor(color);
        r.setVelocidadnueva(velocidadnueva);
        enviar(r);
    }
    public static  void enviar(respuesta r) throws UnknownHostException, IOException{
        Socket socket=new Socket();
        socket.connect(new InetSocketAddress("192.168.13.28",12345),5000);
        PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
        String respues=r.getColor()+","+r.getVelocidadnueva();
        out.println(respues);
        out.close();
        socket.close();
    }
}
