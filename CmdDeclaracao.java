public class CmdDeclaracao extends Comando{
    
    private String content;
    
    public void setContent(String content){
      
        this.content = content;
    }

    public String writeCode(){
        return content+";\n" ;
    }
}