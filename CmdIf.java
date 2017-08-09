import java.util.ArrayList;

public class CmdIf extends Comando{
    private ArrayList<Comando> commandList;
    private ArrayList<Comando> elseList;
    private String             logicalExpr;
    public static final int IF_MODE   = 1;
    public static final int ELSE_MODE = 2;
    private int mode;
    
    public CmdIf(){
        this.commandList = new ArrayList<Comando>();
        this.elseList    = new ArrayList<Comando>();
        this.mode        = IF_MODE;
    }
    
    public void setLogicalExpr(String expr){
        this.logicalExpr = expr;
    }
    
    public void addCommand(Comando comando){
        if (mode == IF_MODE)
           this.commandList.add(comando);
        else
           this.elseList.add(comando);
    }
    
     public void changeMode(int mode){
        this.mode = mode;
    }
    
    public String writeCode(){
        StringBuilder str = new StringBuilder();
        str.append("if (").append(logicalExpr).append("){\n");
        for(Comando c: commandList){
            str.append(c.writeCode());
        }
        if(mode == ELSE_MODE){
        str.append("} else {\n");
            for(Comando c: elseList){
                str.append(c.writeCode());
            }
        }
        str.append("}\n");
        return str.toString();
    }
}