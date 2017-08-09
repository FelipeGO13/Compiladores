public class CmdImprimeMatriz extends Comando{
    
    private String content;
    private int tipo;

    public void setContent(String content){
        this.content = content;
    }

    public void setTipo(int tipo){
        this.tipo = tipo;
    }
  
    public String writeCode(){
        StringBuilder str = new StringBuilder();
        
        if(tipo == 5){
            str.append("for (int i = 0; i < ").append(content).append(".length; i++){\n");
            str.append("System.out.print(").append(content).append("[i] + \" \");\n");
            str.append("}\n");
            str.append("System.out.println();\n");
        } else if (tipo == 6){
            str.append("for (int i = 0; i < ").append(content).append(".length; i++){\n");
            str.append("for (int j = 0; j < ").append(content).append("[0].length; j++){\n");
            str.append("System.out.print(").append(content).append("[i][j] + \" \");\n");
            str.append("}\n");
            str.append("System.out.println(\" \");\n");
            str.append("}\n");
        }

        return  str.toString();
    }
}