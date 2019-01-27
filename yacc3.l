%{
	#include<stdio.h>
	#include<string.h>
	struct symtb{
	char name[20];
	char type[20];
	}symtab[10];
	
	int count = 0;
	void insert();
	void display();
%}

letter [a-zA-Z]*
digit [0-9]*
symbolf "()"
symbola "[]"
id1 {digit}({letter}|{digit})
id {letter}({letter}|{digit})
dt "int"|"float"|"double"|"char"|"String"

kw "main"|"args"|"return"
inbuiltf {kw}{symbolf}
udf {letter}({letter}|{digit}){symbolf}
array {id}{symbola}
semicolon ";"


%%


{dt} {printf("\n%s : Datatype", yytext);}
{array} {printf("\n%s : Array", yytext);}
{kw} {printf("\n%s : Keyword", yytext);}
{inbuiltf} {printf("\n%s : In Built Function ", yytext);}
{udf} {printf("\n%s : User Defined Function", yytext);}
{id1} {printf("\n%s : Invalid Identifier ", yytext);}
{id} {strcpy(temp, yytext); insert();}
{semicolon} {printf("\n%s : Semicolon", yytext);}
%%

insert()
{
 for(int i =0 ; i < count ;i++)
 {
 	if(strcmp(symtab[i].name , yytext) == 0)
 		flag = 1;
 }
 
 if(flag != 1)
 {
 	strcpy(symtab[count].name, yytext);
 	strcpy(symtab[count].type, temp);
 	count++;
 }
}

main()
{
yyin = fopen("temp2.java","r");
yylex();
printf("\nSYMBOL TABLE");

yywrap();
}

int yywrap()
{
return 1;
}


