public class CmdLeitura extends Comando{
    private String id;
    private int tipo;
    
    public void setId(String id){
        this.id = id;
    }
    
    public String getId(){
        return this.id;
    }
    
     public void setTipo(int tipo){
        this.tipo = tipo;
    }
  
    
    public void run(){
        
    }
    public String writeCode(){
        StringBuilder str = new StringBuilder();
        switch (tipo){
            case 1:
                str.append(id).append(" = scan.nextInt();\n");
                break;
            case 2:
                str.append(id).append(" = scan.nextDouble();\n");
                break;
            case 3:
                str.append(id).append(" = scan.next();\n");
                break;
        }   
        return str.toString();
    }
}