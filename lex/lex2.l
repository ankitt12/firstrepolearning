%{
	#include<stdio.h>
	#include<string.h>
	
	struct symtab{
	char name[20];
	char type[20];
	int lineno;
	}symtab[50];
	
	int flag;
	char temp[30];
	int lineno;
	int count = 0;
	void insert();
	void display();
%}
newline [\n]
comment [' '\t]*."//".*
ignore "! @ # $ % ^ & * ( ) - = + * / { } ; , :"
doublequotes ["]..*.["]
package "util"
inbuiltf "main(".*
kw "import"|"java"|"static"|"return"|"public"|"new"|"void"|"class"
/* id1 is not a identifier*/
class [A-Z].[a-zA-Z0-9]*
 /*id1 {digit}({letter}|{digit})*/
dt "int"|"float"|"double"|"char"|"String"

array [a-z]+.[0-9a-zA-Z]*."[]"
id1 [0-9].[a-zA-Z0-9]+  
id [a-z].[a-zA-Z0-9]*

udf [a-z].[a-zA-Z0-9]+.[(]
/*semicolon ";"*/
anythingelse .

%%

 /* insert only identifier name and its data type function name and whether its udf or inbuil and objects then which type of class it is*/ 
{doublequotes} ;
{package} printf("%s : Package%d\n", yytext, yylineno);
{dt} {printf("%s : Datatype%d\n", yytext, yylineno);}

{kw} {printf("%s : Keyword %d\n", yytext, yylineno);}
{newline}    yylineno++;

{comment} printf("comment %s%d\n", yytext,yylineno); 
{class} {
			strcpy(temp,"Class");	
			insert();
		}
{ignore} ;
{array} {
			strcpy(temp,"Array");	
			insert();
		}
 
{inbuiltf} {
			strcpy(temp,"Inbuilt_F");
			insert();
	    }
	    
{udf} {
			strcpy(temp,"User_Defined_F");
			insert();
	  }
	  
{id1} printf("\n%s : Invalid Identifier%d ", yytext,yylineno);

{id} {
			strcpy(temp,"Identifier");
			insert();
	}

{anythingelse} ;

%%

void insert()
{
 flag = 0;
 for(int i =0 ; i < count ;i++)
 {
 	if(strcmp(symtab[i].name , yytext) == 0)
 		flag = 1;
 }
 
 if(flag == 0)
 {
 	symtab[count].lineno = yylineno;
 	strcpy(symtab[count].name, yytext);
 	strcpy(symtab[count].type, temp);
 	count++;
 }
}
void display()
{
	printf("   NAME         TYPE                    LINENO  ");
	for(int i =0 ;i < count ;i++)
	{
		printf("\n   %s         %s     %d  \n",symtab[i].name, symtab[i].type, symtab[i].lineno);
	}
}
int main()
{
yyin = fopen("temp2.java","r");
yylex();
printf("\n###############SYMBOL TABLE##############\n");
display();
yywrap();
}

int yywrap()
{
return 1;
}


