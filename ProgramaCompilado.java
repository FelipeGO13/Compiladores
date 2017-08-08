import java.util.Scanner;

public class ProgramaCompilado {
public static void main(String args[]){
Scanner scan = new Scanner (System.in);

int a, b, f, g;
double c, e;
String d;
d = "Teste";
a = 10;
b = 1000;
c = 11.2;
e = 42.9;
int [] x = {1,2,3};
int [][] y = {{1,2,3},{4,5,6}};
int [][] z = {{1,1,1},{2,2,2}};
System.out.println("Testeasd");
System.out.println(a);
System.out.println("Teste metodo para impressao de array ou matriz");
for (int i = 0; i < y.length; i++){
for (int j = 0; j < y[0].length; j++){
System.out.print(y[i][j] + " ");
}
System.out.println(" ");
}
System.out.println("Teste metodo para soma de array ou matriz");
for (int i = 0; i < y.length; i++){
for (int j = 0; j < z[0].length; j++){
System.out.print(y[i][j] + z[i][j] + " ");
}
System.out.println(" ");
}
System.out.println("Teste metodo para adicao de um numero a um array ou matriz");
for (int i = 0; i < x.length; i++){
System.out.print(x[i] + 50000 + " ");
}
 System.out.println();
System.out.println("Teste metodo para multiplicacao de um numero a um array ou matriz");
for (int i = 0; i < z.length; i++){
for (int j = 0; j < z[0].length; j++){
System.out.print(z[i][j] * 1.5 + " ");
}
System.out.println(' ');
}
System.out.println("Teste estrutura if else com operacao relacional como condicao");
if (3<b){
System.out.println("Teste If");
} else {
System.out.println("Teste Else");
g = 2;
}
System.out.println("Teste estrutura if else com valor booleano como condicao");
if (true){
System.out.println("Teste If");
}
System.out.println("Teste estrutura while com operacao relacional como condicao");
while (a>3){
System.out.println("Teste While");
f = 4;
a = a-1;
}
a = scan.nextInt();
System.out.println(a);
d = scan.next();
System.out.println(d);
}
}