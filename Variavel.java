public class Variavel{
    
    public static final int  INT    = 1;
    public static final int  REAL   = 2;
    public static final int  STRING = 3;
    public static final int  ARRAY  = 5;
    public static final int  MATRIZ = 6;
    
    private String id;
    private int tipo;
    private Object valor;
    
    public Variavel(String id, int tipo, Object valor){
        this.id = id;
        this.tipo = tipo;
        this.valor = valor;
    }
    
    public Variavel(){
        this.id = "";
        this.tipo = 0;
        this.valor = null;
    }
    
    public String getNomeTipo(int tipo){
        String nomeTipo;
        switch(tipo){
            case INT:
                nomeTipo = "Inteiro";
                break;
            case REAL:
                nomeTipo = "Real";
                break;
            case STRING:
                nomeTipo = "Texto";
                break;
            case ARRAY:
                nomeTipo = "Array";
                break;
            case MATRIZ:
                nomeTipo = "Matriz";
                break;
            default:
                nomeTipo = "Nao encontrado";
                break;
        }
            return nomeTipo;  
    }
    
    public void setId(String id){
        this.id = id;
    } 
    
    public String getId(){
        return this.id;
    }
    
    public void setTipo(int tipo){
        this.tipo = tipo;
    }
    
    public int getTipo(){
        return this.tipo;
    }
    
    public void setValor(Object valor){
        this.valor = valor;
    }
    
    public Object getValor(){
        return this.valor;
    }
    
    
    public String toString(){
        return id + " = " + valor;
    }
}