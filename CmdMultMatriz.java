public class CmdMultMatriz extends Comando{
    
    private String content;
    private String array1;
    private String num;
    private int tipo;

    public void setContent(String content){
        this.content = content;
        String[] parts = content.split("\\|");
        array1 = parts[0];
        num    = parts[1];
        
    }

    public void run(){
        
    }
    
    public void setTipo(int tipo){
        this.tipo = tipo;
    }
  
    public String writeCode(){
        StringBuilder str = new StringBuilder();
        
        if(tipo == 5){
            str.append("for (int i = 0; i < ").append(array1).append(".length; i++){\n");
            str.append("System.out.print(").append(array1).append("[i] * ").append(num).append(" + \" \");\n");
            str.append("}\n ");
            str.append("System.out.println();\n");
        } else if (tipo == 6){
            str.append("for (int i = 0; i < ").append(array1).append(".length; i++){\n");
            str.append("for (int j = 0; j < ").append(array1).append("[0].length; j++){\n");
            str.append("System.out.print(").append(array1).append("[i][j] * ").append(num).append(" + \" \");\n");
            str.append("}\n");
            str.append("System.out.println(' ');\n");
            str.append("}\n");
        }

        return  str.toString();
    }
}
  
  
  
  
  
  
       
  
        
                