%{
#include<stdio.h>

int word_count = 0;
int line_count = 0;
int char_count = 0;

%}

word [' '\t]+
eol \n
char [^\n\t]

%%
	  
{eol} {
		line_count++;
		word_count++;
		}
{word} {
		word_count++;
	   }
{char} {
		char_count++;
		}


%%
int main()
{
	yyin = fopen("temp.txt","r");
	yylex();
	
	printf("Word Count%d" , word_count);
	printf("\nLine Count%d" , line_count);
	printf("\nChar Count%d\n" , char_count);
	
	yywrap();
}
int yywrap()
{
	return 1;
}


