public class MainClass{
    public static void main(String args[]) {
        try{
            MeuLexico lexer  = new MeuLexico(System.in);
            MeuParser parser = new MeuParser(lexer);
            parser.init();
            parser.programa();
            
        } catch(Exception ex){
            System.err.println("Um erro foi encontrado: ");
            ex.printStackTrace();
        }
    }
}