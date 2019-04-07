%{
extern int yyerror();
#include "y.tab.h"
%}

%%
"while"					return WHILE;
"("					return OB;
")"					return CB;
"<"|">"|"=="|"<="|">="  		return CO;
[0-9]+					return NUM;
"{"					return OP;
"}"					return CP;
"="					return EO;
\n					return NL;
";"					return SC;
"if"					return IF;
"else"					return ELSE;
[a-zA-Z]+[a-zA-Z0-9]*"++"		return INC;
"for"					return FOR;
"int"					return INT;
[a-zA-Z]+[a-zA-Z0-9]*			return ID;
.					{}

%%

extern int yywrap()
{
return 1;
}
