%{
#include <stdio.h>
extern int yylex();
extern int yywrap();
%}

%token ID SC OB COMMA OPERATOR COMPOP LITERAL

%%

start: 'while' OB condition ')' '{' exp ';' '}' {printf("VALID\n");};

condition: LITERAL COMPOP LITERAL | LITERAL {};

exp: ID '=' ID OPERATOR ID {};
%%

void yyerror(char * str)
{printf("%s",str);}

int main()
{
	yyparse();
}
