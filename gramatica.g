class MeuParser extends Parser;
{
 private java.util.HashMap<String, Variavel> symbolTable;
 private java.util.ArrayList<Variavel> varList;
 private java.util.Scanner scan;
 private int VAR_TYPE;
 private Programa programa;
 private Comando cmd;
 private PilhaComandos pilhaCmd;
 private String conteudo = null;
 private Double resultCalc1 = 0.0;
 private Double resultCalc2 = 1.0;
 private String result = "";
 private int tipoExpr = 0;
 private boolean calcular = false;
 private Matriz matriz;
 private int cmdFlag;
 
    public void init(){
        symbolTable = new java.util.HashMap<String, Variavel>();
        scan = new java.util.Scanner(System.in);
        pilhaCmd = new PilhaComandos();
        programa = new Programa();
    }
     
    public void declareHelper(String id){
        if (symbolTable.get(id) == null){
            Variavel v = new Variavel();
            v.setId(id);
            symbolTable.put(v.getId(), v);
            varList.add(v);
        } else {
            throw new RuntimeException("Variavel ja declarada: " + id);
        }
    }
     
    public void declareHelper(){
        cmd = new CmdDeclaracao();
        
        for(Variavel v : varList){
            if(varList.indexOf(v) == 0)
                conteudo += v.getId();
            else
                conteudo += ", " + v.getId();
            v.setTipo(VAR_TYPE);
        }
        
        ((CmdDeclaracao) cmd).setContent(conteudo);
        
        if (pilhaCmd.isEmpty()){
            programa.addCommand(cmd);
        } else if(cmdFlag == 1) {
            Comando tmp = pilhaCmd.getTopo();
            ((CmdIf)tmp).addCommand(cmd);
        } else if (cmdFlag == 2){
            Comando tmp = pilhaCmd.getTopo();
            ((CmdWhile)tmp).addCommand(cmd);
        }
        
        varList = new java.util.ArrayList<Variavel>();
    }
    
    public void atribHelper(String id){
        cmd = new CmdAtribuicao();
        conteudo += symbolTable.get(id).toString();
        ((CmdAtribuicao) cmd).setContent(conteudo);
        
        if (pilhaCmd.isEmpty()){
            programa.addCommand(cmd);
        } else if(cmdFlag == 1) {
            Comando tmp = pilhaCmd.getTopo();
            ((CmdIf)tmp).addCommand(cmd);
        } else if (cmdFlag == 2){
            Comando tmp = pilhaCmd.getTopo();
            ((CmdWhile)tmp).addCommand(cmd);
        }
        
        conteudo = "";
    }
}

programa     : "programa" (declaracao)+ corpo "fimPrograma" {
                     System.out.println(programa.writeCode());
                }
             ;

declaracao   : "declare"  { 
                            varList = new java.util.ArrayList<Variavel>();
                          }
               "int" ID   { 
                            conteudo = "int ";
                            VAR_TYPE = Variavel.INT;
                            declareHelper(LT(0).getText());
                          } 
                (VIRG ID  {declareHelper(LT(0).getText());})* 
                PV {
                        declareHelper();
                   }
             | "real" ID  { 
                            conteudo = "double ";
                            VAR_TYPE = Variavel.REAL;
                            declareHelper(LT(0).getText());
                          } 
                (VIRG ID  {declareHelper(LT(0).getText());})* 
                PV {
                        declareHelper();
                   }
             | "texto" ID { 
                            conteudo = "String ";
                            VAR_TYPE = Variavel.STRING;
                            declareHelper(LT(0).getText());
                          } 
                (VIRG ID  {declareHelper(LT(0).getText());})* 
                PV {
                      declareHelper();
                   }
             | "array" ID { 
                           VAR_TYPE = Variavel.ARRAY;
                           declareHelper(LT(0).getText());
                          } 
                (VIRG ID  {declareHelper(LT(0).getText());})* 
                PV {
                        for(Variavel v : varList)
                            v.setTipo(VAR_TYPE);
                     
                        varList = new java.util.ArrayList<Variavel>();
                    }
             | "matriz" ID{
                            VAR_TYPE = Variavel.MATRIZ;
                            declareHelper(LT(0).getText());
                          } 
                (VIRG ID  {declareHelper(LT(0).getText());})* 
                PV {
                        for(Variavel v : varList) 
                            v.setTipo(VAR_TYPE);
                           
                        varList = new java.util.ArrayList<Variavel>();
                   }
             ;
             
corpo        : "inicio" (comando)+ "fim"
             ;

atribuicao   : (id:ID EQ {conteudo = "";}
               (STRING   {
                   
                            if (symbolTable.get(id.getText()) == null)
                                throw new RuntimeException("Variavel nao declarada: " + id.getText());
                            else if (symbolTable.get(id.getText()).getTipo() == 3)
                                symbolTable.get(id.getText()).setValor(LT(0).getText());
                            else
                                throw new RuntimeException("Tipo invalido: " + id.getText() + " é " + symbolTable.get(id.getText()).getNomeTipo(symbolTable.get(id.getText()).getTipo()) );
                            atribHelper(id.getText());
                        }   
             | calcExpr {
                        
                            if (symbolTable.get(id.getText()) == null)
                                throw new RuntimeException("Variavel nao declarada: " + id.getText());
                            else if (symbolTable.get(id.getText()).getTipo() == 1)
                                if(resultCalc2 % 1 == 0)
                                    symbolTable.get(id.getText()).setValor(resultCalc2.intValue());
                                else
                                    throw new RuntimeException("Perda de precisão: " + id.getText());
                            else if (symbolTable.get(id.getText()).getTipo() == 2)
                                symbolTable.get(id.getText()).setValor(resultCalc2);
                            else
                               throw new RuntimeException("Tipo invalido: " + id.getText() + " é " + symbolTable.get(id.getText()).getNomeTipo(symbolTable.get(id.getText()).getTipo()) );
                                
                            atribHelper(id.getText());
                            calcular = false;
                        }
             | noCalc   {
                            if (symbolTable.get(id.getText()) == null)
                                throw new RuntimeException("Variavel nao declarada: " + id.getText());
                            else if (symbolTable.get(id.getText()).getTipo() == 1)
                                if(tipoExpr == 1)
                                    symbolTable.get(id.getText()).setValor(result);
                                else
                                    throw new RuntimeException("Perda de precisão ou valor nao correspondente: " + id.getText());
                            else if (symbolTable.get(id.getText()).getTipo() == 2 && tipoExpr == 2)
                                symbolTable.get(id.getText()).setValor(result);
                            else
                                throw new RuntimeException("Tipo invalido: " + id.getText() + " é " + symbolTable.get(id.getText()).getNomeTipo(symbolTable.get(id.getText()).getTipo()) );
                                
                            atribHelper(id.getText());
                        }
             | INT      {
                            if (symbolTable.get(id.getText()) == null)
                                throw new RuntimeException("Variavel nao declarada: " + id.getText());
                            else if (symbolTable.get(id.getText()).getTipo() == 1)
                                symbolTable.get(id.getText()).setValor(LT(0).getText());
                            else
                                throw new RuntimeException("Tipo invalido: " + id.getText());
                            atribHelper(id.getText());
                        }
             | REAL     {
                            if (symbolTable.get(id.getText()) == null)
                                throw new RuntimeException("Variavel nao declarada: " + id.getText());
                            else if (symbolTable.get(id.getText()).getTipo() == 2)
                                symbolTable.get(id.getText()).setValor(LT(0).getText());
                            else
                                 throw new RuntimeException("Tipo invalido: " + id.getText() + " é " + symbolTable.get(id.getText()).getNomeTipo(symbolTable.get(id.getText()).getTipo()) );
                            atribHelper(id.getText());
                        }
             | ARRAY    {
                            if (symbolTable.get(id.getText()) == null)
                                throw new RuntimeException("Variavel nao declarada: " + id.getText());
                            else if (symbolTable.get(id.getText()).getTipo() == 5)
                                symbolTable.get(id.getText()).setValor(LT(0).getText());
                            else
                                throw new RuntimeException("Tipo invalido: " + id.getText() + " é " + symbolTable.get(id.getText()).getNomeTipo(symbolTable.get(id.getText()).getTipo()) );
                            
                            conteudo = "int [] ";
                            atribHelper(id.getText());
                        }
             | MATRIZ   {
                            if (symbolTable.get(id.getText()) == null)
                                throw new RuntimeException("Variavel nao declarada: " + id.getText());
                            else if (symbolTable.get(id.getText()).getTipo() == 6)
                                symbolTable.get(id.getText()).setValor(LT(0).getText());
                            else
                                throw new RuntimeException("Tipo invalido: " + id.getText() + " é " + symbolTable.get(id.getText()).getNomeTipo(symbolTable.get(id.getText()).getTipo()) );
                                 
                            conteudo = "int [][] ";
                            atribHelper(id.getText());
                        
                        }) PV)+
             ;
         
leitura      : "leia" {cmd = new CmdLeitura();} 
               (id:ID) 
               PV {
                    ((CmdLeitura) cmd).setId(id.getText());
                    ((CmdLeitura) cmd).setTipo(symbolTable.get(id.getText()).getTipo());
                    
                    if (pilhaCmd.isEmpty()){
                        programa.addCommand(cmd);
                    } else if(cmdFlag == 1) {
                        Comando tmp = pilhaCmd.getTopo();
                        ((CmdIf)tmp).addCommand(cmd);
                    } else if (cmdFlag == 2){
                     Comando tmp = pilhaCmd.getTopo();
                        ((CmdWhile)tmp).addCommand(cmd);
                    }
                  }
             ;

escrita      : "escreva" {cmd = new CmdEscrita();}
               (id:ID {
                        if (symbolTable.get(id.getText()) == null) 
                            throw new RuntimeException("Variavel nao declarada: " + id.getText());
                        else 
                            conteudo = id.getText();
                      }
             | texto:STRING {conteudo = texto.getText();}) 
               PV {
                    ((CmdEscrita)cmd).setContent(conteudo);
                
                    if (pilhaCmd.isEmpty()){
                        programa.addCommand(cmd);
                    } else if(cmdFlag == 1) {
                        Comando tmp = pilhaCmd.getTopo();
                        ((CmdIf)tmp).addCommand(cmd);
                    } else if (cmdFlag == 2){
                        Comando tmp = pilhaCmd.getTopo();
                        ((CmdWhile)tmp).addCommand(cmd);
                    }
                  }
             ;

comandoIf    : "se" AP 
               ((id:ID{ 
                        if (symbolTable.get(id.getText()) == null)
                            throw new RuntimeException("Variavel nao declarada: " + id.getText());
                        else 
                            conteudo = id.getText();
                     }
                     
             | INT   {conteudo = LT(0).getText();}
            
             | REAL  {conteudo = LT(0).getText();}) 
                
              (oprel:OPREL {conteudo += oprel.getText();}| EQ EQ {conteudo += "==";}) 
                
               (id2:ID {
                        if (symbolTable.get(id2.getText()) == null)
                            throw new RuntimeException("Variavel nao declarada: " + id2.getText());
                        else 
                            conteudo += id2.getText();
                    }
                    
            | INT {conteudo += LT(0).getText();}
            
            | REAL {conteudo += LT(0).getText();}) | BOOLEAN {conteudo = LT(0).getText();}) 
            
                {
                    cmdFlag = 1;
                    cmd = new CmdIf();
                    ((CmdIf) cmd).setLogicalExpr(conteudo);
                    pilhaCmd.push(cmd);
                }
                   
             FP "entao" corpo
              
              ("senao" {
                            CmdIf tmp = (CmdIf)pilhaCmd.getTopo(); 
                            tmp.changeMode(CmdIf.ELSE_MODE);
                       }
              
              corpo)? 
              
              {
                Comando cmd = pilhaCmd.pop();              
                if (pilhaCmd.isEmpty()){
                   programa.addCommand(cmd);
                } else {
                    CmdIf tmp = (CmdIf)pilhaCmd.getTopo();
                    tmp.addCommand(cmd);
                }
                    
              }
                    
              "fimSe"
            ;
            
comandoWhile: "enquanto" AP 
              ((id:ID { 
                        if (symbolTable.get(id.getText()) == null)
                            throw new RuntimeException("Variavel nao declarada: " + id.getText());
                        else 
                            conteudo = id.getText();
                      }
                     
            | INT   {conteudo = LT(0).getText();}
            
            | REAL  {conteudo = LT(0).getText();}) 
                
              (oprel:OPREL {conteudo += oprel.getText();}| EQ EQ {conteudo += "==";}) 
                
              (id2:ID {
                        if (symbolTable.get(id2.getText()) == null) 
                            throw new RuntimeException("Variavel nao declarada: " + id2.getText());
                        else 
                            conteudo += String.valueOf(symbolTable.get(id2.getText()).getValor());
                      }
                    
            | INT {conteudo += LT(0).getText();}
            
            | REAL {conteudo += LT(0).getText();}) | BOOLEAN {conteudo = LT(0).getText();})
            
                {  
                    cmdFlag = 2;
                    cmd = new CmdWhile();
                    ((CmdWhile) cmd).setLogicalExpr(conteudo);
                    pilhaCmd.push(cmd);
                }
            
              FP "entao" corpo
                {
                    Comando cmd = pilhaCmd.pop();
                    if (pilhaCmd.isEmpty()){
                        programa.addCommand(cmd);
                    } else {
                        Comando tmp = pilhaCmd.getTopo();
                        ((CmdIf)tmp).addCommand(cmd);
                    }
                    
                }
              "fimEnq"
            ;
            
cmdImprimeMatriz : "imprimematriz" (id:ID) PV { 
                          
                        if(symbolTable.get(id.getText()).getTipo() != 5 && symbolTable.get(id.getText()).getTipo() != 6 )
                            throw new RuntimeException(id.getText() + " não é uma matriz");
                        else{
                           conteudo = id.getText();
                           cmd = new CmdImprimeMatriz();
                            ((CmdImprimeMatriz) cmd).setTipo(symbolTable.get(id.getText()).getTipo());
                            ((CmdImprimeMatriz) cmd).setContent(conteudo);
                           
                            if (pilhaCmd.isEmpty()){
                                programa.addCommand(cmd);
                            } else if(cmdFlag == 1) {
                                Comando tmp = pilhaCmd.getTopo();
                                ((CmdIf)tmp).addCommand(cmd);
                            } else if (cmdFlag == 2){
                                Comando tmp = pilhaCmd.getTopo();
                                ((CmdWhile)tmp).addCommand(cmd);
                            }
                            
                        }
                }
              ;
              
cmdSomaMatrizes : "somamatrizes" (id:ID) VIRG (id2:ID) PV { 
                          
                        if(symbolTable.get(id.getText()).getTipo() != 5 && symbolTable.get(id.getText()).getTipo() != 6 )
                            throw new RuntimeException(id.getText() + " não é uma matriz");
                        else if(symbolTable.get(id2.getText()).getTipo() != 5 && symbolTable.get(id2.getText()).getTipo() != 6 )
                            throw new RuntimeException(id2.getText() + " não é uma matriz");
                        else{
                            conteudo = id.getText();
                            conteudo += "|";
                            conteudo += id2.getText();
                            cmd = new CmdSomaMatrizes();
                            ((CmdSomaMatrizes) cmd).setTipo(symbolTable.get(id.getText()).getTipo());
                            ((CmdSomaMatrizes) cmd).setContent(conteudo);
                            if (pilhaCmd.isEmpty()){
                                programa.addCommand(cmd);
                            } else if(cmdFlag == 1) {
                                Comando tmp = pilhaCmd.getTopo();
                                ((CmdIf)tmp).addCommand(cmd);
                            } else if (cmdFlag == 2){
                                Comando tmp = pilhaCmd.getTopo();
                                ((CmdWhile)tmp).addCommand(cmd);
                            }
                            
                        }
                }
              ;
              
cmdAdicionaNum : "adicionanum"
                 (id:ID {
                    if(symbolTable.get(id.getText()).getTipo() != 5 && symbolTable.get(id.getText()).getTipo() != 6 )
                        throw new RuntimeException(id.getText() + " não é uma matriz");
                    else{
                        conteudo = id.getText();
                        conteudo += "|";}
                        }) 
                 VIRG
                 (id2:ID {
                    if(symbolTable.get(id2.getText()).getTipo() != 1 && symbolTable.get(id2.getText()).getTipo() != 2)
                        throw new RuntimeException(id2.getText() + " não é um inteiro");                 
                    else{
                        conteudo += id2.getText();
                        }
                 }
                 | INT   {conteudo += LT(0).getText();}
                 | REAL  {conteudo += LT(0).getText();}) 
                 PV { 
                        cmd = new CmdAdicionaNum();
                        ((CmdAdicionaNum) cmd).setTipo(symbolTable.get(id.getText()).getTipo());
                        ((CmdAdicionaNum) cmd).setContent(conteudo);
                        if (pilhaCmd.isEmpty()){
                            programa.addCommand(cmd);
                        } else if(cmdFlag == 1) {
                            Comando tmp = pilhaCmd.getTopo();
                            ((CmdIf)tmp).addCommand(cmd);
                        } else if (cmdFlag == 2){
                            Comando tmp = pilhaCmd.getTopo();
                            ((CmdWhile)tmp).addCommand(cmd);
                        }
                    }
              ;

cmdMultMatriz : "multmatriz" 
                 (id:ID {
                    if(symbolTable.get(id.getText()).getTipo() != 5 && symbolTable.get(id.getText()).getTipo() != 6 )
                        throw new RuntimeException(id.getText() + " não é uma matriz");
                    else{
                        conteudo = id.getText();
                        conteudo += "|";}
                        }) 
                 VIRG
                 (id2:ID {
                    if(symbolTable.get(id2.getText()).getTipo() != 1 && symbolTable.get(id2.getText()).getTipo() != 2)
                        throw new RuntimeException(id2.getText() + " não é um inteiro");                 
                    else{
                        conteudo += id2.getText();
                        }
                 }
                 | INT   {conteudo += LT(0).getText();}
                 | REAL  {conteudo += LT(0).getText();}) 
                 PV { 
                        cmd = new CmdMultMatriz();
                        ((CmdMultMatriz) cmd).setTipo(symbolTable.get(id.getText()).getTipo());
                        ((CmdMultMatriz) cmd).setContent(conteudo);
                        if (pilhaCmd.isEmpty()){
                            programa.addCommand(cmd);
                        } else if(cmdFlag == 1) {
                            Comando tmp = pilhaCmd.getTopo();
                            ((CmdIf)tmp).addCommand(cmd);
                        } else if (cmdFlag == 2){
                            Comando tmp = pilhaCmd.getTopo();
                            ((CmdWhile)tmp).addCommand(cmd);
                        }
                    }
              ;
              
calcExpr    : {calcular = true;} expr 
            ;
            
comando     : (atribuicao | leitura | escrita | comandoIf | comandoWhile | cmdImprimeMatriz | cmdSomaMatrizes | cmdAdicionaNum | cmdMultMatriz)
            ;

expr        : term { if(!calcular) result += LT(0).getText(); else resultCalc2 = resultCalc1; } exprl
            ;
            
noCalc      : AP expr FP
            ;
        
exprl       : (operador:OPARIT1 {
                                    if (!calcular) 
                                        result += operador.getText();
                                }
              term{        
                    if(!calcular)
                        result += LT(0).getText();
                    else {
                        if(operador.getText().equals("+"))
                            resultCalc2+=resultCalc1;
                        else 
                            resultCalc2-=resultCalc1;
                    }})* 
                        
            ;
        
term        : fator {  
                
                            if (symbolTable.get(LT(0).getText()) == null)
                                resultCalc1 = Double.parseDouble(LT(0).getText()); 
                            else
                                resultCalc1 = Double.valueOf(symbolTable.get(LT(0).getText()).getValor().toString());
                        
                    } terml
            ;

terml       : (operador:OPARIT2 {
                                    if(!calcular)
                                        result += operador.getText();
                                }
              fator {   
                            Double num;
                            if (symbolTable.get(LT(0).getText()) == null)
                                num = Double.parseDouble(LT(0).getText()); 
                            else{
                                try{
                                    num =  Double.valueOf(symbolTable.get(LT(0).getText()).getValor().toString());
                                } catch (Exception e) {
                                    System.out.println(symbolTable.get(LT(0).getText()).getId());
                                    throw new RuntimeException("Tipo nao permitido para esta operação");
                                }
                            }
                          if(operador.getText().equals("*"))
                            resultCalc1*=num;    
                          else
                            resultCalc1/=num;
                        })*
            ;

fator       :(ID
              {if(!calcular){
                int tipoDoTermo = symbolTable.get(LT(0).getText()).getTipo();
                if(tipoDoTermo > tipoExpr)
                   tipoExpr = tipoDoTermo;}}
            | INT
            {if(!calcular)
                if(Variavel.INT > tipoExpr)
                tipoExpr = Variavel.INT;}
            | REAL
            {  if(!calcular)
                if(Variavel.REAL > tipoExpr)
                tipoExpr = Variavel.REAL;}
            | STRING 
                {if(!calcular)
                if(Variavel.STRING > tipoExpr)
                tipoExpr = Variavel.STRING;})
            ;


class MeuLexico extends Lexer;

options{
    caseSensitive = true;
    k = 2;
}

WS          : (' ' | '\n' | '\r' | '\t') {_ttype=Token.SKIP;}
            ;
        
ID          : ('a'..'z' | 'A'..'Z') ('a'..'z'|'A'..'Z'|'0'..'9'|'_')*
            ;

protected
INT         : ('0'..'9')+
            ;

protected
REAL        : INT '.' INT
            ;
            

REAL_OU_INT : ( INT '.' )  => REAL { $setType(REAL); }
            | INT  { $setType(INT); }
            ;   
            
STRING      : ASPAS ('A'..'Z' | 'a'..'z' | '0'..'9' | ' ')* ASPAS
            ;
            
BOOLEAN     : "true" | "false"
            ;
            
ARRAY       : AC (INT | REAL) (VIRG (INT | REAL))* FC
            ;

MATRIZ      : AC ARRAY (VIRG ARRAY)* FC
            ;
            
AP          : '('
            ;
        
FP          : ')'
            ;
        
PF          : '.'
            ;
            
PV          : ';'
            ;

VIRG        : ','
            ;
        
OPREL       : ('>' | '<' | ">=" | "<=" | "!=")
            ;
        
OPARIT1     : ('+' | '-')
            ;
        
OPARIT2     : ('*' | '/')
            ;
      
EQ          : '='
            ;
            
ASPAS       : '"'
            ;

AC          : '{'
            ;
        
FC          : '}'
            ;
            
AB          :'['
            ;

FB          :']'
            ;