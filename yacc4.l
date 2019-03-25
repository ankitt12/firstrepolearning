%{
extern int yyerror();
#include "y.tab.h"
%}

%% 
"(" return OB;
"," return COMMA;
";" return SC;
[a-z0-9] return LITERAL;
[+-/*/] return OPERATOR;
 "==" | "<=" | "<" | ">" | ">=" | "!="  return COMPOP;
[a-zA-Z]+[a-zA-Z0-9]* return ID;
. ;
%%

extern int yywrap()
{
return 1;
}

