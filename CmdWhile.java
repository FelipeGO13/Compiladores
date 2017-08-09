import java.util.ArrayList;

public class CmdWhile extends Comando{
    private ArrayList<Comando> commandList;
    private String             logicalExpr;
    
    public CmdWhile(){
        this.commandList = new ArrayList<Comando>();
    }
    
    public void setLogicalExpr(String expr){
        this.logicalExpr = expr;
    }
    
    public void addCommand(Comando comando){
        this.commandList.add(comando);
    }
    
    public String writeCode(){
        StringBuilder str = new StringBuilder();
        str.append("while (").append(logicalExpr).append("){\n");
        for(Comando c: commandList){
            str.append(c.writeCode());
        }
        str.append("}\n");
        return str.toString();
    }
}