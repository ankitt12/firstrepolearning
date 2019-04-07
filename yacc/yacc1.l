%{
extern int yyerror();
#include "y.tab.h"
%}

%%
"int"|"char"	return BUILTIN;
","	return COMMA;
";"	return SC;
\n	return NL; 
[a-zA-Z]+[a-zA-Z0-9]*	return ID;
.	{}

%%

extern int yywrap()
{
return 1;
}
