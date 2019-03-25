%{
#include <stdio.h>
extern int yylex();
extern int yywrap();
%}

%token ID BUILTIN GARBAGE SC DT COMMA NL 

%%

start: BUILTIN varlist SC NL {printf("VALID\n");}

varlist: varlist COMMA ID | ID {};
%%

int yyerror(char * str)
{printf("%s",str);
return 0;}

int main()
{
	yyparse();
}
