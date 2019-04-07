%{
#include<stdio.h>
extern int yylex();
extern int yywrap();
%}

%token WHILE OB CB OP CP NUM ID EO CO NL SC IF ELSE INC INT FOR
%%

//WHILE LOOP
 
S: WHILE OB COND CB OP EXP CP NL                             {printf("Valid Loop");}
COND: BID CO BID|ID|NUM		                              {}
EXP: ID EO BID SC  		                              {}
BID: ID|NUM                  	                              {}
 
//IF ELSE
/*
S: IF OB COND CB OP MEXP CP ELSE OP MEXP CP NL                {printf("Valid If Else Statements");}
COND: BID CO BID					      {}
MEXP: MEXP EXP|EXP	                                      {}
EXP: ID EO BID SC  		                              {}
BID: ID|NUM                  	                              {}
*/
//FOR LOOP
/*
S: FOR OB INT ID EO NUM SC COND SC INC CB OP MEXP CP NL       {printf("Valid Loop");}
COND: BID CO BID|ID|NUM					      {}
MEXP: MEXP EXP|EXP		                              {}
EXP:  ID EO BID SC	                                      {}
BID: ID|NUM                  	                              {}
*/
%%

int yyerror(char * str)
{
printf("%s",str);}

int main()
{
yyparse();
}
