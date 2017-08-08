public class CmdSomaMatrizes extends Comando{
    
    private String content;
    private String array1;
    private String array2;
    private int tipo;

    public void setContent(String content){
        this.content = content;
        String[] parts = content.split("\\|");
        array1 = parts[0];
        array2 = parts[1];
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
            str.append("System.out.print(").append(array1).append("[i] + ").append(array2).append("[i] + \" \");\n");
            str.append("}\n");
            str.append("System.out.println();\n");
        } else if (tipo == 6){
            str.append("for (int i = 0; i < ").append(array1).append(".length; i++){\n");
            str.append("for (int j = 0; j < ").append(array2).append("[0].length; j++){\n");
            str.append("System.out.print(").append(array1).append("[i][j] + ").append(array2).append("[i][j] + \" \");\n");
            str.append("}\n");
            str.append("System.out.println(\" \");\n");
            str.append("}\n");
        }

        return  str.toString();
    }
}
  
  
  
  
  
  
       
  
        
                