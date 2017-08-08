import java.util.ArrayList;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Programa{
    ArrayList<Comando> comandos;
    File file;
    BufferedWriter writer; 
    
    public Programa(){
        this.comandos = new ArrayList<Comando>();
        
        try{
            this.file = new java.io.File("ProgramaCompilado.java");
            this.file.createNewFile();
            this.writer = new BufferedWriter(new FileWriter(file));
        } catch (Exception e){
            System.out.println(e.getMessage());            
        }
    }
    
    public void addCommand(Comando cmd){
        this.comandos.add(cmd);
    }
    
    public void run(){
        
    }
    public String writeCode(){
        StringBuilder str = new StringBuilder();
        try{
          
            str.append("import java.util.Scanner;\n\n"); 
            str.append("public class ProgramaCompilado {\n");
            str.append("public static void main(String args[]){\n");
            str.append("Scanner scan = new Scanner (System.in);\n\n");
            for (Comando cmd: comandos){
                str.append(cmd.writeCode());
            }
            str.append("}\n");
            str.append("}");
            writer.write(str.toString());
            writer.flush();
            writer.close();
        } catch (Exception e){
            
            System.out.println(e.getMessage());
        }
        return str.toString();
    }
    
}