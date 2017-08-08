// $ANTLR 2.7.6 (2005-12-22): "gramatica.g" -> "MeuParser.java"$

import antlr.TokenBuffer;
import antlr.TokenStreamException;
import antlr.TokenStreamIOException;
import antlr.ANTLRException;
import antlr.LLkParser;
import antlr.Token;
import antlr.TokenStream;
import antlr.RecognitionException;
import antlr.NoViableAltException;
import antlr.MismatchedTokenException;
import antlr.SemanticException;
import antlr.ParserSharedInputState;
import antlr.collections.impl.BitSet;

public class MeuParser extends antlr.LLkParser       implements MeuParserTokenTypes
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

protected MeuParser(TokenBuffer tokenBuf, int k) {
  super(tokenBuf,k);
  tokenNames = _tokenNames;
}

public MeuParser(TokenBuffer tokenBuf) {
  this(tokenBuf,1);
}

protected MeuParser(TokenStream lexer, int k) {
  super(lexer,k);
  tokenNames = _tokenNames;
}

public MeuParser(TokenStream lexer) {
  this(lexer,1);
}

public MeuParser(ParserSharedInputState state) {
  super(state,1);
  tokenNames = _tokenNames;
}

	public final void programa() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(LITERAL_programa);
			{
			int _cnt3=0;
			_loop3:
			do {
				if ((_tokenSet_0.member(LA(1)))) {
					declaracao();
				}
				else {
					if ( _cnt3>=1 ) { break _loop3; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt3++;
			} while (true);
			}
			corpo();
			match(LITERAL_fimPrograma);
			
			System.out.println(programa.writeCode());
			
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_1);
		}
	}
	
	public final void declaracao() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			switch ( LA(1)) {
			case LITERAL_declare:
			{
				match(LITERAL_declare);
				
				varList = new java.util.ArrayList<Variavel>();
				
				match(LITERAL_int);
				match(ID);
				
				conteudo = "int ";
				VAR_TYPE = Variavel.INT;
				declareHelper(LT(0).getText());
				
				{
				_loop6:
				do {
					if ((LA(1)==VIRG)) {
						match(VIRG);
						match(ID);
						declareHelper(LT(0).getText());
					}
					else {
						break _loop6;
					}
					
				} while (true);
				}
				match(PV);
				
				declareHelper();
				
				break;
			}
			case LITERAL_real:
			{
				match(LITERAL_real);
				match(ID);
				
				conteudo = "double ";
				VAR_TYPE = Variavel.REAL;
				declareHelper(LT(0).getText());
				
				{
				_loop8:
				do {
					if ((LA(1)==VIRG)) {
						match(VIRG);
						match(ID);
						declareHelper(LT(0).getText());
					}
					else {
						break _loop8;
					}
					
				} while (true);
				}
				match(PV);
				
				declareHelper();
				
				break;
			}
			case LITERAL_texto:
			{
				match(LITERAL_texto);
				match(ID);
				
				conteudo = "String ";
				VAR_TYPE = Variavel.STRING;
				declareHelper(LT(0).getText());
				
				{
				_loop10:
				do {
					if ((LA(1)==VIRG)) {
						match(VIRG);
						match(ID);
						declareHelper(LT(0).getText());
					}
					else {
						break _loop10;
					}
					
				} while (true);
				}
				match(PV);
				
				declareHelper();
				
				break;
			}
			case LITERAL_array:
			{
				match(LITERAL_array);
				match(ID);
				
				VAR_TYPE = Variavel.ARRAY;
				declareHelper(LT(0).getText());
				
				{
				_loop12:
				do {
					if ((LA(1)==VIRG)) {
						match(VIRG);
						match(ID);
						declareHelper(LT(0).getText());
					}
					else {
						break _loop12;
					}
					
				} while (true);
				}
				match(PV);
				
				for(Variavel v : varList)
				v.setTipo(VAR_TYPE);
				
				varList = new java.util.ArrayList<Variavel>();
				
				break;
			}
			case LITERAL_matriz:
			{
				match(LITERAL_matriz);
				match(ID);
				
				VAR_TYPE = Variavel.MATRIZ;
				declareHelper(LT(0).getText());
				
				{
				_loop14:
				do {
					if ((LA(1)==VIRG)) {
						match(VIRG);
						match(ID);
						declareHelper(LT(0).getText());
					}
					else {
						break _loop14;
					}
					
				} while (true);
				}
				match(PV);
				
				for(Variavel v : varList) 
				v.setTipo(VAR_TYPE);
				
				varList = new java.util.ArrayList<Variavel>();
				
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_2);
		}
	}
	
	public final void corpo() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(LITERAL_inicio);
			{
			int _cnt17=0;
			_loop17:
			do {
				if ((_tokenSet_3.member(LA(1)))) {
					comando();
				}
				else {
					if ( _cnt17>=1 ) { break _loop17; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt17++;
			} while (true);
			}
			match(LITERAL_fim);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_4);
		}
	}
	
	public final void comando() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case ID:
			{
				atribuicao();
				break;
			}
			case LITERAL_leia:
			{
				leitura();
				break;
			}
			case LITERAL_escreva:
			{
				escrita();
				break;
			}
			case LITERAL_se:
			{
				comandoIf();
				break;
			}
			case LITERAL_enquanto:
			{
				comandoWhile();
				break;
			}
			case LITERAL_imprimematriz:
			{
				cmdImprimeMatriz();
				break;
			}
			case LITERAL_somamatrizes:
			{
				cmdSomaMatrizes();
				break;
			}
			case LITERAL_adicionanum:
			{
				cmdAdicionaNum();
				break;
			}
			case LITERAL_multmatriz:
			{
				cmdMultMatriz();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_5);
		}
	}
	
	public final void atribuicao() throws RecognitionException, TokenStreamException {
		
		Token  id = null;
		
		try {      // for error handling
			{
			int _cnt21=0;
			_loop21:
			do {
				if ((LA(1)==ID)) {
					id = LT(1);
					match(ID);
					match(EQ);
					conteudo = "";
					{
					switch ( LA(1)) {
					case AP:
					{
						noCalc();
						
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
						throw new RuntimeException("Tipo invalido: " + id.getText());
						
						atribHelper(id.getText());
						
						break;
					}
					case ARRAY:
					{
						match(ARRAY);
						
						if (symbolTable.get(id.getText()) == null)
						throw new RuntimeException("Variavel nao declarada: " + id.getText());
						else if (symbolTable.get(id.getText()).getTipo() == 5)
						symbolTable.get(id.getText()).setValor(LT(0).getText());
						else
						throw new RuntimeException("Tipo invalido: " + id.getText());
						
						conteudo = "int [] ";
						atribHelper(id.getText());
						
						break;
					}
					case MATRIZ:
					{
						match(MATRIZ);
						
						if (symbolTable.get(id.getText()) == null)
						throw new RuntimeException("Variavel nao declarada: " + id.getText());
						else if (symbolTable.get(id.getText()).getTipo() == 6)
						symbolTable.get(id.getText()).setValor(LT(0).getText());
						else
						throw new RuntimeException("Tipo invalido: " + id.getText());
						
						conteudo = "int [][] ";
						atribHelper(id.getText());
						
						
						break;
					}
					default:
						if ((LA(1)==STRING)) {
							match(STRING);
							
							
							if (symbolTable.get(id.getText()) == null)
							throw new RuntimeException("Variavel nao declarada: " + id.getText());
							else if (symbolTable.get(id.getText()).getTipo() == 3)
							symbolTable.get(id.getText()).setValor(LT(0).getText());
							else
							throw new RuntimeException("Tipo invalido: " + id.getText());
							atribHelper(id.getText());
							
						}
						else if ((_tokenSet_6.member(LA(1)))) {
							calcExpr();
							
							
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
							throw new RuntimeException("Tipo invalido: " + id.getText());
							
							atribHelper(id.getText());
							calcular = false;
							
						}
						else if ((LA(1)==INT)) {
							match(INT);
							
							if (symbolTable.get(id.getText()) == null)
							throw new RuntimeException("Variavel nao declarada: " + id.getText());
							else if (symbolTable.get(id.getText()).getTipo() == 1)
							symbolTable.get(id.getText()).setValor(LT(0).getText());
							else
							throw new RuntimeException("Tipo invalido: " + id.getText());
							atribHelper(id.getText());
							
						}
						else if ((LA(1)==REAL)) {
							match(REAL);
							
							if (symbolTable.get(id.getText()) == null)
							throw new RuntimeException("Variavel nao declarada: " + id.getText());
							else if (symbolTable.get(id.getText()).getTipo() == 2)
							symbolTable.get(id.getText()).setValor(LT(0).getText());
							else
							throw new RuntimeException("Tipo invalido: " + id.getText());
							atribHelper(id.getText());
							
						}
					else {
						throw new NoViableAltException(LT(1), getFilename());
					}
					}
					}
					match(PV);
				}
				else {
					if ( _cnt21>=1 ) { break _loop21; } else {throw new NoViableAltException(LT(1), getFilename());}
				}
				
				_cnt21++;
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_5);
		}
	}
	
	public final void calcExpr() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			calcular = true;
			expr();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_7);
		}
	}
	
	public final void noCalc() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			match(AP);
			expr();
			match(FP);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_7);
		}
	}
	
	public final void leitura() throws RecognitionException, TokenStreamException {
		
		Token  id = null;
		
		try {      // for error handling
			match(LITERAL_leia);
			cmd = new CmdLeitura();
			{
			id = LT(1);
			match(ID);
			}
			match(PV);
			
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
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_5);
		}
	}
	
	public final void escrita() throws RecognitionException, TokenStreamException {
		
		Token  id = null;
		Token  texto = null;
		
		try {      // for error handling
			match(LITERAL_escreva);
			cmd = new CmdEscrita();
			{
			switch ( LA(1)) {
			case ID:
			{
				id = LT(1);
				match(ID);
				
				if (symbolTable.get(id.getText()) == null) 
				throw new RuntimeException("Variavel nao declarada: " + id.getText());
				else 
				conteudo = id.getText();
				
				break;
			}
			case STRING:
			{
				texto = LT(1);
				match(STRING);
				conteudo = texto.getText();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(PV);
			
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
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_5);
		}
	}
	
	public final void comandoIf() throws RecognitionException, TokenStreamException {
		
		Token  id = null;
		Token  oprel = null;
		Token  id2 = null;
		
		try {      // for error handling
			match(LITERAL_se);
			match(AP);
			{
			switch ( LA(1)) {
			case ID:
			case INT:
			case REAL:
			{
				{
				switch ( LA(1)) {
				case ID:
				{
					id = LT(1);
					match(ID);
					
					if (symbolTable.get(id.getText()) == null)
					throw new RuntimeException("Variavel nao declarada: " + id.getText());
					else 
					conteudo = id.getText();
					
					break;
				}
				case INT:
				{
					match(INT);
					conteudo = LT(0).getText();
					break;
				}
				case REAL:
				{
					match(REAL);
					conteudo = LT(0).getText();
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				{
				switch ( LA(1)) {
				case OPREL:
				{
					oprel = LT(1);
					match(OPREL);
					conteudo += oprel.getText();
					break;
				}
				case EQ:
				{
					match(EQ);
					match(EQ);
					conteudo += "==";
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				{
				switch ( LA(1)) {
				case ID:
				{
					id2 = LT(1);
					match(ID);
					
					if (symbolTable.get(id2.getText()) == null)
					throw new RuntimeException("Variavel nao declarada: " + id2.getText());
					else 
					conteudo += id2.getText();
					
					break;
				}
				case INT:
				{
					match(INT);
					conteudo += LT(0).getText();
					break;
				}
				case REAL:
				{
					match(REAL);
					conteudo += LT(0).getText();
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				break;
			}
			case BOOLEAN:
			{
				match(BOOLEAN);
				conteudo = LT(0).getText();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			
			cmdFlag = 1;
			cmd = new CmdIf();
			((CmdIf) cmd).setLogicalExpr(conteudo);
			pilhaCmd.push(cmd);
			
			match(FP);
			match(LITERAL_entao);
			corpo();
			{
			switch ( LA(1)) {
			case LITERAL_senao:
			{
				match(LITERAL_senao);
				
				CmdIf tmp = (CmdIf)pilhaCmd.getTopo(); 
				tmp.changeMode(CmdIf.ELSE_MODE);
				
				corpo();
				break;
			}
			case LITERAL_fimSe:
			{
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			
			Comando cmd = pilhaCmd.pop();              
			if (pilhaCmd.isEmpty()){
			programa.addCommand(cmd);
			} else {
			CmdIf tmp = (CmdIf)pilhaCmd.getTopo();
			tmp.addCommand(cmd);
			}
			
			
			match(LITERAL_fimSe);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_5);
		}
	}
	
	public final void comandoWhile() throws RecognitionException, TokenStreamException {
		
		Token  id = null;
		Token  oprel = null;
		Token  id2 = null;
		
		try {      // for error handling
			match(LITERAL_enquanto);
			match(AP);
			{
			switch ( LA(1)) {
			case ID:
			case INT:
			case REAL:
			{
				{
				switch ( LA(1)) {
				case ID:
				{
					id = LT(1);
					match(ID);
					
					if (symbolTable.get(id.getText()) == null)
					throw new RuntimeException("Variavel nao declarada: " + id.getText());
					else 
					conteudo = id.getText();
					
					break;
				}
				case INT:
				{
					match(INT);
					conteudo = LT(0).getText();
					break;
				}
				case REAL:
				{
					match(REAL);
					conteudo = LT(0).getText();
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				{
				switch ( LA(1)) {
				case OPREL:
				{
					oprel = LT(1);
					match(OPREL);
					conteudo += oprel.getText();
					break;
				}
				case EQ:
				{
					match(EQ);
					match(EQ);
					conteudo += "==";
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				{
				switch ( LA(1)) {
				case ID:
				{
					id2 = LT(1);
					match(ID);
					
					if (symbolTable.get(id2.getText()) == null) 
					throw new RuntimeException("Variavel nao declarada: " + id2.getText());
					else 
					conteudo += String.valueOf(symbolTable.get(id2.getText()).getValor());
					
					break;
				}
				case INT:
				{
					match(INT);
					conteudo += LT(0).getText();
					break;
				}
				case REAL:
				{
					match(REAL);
					conteudo += LT(0).getText();
					break;
				}
				default:
				{
					throw new NoViableAltException(LT(1), getFilename());
				}
				}
				}
				break;
			}
			case BOOLEAN:
			{
				match(BOOLEAN);
				conteudo = LT(0).getText();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			
			cmdFlag = 2;
			cmd = new CmdWhile();
			((CmdWhile) cmd).setLogicalExpr(conteudo);
			pilhaCmd.push(cmd);
			
			match(FP);
			match(LITERAL_entao);
			corpo();
			
			Comando cmd = pilhaCmd.pop();
			if (pilhaCmd.isEmpty()){
			programa.addCommand(cmd);
			} else {
			Comando tmp = pilhaCmd.getTopo();
			((CmdIf)tmp).addCommand(cmd);
			}
			
			
			match(LITERAL_fimEnq);
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_5);
		}
	}
	
	public final void cmdImprimeMatriz() throws RecognitionException, TokenStreamException {
		
		Token  id = null;
		
		try {      // for error handling
			match(LITERAL_imprimematriz);
			{
			id = LT(1);
			match(ID);
			}
			match(PV);
			
			
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
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_5);
		}
	}
	
	public final void cmdSomaMatrizes() throws RecognitionException, TokenStreamException {
		
		Token  id = null;
		Token  id2 = null;
		
		try {      // for error handling
			match(LITERAL_somamatrizes);
			{
			id = LT(1);
			match(ID);
			}
			match(VIRG);
			{
			id2 = LT(1);
			match(ID);
			}
			match(PV);
			
			
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
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_5);
		}
	}
	
	public final void cmdAdicionaNum() throws RecognitionException, TokenStreamException {
		
		Token  id = null;
		Token  id2 = null;
		
		try {      // for error handling
			match(LITERAL_adicionanum);
			{
			id = LT(1);
			match(ID);
			
			if(symbolTable.get(id.getText()).getTipo() != 5 && symbolTable.get(id.getText()).getTipo() != 6 )
			throw new RuntimeException(id.getText() + " não é uma matriz");
			else{
			conteudo = id.getText();
			conteudo += "|";}
			
			}
			match(VIRG);
			{
			switch ( LA(1)) {
			case ID:
			{
				id2 = LT(1);
				match(ID);
				
				if(symbolTable.get(id2.getText()).getTipo() != 1 && symbolTable.get(id2.getText()).getTipo() != 2)
				throw new RuntimeException(id2.getText() + " não é um inteiro");                 
				else{
				conteudo += id2.getText();
				}
				
				break;
			}
			case INT:
			{
				match(INT);
				conteudo += LT(0).getText();
				break;
			}
			case REAL:
			{
				match(REAL);
				conteudo += LT(0).getText();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(PV);
			
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
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_5);
		}
	}
	
	public final void cmdMultMatriz() throws RecognitionException, TokenStreamException {
		
		Token  id = null;
		Token  id2 = null;
		
		try {      // for error handling
			match(LITERAL_multmatriz);
			{
			id = LT(1);
			match(ID);
			
			if(symbolTable.get(id.getText()).getTipo() != 5 && symbolTable.get(id.getText()).getTipo() != 6 )
			throw new RuntimeException(id.getText() + " não é uma matriz");
			else{
			conteudo = id.getText();
			conteudo += "|";}
			
			}
			match(VIRG);
			{
			switch ( LA(1)) {
			case ID:
			{
				id2 = LT(1);
				match(ID);
				
				if(symbolTable.get(id2.getText()).getTipo() != 1 && symbolTable.get(id2.getText()).getTipo() != 2)
				throw new RuntimeException(id2.getText() + " não é um inteiro");                 
				else{
				conteudo += id2.getText();
				}
				
				break;
			}
			case INT:
			{
				match(INT);
				conteudo += LT(0).getText();
				break;
			}
			case REAL:
			{
				match(REAL);
				conteudo += LT(0).getText();
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
			match(PV);
			
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
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_5);
		}
	}
	
	public final void expr() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			term();
			if(!calcular) result += LT(0).getText(); else resultCalc2 = resultCalc1;
			exprl();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_8);
		}
	}
	
	public final void term() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			fator();
			
			
			if (symbolTable.get(LT(0).getText()) == null)
			resultCalc1 = Double.parseDouble(LT(0).getText()); 
			else
			resultCalc1 = Double.valueOf(symbolTable.get(LT(0).getText()).getValor().toString());
			
			
			terml();
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_9);
		}
	}
	
	public final void exprl() throws RecognitionException, TokenStreamException {
		
		Token  operador = null;
		
		try {      // for error handling
			{
			_loop55:
			do {
				if ((LA(1)==OPARIT1)) {
					operador = LT(1);
					match(OPARIT1);
					
					if (!calcular) 
					result += operador.getText();
					
					term();
					
					if(!calcular)
					result += LT(0).getText();
					else {
					if(operador.getText().equals("+"))
					resultCalc2+=resultCalc1;
					else 
					resultCalc2-=resultCalc1;
					}
				}
				else {
					break _loop55;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_8);
		}
	}
	
	public final void fator() throws RecognitionException, TokenStreamException {
		
		
		try {      // for error handling
			{
			switch ( LA(1)) {
			case ID:
			{
				match(ID);
				if(!calcular){
				int tipoDoTermo = symbolTable.get(LT(0).getText()).getTipo();
				if(tipoDoTermo > tipoExpr)
				tipoExpr = tipoDoTermo;}
				break;
			}
			case INT:
			{
				match(INT);
				if(!calcular)
				if(Variavel.INT > tipoExpr)
				tipoExpr = Variavel.INT;
				break;
			}
			case REAL:
			{
				match(REAL);
				if(!calcular)
				if(Variavel.REAL > tipoExpr)
				tipoExpr = Variavel.REAL;
				break;
			}
			case STRING:
			{
				match(STRING);
				if(!calcular)
				if(Variavel.STRING > tipoExpr)
				tipoExpr = Variavel.STRING;
				break;
			}
			default:
			{
				throw new NoViableAltException(LT(1), getFilename());
			}
			}
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_10);
		}
	}
	
	public final void terml() throws RecognitionException, TokenStreamException {
		
		Token  operador = null;
		
		try {      // for error handling
			{
			_loop59:
			do {
				if ((LA(1)==OPARIT2)) {
					operador = LT(1);
					match(OPARIT2);
					
					if(!calcular)
					result += operador.getText();
					
					fator();
					
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
					
				}
				else {
					break _loop59;
				}
				
			} while (true);
			}
		}
		catch (RecognitionException ex) {
			reportError(ex);
			recover(ex,_tokenSet_9);
		}
	}
	
	
	public static final String[] _tokenNames = {
		"<0>",
		"EOF",
		"<2>",
		"NULL_TREE_LOOKAHEAD",
		"\"programa\"",
		"\"fimPrograma\"",
		"\"declare\"",
		"\"int\"",
		"ID",
		"VIRG",
		"PV",
		"\"real\"",
		"\"texto\"",
		"\"array\"",
		"\"matriz\"",
		"\"inicio\"",
		"\"fim\"",
		"EQ",
		"STRING",
		"INT",
		"REAL",
		"ARRAY",
		"MATRIZ",
		"\"leia\"",
		"\"escreva\"",
		"\"se\"",
		"AP",
		"OPREL",
		"BOOLEAN",
		"FP",
		"\"entao\"",
		"\"senao\"",
		"\"fimSe\"",
		"\"enquanto\"",
		"\"fimEnq\"",
		"\"imprimematriz\"",
		"\"somamatrizes\"",
		"\"adicionanum\"",
		"\"multmatriz\"",
		"OPARIT1",
		"OPARIT2",
		"WS",
		"REAL_OU_INT",
		"PF",
		"ASPAS",
		"AC",
		"FC",
		"AB",
		"FB"
	};
	
	private static final long[] mk_tokenSet_0() {
		long[] data = { 30784L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
	private static final long[] mk_tokenSet_1() {
		long[] data = { 2L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
	private static final long[] mk_tokenSet_2() {
		long[] data = { 63552L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
	private static final long[] mk_tokenSet_3() {
		long[] data = { 524044730624L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
	private static final long[] mk_tokenSet_4() {
		long[] data = { 23622320160L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
	private static final long[] mk_tokenSet_5() {
		long[] data = { 524044796160L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
	private static final long[] mk_tokenSet_6() {
		long[] data = { 1835264L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
	private static final long[] mk_tokenSet_7() {
		long[] data = { 1024L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
	private static final long[] mk_tokenSet_8() {
		long[] data = { 536871936L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
	private static final long[] mk_tokenSet_9() {
		long[] data = { 550292685824L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());
	private static final long[] mk_tokenSet_10() {
		long[] data = { 1649804313600L, 0L};
		return data;
	}
	public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());
	
	}
