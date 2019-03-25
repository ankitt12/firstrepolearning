/*this is first part its optional or if we wnat to write c code we have to include special symbol %{ %}*/
%{
#include<stdio.h>
#include<string.h>
int word_count = 0;
int line_count = 0;
long unsigned char_count = 0;
 /*string temp;*/
%}

/*RE*/
/*word [' '\t]+this will not work what if i start with a space*/
word [a-zA-Z0-9!@#$%^&*()-+]+
eol \n
/*char [^\n\t]*/

%%
 /*Rules and actions associated*/
{eol} line_count++;
{word} {	
			
			char_count += strlen(yytext);
			word_count++;
					}
. 		;



%%
int main()
{
	yyin = fopen("temp.txt","r");
	yylex();
	
	printf("Word Count%d" , word_count);
	printf("\nLine Count%d" , line_count);
	printf("\nChar Count%lu\n" , char_count);
	
	yywrap();
}
int yywrap()
{
	return 1;
}


