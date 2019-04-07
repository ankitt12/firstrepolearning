%{
#include<stdio.h>
extern int yylex();
extern int yywrap();
%}

%token ID BUILTIN SC DT COMMA NL
%%

start:BUILTIN varlist SC NL {printf("VALID");}
|
varlist:varlist COMMA ID|ID  {}  // a,b,c -> varlist,b,c [since varlist: ID] -> varlist,c [since varlist: varlist,ID] -> varlist [since varlist: varlist,ID]

%%

int yyerror(char * str)
{
printf("%s",str);}

int main()
{
yyparse();
}
